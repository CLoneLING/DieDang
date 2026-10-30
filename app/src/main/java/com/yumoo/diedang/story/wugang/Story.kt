package com.yumoo.diedang.story.wugang

import com.yumoo.diedang.engine.StoryInfo
import com.yumoo.diedang.model.Scene

/**
 * 《雾港旧案》—— 四部 × 二十章，共 80 章。
 *
 * 写作与结构规范见 docs/wugang_bible.md 与 docs/wugang_spec.md。
 * 每个 PartXY.kt 只负责 5 章（主场景 + 分支场景），这里按顺序拼成一条完整的场景表。
 */

fun buildScenes(): List<Scene> = ArrayList<Scene>(360).apply {
    addAll(part1aScenes())
    addAll(part1bScenes())
    addAll(part1cScenes())
    addAll(part1dScenes())
    addAll(part2aScenes())
    addAll(part2bScenes())
    addAll(part2cScenes())
    addAll(part2dScenes())
    addAll(part3aScenes())
    addAll(part3bScenes())
    addAll(part3cScenes())
    addAll(part3dScenes())
    addAll(part4aScenes())
    addAll(part4bScenes())
    addAll(part4cScenes())
    addAll(part4dScenes())
}

fun getStoryInfo(): StoryInfo = StoryInfo(
    title = "雾港旧案",
    author = "YuMoo / 含AI内容",
    genre = "悬疑 / 推理 / 犯罪",
    description = "十九年前，白鸦号载着七个人出海，只冲回来一具没有脸、右手小指缺了一节的尸体。" +
        "今天，又出现了一具一模一样的。\n\n" +
        "冷案组技术员周砚靠耳朵吃饭，也靠耳朵吃过亏。他要做的不是抓住一个凶手，" +
        "而是想明白一件事：为什么有人花了十九年，非要让这桩旧案被重新翻开。\n\n" +
        "四部八十章，四次判断失误，一个父亲，一份不肯公开的死亡证明。",
    version = "1.0"
)
