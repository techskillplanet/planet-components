package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.heightIn
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.width
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspCascaderOption(
    val value: String,
    val label: String,
    val children: List<TspCascaderOption> = emptyList(),
)

@Composable
fun TspCascader(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    options: List<TspCascaderOption> = emptyList(),
    value: List<String> = emptyList(),
    placeholder: String = "请选择",
    disabled: Boolean = false,
    onChange: (List<String>, List<String>) -> Unit = { _, _ -> },
) {
    val open = remember { mutableStateOf(false) }
    val draft = remember { mutableStateOf(value) }
    val labels = findPathLabels(options, value)
    val display = if (labels.isEmpty()) placeholder else labels.joinToString(" / ")
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
                .border(2.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
                .clickable(enabled = !disabled) {
                    draft.value = value
                    open.value = !open.value
                }
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = display,
                color = Color(
                    when {
                        disabled -> theme.textTertiary
                        labels.isEmpty() -> theme.textTertiary
                        else -> theme.textPrimary
                    },
                ),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f),
            )
            Text(text = "▾", color = Color(theme.textTertiary), fontWeight = FontWeight.Bold)
        }
        if (open.value && !disabled) {
            val columns = buildColumns(options, draft.value)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .background(Color(theme.surfaceSubtle), RoundedCornerShape(14.dp))
                    .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(14.dp))
                    .padding(8.dp),
            ) {
                columns.forEachIndexed { colIndex, col ->
                    Column(
                        modifier = Modifier
                            .width(120.dp)
                            .padding(end = 6.dp),
                    ) {
                        col.forEach { opt ->
                            val active = draft.value.getOrNull(colIndex) == opt.value
                            Text(
                                text = opt.label,
                                color = Color(if (active) theme.brandPrimary else theme.textPrimary),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color(if (active) theme.brandSubtle else 0x00000000),
                                        RoundedCornerShape(10.dp),
                                    )
                                    .clickable {
                                        val next = draft.value.take(colIndex) + opt.value
                                        draft.value = next
                                        if (opt.children.isEmpty()) {
                                            open.value = false
                                            onChange(next, findPathLabels(options, next))
                                        }
                                    }
                                    .padding(horizontal = 10.dp, vertical = 10.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun buildColumns(
    options: List<TspCascaderOption>,
    walk: List<String>,
): List<List<TspCascaderOption>> {
    val columns = mutableListOf<List<TspCascaderOption>>()
    var level: List<TspCascaderOption>? = options
    var i = 0
    while (level != null && level.isNotEmpty()) {
        columns.add(level)
        if (i >= walk.size) break
        val cur = walk[i]
        val hit = level.find { it.value == cur }
        level = hit?.children
        i += 1
    }
    return columns
}

private fun findPathLabels(options: List<TspCascaderOption>, path: List<String>): List<String> {
    val labels = mutableListOf<String>()
    var level = options
    for (v in path) {
        val hit = level.find { it.value == v } ?: break
        labels.add(hit.label)
        level = hit.children
    }
    return labels
}
