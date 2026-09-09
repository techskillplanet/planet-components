package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspCalendarCell(
    val date: String,
    val level: String = "none",
)

private val WEEKDAYS = listOf("一", "二", "三", "四", "五", "六", "日")
private val LEVELS = listOf(
    "full" to "全勤",
    "partial" to "部分",
    "none" to "未打",
    "exempt" to "豁免",
)

@Composable
fun TspCalendarHeatmap(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    yearMonth: String? = null,
    cells: List<TspCalendarCell> = emptyList(),
    showLegend: Boolean = true,
    onSelectDay: (String, String) -> Unit = { _, _ -> },
) {
    val (year, month) = parseYearMonth(yearMonth)
    val levelMap = cells.associate { it.date to it.level }
    val firstWeekday = weekdayMondayFirst(year, month, 1)
    val daysInMonth = daysInMonth(year, month)
    val slots = buildList {
        repeat(firstWeekday) { add(null as Triple<Int, String, String>?) }
        for (d in 1..daysInMonth) {
            val date = ymd(year, month, d)
            add(Triple(d, date, levelMap[date] ?: "none"))
        }
    }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        if (showLegend) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                LEVELS.forEach { (level, label) ->
                    val (bg, border, fg) = levelColors(level, theme)
                    Text(
                        text = label,
                        color = Color(fg),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .height(28.dp)
                            .background(Color(bg), RoundedCornerShape(999.dp))
                            .border(1.dp, Color(border), RoundedCornerShape(999.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                    )
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            WEEKDAYS.forEach { w ->
                Text(
                    text = w,
                    color = Color(theme.textTertiary),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                )
            }
        }
        slots.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                week.forEach { slot ->
                    if (slot == null) {
                        Spacer(modifier = Modifier.weight(1f).height(36.dp))
                    } else {
                        val (day, date, level) = slot
                        val (bg, border, fg) = levelColors(level, theme)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp)
                                .background(Color(bg), RoundedCornerShape(8.dp))
                                .border(1.dp, Color(border), RoundedCornerShape(8.dp))
                                .clickable { onSelectDay(date, level) },
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "$day",
                                color = Color(fg),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
                repeat(7 - week.size) {
                    Spacer(modifier = Modifier.weight(1f).height(36.dp))
                }
            }
        }
    }
}

private fun levelColors(level: String, theme: PhonicsColors): Triple<Long, Long, Long> {
    return when (level) {
        "full" -> Triple(theme.success, theme.success, 0xFFFFFFFF)
        "partial" -> Triple(theme.warning, theme.warning, theme.textPrimary)
        "exempt" -> Triple(theme.brandPrimary, theme.brandPrimary, 0xFFFFFFFF)
        else -> Triple(theme.pageEnd, theme.borderDefault, theme.textTertiary)
    }
}

private fun parseYearMonth(raw: String?): Pair<Int, Int> {
    if (!raw.isNullOrBlank()) {
        val parts = raw.split("-")
        if (parts.size >= 2) {
            val y = parts[0].toIntOrNull()
            val m = parts[1].toIntOrNull()
            if (y != null && m != null && m in 1..12) return y to m
        }
    }
    return 2026 to 9
}

private fun ymd(year: Int, month: Int, day: Int): String {
    val mm = if (month < 10) "0$month" else "$month"
    val dd = if (day < 10) "0$day" else "$day"
    return "$year-$mm-$dd"
}

private fun daysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
        else -> 30
    }
}

/** Monday-first weekday index: 0=Mon … 6=Sun. */
private fun weekdayMondayFirst(year: Int, month: Int, day: Int): Int {
    var y = year
    var m = month
    if (m < 3) {
        m += 12
        y -= 1
    }
    val k = y % 100
    val j = y / 100
    val h = (day + (13 * (m + 1)) / 5 + k + k / 4 + j / 4 + 5 * j) % 7
    // Sakamoto/Zeller: 0=Sat … convert to Mon=0
    return (h + 5) % 7
}
