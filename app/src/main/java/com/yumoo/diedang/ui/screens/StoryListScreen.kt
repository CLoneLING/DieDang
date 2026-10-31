package com.yumoo.diedang.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.engine.Story
import com.yumoo.diedang.save.SaveRepository
import com.yumoo.diedang.ui.components.SimpleConfirmDialog
import com.yumoo.diedang.ui.theme.LocalStoryFont

/** 剧本列表：展示全部已注册剧本 + 存档状态 + 结局收集进度。 */
@Composable
fun StoryListScreen(
    stories: List<Story>,
    saves: SaveRepository,
    onPlay: (Story, Boolean) -> Unit, // story, continueSaved
    onBack: () -> Unit
) {
    BackHandler { onBack() }
    var confirmClearId by remember { mutableStateOf<String?>(null) }

    val hasSave = remember(stories) { stories.associate { it.id to saves.hasProgress(it) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) {
                Text("← 返回", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
            }
            Spacer(Modifier.width(8.dp))
            Text("📚 剧本列表", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 22.sp, color = MaterialTheme.colorScheme.onSurface))
        }
        Spacer(Modifier.height(12.dp))
        Text(
            "共 ${stories.size} 个剧本",
            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Spacer(Modifier.height(16.dp))

        if (stories.isEmpty()) {
            Text("暂无剧本", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
        }

        stories.forEach { story ->
            StoryCard(
                story = story,
                hasSave = hasSave[story.id] == true,
                unlocked = saves.unlockedEndings(story.id),
                onNewGame = { onPlay(story, false) },
                onContinue = { onPlay(story, true) },
                onClear = { confirmClearId = story.id }
            )
            Spacer(Modifier.height(12.dp))
        }
    }

    confirmClearId?.let { id ->
        val story = stories.firstOrNull { it.id == id }
        SimpleConfirmDialog(
            title = "清除存档",
            message = "将删除《${story?.info?.title ?: id}》的进行中进度（已解锁结局保留）。",
            confirmLabel = "清除",
            onConfirm = {
                saves.clearProgress(id)
                confirmClearId = null
            },
            onDismiss = { confirmClearId = null }
        )
    }
}

@Composable
private fun StoryCard(
    story: Story,
    hasSave: Boolean,
    unlocked: Set<String>,
    onNewGame: () -> Unit,
    onContinue: () -> Unit,
    onClear: () -> Unit
) {
    val meta = story.meta
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "《${story.info.title}》",
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                buildList {
                    if (story.info.genre.isNotBlank()) add(story.info.genre)
                    add("v${story.info.version}")
                    add("作者：${story.info.author}")
                }.joinToString(" / "),
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                story.info.description,
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 13.sp, lineHeight = 22.sp, color = MaterialTheme.colorScheme.onSurface)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "场景 ${meta.sceneCount} / 结局 ${meta.endingCount} / 约 ${meta.wordCount} 字",
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "🏁 结局收集 ${unlocked.size}/${meta.endingCount}" +
                    if (meta.hasTimedScene) " / 含限时场景" else "",
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                if (hasSave) {
                    Button(
                        onClick = onContinue,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.height(44.dp)
                    ) { Text("▶ 继续", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)) }
                }
                Button(
                    onClick = onNewGame,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.height(44.dp)
                ) { Text("🆕 新游戏", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)) }
                Spacer(Modifier.weight(1f))
                if (hasSave) {
                    TextButton(onClick = onClear) {
                        Text("清档", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
                    }
                }
            }
        }
    }
}
