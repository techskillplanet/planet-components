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
 * Themed YYYY-MM-DD date field.
 * Kuikly 端以字段展示 + OptionSheet 当月日期列表简化实现（无原生 DatePicker）。
 */
@Composable
fun TspDatePicker(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    value: String = "",
    min: String? = null,
    max: String? = null,
    placeholder: String = "YYYY-MM-DD",
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
        val options = buildDateOptions(value, min, max)
        val selectedIndex = options.indexOf(value).let { if (it < 0) 0 else it }
        TspOptionSheet(
            title = "选择日期",
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

private fun buildDateOptions(value: String, min: String?, max: String?): List<String> {
    val base = parseYmd(value) ?: parseYmd(min) ?: parseYmd(max) ?: Triple(2026, 9, 8)
    val (y, m, _) = base
    val days = daysInMonth(y, m)
    val minParsed = parseYmd(min)
    val maxParsed = parseYmd(max)
    val minDay = if (minParsed != null && minParsed.first == y && minParsed.second == m) minParsed.third else 1
    val maxDay = if (maxParsed != null && maxParsed.first == y && maxParsed.second == m) maxParsed.third else days
    return (minDay..maxDay).map { d -> ymd(y, m, d) }
}

private fun ymd(year: Int, month: Int, day: Int): String {
    val mm = if (month < 10) "0$month" else "$month"
    val dd = if (day < 10) "0$day" else "$day"
    return "$year-$mm-$dd"
}

private fun parseYmd(raw: String?): Triple<Int, Int, Int>? {
    if (raw.isNullOrBlank()) return null
    val parts = raw.split("-")
    if (parts.size != 3) return null
    val y = parts[0].toIntOrNull() ?: return null
    val m = parts[1].toIntOrNull() ?: return null
    val d = parts[2].toIntOrNull() ?: return null
    return Triple(y, m, d)
}

private fun daysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        else -> 30
    }
}
