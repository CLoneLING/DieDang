package com.yumoo.diedang.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.ui.theme.LocalStoryFont

/** 顶部状态栏：返回键 + 计时 / ∞ + 右侧占位。 */
@Composable
fun GameStatusBar(
    timeLeft: Int,
    timeLimit: Int,
    onExit: () -> Unit
) {
    val timed = timeLimit > 0
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
            .padding(horizontal = 4.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onExit) {
            Text("←", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant))
        }
        Text(
            text = if (timed) "余 ${formatSeconds(timeLeft)}" else "不限时",
            style = TextStyle(
                fontFamily = LocalStoryFont.current,
                fontSize = 14.sp,
                color = when {
                    !timed -> MaterialTheme.colorScheme.onSurfaceVariant
                    timeLeft <= 30 -> MaterialTheme.colorScheme.error
                    timeLeft <= 120 -> MaterialTheme.colorScheme.secondary
                    else -> MaterialTheme.colorScheme.primary
                }
            )
        )
        IconButton(onClick = {}, enabled = false) {
            Text("", style = TextStyle(fontFamily = LocalStoryFont.current, fontSize = 18.sp))
        }
    }
}

fun formatSeconds(seconds: Int): String {
    val s = seconds.coerceAtLeast(0)
    val h = s / 3600
    val m = (s % 3600) / 60
    val sec = s % 60
    return if (h > 0) "%d:%02d:%02d".format(h, m, sec) else "%d:%02d".format(m, sec)
}
