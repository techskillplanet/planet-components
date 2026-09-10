package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
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
fun TspTag(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    text: String = "",
    closable: Boolean = false,
    selected: Boolean = false,
    disabled: Boolean = false,
    variant: String = "default",
    onClose: () -> Unit = {},
    onTap: () -> Unit = {},
) {
    val (bg, fg, border) = tagColors(theme, variant)
    Row(
        modifier = modifier
            .background(Color(bg), RoundedCornerShape(999.dp))
            .border(
                if (selected) 2.dp else 1.dp,
                Color(if (selected) theme.selectedBorder else border),
                RoundedCornerShape(999.dp),
            )
            .clickable(enabled = !disabled) { onTap() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = text,
            color = Color(if (disabled) theme.textTertiary else fg),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
        )
        if (closable) {
            Text(
                text = "×",
                color = Color(if (disabled) theme.textTertiary else fg),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = !disabled) { onClose() },
            )
        }
    }
}

private fun tagColors(theme: PhonicsColors, variant: String): Triple<Long, Long, Long> {
    return when (variant) {
        "primary" -> Triple(theme.brandSubtle, theme.brandPrimary, 0x00000000)
        "success" -> Triple(theme.successSubtle, theme.success, 0x00000000)
        "warning" -> Triple(theme.activeFill, theme.textPrimary, 0x00000000)
        "danger" -> Triple(theme.dangerSoft, theme.danger, 0x00000000)
        else -> Triple(theme.surfaceRaised, theme.textPrimary, theme.borderDefault)
    }
}
