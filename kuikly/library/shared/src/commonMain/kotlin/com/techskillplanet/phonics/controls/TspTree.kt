package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.width
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspTreeNode(
    val id: String,
    val label: String,
    val children: List<TspTreeNode> = emptyList(),
)

@Composable
fun TspTree(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    items: List<TspTreeNode> = emptyList(),
    selectedId: String? = null,
    expandedIds: List<String>? = null,
    onSelect: (String, TspTreeNode) -> Unit = { _, _ -> },
    onExpand: (List<String>) -> Unit = {},
) {
    val localExpanded = remember { mutableStateOf(expandedIds?.toSet() ?: emptySet()) }
    val expanded = expandedIds?.toSet() ?: localExpanded.value
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
            .padding(8.dp),
    ) {
        items.forEach { node ->
            TreeNodeView(
                node = node,
                depth = 0,
                selectedId = selectedId,
                expanded = expanded,
                theme = theme,
                onToggle = { id ->
                    val next = expanded.toMutableSet()
                    if (!next.add(id)) next.remove(id)
                    if (expandedIds == null) localExpanded.value = next
                    onExpand(next.toList())
                },
                onSelect = onSelect,
            )
        }
    }
}

@Composable
private fun TreeNodeView(
    node: TspTreeNode,
    depth: Int,
    selectedId: String?,
    expanded: Set<String>,
    theme: PhonicsColors,
    onToggle: (String) -> Unit,
    onSelect: (String, TspTreeNode) -> Unit,
) {
    val open = expanded.contains(node.id)
    val selected = selectedId == node.id
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(if (selected) theme.brandSubtle else 0x00000000),
                RoundedCornerShape(10.dp),
            )
            .padding(start = (8 + depth * 16).dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = when {
                node.children.isEmpty() -> " "
                open -> "▾"
                else -> "▸"
            },
            color = Color(theme.textSecondary),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .width(20.dp)
                .clickable(enabled = node.children.isNotEmpty()) { onToggle(node.id) },
        )
        Text(
            text = node.label,
            color = Color(if (selected) theme.brandPrimary else theme.textPrimary),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .clickable { onSelect(node.id, node) },
        )
    }
    if (open) {
        node.children.forEach { child ->
            TreeNodeView(
                node = child,
                depth = depth + 1,
                selectedId = selectedId,
                expanded = expanded,
                theme = theme,
                onToggle = onToggle,
                onSelect = onSelect,
            )
        }
    }
}
