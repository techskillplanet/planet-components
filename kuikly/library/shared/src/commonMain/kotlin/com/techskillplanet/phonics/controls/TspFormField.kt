package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

/**
 * 表单项壳：Label + 控件槽 + helper/error。
 * 减少业务页手拼导致的标签错位、错误色不一致等 UI Bug。
 */
@Composable
fun TspFormField(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    label: String = "",
    required: Boolean = false,
    helper: String = "",
    error: String = "",
    content: @Composable () -> Unit,
) {
    val showError = error.isNotBlank()
    val hint = if (showError) error.trim() else helper.trim()
    val hintColor = if (showError) theme.danger else theme.textTertiary
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (label.isNotBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = label.trim(),
                    color = Color(theme.textPrimary),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                if (required) {
                    Text(
                        text = "*",
                        color = Color(theme.danger),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        content()
        if (hint.isNotBlank()) {
            Text(
                text = hint,
                color = Color(hintColor),
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 2.dp),
            )
        }
    }
}
