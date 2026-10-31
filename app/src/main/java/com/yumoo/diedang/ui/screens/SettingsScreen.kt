package com.yumoo.diedang.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.settings.APP_VERSION
import com.yumoo.diedang.settings.AppSettings
import com.yumoo.diedang.settings.FontStyleOption
import com.yumoo.diedang.settings.TextSizeOption
import com.yumoo.diedang.settings.ThemeStyle
import com.yumoo.diedang.ui.theme.LocalStoryFont

/** 设置页：主题 / 字体 / 字号，以及更新入口。改动即时生效并落盘。 */
@Composable
fun SettingsScreen(
    settings: AppSettings,
    onSettingsChange: (AppSettings) -> Unit,
    onCheckUpdate: () -> Unit,
    onBack: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = colors.surfaceVariant, contentColor = colors.onSurface),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text("← 返回", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, color = colors.onSurface))
            }
        }
        Spacer(Modifier.height(16.dp))
        Text(
            "⚙ 设置",
            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 24.sp, color = colors.onBackground)
        )
        Spacer(Modifier.height(24.dp))

        SectionTitle("主题")
        ThemeStyle.values().forEach { option ->
            ChoiceRow(
                label = option.label,
                selected = settings.theme == option,
                onClick = { onSettingsChange(settings.copy(theme = option)) }
            )
        }

        Spacer(Modifier.height(20.dp))
        SectionTitle("字体")
        FontStyleOption.values().forEach { option ->
            ChoiceRow(
                label = option.label,
                selected = settings.font == option,
                onClick = { onSettingsChange(settings.copy(font = option)) }
            )
        }

        Spacer(Modifier.height(20.dp))
        SectionTitle("字号")
        TextSizeOption.values().forEach { option ->
            ChoiceRow(
                label = option.label,
                selected = settings.textSize == option,
                onClick = { onSettingsChange(settings.copy(textSize = option)) }
            )
        }

        Spacer(Modifier.height(28.dp))
        SectionTitle("更新")
        Button(
            onClick = onCheckUpdate,
            colors = ButtonDefaults.buttonColors(containerColor = colors.primary, contentColor = colors.onPrimary),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text(
                "检查更新",
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 15.sp, color = colors.onPrimary)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            "v$APP_VERSION",
            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 11.sp, color = colors.onSurfaceVariant)
        )
        Spacer(Modifier.height(40.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    val colors = MaterialTheme.colorScheme
    Text(
        text,
        style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = colors.primary)
    )
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun ChoiceRow(label: String, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val bg = if (selected) colors.primary else colors.surfaceVariant
    val fg = if (selected) colors.onPrimary else colors.onSurfaceVariant
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = bg, contentColor = fg),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .height(42.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = fg))
            if (selected) {
                Text("✓", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = fg))
            }
        }
    }
}
