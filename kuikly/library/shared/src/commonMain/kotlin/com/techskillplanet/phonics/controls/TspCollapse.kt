package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspCollapse(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    title: String = "",
    message: String = "",
    expanded: Boolean = false,
    disabled: Boolean = false,
    variant: String = "default",
    onChange: (Boolean) -> Unit = {},
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedVariant = variant
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(
                1.dp,
                Color(if (expanded) theme.brandPrimary else theme.borderDefault),
                RoundedCornerShape(16.dp),
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !disabled) { onChange(!expanded) }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (expanded) "−" else "+",
                    color = Color(theme.brandPrimary),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        if (expanded) {
            Text(
                text = message,
                color = Color(if (disabled) theme.textTertiary else theme.textSecondary),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
            )
        }
    }
}
