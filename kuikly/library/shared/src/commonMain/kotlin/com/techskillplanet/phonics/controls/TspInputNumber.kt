package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.width
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors
import kotlin.math.abs
import kotlin.math.round

@Composable
fun TspInputNumber(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    value: Float = 0f,
    min: Float = Float.NEGATIVE_INFINITY,
    max: Float = Float.POSITIVE_INFINITY,
    step: Float = 1f,
    disabled: Boolean = false,
    onChange: (Float) -> Unit = {},
) {
    val current = value.coerceIn(min, max)
    val s = if (step == 0f) 1f else step
    fun emit(next: Float) = onChange(next.coerceIn(min, max))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                Color(if (disabled) theme.surfaceMuted else theme.surfaceRaised),
                RoundedCornerShape(14.dp),
            )
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(14.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "−",
            color = Color(if (disabled || current <= min) theme.textTertiary else theme.textPrimary),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(44.dp)
                .clickable(enabled = !disabled && current > min) { emit(current - s) },
        )
        Text(
            text = formatNumber(current),
            color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "+",
            color = Color(if (disabled || current >= max) theme.textTertiary else theme.textPrimary),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(44.dp)
                .clickable(enabled = !disabled && current < max) { emit(current + s) },
        )
    }
}

private fun formatNumber(value: Float): String {
    val rounded = round(value)
    return if (abs(value - rounded) < 0.001f) rounded.toInt().toString() else value.toString()
}
