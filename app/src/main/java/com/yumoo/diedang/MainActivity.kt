package com.yumoo.diedang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import com.yumoo.diedang.engine.Story
import com.yumoo.diedang.engine.StoryRegistry
import com.yumoo.diedang.engine.StorySession
import com.yumoo.diedang.engine.newRun
import com.yumoo.diedang.save.SaveRepository
import com.yumoo.diedang.settings.AppSettings
import com.yumoo.diedang.settings.SettingsStore
import com.yumoo.diedang.ui.components.UpdateDialog
import com.yumoo.diedang.ui.screens.AboutScreen
import com.yumoo.diedang.ui.screens.GameScreen
import com.yumoo.diedang.ui.screens.MenuScreen
import com.yumoo.diedang.ui.screens.SettingsScreen
import com.yumoo.diedang.ui.screens.StoryListScreen
import com.yumoo.diedang.ui.theme.UpDownTheme

// ============================================================
// 入口
//
// 软件层结构：
//   model/    通用数据模型（GameState / Scene / Option）
//   engine/   剧本契约 + 注册表 + 会话规则（Story / StoryRegistry / StorySession）
//   save/     本地存档（进度 + 结局解锁）
//   settings/ 外观设置（主题 / 字体 / 字号）
//   update/   应用内更新（GitHub Release 检测 + 自制下载器 + 安装）
//   ui/       Compose 界面（组件 + 页面）
//
// 剧本文件只在 StoryRegistry 中注册，UI 与引擎不认识任何具体剧本。
// ============================================================

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 允许由应用自己绘制系统栏背景（否则 statusBarColor 会被忽略，
        // 顶部会一直是主题里写死的黑色，换浅色主题时特别显眼）。
        @Suppress("DEPRECATION")
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        setContent {
            val context = LocalContext.current
            val store = remember(context) { SettingsStore(context) }
            var settings by remember { mutableStateOf(store.load()) }

            UpDownTheme(
                theme = settings.theme,
                font = settings.font,
                scale = settings.textSize.scale
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // targetSdk 36 强制 edge-to-edge：这里统一让出状态栏/导航栏，
                    // 否则内容会被状态栏压住（用户反馈的遮挡问题）。
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .windowInsetsPadding(WindowInsets.safeDrawing)
                    ) {
                        UpDownApp(
                            settings = settings,
                            onSettingsChange = {
                                settings = it
                                store.save(it)
                            }
                        )
                    }
                }
            }
        }
    }
}

private sealed interface Screen {
    data object Menu : Screen
    data object Stories : Screen
    data object About : Screen
    data object Settings : Screen
    data class Game(val story: Story, val session: StorySession) : Screen
}

@Composable
fun UpDownApp(
    settings: AppSettings,
    onSettingsChange: (AppSettings) -> Unit
) {
    val context = LocalContext.current
    val saves = remember(context) { SaveRepository(context) }
    val stories = StoryRegistry.stories
    // 主菜单的「继续」使用上次游玩的剧本
    val lastStory = remember { saves.lastStoryId?.let { StoryRegistry.find(it) } ?: stories.firstOrNull() }
    val lastStoryHasSave = remember(lastStory?.id) { lastStory?.let { saves.hasProgress(it) } == true }

    var screen by remember { mutableStateOf<Screen>(Screen.Menu) }
    var showUpdate by remember { mutableStateOf(false) }

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
            onAbout = { screen = Screen.About },
            onSettings = { screen = Screen.Settings }
        )

        Screen.Stories -> StoryListScreen(
            stories = stories,
            saves = saves,
            onPlay = { story, cont -> play(story, cont) },
            onBack = { screen = Screen.Menu }
        )

        Screen.About -> AboutScreen(onBack = { screen = Screen.Menu })

        Screen.Settings -> SettingsScreen(
            settings = settings,
            onSettingsChange = onSettingsChange,
            onCheckUpdate = { showUpdate = true },
            onBack = { screen = Screen.Menu }
        )

        is Screen.Game -> GameScreen(
            session = current.session,
            saves = saves,
            onExitToMenu = { screen = Screen.Stories },
            onRestartStory = { play(current.story, false) }
        )
    }

    if (showUpdate) {
        UpdateDialog(onDismiss = { showUpdate = false })
    }
}
