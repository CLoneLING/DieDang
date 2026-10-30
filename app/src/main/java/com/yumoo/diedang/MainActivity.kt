package com.yumoo.diedang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.yumoo.diedang.engine.Story
import com.yumoo.diedang.engine.StoryRegistry
import com.yumoo.diedang.engine.StorySession
import com.yumoo.diedang.engine.newRun
import com.yumoo.diedang.save.SaveRepository
import com.yumoo.diedang.ui.screens.AboutScreen
import com.yumoo.diedang.ui.screens.GameScreen
import com.yumoo.diedang.ui.screens.MenuScreen
import com.yumoo.diedang.ui.screens.StoryListScreen
import com.yumoo.diedang.ui.theme.UpDownTheme

// ============================================================
// 入口
//
// 软件层结构：
//   model/    通用数据模型（GameState / Scene / Option）
//   engine/   剧本契约 + 注册表 + 会话规则（Story / StoryRegistry / StorySession）
//   save/     本地存档（进度 + 结局解锁）
//   ui/       Compose 界面（组件 + 页面）
//
// 剧本文件只在 StoryRegistry 中注册，UI 与引擎不认识任何具体剧本。
// ============================================================

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpDownTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    UpDownApp()
                }
            }
        }
    }
}

private sealed interface Screen {
    data object Menu : Screen
    data object Stories : Screen
    data object About : Screen
    data class Game(val story: Story, val session: StorySession) : Screen
}

@Composable
fun UpDownApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val saves = remember(context) { SaveRepository(context) }
    val stories = StoryRegistry.stories
    // 主菜单的「继续」使用上次游玩的剧本
    val lastStory = remember { saves.lastStoryId?.let { StoryRegistry.find(it) } ?: stories.firstOrNull() }
    val lastStoryHasSave = remember(lastStory?.id) { lastStory?.let { saves.hasProgress(it) } == true }

    var screen by remember { mutableStateOf<Screen>(Screen.Menu) }

    fun play(story: Story, continueSaved: Boolean) {
        saves.lastStoryId = story.id
        val snapshot = if (continueSaved) saves.loadValidated(story) else null
        val session = StorySession(
            story = story,
            initial = snapshot?.state ?: story.newRun()
        )
        screen = Screen.Game(story, session)
    }

    when (val current = screen) {
        Screen.Menu -> MenuScreen(
            lastStoryTitle = if (lastStoryHasSave) lastStory?.info?.title else null,
            onContinue = { lastStory?.let { play(it, true) } },
            onStories = { screen = Screen.Stories },
            onAbout = { screen = Screen.About }
        )

        Screen.Stories -> StoryListScreen(
            stories = stories,
            saves = saves,
            onPlay = { story, cont -> play(story, cont) },
            onBack = { screen = Screen.Menu }
        )

        Screen.About -> AboutScreen(onBack = { screen = Screen.Menu })

        is Screen.Game -> GameScreen(
            session = current.session,
            saves = saves,
            onExitToMenu = { screen = Screen.Stories },
            onRestartStory = { play(current.story, false) }
        )
    }
}
