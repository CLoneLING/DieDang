package com.yumoo.diedang.model

// ============================================================
// 引擎通用数据模型
//
// 这一层与任何具体剧本无关：
//   - 剧本只通过 GameState 的 flags / counters / 内置字段读写状态
//   - 剧本不再需要知道「信任度」「手机」「作业」之类的私有概念
// 旧的 com.yumoo.diedang.GameState / Scene / Option 命名保持不变，
// 因此已有的剧本文件（如 com.yumoo.diedang.story.wutong）无需改动即可编译。
// ============================================================

/**
 * 引擎内置状态。
 *
 * 剧本自定义的一切变量都走 [flags] / [counters]，不要再往这里加字段；
 * 这里只保留引擎本身（计时、结局、选段、跳转）必须读写的内容。
 */
data class GameState(
    /** 秒。<= 0 表示本场景不限时。每次跳转场景后由引擎重置为本场景的限时。 */
    var currentTimeLeft: Int = 0,
    /** 已经进入过计时归零分支。保证每局只触发一次，避免结局被反复重写。 */
    var timeOutTriggered: Boolean = false,
    /** 当前场景文本已全部读完，选项此时才允许点击。 */
    var isTypingComplete: Boolean = false,
    /** 当前场景内已读到的段落序号（引擎自动维护，剧本不必关心）。 */
    var currentParagraphIndex: Int = 0,
    /** 当前所在场景 id。 */
    var currentSceneId: String = "",
    /** 剧本通过 onSelect 写入的标记/物品/知识，例如 "phone_found"。 */
    var flags: MutableSet<String> = mutableSetOf(),
    /** 剧本通过 onSelect 累加的数值变量，例如 trust -> 2。 */
    var counters: MutableMap<String, Int> = mutableMapOf(),
    /** 引擎历史跳转记录（场景 id），供调试与「回到上一场景」使用。 */
    var visitedScenes: MutableList<String> = mutableListOf()
) {
    // ---------- 便捷读写 ----------
    fun has(flag: String): Boolean = flag in flags
    fun add(flag: String) { flags.add(flag) }
    fun remove(flag: String) { flags.remove(flag) }
    fun count(key: String): Int = counters[key] ?: 0
    fun addCount(key: String, delta: Int) { counters[key] = count(key) + delta }

    /** 深拷贝，用于 UI 状态快照 / 存档，避免 Compose 因为可变集合不刷新。 */
    fun copyState(): GameState = copy(
        flags = flags.toMutableSet(),
        counters = counters.toMutableMap(),
        visitedScenes = visitedScenes.toMutableList()
    )

    /** 只保留引擎必要的字段作为存档，剧本相关变量在 flags / counters 里。 */
    fun resetForNewRun(startSceneId: String): GameState = GameState(
        currentSceneId = startSceneId,
        visitedScenes = mutableListOf(startSceneId)
    )
}

/** 单个选项的可见条件：返回 false 时该选项在界面上隐藏。 */
typealias SceneCondition = (GameState) -> Boolean

/** 选中选项时的副作用：写入标记、累加数值、解锁内容等。 */
typealias SceneEffect = (GameState) -> Unit

data class Option(
    val text: String,
    val nextSceneId: String,
    val condition: SceneCondition = { true },
    val onSelect: SceneEffect = {}
) {
    /**
     * 特殊目标约定（与引擎一致）：
     *  - "end" / null-ish：本选项直接结束游戏（走 [Scene.isEnding] 的结算路径）
     *  - 目标场景 id 不存在：引擎回退到剧本起始场景（并记录告警）
     */
    fun isExit(): Boolean = nextSceneId.isBlank() || nextSceneId == EXIT_TARGET

    companion object {
        const val EXIT_TARGET = "#end"
    }
}

data class Scene(
    val id: String,
    val text: String,
    val options: List<Option> = emptyList(),
    val isEnding: Boolean = false,
    /**
     * 本场景的限时（秒），<= 0 表示不限时。
     * 旧剧本写法 timeLimit = 7200 表示 7200 秒，语义保持不变。
     */
    val timeLimit: Int = 0,
    /** 可选：结局标题。为空时由引擎从 id / 首行自动推断。 */
    val endingTitle: String? = null
) {
    /** 剧本用 \n\n 分段，引擎逐段打字机输出。 */
    val paragraphs: List<String> get() = text.split("\n\n")

    /** 首行作为兜底标题。 */
    val firstLine: String get() = text.lineSequence().firstOrNull { it.isNotBlank() }?.trim() ?: id
}
