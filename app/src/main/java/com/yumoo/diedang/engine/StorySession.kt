package com.yumoo.diedang.engine

import com.yumoo.diedang.model.GameState
import com.yumoo.diedang.model.Option
import com.yumoo.diedang.model.Scene

// ============================================================
// 一次游戏会话：所有「剧本规则」都集中在这里，UI 只负责展示。
//
// 规则约定：
//  1. 跳转到新场景时，currentTimeLeft 重置为该场景 timeLimit
//     （timeLimit <= 0 时 currentTimeLeft = 0，表示不限时）。
//  2. 限时场景倒计时归零 -> 强制跳转 TIMEOUT_SCENE_ID 场景（剧本需提供）。
//  3. 选项目标 id 不存在 -> 回退到起始场景，并记录告警，玩家不会卡死。
//  4. 选项 onSelect 在跳转前执行，可以写 flags / counters。
// ============================================================

class StorySession(
    val story: Story,
    initial: GameState? = null
) {
    /** 当前状态（不可变快照。每次变更都拷贝，Compose 才能正确刷新）。 */
    var state: GameState = initial ?: story.newRun()
        private set

    /** 运行时告警（悬空跳转等），供调试面板展示。 */
    val warnings: MutableList<String> = mutableListOf()

    val currentScene: Scene? get() = story.scene(state.currentSceneId)
    val isEnding: Boolean get() = currentScene?.isEnding == true

    /** 本局上限（秒）；0 表示当前场景不限时。 */
    val currentTimeLimit: Int get() = currentScene?.let { maxOf(it.timeLimit, 0) } ?: 0

    // ---------- 状态变更 ----------
    private fun commit(next: GameState) { state = next }

    fun setTypingComplete(done: Boolean) {
        if (state.isTypingComplete == done) return
        commit(state.copy(isTypingComplete = done))
    }

    fun setParagraphIndex(index: Int) {
        if (state.currentParagraphIndex == index) return
        commit(state.copy(currentParagraphIndex = index))
    }

    /** 倒计时 -1 秒；归零则跳超时结局。返回跳转后的新场景 id（若有）。 */
    fun tick(): String? {
        if (isEnding) return null
        val scene = currentScene ?: return null
        if (scene.timeLimit <= 0) return null
        val left = state.currentTimeLeft - 1
        if (left > 0) {
            commit(state.copy(currentTimeLeft = left))
            return null
        }
        if (state.timeOutTriggered) return null
        // 归零：强制进入超时场景
        val target = TIMEOUT_SCENE_ID.takeIf { it in story.scenes } ?: story.startSceneId
        if (target != TIMEOUT_SCENE_ID) warnings += "限时场景 '${scene.id}' 超时，但剧本缺少 '$TIMEOUT_SCENE_ID'，已回到起始场景"
        val next = state.copy(timeOutTriggered = true)
        commit(next)
        return enterScene(target)
    }

    /**
     * 选中某个选项：执行副作用 -> 跳转。
     * @return 新场景 id；null 表示该选项是「结束游戏」（由 UI 走结局结算）。
     */
    fun select(option: Option): String? {
        if (isEnding) return null
        // 副作用在副本上执行，避免可变集合被原地修改导致 UI 不刷新
        val effectsState = state.copyState()
        option.onSelect(effectsState)
        commit(effectsState)

        if (option.isExit()) return null
        if (option.nextSceneId !in story.scenes) {
            warnings += "场景 '${state.currentSceneId}' 的选项「${option.text}」指向不存在的场景 '${option.nextSceneId}'，已回到起始场景"
            return enterScene(story.startSceneId, recordWarning = false)
        }
        return enterScene(option.nextSceneId)
    }

    /** 当前可见的选项（过滤掉不满足 condition 的）。 */
    fun visibleOptions(): List<Option> {
        val scene = currentScene ?: return emptyList()
        return scene.options.filter { it.condition(state) }
    }

    /** 统一的场景进入流程：记录历史 + 重置段落 + 重置计时。 */
    private fun enterScene(sceneId: String, recordWarning: Boolean = true): String? {
        val scene = story.scene(sceneId)
        if (scene == null) {
            if (recordWarning) warnings += "场景 '$sceneId' 不存在"
            return null
        }
        commit(
            state.copy(
                currentSceneId = sceneId,
                currentParagraphIndex = 0,
                isTypingComplete = false,
                currentTimeLeft = maxOf(scene.timeLimit, 0),
                visitedScenes = (state.visitedScenes + sceneId).toMutableList()
            )
        )
        return sceneId
    }

    /** 结局标题：优先用剧本显式指定的，其次从 id 推断，最后用首行文字。 */
    fun endingTitle(scene: Scene? = currentScene): String {
        val target = scene ?: return "结局"
        target.endingTitle?.let { return it }
        val raw = target.id
            .removePrefix("ending_")
            .replace('_', ' ')
            .replaceFirstChar { it.uppercase() }
        return raw.ifBlank { target.firstLine }
    }
}

/** 为一个剧本创建初始状态（放在顶层，便于 UI 直接调用）。 */
fun Story.newRun(): GameState {
    val scene = this.scene(startSceneId)
    return GameState(
        currentSceneId = startSceneId,
        currentTimeLeft = maxOf(scene?.timeLimit ?: 0, 0),
        visitedScenes = mutableListOf(startSceneId)
    )
}
