package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.width
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors
import kotlin.math.abs
import kotlin.math.round

@Composable
fun TspSlider(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    value: Float = 0f,
    min: Float = 0f,
    max: Float = 100f,
    step: Float = 1f,
    disabled: Boolean = false,
    onChange: (Float) -> Unit = {},
) {
    val lo = min
    val hi = if (max < min) min else max
    val current = value.coerceIn(lo, hi)
    val span = (hi - lo).takeIf { it != 0f } ?: 1f
    val pct = ((current - lo) / span).coerceIn(0f, 1f)
    fun snap(next: Float): Float {
        val s = if (step <= 0f) 1f else step
        val snapped = round((next - lo) / s) * s + lo
        return snapped.coerceIn(lo, hi)
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "−",
            color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(enabled = !disabled && current > lo) { onChange(snap(current - step)) }
                .padding(6.dp),
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .background(Color(theme.borderDefault), RoundedCornerShape(999.dp)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(pct)
                    .height(8.dp)
                    .background(Color(theme.brandPrimary), RoundedCornerShape(999.dp)),
            )
        }
        Text(
            text = "+",
            color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable(enabled = !disabled && current < hi) { onChange(snap(current + step)) }
                .padding(6.dp),
        )
        Text(
            text = formatSliderValue(current),
            color = Color(theme.textSecondary),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(40.dp),
        )
    }
}

private fun formatSliderValue(value: Float): String {
    val rounded = round(value)
    return if (abs(value - rounded) < 0.001f) rounded.toInt().toString() else value.toString()
}
