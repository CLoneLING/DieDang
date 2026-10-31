package com.yumoo.diedang.ui.components

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.ui.theme.LocalStoryFont
import com.yumoo.diedang.ui.theme.LocalStoryScale
import kotlinx.coroutines.delay

/** \n\n 分段：与剧本写作约定一致。 */
fun splitParagraphs(text: String): List<String> =
    text.split("\n\n").map { it.trim('\n') }.filter { it.isNotBlank() }

/** 正文样式：字体与字号跟随「设置」里的选择，颜色跟随当前主题。 */
@Composable
fun storyTextStyle(lineHeight: TextUnit = 28.sp, indentSize: TextUnit = 32.sp): TextStyle {
    val scale = LocalStoryScale.current
    return TextStyle(
        fontFamily = LocalStoryFont.current,
        fontSize = (16f * scale).sp,
        lineHeight = lineHeight * scale,
        color = MaterialTheme.colorScheme.onSurface,
        textIndent = TextIndent(firstLine = indentSize * scale)
    )
}

/** 已经读完的段落：整段直接渲染。 */
@Composable
fun StoryParagraph(
    text: String,
    modifier: Modifier = Modifier,
    lineHeight: TextUnit = 28.sp,
    indentSize: TextUnit = 32.sp
) {
    BasicText(text = text, style = storyTextStyle(lineHeight, indentSize), modifier = modifier)
}

/**
 * 打字机段落。
 *
 * 关键点（相对旧实现修正）：
 *  - 以 [text] 为 key 启动协程，切换场景/段落会自动取消上一次
 *  - 结束时先置完成标志，避免旧协程在组合销毁后继续吐字
 *  - [revealAll] 由父级控制（点击跳过），一旦为 true 立刻显示全文
 */
@Composable
fun TypewriterParagraph(
    text: String,
    modifier: Modifier = Modifier,
    speedMs: Long = 40,
    lineHeight: TextUnit = 28.sp,
    indentSize: TextUnit = 32.sp,
    revealAll: Boolean = false,
    onComplete: () -> Unit = {},
    onChar: () -> Unit = {}
) {
    var shown by remember(text) { mutableStateOf(0) }
    var finished by remember(text) { mutableStateOf(false) }
    val completeCallback by rememberUpdatedState(onComplete)
    val charCallback by rememberUpdatedState(onChar)

    LaunchedEffect(text) {
        if (revealAll) {
            shown = text.length
            if (!finished) { finished = true; completeCallback() }
            return@LaunchedEffect
        }
        val total = text.length
        // 用局部游标：shown 可能被「点击跳过」并发改成 total，不能拿它直接当下标
        var cursor = shown
        while (cursor < total) {
            delay(speedMs)
            val c = text[cursor]
            cursor++
            shown = cursor
            if (c != ' ' && c != '\n') charCallback()
        }
        if (!finished) { finished = true; completeCallback() }
    }

    // 外部要求「立刻显示全文」（点击跳过）
    LaunchedEffect(revealAll) {
        if (revealAll && shown < text.length) {
            shown = text.length
            if (!finished) { finished = true; completeCallback() }
        }
    }

    BasicText(
        text = text.substring(0, shown.coerceIn(0, text.length)),
        style = storyTextStyle(lineHeight, indentSize),
        modifier = modifier
    )
}
