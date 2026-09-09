package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors
import kotlin.math.roundToInt

@Composable
fun TspCheckInStreakCard(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    streakDays: Int = 0,
    totalDays: Int = 0,
    weekProgress: Float = 0f,
    disabled: Boolean = false,
    onOpen: () -> Unit = {},
) {
    val pct = (weekProgress * 100f).roundToInt().coerceIn(0, 100)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(18.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(18.dp))
            .clickable(enabled = !disabled) { onOpen() }
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "连续打卡",
            color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "连续 $streakDays 天",
                color = Color(theme.textSecondary),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "累计 $totalDays 天",
                color = Color(theme.textSecondary),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "本周 $pct%",
                color = Color(theme.textSecondary),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
