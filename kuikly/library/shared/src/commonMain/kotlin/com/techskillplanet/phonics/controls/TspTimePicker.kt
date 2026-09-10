package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

/**
 * HH:mm 时间字段。Kuikly 端以 OptionSheet 列出常见时间点简化实现。
 */
@Composable
fun TspTimePicker(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String = "HH:mm",
    disabled: Boolean = false,
    onChange: (String) -> Unit = {},
) {
    val open = remember { mutableStateOf(false) }
    val label = if (value.isEmpty()) placeholder else value
    val isPlaceholder = value.isEmpty()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(2.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
            .clickable(enabled = !disabled) { open.value = true }
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = label,
            color = Color(
                when {
                    disabled -> theme.textTertiary
                    isPlaceholder -> theme.textTertiary
                    else -> theme.textPrimary
                },
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
        )
    }
    if (open.value && !disabled) {
        val options = buildTimeOptions()
        val selectedIndex = options.indexOf(value).let { if (it < 0) 0 else it }
        TspOptionSheet(
            title = "选择时间",
            options = options,
            selectedIndex = selectedIndex,
            visible = true,
            theme = theme,
            onCancel = { open.value = false },
            onSelect = { _, option ->
                open.value = false
                onChange(option.toString())
            },
        )
    }
}

private fun buildTimeOptions(): List<String> {
    val list = ArrayList<String>(24 * 4)
    for (h in 0..23) {
        for (m in listOf(0, 15, 30, 45)) {
            val hh = if (h < 10) "0$h" else "$h"
            val mm = if (m < 10) "0$m" else "$m"
            list.add("$hh:$mm")
        }
    }
    return list
}
