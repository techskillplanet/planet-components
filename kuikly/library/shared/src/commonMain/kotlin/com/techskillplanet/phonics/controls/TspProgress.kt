package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspProgressVariant { Primary, Warning, Success, Danger }

@Composable
fun TspProgress(
    progress: Float,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspProgressVariant = TspProgressVariant.Primary,
) {
    val bar = when (variant) {
        TspProgressVariant.Primary -> theme.brandPrimary
        TspProgressVariant.Warning -> theme.warning
        TspProgressVariant.Success -> theme.success
        TspProgressVariant.Danger -> theme.danger
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(theme.borderDefault), RoundedCornerShape(999.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(8.dp)
                .background(Color(bar), RoundedCornerShape(999.dp)),
        )
    }
}
