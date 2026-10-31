package com.yumoo.diedang.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.engine.StorySession
import com.yumoo.diedang.model.Option
import com.yumoo.diedang.save.SaveRepository
import com.yumoo.diedang.ui.components.ConfirmExitDialog
import com.yumoo.diedang.ui.components.EndingDialog
import com.yumoo.diedang.ui.components.GameStatusBar
import com.yumoo.diedang.ui.components.StoryParagraph
import com.yumoo.diedang.ui.components.TypewriterParagraph
import com.yumoo.diedang.ui.components.rememberSoundEffects
import com.yumoo.diedang.ui.components.splitParagraphs
import com.yumoo.diedang.ui.components.storyTextStyle
import com.yumoo.diedang.ui.theme.LocalStoryFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val LINE_HEIGHT = 28.sp
private val INDENT = 32.sp

/** 长段落打字更快，短段落留着节奏感。 */
private fun speedFor(length: Int): Long = when {
    length > 220 -> 10L
    length > 90 -> 16L
    else -> 26L
}

/**
 * 游戏画面。
 *
 * 分工：UI 只负责展示与交互；跳转、条件、计时等规则全部委托给 [StorySession]。
 * 状态传递：会话状态是不可变快照，每次变更后通过 [refresh] 提升版本号触发重组。
 */
@Composable
fun GameScreen(
    session: StorySession,
    saves: SaveRepository,
    onExitToMenu: () -> Unit,
    onRestartStory: () -> Unit
) {
    val story = session.story
    val sound = rememberSoundEffects()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // 版本号：会话状态变更后自增，用于触发重组
    var revision by remember { mutableIntStateOf(0) }
    // 显式读取 revision，使会话状态变更（tick / select）能触发重组
    @Suppress("UNUSED_EXPRESSION") revision
    val state = session.state
    val scene = session.currentScene

    if (scene == null) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
            Text(
                "场景加载失败：${state.currentSceneId}",
                style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
            )
        }
        return
    }

    val isEnding = scene.isEnding
    val paragraphs = remember(scene.id) { splitParagraphs(scene.text) }

    var currentPara by remember(scene.id) { mutableIntStateOf(0) }
    var paraDone by remember(scene.id) { mutableStateOf(false) }
    // 点击可让当前段落立刻显示全文
    var revealCurrent by remember(scene.id, currentPara) { mutableStateOf(false) }
    var showExit by rememberSaveable { mutableStateOf(false) }
    var endingDismissed by remember(scene.id) { mutableStateOf(false) }

    // 场景进入：滚到顶部；结局则记录解锁并清掉进行中存档
    LaunchedEffect(scene.id) {
        scrollState.scrollTo(0)
        if (isEnding) {
            saves.unlockEnding(story.id, scene.id)
            saves.clearProgress(story.id)
        } else {
            saves.saveProgress(story.id, session.state)
        }
        revision++
    }

    // 打字机进行中：自动跟随滚动，让新字始终可见
    LaunchedEffect(paraDone, currentPara) {
        while (!paraDone) {
            scrollState.scrollTo(scrollState.maxValue)
            delay(120)
        }
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    // 倒计时（可能跨多个场景，按场景 id 重启）
    LaunchedEffect(scene.id, session.currentTimeLimit) {
        while (session.currentTimeLimit > 0 && !session.isEnding) {
            delay(1000)
            session.tick()
            revision++
        }
    }

    BackHandler(enabled = true) { showExit = true }

    fun advance() {
        if (isEnding) return
        // 还在打字：先把这一段显示完
        if (!paraDone) {
            revealCurrent = true
            return
        }
        if (currentPara < paragraphs.lastIndex) {
            currentPara++
            paraDone = false
            session.setParagraphIndex(currentPara)
        } else {
            session.setTypingComplete(true)
            revision++
        }
    }

    fun select(option: Option) {
        sound.click()
        session.select(option)
        revision++
        saves.saveProgress(story.id, session.state)
    }

    val typedComplete = state.isTypingComplete

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        GameStatusBar(
            timeLeft = state.currentTimeLeft,
            timeLimit = session.currentTimeLimit,
            onExit = { showExit = true }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                .clickable(enabled = !isEnding) { advance() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
                for (i in 0..currentPara) {
                    if (i > 0) Spacer(Modifier.height(20.dp))
                    if (i == currentPara && !paraDone) {
                        TypewriterParagraph(
                            text = paragraphs[i],
                            speedMs = speedFor(paragraphs[i].length),
                            lineHeight = LINE_HEIGHT,
                            indentSize = INDENT,
                            revealAll = revealCurrent,
                            onComplete = { paraDone = true },
                            onChar = { sound.key(18) }
                        )
                    } else {
                        StoryParagraph(paragraphs[i], lineHeight = LINE_HEIGHT, indentSize = INDENT)
                    }
                }

                if (paraDone && !typedComplete && !isEnding) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        if (currentPara < paragraphs.lastIndex) "▸ 点击继续" else "▸ 请选择",
                        style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }

        // 选项区：文本读完后出现
        if (!isEnding && typedComplete) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                session.visibleOptions().forEach { option ->
                    Button(
                        onClick = { select(option) },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            option.text,
                            style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // 结局：关闭结局卡片后仍可重开
        if (isEnding && endingDismissed) {
            Button(
                onClick = onRestartStory,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("🔄 重新开始", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface))
            }
        }
    }

    // 结局卡片（每次进入结局场景弹一次）
    if (isEnding && !endingDismissed) {
        EndingDialog(
            title = "🏁 ${session.endingTitle(scene)}",
            description = scene.text,
            onRestart = onRestartStory,
            onDismiss = { endingDismissed = true }
        )
    }

    if (showExit) {
        ConfirmExitDialog(
            onConfirm = onExitToMenu,
            onDismiss = { showExit = false }
        )
    }
}

/** 结局场景的正文展示（供需要单独渲染结局页时复用）。 */
@Composable
fun EndingBody(text: String) {
    Text(text, style = storyTextStyle(LINE_HEIGHT, INDENT))
}
