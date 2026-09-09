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
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspScoreRule(
    val id: String = "",
    val name: String = "",
    val icon: String = "",
    val value: Int = 0,
    val count: Int = 0,
    val dailyLimit: Int? = null,
)

enum class TspScoreRuleGridVariant { Default, ReadOnly }

@Composable
fun TspScoreRuleGrid(
    rules: List<TspScoreRule>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    variant: TspScoreRuleGridVariant = TspScoreRuleGridVariant.Default,
    disabled: Boolean = false,
    onIncrement: (TspScoreRule) -> Unit = {},
) {
    val readOnly = variant == TspScoreRuleGridVariant.ReadOnly || disabled
    val cols = columns.coerceAtLeast(1)
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        rules.chunked(cols).forEach { rowRules ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                rowRules.forEach { rule ->
                    val atLimit = rule.dailyLimit != null && rule.count >= rule.dailyLimit
                    val valueText = if (rule.value > 0) "+${rule.value}" else "${rule.value}"
                    val meta = if (rule.dailyLimit == null) {
                        "已记 ${rule.count}"
                    } else {
                        "已记 ${rule.count}/${rule.dailyLimit}"
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color(theme.surfaceRaised), RoundedCornerShape(18.dp))
                            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(18.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            if (rule.icon.isNotEmpty()) {
                                Text(text = rule.icon)
                            }
                            Text(
                                text = rule.name,
                                color = Color(theme.textPrimary),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f),
                            )
                        }
                        Text(
                            text = valueText,
                            color = Color(if (rule.value >= 0) theme.success else theme.danger),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                        )
                        Text(text = meta, color = Color(theme.textTertiary), fontSize = 12.sp)
                        if (!readOnly) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp)
                                    .background(Color(theme.selectedFill), RoundedCornerShape(999.dp))
                                    .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp))
                                    .clickable(enabled = !atLimit && !disabled) { onIncrement(rule) },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "+1",
                                    color = Color(if (atLimit || disabled) theme.textTertiary else theme.brandDark),
                                    fontWeight = FontWeight.Bold,
                                )
                            }
                        }
                    }
                }
                repeat(cols - rowRules.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
