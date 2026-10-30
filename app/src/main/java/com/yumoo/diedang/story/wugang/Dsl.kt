package com.yumoo.diedang.story.wugang

import com.yumoo.diedang.model.GameState
import com.yumoo.diedang.model.Option
import com.yumoo.diedang.model.Scene

/**
 * 剧本写作 DSL —— 只为了让剧本文件写起来像剧本，不像代码。
 *
 * 约定（写作时必须遵守）：
 *  1. 场景 id 唯一，格式 `p{部}c{章}{分支后缀}`，例如 p2c07 / p2c07a / p2c07b。
 *  2. 永远只能指向"更后面"的场景，禁止回头，禁止互相跳转成环。
 *  3. 每章 = 一个主场景 + 若干分支场景，分支各自收束到下一章主场景。
 *  4. 文本里不要出现美元符号与反斜杠（会破坏 Kotlin 字符串）。
 */
internal fun sc(
    id: String,
    text: String,
    vararg opts: Option,
    isEnding: Boolean = false,
    timeLimit: Int = 0,
    endingTitle: String? = null
): Scene = Scene(
    id = id,
    text = text.trimIndent().trim(),
    options = opts.toList(),
    isEnding = isEnding,
    timeLimit = timeLimit,
    endingTitle = endingTitle
)

/** 普通选项。 */
internal fun op(text: String, target: String, condition: (GameState) -> Boolean = { true }): Option =
    Option(text = text, nextSceneId = target, condition = condition)

/** 选中时写入一个或多个线索/状态标记。 */
internal fun opFlag(text: String, target: String, vararg flags: String, condition: (GameState) -> Boolean = { true }): Option =
    Option(
        text = text,
        nextSceneId = target,
        condition = condition,
        onSelect = { state -> flags.forEach { state.add(it) } }
    )

/** 只有已持有该标记时才出现的选项（用于"因为你先看懂了某件事，现在才问得出口"）。 */
internal fun opIf(text: String, target: String, flag: String): Option = opFlag(text, target, condition = { it.has(flag) })

/** 只有不持有该标记时才出现的选项。 */
internal fun opUnless(text: String, target: String, flag: String): Option =
    Option(text = text, nextSceneId = target, condition = { !it.has(flag) })

/** 选中时同时写入标记并累加计数。 */
internal fun opCount(text: String, target: String, flag: String, counter: String): Option = Option(
    text = text,
    nextSceneId = target,
    onSelect = { state ->
        if (flag.isNotEmpty()) state.add(flag)
        state.addCount(counter, 1)
    }
)
