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
import com.tencent.kuikly.compose.foundation.layout.size
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

@Composable
fun TspIconButton(
    icon: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    disabled: Boolean = false,
    primary: Boolean = false,
    onClick: () -> Unit,
) {
    val fill = when {
        disabled -> theme.surfaceMuted
        selected -> theme.activeFill
        primary -> theme.brandPrimary
        else -> theme.surfaceRaised
    }
    val color = when {
        disabled -> theme.textTertiary
        primary && !selected -> 0xFFFFFFFFL
        selected || primary -> theme.brandPrimary
        else -> theme.textPrimary
    }
    Box(
        modifier = modifier
            .size(40.dp)
            .background(Color(fill), RoundedCornerShape(999.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp))
            .clickable(enabled = !disabled) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = icon, color = Color(color), fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}
