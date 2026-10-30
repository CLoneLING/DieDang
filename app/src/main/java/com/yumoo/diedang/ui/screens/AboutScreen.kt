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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.ui.theme.PixelFont

/** 关于页：应用信息 + 引擎能力说明（改版后不再展示单个剧本的硬编码信息）。 */
@Composable
fun AboutScreen(onBack: () -> Unit) {
    BackHandler { onBack() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A1A1A))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            TextButton(onClick = onBack) {
                Text("← 返回", style = TextStyle(fontFamily = PixelFont, fontSize = 14.sp, color = Color.Gray))
            }
            Spacer(Modifier.width(8.dp))
            Text("ℹ️ 关于", style = TextStyle(fontFamily = PixelFont, fontSize = 22.sp, color = Color.White))
        }
        Spacer(Modifier.height(16.dp))

        InfoCard("📱 应用信息") {
            InfoRow("名称", APP_NAME)
            InfoRow("版本", APP_VERSION)
            InfoRow("开发者", "YuMoo")
            InfoRow("简介", "选择取向型剧情游戏合集，可自由制作剧本上传至游戏。")
        }
        Spacer(Modifier.height(16.dp))

        InfoCard("🧩 引擎能力") {
            InfoRow("剧本格式", "Kotlin Scene/Option 列表（JSON 文件剧本开发中）")
            InfoRow("状态系统", "flags 标记 / counters 数值，剧本自由定义")
            InfoRow("条件选项", "option.condition 按状态显示或隐藏")
            InfoRow("限时机制", "场景 timeLimit，归零跳转 timeout 结局")
            InfoRow("存档", "自动保存进度 + 结局收集记录")
        }
        Spacer(Modifier.height(16.dp))

        InfoCard("✍️ 剧本作者须知") {
            Text(
                "剧本只需实现 buildScenes(): List<Scene> 与 getStoryInfo()，"
                    + "并在 StoryRegistry 中注册即可被引擎加载。\n"
                    + "场景文本用空行分段，引擎逐段打字机展示；"
                    + "限时场景需要提供 id = \"timeout\" 的结局场景。",
                style = TextStyle(fontFamily = PixelFont, fontSize = 13.sp, lineHeight = 22.sp, color = Color(0xFFDDDDDD))
            )
        }
    }
}

@Composable
private fun InfoCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = TextStyle(fontFamily = PixelFont, fontSize = 16.sp, color = Color.White))
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) {
        Text(
            "$label：",
            style = TextStyle(fontFamily = PixelFont, fontSize = 13.sp, color = Color.Gray),
            modifier = Modifier.width(76.dp)
        )
        Text(
            value,
            style = TextStyle(fontFamily = PixelFont, fontSize = 13.sp, lineHeight = 22.sp, color = Color.White),
            modifier = Modifier.weight(1f)
        )
    }
}
