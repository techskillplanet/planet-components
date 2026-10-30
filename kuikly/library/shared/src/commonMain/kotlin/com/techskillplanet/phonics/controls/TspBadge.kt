package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.draw.clip
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

/**
 * Soft*：浅底+描边（听写/批改徽标）；其余为实心胶囊。
 */
enum class TspBadgeVariant {
    Default,
    Primary,
    Success,
    Warning,
    Danger,
    SoftPrimary,
    SoftSuccess,
    SoftNeutral,
}

@Composable
fun TspBadge(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspBadgeVariant = TspBadgeVariant.Default,
    disabled: Boolean = false,
) {
    val shape = RoundedCornerShape(999.dp)
    val soft = variant == TspBadgeVariant.SoftPrimary ||
        variant == TspBadgeVariant.SoftSuccess ||
        variant == TspBadgeVariant.SoftNeutral
    val fillColor = when (variant) {
        TspBadgeVariant.SoftSuccess -> Color(theme.success).copy(alpha = 0.12f)
        TspBadgeVariant.SoftPrimary -> Color(theme.brandSubtle)
        TspBadgeVariant.SoftNeutral -> Color(theme.surfaceSubtle)
        TspBadgeVariant.Primary -> Color(theme.brandPrimary)
        TspBadgeVariant.Success -> Color(theme.success)
        TspBadgeVariant.Warning -> Color(theme.warning)
        TspBadgeVariant.Danger -> Color(theme.danger)
        TspBadgeVariant.Default -> Color(theme.pageEnd)
    }
    val fg = when (variant) {
        TspBadgeVariant.Primary, TspBadgeVariant.Success, TspBadgeVariant.Danger -> Color.White
        TspBadgeVariant.SoftPrimary -> Color(theme.brandDark)
        TspBadgeVariant.SoftSuccess -> Color(theme.success)
        TspBadgeVariant.SoftNeutral -> Color(theme.textSecondary)
        else -> Color(theme.textPrimary)
    }
    val stroke = when (variant) {
        TspBadgeVariant.SoftPrimary -> Color(theme.brandPrimary).copy(alpha = 0.28f)
        TspBadgeVariant.SoftSuccess -> Color(theme.success).copy(alpha = 0.28f)
        TspBadgeVariant.SoftNeutral -> Color(theme.borderDefault)
        else -> Color.Transparent
    }
    Box(
        modifier = modifier
            .then(if (soft) Modifier.height(32.dp) else Modifier)
            .border(if (soft) 1.dp else 0.dp, stroke, shape)
            .background(fillColor, shape)
            .clip(shape)
            .padding(
                horizontal = if (soft) 12.dp else 10.dp,
                vertical = if (soft) 0.dp else 4.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if (disabled) Color(theme.textTertiary) else fg,
            fontSize = if (soft) 12.sp else 13.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }
}
