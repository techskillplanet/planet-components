package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspCardVariant { Default, Subtle, Selected, Disabled }

@Composable
fun TspCard(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspCardVariant = TspCardVariant.Default,
    selected: Boolean = false,
    disabled: Boolean = false,
    content: @Composable () -> Unit,
) {
    val isSelected = selected || variant == TspCardVariant.Selected
    val isDisabled = disabled || variant == TspCardVariant.Disabled
    val fill = when {
        isDisabled -> Color(theme.surfaceMuted)
        isSelected -> Color(theme.selectedFill)
        variant == TspCardVariant.Subtle -> Color(theme.surfaceMuted)
        else -> Color(theme.surfaceRaised)
    }
    val stroke = when {
        isDisabled -> Color(theme.borderDefault)
        isSelected -> Color(theme.success)
        else -> Color(theme.borderDefault)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(fill, RoundedCornerShape(16.dp))
            .border(1.dp, stroke, RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        content()
    }
}
