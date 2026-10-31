package com.yumoo.diedang.update

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

// ============================================================
// 应用内更新：查 GitHub Release → 自查权限 → 自制下载器 → 拉起安装
//
// 这里不使用系统 DownloadManager，下载、写盘、通知、安装全部自己走，
// 这样没有本地服务器也能发版（正式版就挂在 GitHub Release 上）。
// ============================================================

/** 线上最新版本信息。 */
data class UpdateInfo(
    val version: String,
    val notes: String,
    val apkUrl: String,
    val apkSize: Long,
    val pageUrl: String
)

object UpdateChecker {

    const val OWNER = "CLoneLING"
    const val REPO = "DieDang"
    private const val API = "https://api.github.com/repos/$OWNER/$REPO/releases/latest"

    /** 拉取最新 Release；网络/解析失败时抛出异常，由界面转成提示。 */
    suspend fun fetchLatest(): UpdateInfo = withContext(Dispatchers.IO) {
        val conn = (URL(API).openConnection() as HttpURLConnection).apply {
            connectTimeout = 8000
            readTimeout = 12000
            setRequestProperty("Accept", "application/vnd.github+json")
            setRequestProperty("User-Agent", "DieDang-Android")
        }
        try {
            if (conn.responseCode !in 200..299) {
                throw IllegalStateException("GitHub 返回 ${conn.responseCode}")
            }
            val text = conn.inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(text)
            val tag = json.optString("tag_name").ifBlank {
                throw IllegalStateException("发布信息缺少 tag_name")
            }
            val assets = json.optJSONArray("assets")
            var url = ""
            var size = 0L
            if (assets != null) {
                for (i in 0 until assets.length()) {
                    val a = assets.getJSONObject(i)
                    val name = a.optString("name")
                    if (name.endsWith(".apk", ignoreCase = true)) {
                        url = a.optString("browser_download_url")
                        size = a.optLong("size")
                        break
                    }
                }
            }
            if (url.isBlank()) throw IllegalStateException("该版本没有附带 APK")
            UpdateInfo(
                version = tag.removePrefix("v").removePrefix("V"),
                notes = json.optString("body"),
                apkUrl = url,
                apkSize = size,
                pageUrl = json.optString("html_url")
            )
        } finally {
            conn.disconnect()
        }
    }

    /** 版本号比较：逐段比数字，缺失的段按 0 处理。返回 true 表示 remote 更新。 */
    fun isNewer(remote: String, local: String): Boolean {
        val a = remote.split('.', '-').mapNotNull { it.toIntOrNull() }
        val b = local.split('.', '-').mapNotNull { it.toIntOrNull() }
        for (i in 0 until maxOf(a.size, b.size)) {
            val x = a.getOrElse(i) { 0 }
            val y = b.getOrElse(i) { 0 }
            if (x != y) return x > y
        }
        return false
    }
}

/** 权限前置条件：通知（下载进度要用）与「安装未知应用」。 */
object UpdatePermissions {

    fun hasNotificationPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    fun canInstallPackages(context: Context): Boolean =
        context.packageManager.canRequestPackageInstalls()

    /** 跳到「安装未知应用」授权页。 */
    fun openInstallSettings(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
            Uri.parse("package:${context.packageName}")
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        runCatching { context.startActivity(intent) }
            .onFailure { runCatching { context.startActivity(Intent(Settings.ACTION_SETTINGS)) } }
    }
}

/** 自制下载器：边下边报进度，同时更新通知栏。 */
class UpdateDownloader(private val context: Context) {

    suspend fun download(
        info: UpdateInfo,
        onProgress: (percent: Int) -> Unit
    ): File = withContext(Dispatchers.IO) {
        val dir = File(context.cacheDir, "update").apply { mkdirs() }
        val target = File(dir, "DieDang-${info.version}.apk")
        if (target.exists()) target.delete()

        val conn = (URL(info.apkUrl).openConnection() as HttpURLConnection).apply {
            connectTimeout = 15000
            readTimeout = 30000
            instanceFollowRedirects = true
            setRequestProperty("User-Agent", "DieDang-Android")
        }
        try {
            if (conn.responseCode !in 200..299) {
                throw IllegalStateException("下载失败：HTTP ${conn.responseCode}")
            }
            val total = conn.contentLengthLong.takeIf { it > 0 } ?: info.apkSize
            var done = 0L
            var lastPercent = -1
            notifyProgress(0, "准备下载")
            conn.inputStream.use { input ->
                target.outputStream().use { output ->
                    val buffer = ByteArray(64 * 1024)
                    while (true) {
                        val read = input.read(buffer)
                        if (read <= 0) break
                        output.write(buffer, 0, read)
                        done += read
                        if (total > 0) {
                            val percent = (done * 100 / total).toInt()
                            if (percent != lastPercent) {
                                lastPercent = percent
                                withContext(Dispatchers.Main) { onProgress(percent) }
                                notifyProgress(percent, "正在下载")
                            }
                        }
                    }
                }
            }
            if (target.length() <= 0) throw IllegalStateException("下载内容为空")
            notifyProgress(100, "下载完成")
            target
        } finally {
            conn.disconnect()
        }
    }

    private fun notifyProgress(percent: Int, text: String) {
        runCatching {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                    manager.createNotificationChannel(
                        NotificationChannel(CHANNEL_ID, "应用更新", NotificationManager.IMPORTANCE_LOW)
                    )
                }
            }
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("跌宕更新")
                .setContentText("$text $percent%")
                .setProgress(100, percent, false)
                .setOnlyAlertOnce(true)
                .setOngoing(percent < 100)
                .build()
            manager.notify(NOTIFY_ID, notification)
        }
    }

    fun clearNotification() {
        runCatching {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(NOTIFY_ID)
        }
    }

    companion object {
        const val CHANNEL_ID = "app_update"
        const val NOTIFY_ID = 4102
    }
}

/** 拉起系统安装器（走 FileProvider，Android 7+ 不再暴露 file:// ）。 */
object ApkInstaller {

    fun install(context: Context, apk: File): Boolean {
        if (!settingsCanInstall(context)) return false
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", apk)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/vnd.android.package-archive")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        return runCatching { context.startActivity(intent) }.isSuccess
    }

    fun settingsCanInstall(context: Context): Boolean = context.packageManager.canRequestPackageInstalls()
}
