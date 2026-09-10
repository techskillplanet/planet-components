package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.heightIn
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.widthIn
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspTableColumn(
    val key: String = "",
    val title: String = "",
)

@Composable
fun TspTable(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    columns: List<Any> = emptyList(),
    rows: List<Any> = emptyList(),
    variant: String = "default",
    emptyText: String = "暂无数据",
) {
    val headers = columns.map { col ->
        when (col) {
            is String -> col
            is TspTableColumn -> col.title.ifEmpty { col.key }
            is Map<*, *> -> (col["title"] ?: col["label"] ?: col["key"] ?: "").toString()
            else -> col.toString()
        }
    }
    val keys = columns.mapIndexed { index, col ->
        when (col) {
            is TspTableColumn -> col.key.ifEmpty { index.toString() }
            is Map<*, *> -> (col["key"] ?: col["id"] ?: index).toString()
            else -> index.toString()
        }
    }
    val striped = variant == "striped"
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
            .padding(8.dp),
    ) {
        TableRowView(
            cells = headers,
            header = true,
            stripe = false,
            theme = theme,
        )
        if (rows.isEmpty()) {
            Text(
                text = emptyText,
                color = Color(theme.textTertiary),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
            )
        } else {
            rows.forEachIndexed { ri, row ->
                val cells = keys.mapIndexed { ci, key -> cellText(row, key, ci) }
                TableRowView(
                    cells = cells,
                    header = false,
                    stripe = striped && ri % 2 == 1,
                    theme = theme,
                )
            }
        }
    }
}

@Composable
private fun TableRowView(
    cells: List<String>,
    header: Boolean,
    stripe: Boolean,
    theme: PhonicsColors,
) {
    val bg = when {
        header || stripe -> theme.surfaceSubtle
        else -> theme.surfaceRaised
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(bg), RoundedCornerShape(10.dp))
            .heightIn(min = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        cells.forEach { cell ->
            Text(
                text = cell,
                color = Color(if (header) theme.textPrimary else theme.textSecondary),
                fontWeight = if (header) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier
                    .widthIn(min = 88.dp)
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
    }
}

private fun cellText(row: Any?, key: String, index: Int): String {
    return when (row) {
        null -> ""
        is String, is Number -> row.toString()
        is List<*> -> row.getOrNull(index)?.toString().orEmpty()
        is Map<*, *> -> (row[key] ?: row[index] ?: "").toString()
        else -> row.toString()
    }
}
