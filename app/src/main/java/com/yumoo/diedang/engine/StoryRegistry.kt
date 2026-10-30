package com.yumoo.diedang.engine

import com.yumoo.diedang.model.Scene

// ============================================================
// 剧本注册表
//
// 切换剧本 / 引入新剧本 / 用户上传剧本时，只需要在这里增加一个 entry
// （或改成运行时注册），UI 与引擎都不用动。
// ============================================================

object StoryRegistry {

    /** 内置剧本条目。 */
    private data class BuiltinStory(
        val id: String,
        /** 起始场景 id。 */
        val start: String,
        val scenes: () -> List<Scene>,
        val info: () -> StoryInfo
    )

    /** 内置剧本清单（顺序即剧本选择页顺序）。 */
    private val builtins: List<BuiltinStory> = listOf(
        BuiltinStory(
            id = "wugang",
            start = "p1c01",
            scenes = { com.yumoo.diedang.story.wugang.buildScenes() },
            info = { com.yumoo.diedang.story.wugang.getStoryInfo() }
        ),
        BuiltinStory(
            id = "lifechoices",
            start = "start",
            scenes = { com.yumoo.diedang.story.wutong.buildScenes() },
            info = {
                val old = com.yumoo.diedang.story.wutong.getStoryInfo()
                StoryInfo(
                    title = old.title,
                    author = old.author,
                    genre = old.genre,
                    description = old.description,
                    version = old.version
                )
            }
        )
    )

    /** 载入全部剧本。剧本只解析一次并缓存，避免每帧重复构建场景表。 */
    val stories: List<Story> by lazy(LazyThreadSafetyMode.NONE) {
        builtins.map { loadBuiltin(it) }
    }

    fun find(id: String): Story? = stories.firstOrNull { it.id == id }

    /** 第一个剧本，作为默认值（存档缺省、直接开玩等场景）。 */
    val default: Story? get() = stories.firstOrNull()

    private fun loadBuiltin(entry: BuiltinStory): Story = StoryAdapter(
        id = entry.id,
        info = entry.info(),
        scenes = entry.scenes(),
        startSceneId = entry.start
    )
}
