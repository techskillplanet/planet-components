package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspChipVariant { Default, Filter, Choice }

@Composable
fun TspChip(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspChipVariant = TspChipVariant.Default,
    selected: Boolean = false,
    disabled: Boolean = false,
    onTap: (() -> Unit)? = null,
) {
    val fill = when {
        disabled -> Color(theme.surfaceMuted)
        selected || variant == TspChipVariant.Choice && selected -> Color(theme.selectedFill)
        variant == TspChipVariant.Filter && selected -> Color(theme.brandSoft)
        else -> Color(theme.surfaceMuted)
    }
    val clickableModifier = if (onTap != null && !disabled) {
        modifier.clickable { onTap() }
    } else {
        modifier
    }
    Box(
        modifier = clickableModifier
            .background(fill, RoundedCornerShape(999.dp))
            .border(1.dp, Color(if (selected) theme.success else theme.borderDefault), RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(text = text, color = Color(if (disabled) theme.textTertiary else theme.textPrimary))
    }
}
