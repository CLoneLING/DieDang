package com.yumoo.diedang.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.settings.APP_VERSION
import com.yumoo.diedang.ui.theme.LocalStoryFont

private const val REPO_URL = "https://github.com/CLoneLING/DieDang"

/** 关于页：只有事实信息。 */
@Composable
fun AboutScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            TextButton(onClick = onBack) {
                Text("← 返回", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
            }
            Spacer(Modifier.width(8.dp))
            Text("ℹ️ 关于", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 22.sp, color = MaterialTheme.colorScheme.onSurface))
        }
        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow("名称", APP_NAME)
                InfoRow("版本", APP_VERSION)
                InfoRow("开发者", "YuMoo")
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
                    Text(
                        "仓库：",
                        style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.width(76.dp)
                    )
                    TextButton(onClick = { runCatching { uriHandler.openUri(REPO_URL) } }, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                        Text(
                            REPO_URL.removePrefix("https://"),
                            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, color = MaterialTheme.colorScheme.primary),
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
        Text(
            "$label：",
            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.width(76.dp)
        )
        Text(
            value,
            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, lineHeight = 22.sp, color = MaterialTheme.colorScheme.onSurface),
            modifier = Modifier.weight(1f)
        )
    }
}
