package com.yumoo.diedang.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yumoo.diedang.ui.theme.LocalStoryFont

@Composable
private fun dialogTitleStyle() = TextStyle(
    fontFamily = LocalStoryFont.current,
    fontSize = 20.sp,
    color = MaterialTheme.colorScheme.onSurface
)

@Composable
private fun dialogBodyStyle() = TextStyle(
    fontFamily = LocalStoryFont.current,
    fontSize = 14.sp,
    lineHeight = 24.sp,
    color = MaterialTheme.colorScheme.onSurface
)

/** 结局卡片：展示结局文本，可重开或（非强制时）收起回看。 */
@Composable
fun EndingDialog(
    title: String,
    description: String,
    onRestart: () -> Unit,
    onDismiss: (() -> Unit)? = null
) {
    val colors = MaterialTheme.colorScheme
    AlertDialog(
        onDismissRequest = { onDismiss?.invoke() },
        title = { Text(title, style = dialogTitleStyle()) },
        text = {
            Text(
                text = description,
                style = dialogBodyStyle(),
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        },
        confirmButton = {
            Button(
                onClick = onRestart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Text("🔄 重新开始", style = dialogBodyStyle().copy(color = colors.onPrimary))
            }
        },
        dismissButton = if (onDismiss != null) {
            {
                OutlinedButton(onClick = onDismiss) {
                    Text("读完文本", style = dialogBodyStyle())
                }
            }
        } else null,
        containerColor = colors.surface,
        titleContentColor = colors.onSurface,
        textContentColor = colors.onSurface
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
    val colors = MaterialTheme.colorScheme
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, style = dialogTitleStyle()) },
        text = { Text(message, style = dialogBodyStyle()) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.error,
                    contentColor = colors.onPrimary
                )
            ) { Text("退出", style = dialogBodyStyle().copy(color = colors.onPrimary)) }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) { Text("继续游戏", style = dialogBodyStyle().copy(color = colors.onPrimary)) }
        },
        containerColor = colors.surface,
        titleContentColor = colors.onSurface,
        textContentColor = colors.onSurface
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
    val colors = MaterialTheme.colorScheme
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, style = dialogTitleStyle()) },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(message, style = dialogBodyStyle())
                Spacer(Modifier.height(8.dp))
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.error,
                    contentColor = colors.onPrimary
                )
            ) { Text(confirmLabel, style = dialogBodyStyle().copy(color = colors.onPrimary)) }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) { Text("取消", style = dialogBodyStyle()) }
        },
        containerColor = colors.surface,
        titleContentColor = colors.onSurface,
        textContentColor = colors.onSurface
    )
}
