package com.yumoo.diedang.settings

import android.content.Context

// ============================================================
// 外观设置：主题色调 / 字体 / 字号
//
// 只存枚举名与序号，读不出来就退回默认值，避免升级后崩溃。
// ============================================================

/** 全应用唯一的版本号来源（与 app/build.gradle.kts 的 versionName 保持一致）。 */
const val APP_VERSION = "2.1.0"

/** 主题色调。跟随系统时在 Theme 里按系统深浅色切换。 */
enum class ThemeStyle(val label: String) {
    FOLLOW_SYSTEM("跟随系统"),
    DARK("深色"),
    LIGHT("浅色"),
    KHAKI("卡其（暖黄护眼）"),
    COFFEE("咖啡")
}

/** 正文字体。像素体是内置 pixel.ttf，其余用系统字体。 */
enum class FontStyleOption(val label: String) {
    PIXEL("像素体"),
    SANS("默认无衬线"),
    SERIF("衬线体")
}

/** 正文字号：整体缩放正文、行高、缩进。 */
enum class TextSizeOption(val label: String, val scale: Float) {
    SMALL("小", 0.88f),
    NORMAL("标准", 1.0f),
    LARGE("大", 1.15f),
    HUGE("特大", 1.3f)
}

data class AppSettings(
    val theme: ThemeStyle = ThemeStyle.DARK,
    val font: FontStyleOption = FontStyleOption.PIXEL,
    val textSize: TextSizeOption = TextSizeOption.NORMAL
)

class SettingsStore(context: Context) {

    private val prefs = context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun load(): AppSettings = AppSettings(
        theme = enumOf(prefs.getString(KEY_THEME, null), ThemeStyle.DARK),
        font = enumOf(prefs.getString(KEY_FONT, null), FontStyleOption.PIXEL),
        textSize = enumOf(prefs.getString(KEY_SIZE, null), TextSizeOption.NORMAL)
    )

    fun save(settings: AppSettings) {
        prefs.edit()
            .putString(KEY_THEME, settings.theme.name)
            .putString(KEY_FONT, settings.font.name)
            .putString(KEY_SIZE, settings.textSize.name)
            .apply()
    }

    private inline fun <reified T : Enum<T>> enumOf(name: String?, fallback: T): T =
        name?.let { runCatching { enumValueOf<T>(it) }.getOrNull() } ?: fallback

    companion object {
        private const val PREFS = "updown_settings"
        private const val KEY_THEME = "theme"
        private const val KEY_FONT = "font"
        private const val KEY_SIZE = "text_size"
    }
}
