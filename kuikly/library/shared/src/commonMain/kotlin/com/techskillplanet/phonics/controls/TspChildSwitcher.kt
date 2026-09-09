package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextOverflow
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspChildSwitcherItem(
    val id: String,
    val label: String = "",
    val name: String = "",
    val text: String = "",
    val emoji: String = "",
) {
    val resolvedLabel: String
        get() = when {
            label.isNotEmpty() -> label
            name.isNotEmpty() -> name
            else -> text
        }
}

enum class TspChildSwitcherVariant { Chip, Tabs }

@Composable
fun TspChildSwitcher(
    items: List<TspChildSwitcherItem>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    selectedId: String? = null,
    variant: TspChildSwitcherVariant = TspChildSwitcherVariant.Chip,
    disabled: Boolean = false,
    onChange: (String) -> Unit = {},
) {
    val useTabs = variant == TspChildSwitcherVariant.Tabs
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach { item ->
            val selected = item.id == selectedId
            val fill = if (selected) theme.brandPrimary else theme.surfaceRaised
            val stroke = if (selected) theme.brandPrimary else theme.borderDefault
            val color = if (selected) 0xFFFFFFFF else theme.textSecondary
            val label = if (item.emoji.isNotEmpty()) "${item.emoji} ${item.resolvedLabel}" else item.resolvedLabel
            val itemModifier = if (useTabs) {
                Modifier.weight(1f)
            } else {
                Modifier
            }
            Row(
                modifier = itemModifier
                    .height(44.dp)
                    .background(Color(fill), RoundedCornerShape(if (useTabs) 14.dp else 999.dp))
                    .border(1.dp, Color(stroke), RoundedCornerShape(if (useTabs) 14.dp else 999.dp))
                    .clickable(enabled = !disabled) { onChange(item.id) }
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = label,
                    color = Color(if (disabled) theme.textTertiary else color),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}
