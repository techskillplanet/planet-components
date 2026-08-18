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
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspToastVariant { Info, Success, Warning, Error }

@Composable
fun TspToast(
    message: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspToastVariant = TspToastVariant.Info,
) {
    val fill = when (variant) {
        TspToastVariant.Success -> theme.success
        TspToastVariant.Warning -> theme.warning
        TspToastVariant.Error -> theme.danger
        TspToastVariant.Info -> theme.brandDark
    }
    val color = if (variant == TspToastVariant.Warning) Color(theme.textPrimary) else Color.White
    Box(
        modifier = modifier
            .background(Color(fill), RoundedCornerShape(999.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = message, color = color, fontWeight = FontWeight.Bold)
    }
}
