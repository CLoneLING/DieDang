package com.yumoo.diedang.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.settings.APP_VERSION
import com.yumoo.diedang.ui.theme.LocalStoryFont

const val APP_NAME = "跌宕"

/** 主菜单：只做导航，不承载任何剧本知识。 */
@Composable
fun MenuScreen(
    lastStoryTitle: String?,
    onContinue: () -> Unit,
    onStories: () -> Unit,
    onAbout: () -> Unit,
    onSettings: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier.fillMaxSize().background(colors.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "📖 $APP_NAME",
                style = TextStyle(
                    fontFamily = LocalStoryFont.current,
                    fontSize = 44.sp,
                    color = colors.onBackground,
                    letterSpacing = 8.sp
                )
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "选择取向型剧情游戏合集",
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = colors.onSurfaceVariant)
            )
            Spacer(Modifier.height(56.dp))

            if (!lastStoryTitle.isNullOrBlank()) {
                PrimaryButton(
                    label = "▶ 继续《$lastStoryTitle》",
                    width = 280.dp,
                    onClick = onContinue
                )
                Spacer(Modifier.height(16.dp))
            }
            PrimaryButton(label = "📚 剧本列表", width = 220.dp, onClick = onStories)
            Spacer(Modifier.height(16.dp))
            SecondaryButton(label = "⚙️ 设置", width = 180.dp, onClick = onSettings)
            Spacer(Modifier.height(12.dp))
            SecondaryButton(label = "ℹ️ 关于", width = 180.dp, onClick = onAbout)

            Spacer(Modifier.height(48.dp))
            Text(
                text = "v$APP_VERSION",
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = colors.outline)
            )
        }
    }
}

@Composable
fun PrimaryButton(label: String, width: Dp, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colors.primary, contentColor = colors.onPrimary),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(width).height(56.dp)
    ) {
        Text(
            label,
            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 16.sp, color = colors.onPrimary),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SecondaryButton(label: String, width: Dp, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colors.surfaceVariant, contentColor = colors.onSurface),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(width).height(44.dp)
    ) {
        Text(label, style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = colors.onSurface))
    }
}
