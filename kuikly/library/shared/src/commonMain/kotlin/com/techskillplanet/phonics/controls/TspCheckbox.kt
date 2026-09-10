package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
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
fun TspCheckbox(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    text: String = "",
    checked: Boolean = false,
    disabled: Boolean = false,
    variant: String = "default",
    onChange: (Boolean) -> Unit = {},
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedVariant = variant
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = !disabled) { onChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(
                    Color(if (checked) theme.brandPrimary else theme.surfaceRaised),
                    RoundedCornerShape(6.dp),
                )
                .border(
                    2.dp,
                    Color(if (checked) theme.brandPrimary else theme.borderDefault),
                    RoundedCornerShape(6.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        if (text.isNotEmpty()) {
            Text(
                text = text,
                color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
