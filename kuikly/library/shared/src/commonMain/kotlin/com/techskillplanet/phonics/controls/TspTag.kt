package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
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
    val shape = RoundedCornerShape(999.dp)
    Row(
        modifier = modifier
            .height(28.dp)
            .background(Color(bg), shape)
            .border(
                if (selected) 2.dp else 1.dp,
                Color(if (selected) theme.selectedBorder else border),
                shape,
            )
            .clickable(enabled = !disabled) { onTap() }
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        TspFaceLabel(
            text = text,
            color = Color(if (disabled) theme.textTertiary else fg),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
        )
        if (closable) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clickable(enabled = !disabled) { onClose() },
                contentAlignment = Alignment.Center,
            ) {
                TspGlyphMark(
                    glyph = "×",
                    color = Color(if (disabled) theme.textTertiary else fg),
                    fontSize = 14.sp,
                )
            }
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
