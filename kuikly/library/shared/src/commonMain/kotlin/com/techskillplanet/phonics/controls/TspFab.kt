package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.layout.widthIn
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
fun TspFab(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    icon: String = "+",
    text: String = "",
    variant: String = "primary",
    disabled: Boolean = false,
    onTap: () -> Unit = {},
) {
    val primary = variant != "default"
    val extended = text.isNotEmpty()
    val bg = when {
        disabled -> theme.surfaceMuted
        primary -> theme.brandPrimary
        else -> theme.surfaceRaised
    }
    val fg = when {
        disabled -> theme.textTertiary
        primary -> 0xFFFFFFFF
        else -> theme.textPrimary
    }
    val border = if (primary) bg else theme.borderDefault
    Row(
        modifier = modifier
            .then(if (extended) Modifier.height(56.dp).widthIn(min = 56.dp) else Modifier.size(56.dp))
            .background(Color(bg), RoundedCornerShape(999.dp))
            .border(1.dp, Color(border), RoundedCornerShape(999.dp))
            .clickable(enabled = !disabled) { onTap() }
            .padding(horizontal = if (extended) 20.dp else 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = icon,
            color = Color(fg),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
        )
        if (extended) {
            Text(
                text = text,
                color = Color(fg),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}
