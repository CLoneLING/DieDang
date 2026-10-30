package com.yumoo.diedang.engine

import com.yumoo.diedang.model.Scene

// ============================================================
// 剧本契约
//
// 一个剧本 = 元信息 + 场景表。引擎只认这套接口，不认具体剧本实现。
// 现有剧本通过 StoryAdapter 即可接入（见 StoryRegistry）。
// 将来的文件剧本 / 新剧本只要实现本接口就能被加载。
// ============================================================

data class StoryInfo(
    val title: String,
    val author: String = "",
    val genre: String = "",
    val description: String = "",
    val version: String = "1.0"
)

/** 剧本静态信息之外的统计，用于剧本选择页展示。 */
data class StoryMeta(
    val sceneCount: Int,
    val endingCount: Int,
    val wordCount: Int,
    val hasTimedScene: Boolean
)

interface Story {
    /** 全局唯一 id，用于存档与解锁记录。 */
    val id: String
    val info: StoryInfo
    /** 起始场景 id。 */
    val startSceneId: String
    /** 场景总表，按 id 索引。 */
    val scenes: Map<String, Scene>

    fun scene(id: String): Scene? = scenes[id]

    val meta: StoryMeta
        get() = StoryMeta(
            sceneCount = scenes.size,
            endingCount = scenes.values.count { it.isEnding },
            wordCount = scenes.values.sumOf { it.text.length },
            hasTimedScene = scenes.values.any { it.timeLimit > 0 }
        )
}

/** 把任意 `List<Scene> + StoryInfo` 组成的剧本包装成 [Story]。 */
class StoryAdapter(
    override val id: String,
    override val info: StoryInfo,
    scenes: List<Scene>,
    override val startSceneId: String = "start"
) : Story {
    override val scenes: Map<String, Scene> = scenes.associateBy { it.id }

    /** 剧本自身的问题（重复 id、跳转到不存在的场景、没有起始场景）。 */
    val issues: List<String> by lazy(LazyThreadSafetyMode.NONE) {
        val list = mutableListOf<String>()
        list += validateScenes(scenes = this.scenes, defineStart = startSceneId)
        list
    }
}

/**
 * 剧本静态校验：重复 id、悬空跳转、缺起始场景、缺超时场景。
 * 只返回问题描述，不抛异常，方便作者页直接展示。
 */
fun validateScenes(scenes: Map<String, Scene>, defineStart: String?): List<String> {
    val problems = mutableListOf<String>()
    if (defineStart != null && defineStart !in scenes) {
        problems += "起始场景 '$defineStart' 不存在"
    }
    val timed = scenes.values.filter { it.timeLimit > 0 }
    if (timed.isNotEmpty() && TIMEOUT_SCENE_ID !in scenes) {
        problems += "有 ${timed.size} 个限时场景，但缺少超时结局场景 '$TIMEOUT_SCENE_ID'"
    }
    for (scene in scenes.values) {
        if (scene.options.isEmpty() && !scene.isEnding) {
            problems += "场景 '${scene.id}' 没有任何选项，也不是结局（会卡死）"
        }
        for (opt in scene.options) {
            if (opt.isExit()) continue
            if (opt.nextSceneId !in scenes) {
                problems += "场景 '${scene.id}' 的选项「${opt.text}」跳转到不存在的场景 '${opt.nextSceneId}'"
            }
        }
    }
    return problems
}

/** 限时场景归零后强制跳转的目标 id（引擎约定）。 */
const val TIMEOUT_SCENE_ID = "timeout"
