package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspButtonVariant { Primary, Default, Danger, Text, Link }

@Composable
fun TspButton(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspButtonVariant = TspButtonVariant.Default,
    enabled: Boolean = true,
    fullWidth: Boolean = true,
    onClick: () -> Unit,
) {
    val flat = variant == TspButtonVariant.Text || variant == TspButtonVariant.Link
    val faceColor = when (variant) {
        TspButtonVariant.Primary -> Color(theme.brandPrimary)
        TspButtonVariant.Danger -> Color(theme.danger)
        TspButtonVariant.Text, TspButtonVariant.Link -> Color.Transparent
        TspButtonVariant.Default -> Color(theme.surfaceRaised)
    }
    val textColor = when (variant) {
        TspButtonVariant.Primary, TspButtonVariant.Danger -> Color.White
        TspButtonVariant.Text, TspButtonVariant.Link -> Color(theme.brandPrimary)
        TspButtonVariant.Default -> Color(theme.textPrimary)
    }
    val widthModifier = if (fullWidth) modifier.fillMaxWidth() else modifier
    Box(
        modifier = widthModifier
            .height(48.dp)
            .background(faceColor, RoundedCornerShape(999.dp))
            .then(
                if (flat) Modifier else Modifier.border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp)),
            )
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = if (enabled) textColor else Color(theme.textTertiary),
            fontWeight = FontWeight.Bold,
        )
    }
}
