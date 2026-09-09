package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspBalanceHeroVariant { Default, Compact }

private val BREAKDOWN_LABELS = mapOf(
    "balance" to "余额",
    "ruleScore" to "规则分",
    "streakBonus" to "连续奖励",
    "redeemTotal" to "已兑换",
)

@Composable
fun TspBalanceHero(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    total: Int = 0,
    breakdown: Map<String, Int>? = null,
    suffix: String = "分",
    variant: TspBalanceHeroVariant = TspBalanceHeroVariant.Default,
) {
    val compact = variant == TspBalanceHeroVariant.Compact
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(20.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "可用积分",
            color = Color(theme.textSecondary),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        )
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "$total",
                color = Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
                fontSize = if (compact) 28.sp else 36.sp,
            )
            if (suffix.isNotEmpty()) {
                Text(
                    text = suffix,
                    color = Color(theme.textSecondary),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
            }
        }
        if (!breakdown.isNullOrEmpty()) {
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                breakdown.forEach { (key, value) ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = BREAKDOWN_LABELS[key] ?: key,
                            color = Color(theme.textSecondary),
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f),
                        )
                        Text(
                            text = "$value",
                            color = Color(theme.textPrimary),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        )
                    }
                }
            }
        }
    }
}
