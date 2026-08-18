package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.layout.width
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspStepper(
    stepCount: Int,
    currentStep: Int,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
) {
    val safeCount = stepCount.coerceIn(3, 5)
    val safeStep = currentStep.coerceIn(1, safeCount)
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(safeCount) { index ->
            val step = index + 1
            val done = step < safeStep
            val active = step == safeStep
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        Color(
                            when {
                                done -> theme.success
                                active -> theme.brandPrimary
                                else -> theme.surfaceRaised
                            },
                        ),
                        RoundedCornerShape(999.dp),
                    )
                    .border(1.dp, Color(if (active) theme.brandPrimary else theme.borderDefault), RoundedCornerShape(999.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (done) "✓" else "$step",
                    color = Color(if (done || active) 0xFFFFFFFF else theme.textTertiary),
                    fontWeight = FontWeight.Bold,
                )
            }
            if (index < safeCount - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(Color(if (step < safeStep) theme.success else theme.borderDefault)),
                )
            }
        }
    }
}
