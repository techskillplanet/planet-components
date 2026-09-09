package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspRedeemItem(
    val id: String = "",
    val name: String = "",
    val icon: String = "🎁",
    val cost: Int = 0,
)

@Composable
fun TspRedeemCardGrid(
    items: List<TspRedeemItem>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    availablePoints: Int = 0,
    frozen: Boolean = false,
    disabled: Boolean = false,
    columns: Int = 2,
    onRedeem: (TspRedeemItem) -> Unit = {},
) {
    val cols = columns.coerceAtLeast(1)
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        if (frozen) {
            Text(
                text = "今日已冻结，暂不可兑换",
                color = Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(theme.activeFill), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            )
        }
        items.chunked(cols).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                rowItems.forEach { item ->
                    val insufficient = availablePoints < item.cost
                    val blocked = frozen || disabled || insufficient
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                            .background(Color(theme.surfaceRaised), RoundedCornerShape(18.dp))
                            .border(2.dp, Color(theme.borderDefault), RoundedCornerShape(18.dp))
                            .clickable(enabled = !blocked) { onRedeem(item) }
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(text = item.icon.ifEmpty { "🎁" }, fontSize = 28.sp)
                        Text(
                            text = item.name,
                            color = Color(if (blocked) theme.textTertiary else theme.textPrimary),
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = "${item.cost} 分",
                            color = Color(theme.brandPrimary),
                            fontWeight = FontWeight.Bold,
                        )
                        if (insufficient && !frozen) {
                            Text(text = "积分不足", color = Color(theme.danger), fontSize = 12.sp)
                        }
                    }
                }
                repeat(cols - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
