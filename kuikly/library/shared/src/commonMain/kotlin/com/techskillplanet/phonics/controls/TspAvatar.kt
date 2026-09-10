package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.CircleShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspAvatar(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    text: String = "",
    size: String = "md",
    variant: String = "default",
) {
    val dim = when (size) {
        "sm" -> 32
        "lg" -> 56
        else -> 40
    }
    val fill = when (variant) {
        "primary" -> theme.brandPrimary
        "subtle" -> theme.brandSubtle
        else -> theme.surfaceSubtle
    }
    val textColor = when (variant) {
        "primary" -> 0xFFFFFFFF
        "subtle" -> theme.brandPrimary
        else -> theme.textPrimary
    }
    val initial = text.trim().ifEmpty { "?" }.take(2).uppercase()
    Box(
        modifier = modifier
            .size(dim.dp)
            .background(Color(fill), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = initial,
            color = Color(textColor),
            fontWeight = FontWeight.Bold,
            fontSize = (dim * 0.38f).sp,
        )
    }
}
