package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspBadgeVariant { Default, Primary, Success, Warning, Danger }

@Composable
fun TspBadge(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspBadgeVariant = TspBadgeVariant.Default,
    disabled: Boolean = false,
) {
    val fill = when (variant) {
        TspBadgeVariant.Primary -> theme.brandPrimary
        TspBadgeVariant.Success -> theme.success
        TspBadgeVariant.Warning -> theme.warning
        TspBadgeVariant.Danger -> theme.danger
        TspBadgeVariant.Default -> theme.pageEnd
    }
    val color = when (variant) {
        TspBadgeVariant.Primary, TspBadgeVariant.Success, TspBadgeVariant.Danger -> Color.White
        else -> Color(theme.textPrimary)
    }
    Box(
        modifier = modifier
            .background(Color(fill), RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if (disabled) Color(theme.textTertiary) else color,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}
