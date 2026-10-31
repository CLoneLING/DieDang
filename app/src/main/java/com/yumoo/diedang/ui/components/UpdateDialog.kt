package com.yumoo.diedang.ui.components

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.settings.APP_VERSION
import com.yumoo.diedang.ui.theme.LocalStoryFont
import com.yumoo.diedang.update.ApkInstaller
import com.yumoo.diedang.update.UpdateChecker
import com.yumoo.diedang.update.UpdateDownloader
import com.yumoo.diedang.update.UpdateInfo
import com.yumoo.diedang.update.UpdatePermissions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

/**
 * 应用内更新对话框。
 *
 * 流程：查 GitHub Release → 有新版本 → 先过权限门（通知 + 安装未知应用）
 * → 自制下载器下载（通知栏进度）→ 拉起系统安装器。
 * 权限没给齐就不下载，这是用户的明确要求。
 */
private sealed interface Stage {
    data object Checking : Stage
    data object UpToDate : Stage
    data class Available(val info: UpdateInfo) : Stage
    data class Downloading(val info: UpdateInfo, val percent: Int) : Stage
    data class Ready(val file: File) : Stage
    data class Failed(val message: String) : Stage
}

@Composable
fun UpdateDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val colors = MaterialTheme.colorScheme
    val style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, color = colors.onSurface)

    var stage by remember { mutableStateOf<Stage>(Stage.Checking) }
    var retryTarget by remember { mutableStateOf<UpdateInfo?>(null) }
    var notifGranted by remember { mutableStateOf(UpdatePermissions.hasNotificationPermission(context)) }
    var installGranted by remember { mutableStateOf(UpdatePermissions.canInstallPackages(context)) }
    val downloader = remember { UpdateDownloader(context) }

    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted -> notifGranted = granted }

    // 从「安装未知应用」授权页返回后，轮询刷新状态
    LaunchedEffect(Unit) {
        while (true) {
            installGranted = UpdatePermissions.canInstallPackages(context)
            if (!notifGranted) notifGranted = UpdatePermissions.hasNotificationPermission(context)
            delay(700)
        }
    }

    LaunchedEffect(Unit) {
        stage = try {
            val info = UpdateChecker.fetchLatest()
            if (UpdateChecker.isNewer(info.version, APP_VERSION)) Stage.Available(info) else Stage.UpToDate
        } catch (e: Exception) {
            Stage.Failed(e.message ?: "检查更新失败")
        }
    }

    fun startDownload(info: UpdateInfo) {
        if (!notifGranted || !installGranted) return
        retryTarget = info
        stage = Stage.Downloading(info, 0)
        scope.launch {
            try {
                val file = downloader.download(info) { percent ->
                    stage = Stage.Downloading(info, percent)
                }
                downloader.clearNotification()
                stage = Stage.Ready(file)
            } catch (e: Exception) {
                downloader.clearNotification()
                stage = Stage.Failed(e.message ?: "下载失败")
            }
        }
    }

    AlertDialog(
        onDismissRequest = { if (stage !is Stage.Downloading) onDismiss() },
        containerColor = colors.surface,
        titleContentColor = colors.onSurface,
        textContentColor = colors.onSurfaceVariant,
        title = {
            Text("应用更新", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 18.sp, color = colors.onSurface))
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                when (val s = stage) {
                    Stage.Checking -> Text("正在检查最新版本…", style = style)

                    Stage.UpToDate -> Text("已是最新版本 v${APP_VERSION}", style = style)

                    is Stage.Available -> {
                        Text("发现新版本 v${s.info.version}", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 15.sp, color = colors.primary))
                        Spacer(Modifier.height(10.dp))
                        // 权限门放在最上面：没给授权就不进入下载
                        if (!notifGranted || !installGranted) {
                            Text(
                                "下载前需要两项授权：通知权限（显示下载进度）与允许安装未知应用。未授权时不会开始下载。",
                                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = colors.error)
                            )
                            Spacer(Modifier.height(8.dp))
                            if (!notifGranted) {
                                Button(
                                    onClick = { notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) },
                                    modifier = Modifier.fillMaxWidth()
                                ) { Text("① 允许通知", style = style) }
                                Spacer(Modifier.height(6.dp))
                            }
                            if (!installGranted) {
                                Button(
                                    onClick = { UpdatePermissions.openInstallSettings(context) },
                                    modifier = Modifier.fillMaxWidth()
                                ) { Text("② 允许安装应用", style = style) }
                            }
                            Spacer(Modifier.height(10.dp))
                        } else {
                            Text(
                                "授权已完成，可以下载。",
                                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = colors.primary)
                            )
                            Spacer(Modifier.height(10.dp))
                        }

                        val notes = s.info.notes
                            .replace("###", "")
                            .replace("##", "")
                            .replace("**", "")
                            .replace("- ", "· ")
                            .trim()
                        if (notes.isNotEmpty()) {
                            Text(
                                notes.take(420),
                                style = style,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 260.dp)
                                    .verticalScroll(rememberScrollState())
                            )
                        }
                    }

                    is Stage.Downloading -> {
                        Text("正在下载 v${s.info.version}… ${s.percent}%", style = style)
                        Spacer(Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { s.percent / 100f },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    is Stage.Ready -> Text("下载完成（${s.file.length() / 1024 / 1024} MB），点击「立即安装」继续。", style = style)

                    is Stage.Failed -> Text(s.message, style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, color = colors.error))
                }
            }
        },
        confirmButton = {
            when (val s = stage) {
                is Stage.Available -> {
                    val canGo = notifGranted && installGranted
                    Button(
                        onClick = { if (canGo) startDownload(s.info) },
                        enabled = canGo
                    ) { Text("下载并安装", style = style) }
                }

                is Stage.Ready -> Button(onClick = {
                    if (!ApkInstaller.install(context, s.file)) {
                        UpdatePermissions.openInstallSettings(context)
                    }
                }) { Text("立即安装", style = style) }

                is Stage.Failed -> {
                    val target = retryTarget
                    if (target != null) {
                        Button(onClick = { startDownload(target) }) { Text("重试", style = style) }
                    } else {
                        Button(onClick = onDismiss) { Text("关闭", style = style) }
                    }
                }

                Stage.UpToDate -> Button(onClick = onDismiss) { Text("关闭", style = style) }

                else -> Unit
            }
        },
        dismissButton = {
            if (stage !is Stage.Downloading) {
                TextButton(onClick = onDismiss) { Text("取消", style = style) }
            }
        }
    )
}
