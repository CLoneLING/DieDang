package com.yumoo.diedang.save

import android.content.Context
import android.util.Log
import com.yumoo.diedang.engine.Story
import com.yumoo.diedang.engine.StoryRegistry
import com.yumoo.diedang.model.GameState
import org.json.JSONArray
import org.json.JSONObject

// ============================================================
// 本地存档：SharedPreferences + JSON
//
// 存什么：
//   - 每个剧本一条进行中存档（场景 id + flags + counters + 计时 + 历史）
//   - 已解锁的结局集合
//   - 上次游玩的剧本 id
// 引擎只依赖 GameState 的通用字段，因此新剧本无需改存档代码。
// ============================================================

data class ProgressSnapshot(
    val storyId: String,
    val state: GameState,
    val updatedAt: Long
)

class SaveRepository(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // ---------- 进行中存档 ----------
    fun loadProgress(storyId: String): ProgressSnapshot? {
        val raw = prefs.getString(KEY_PROGRESS_PREFIX + storyId, null) ?: return null
        return try {
            val obj = JSONObject(raw)
            val state = GameState(
                currentTimeLeft = obj.optInt("timeLeft", 0),
                timeOutTriggered = obj.optBoolean("timeOut", false),
                isTypingComplete = false,
                currentParagraphIndex = 0,
                currentSceneId = obj.getString("scene"),
                flags = obj.optJSONArray("flags").toStringSet(),
                counters = obj.optJSONObject("counters").toIntMap(),
                visitedScenes = obj.optJSONArray("visited").toStringList().toMutableList()
            )
            ProgressSnapshot(storyId, state, obj.optLong("updatedAt", 0L))
        } catch (t: Throwable) {
            Log.w(TAG, "读取存档失败：$storyId", t)
            null
        }
    }

    fun saveProgress(storyId: String, state: GameState) {
        try {
            val obj = JSONObject().apply {
                put("scene", state.currentSceneId)
                put("timeLeft", state.currentTimeLeft)
                put("timeOut", state.timeOutTriggered)
                put("flags", JSONArray(state.flags.toList()))
                put("counters", JSONObject().apply {
                    state.counters.forEach { (k, v) -> put(k, v) }
                })
                put("visited", JSONArray(state.visitedScenes.takeLast(MAX_HISTORY)))
                put("updatedAt", System.currentTimeMillis())
            }
            prefs.edit().putString(KEY_PROGRESS_PREFIX + storyId, obj.toString()).apply()
        } catch (t: Throwable) {
            Log.w(TAG, "写入存档失败：$storyId", t)
        }
    }

    fun clearProgress(storyId: String) {
        prefs.edit().remove(KEY_PROGRESS_PREFIX + storyId).apply()
    }

    /** 某剧本是否有可续玩的存档（结局场景上的存档视为已完成，不算）。 */
    fun hasProgress(story: Story): Boolean {
        val snap = loadProgress(story.id) ?: return false
        return story.scene(snap.state.currentSceneId)?.isEnding != true
    }

    // ---------- 结局解锁 ----------
    fun unlockedEndings(storyId: String): Set<String> =
        prefs.getStringSet(KEY_ENDINGS_PREFIX + storyId, emptySet())?.toSet() ?: emptySet()

    fun unlockEnding(storyId: String, endingId: String) {
        val current = unlockedEndings(storyId).toMutableSet()
        if (current.add(endingId)) {
            prefs.edit().putStringSet(KEY_ENDINGS_PREFIX + storyId, current).apply()
        }
    }

    // ---------- 最近游玩 ----------
    var lastStoryId: String?
        get() = prefs.getString(KEY_LAST_STORY, null)
        set(value) {
            prefs.edit().putString(KEY_LAST_STORY, value).apply()
        }

    /**
     * 读档时校验：场景必须仍然存在，且（如有限时）剩余时间落在合理区间。
     * 剧本更新导致存档失效时返回 null，调用方从开头重来。
     */
    fun loadValidated(story: Story): ProgressSnapshot? {
        val snap = loadProgress(story.id) ?: return null
        val scene = story.scene(snap.state.currentSceneId) ?: run {
            clearProgress(story.id)
            return null
        }
        val clampedLeft = when {
            scene.timeLimit <= 0 -> 0
            snap.state.currentTimeLeft <= 0 -> 1
            else -> minOf(snap.state.currentTimeLeft, scene.timeLimit)
        }
        return snap.copy(state = snap.state.copy(currentTimeLeft = clampedLeft))
    }

    /** 入口便捷方法。 */
    companion object {
        private const val TAG = "SaveRepository"
        private const val PREFS_NAME = "updown_saves"
        private const val KEY_PROGRESS_PREFIX = "progress_"
        private const val KEY_ENDINGS_PREFIX = "endings_"
        private const val KEY_LAST_STORY = "last_story"
        private const val MAX_HISTORY = 200

        private fun JSONArray?.toStringList(): List<String> {
            if (this == null) return emptyList()
            return (0 until length()).mapNotNull { optString(it, null) }
        }

        private fun JSONArray?.toStringSet(): MutableSet<String> =
            toStringList().toMutableSet()

        private fun JSONObject?.toIntMap(): MutableMap<String, Int> {
            if (this == null) return mutableMapOf()
            val map = mutableMapOf<String, Int>()
            val keys = keys()
            while (keys.hasNext()) {
                val k = keys.next()
                map[k] = optInt(k, 0)
            }
            return map
        }

        /** 供 UI 展示剧本列表时读取全部记录。 */
        fun allProgress(context: Context, stories: List<Story>): Map<String, ProgressSnapshot> =
            stories.mapNotNull { story ->
                SaveRepository(context).loadValidated(story)?.let { story.id to it }
            }.toMap()

        /** 入口便捷方法。 */
        fun create(context: Context): SaveRepository = SaveRepository(context)

        /** 默认剧本。 */
        val defaultStoryId: String get() = StoryRegistry.default?.id.orEmpty()
    }
}
