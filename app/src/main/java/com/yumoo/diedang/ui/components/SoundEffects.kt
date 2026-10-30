package com.yumoo.diedang.ui.components

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

// ============================================================
// 极简音效：系统提示音。做成 effect 对象，随 Composable 生命周期释放。
// ============================================================

class SoundEffects {
    private val tone: ToneGenerator? = try {
        ToneGenerator(AudioManager.STREAM_MUSIC, 60)
    } catch (_: Throwable) {
        null
    }

    fun key(ms: Int = 50) {
        try { tone?.startTone(ToneGenerator.TONE_PROP_BEEP, ms) } catch (_: Throwable) {}
    }

    fun click() = key(120)

    fun release() {
        try { tone?.release() } catch (_: Throwable) {}
    }
}

@Composable
fun rememberSoundEffects(): SoundEffects {
    val effects = remember { SoundEffects() }
    DisposableEffect(effects) {
        onDispose { effects.release() }
    }
    return effects
}
