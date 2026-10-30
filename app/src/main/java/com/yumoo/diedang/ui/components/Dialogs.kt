package com.yumoo.diedang.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.ui.theme.PixelFont

private val dialogTitleStyle = TextStyle(fontFamily = PixelFont, fontSize = 20.sp, color = Color.White)
private val dialogBodyStyle = TextStyle(fontFamily = PixelFont, fontSize = 14.sp, lineHeight = 24.sp, color = Color.White)
private val cardColor = Color(0xFF2A2A2A)

/** 结局卡片：展示结局文本，可重开或（非强制时）收起回看。 */
@Composable
fun EndingDialog(
    title: String,
    description: String,
    onRestart: () -> Unit,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        title = { Text(title, style = dialogTitleStyle) },
        text = {
            Text(
                text = description,
                style = dialogBodyStyle,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        },
        confirmButton = {
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("🔄 重新开始", style = dialogBodyStyle)
            }
        },
        dismissButton = if (onDismiss != null) {
            {
                OutlinedButton(onClick = onDismiss) {
                    Text("读完文本", style = dialogBodyStyle)
                }
            }
        } else null,
        containerColor = cardColor
    )
}

/** 退出 / 返回剧本列表的确认框。 */
@Composable
fun ConfirmExitDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String = "确认退出",
    message: String = "返回剧本列表。\n当前进度已自动保存。"
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, style = dialogTitleStyle) },
        text = { Text(message, style = dialogBodyStyle) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00020))
            ) { Text("退出", style = dialogBodyStyle) }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3700B3))
            ) { Text("继续游戏", style = dialogBodyStyle) }
        },
        containerColor = cardColor
    )
}

/** 通用确认框（清空存档等）。 */
@Composable
fun SimpleConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String = "确定",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, style = dialogTitleStyle) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(message, style = dialogBodyStyle)
                Spacer(Modifier.height(8.dp))
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB00020))
            ) { Text(confirmLabel, style = dialogBodyStyle) }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("取消", style = dialogBodyStyle) }
        },
        containerColor = cardColor
    )
}
