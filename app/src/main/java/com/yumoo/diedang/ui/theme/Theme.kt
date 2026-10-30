package com.yumoo.diedang.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ============================================================
// 应用外观（固定深色像素风，不再跟随系统动态取色）
// ============================================================

val UpDownColors = darkColorScheme(
    primary = Color(0xFF6200EE),
    secondary = Color(0xFF3700B3),
    background = Color(0xFF1A1A1A),
    surface = Color(0xFF1A1A1A),
    surfaceVariant = Color(0xFF2A2A2A),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

/** 像素字体；字体文件缺失时退回等宽字体，保证不崩。 */
val PixelFont: FontFamily = try {
    FontFamily(Font(com.yumoo.diedang.R.font.pixel, FontWeight.Normal))
} catch (_: Exception) {
    FontFamily.Monospace
}

@Composable
fun UpDownTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = UpDownColors,
        typography = Typography(
            bodyMedium = TextStyle(fontFamily = PixelFont, fontSize = 14.sp, color = Color.White)
        ),
        content = content
    )
}
