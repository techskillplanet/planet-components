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
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspButtonVariant { Primary, Default, Danger, Text, Link }

@Composable
fun TspButton(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspButtonVariant = TspButtonVariant.Default,
    disabled: Boolean = false,
    fullWidth: Boolean = true,
    onClick: () -> Unit,
) {
    val flat = variant == TspButtonVariant.Text || variant == TspButtonVariant.Link
    val faceColor = when {
        disabled -> Color(theme.surfaceMuted)
        variant == TspButtonVariant.Primary -> Color(theme.brandPrimary)
        variant == TspButtonVariant.Danger -> Color(theme.danger)
        variant == TspButtonVariant.Text || variant == TspButtonVariant.Link -> Color(0x00000000)
        else -> Color(theme.surfaceRaised)
    }
    val textColor = when {
        disabled -> Color(theme.textTertiary)
        variant == TspButtonVariant.Primary || variant == TspButtonVariant.Danger -> Color.White
        variant == TspButtonVariant.Text || variant == TspButtonVariant.Link -> Color(theme.brandPrimary)
        else -> Color(theme.textPrimary)
    }
    val widthModifier = if (fullWidth) modifier.fillMaxWidth() else modifier
    val lift = if (flat) 0 else 3
    Box(modifier = widthModifier.height((48 + lift).dp)) {
        if (!flat) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color(theme.borderDefault), RoundedCornerShape(999.dp)),
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(48.dp)
                .background(faceColor, RoundedCornerShape(999.dp))
                .then(
                    if (flat) Modifier else Modifier.border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp)),
                )
                .clickable(enabled = !disabled) { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
