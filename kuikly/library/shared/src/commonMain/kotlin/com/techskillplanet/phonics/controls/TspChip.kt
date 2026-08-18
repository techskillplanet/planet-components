package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspChipVariant { Default, Primary, Success, Warning, Danger }

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
        disabled -> theme.surfaceMuted
        selected -> theme.selectedFill
        variant == TspChipVariant.Primary -> theme.brandSoft
        variant == TspChipVariant.Success -> theme.selectedFill
        variant == TspChipVariant.Warning -> theme.activeFill
        variant == TspChipVariant.Danger -> theme.dangerSoft
        else -> theme.surfaceMuted
    }
    val stroke = when {
        disabled -> theme.borderDefault
        selected || variant == TspChipVariant.Success -> theme.success
        variant == TspChipVariant.Primary -> theme.brandPrimary
        variant == TspChipVariant.Warning -> theme.warning
        variant == TspChipVariant.Danger -> theme.danger
        else -> theme.borderDefault
    }
    val color = when {
        disabled -> theme.textTertiary
        variant == TspChipVariant.Danger -> theme.danger
        variant == TspChipVariant.Primary -> theme.brandDark
        else -> theme.textPrimary
    }
    val clickableModifier = if (onTap != null && !disabled) {
        modifier.clickable { onTap() }
    } else {
        modifier
    }
    Box(
        modifier = clickableModifier
            .background(Color(fill), RoundedCornerShape(999.dp))
            .border(1.dp, Color(stroke), RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = Color(color), textAlign = TextAlign.Center)
    }
}
