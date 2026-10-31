package com.yumoo.diedang.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.yumoo.diedang.settings.FontStyleOption
import com.yumoo.diedang.settings.ThemeStyle

// ============================================================
// 应用外观
//
// 三件事：色调（深色 / 浅色 / 卡其护眼 / 咖啡，可跟随系统）、
// 正文字体、正文字号缩放。全部通过 CompositionLocal 下发给界面，
// 界面里不再出现写死的颜色。
// ============================================================

/** 像素字体；字体文件缺失时退回等宽字体，保证不崩。 */
val PixelFont: FontFamily = try {
    FontFamily(Font(com.yumoo.diedang.R.font.pixel, FontWeight.Normal))
} catch (_: Exception) {
    FontFamily.Monospace
}

/** 当前正文字体。 */
val LocalStoryFont = staticCompositionLocalOf<FontFamily> { PixelFont }

/** 当前正文字号缩放（1.0 = 标准）。 */
val LocalStoryScale = staticCompositionLocalOf { 1f }

private val DarkScheme = darkColorScheme(
    primary = Color(0xFF7C4DFF),
    onPrimary = Color.White,
    secondary = Color(0xFFB39DDB),
    background = Color(0xFF101010),
    onBackground = Color(0xFFF2F2F2),
    surface = Color(0xFF1A1A1A),
    onSurface = Color(0xFFF2F2F2),
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = Color(0xFFBDBDBD),
    outline = Color(0xFF3D3D3D),
    error = Color(0xFFCF6679)
)

private val LightScheme = lightColorScheme(
    primary = Color(0xFF5B4B8A),
    onPrimary = Color.White,
    secondary = Color(0xFF7E6BA8),
    background = Color(0xFFF6F4F0),
    onBackground = Color(0xFF1B1B1B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1B1B1B),
    surfaceVariant = Color(0xFFE8E4DC),
    onSurfaceVariant = Color(0xFF4A4A4A),
    outline = Color(0xFFC9C3B8),
    error = Color(0xFFB3261E)
)

/** 卡其：暖黄护眼，浅色系的纸感底色。 */
private val KhakiScheme = lightColorScheme(
    primary = Color(0xFF8A6B43),
    onPrimary = Color(0xFFFFF8E7),
    secondary = Color(0xFFA98B5F),
    background = Color(0xFFF3E9D2),
    onBackground = Color(0xFF3B3021),
    surface = Color(0xFFEDE0C4),
    onSurface = Color(0xFF3B3021),
    surfaceVariant = Color(0xFFE3D3B0),
    onSurfaceVariant = Color(0xFF5B4B34),
    outline = Color(0xFFC9B693),
    error = Color(0xFF9C3D2E)
)

/** 咖啡：深暖色，夜里看不刺眼。 */
private val CoffeeScheme = darkColorScheme(
    primary = Color(0xFFC79A62),
    onPrimary = Color(0xFF231A12),
    secondary = Color(0xFF9C7B52),
    background = Color(0xFF221A14),
    onBackground = Color(0xFFF0E2CE),
    surface = Color(0xFF2C221A),
    onSurface = Color(0xFFF0E2CE),
    surfaceVariant = Color(0xFF3B2E23),
    onSurfaceVariant = Color(0xFFCDB99F),
    outline = Color(0xFF554334),
    error = Color(0xFFD98070)
)

fun fontFamilyOf(option: FontStyleOption): FontFamily = when (option) {
    FontStyleOption.PIXEL -> PixelFont
    FontStyleOption.SANS -> FontFamily.SansSerif
    FontStyleOption.SERIF -> FontFamily.Serif
}

@Composable
fun UpDownTheme(
    theme: ThemeStyle = ThemeStyle.DARK,
    font: FontStyleOption = FontStyleOption.PIXEL,
    scale: Float = 1f,
    content: @Composable () -> Unit
) {
    val systemDark = isSystemInDarkTheme()
    val scheme = when (theme) {
        ThemeStyle.FOLLOW_SYSTEM -> if (systemDark) DarkScheme else LightScheme
        ThemeStyle.DARK -> DarkScheme
        ThemeStyle.LIGHT -> LightScheme
        ThemeStyle.KHAKI -> KhakiScheme
        ThemeStyle.COFFEE -> CoffeeScheme
    }
    val family = fontFamilyOf(font)

    // 状态栏图标：浅色背景用深色图标，否则看不清
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? android.app.Activity)?.window ?: return@SideEffect
            val controller = WindowCompat.getInsetsController(window, view)
            val lightBackground = scheme.background.luminance() > 0.5f
            controller.isAppearanceLightStatusBars = lightBackground
            controller.isAppearanceLightNavigationBars = lightBackground
            // 窗口底色跟着主题走：系统栏区域在部分机型上取的是 windowBackground，
            // 不刷成主题色就会留一条与主题无关的黑边。
            @Suppress("DEPRECATION")
            run {
                window.statusBarColor = scheme.background.toArgb()
                window.navigationBarColor = scheme.background.toArgb()
                window.setBackgroundDrawable(
                    android.graphics.drawable.ColorDrawable(scheme.background.toArgb())
                )
            }
        }
    }

    CompositionLocalProvider(
        LocalStoryFont provides family,
        LocalStoryScale provides scale
    ) {
        MaterialTheme(
            colorScheme = scheme,
            typography = Typography(
                bodyMedium = TextStyle(
                    fontFamily = family,
                    fontSize = (14 * scale).sp,
                    color = scheme.onBackground
                )
            ),
            content = content
        )
    }
}
