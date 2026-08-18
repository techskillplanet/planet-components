package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
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
@Suppress("UNUSED_PARAMETER")
fun TspSwitch(
    checked: Boolean,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    text: String = "",
    checkedText: String = "ON",
    uncheckedText: String = "OFF",
    loading: Boolean = false,
    disabled: Boolean = false,
    variant: String = "md",
    onChange: (Boolean) -> Unit = {},
) {
    val isSm = variant == "sm" || variant == "small"
    val width = if (isSm) 40 else 52
    val height = if (isSm) 22 else 28
    val handle = if (isSm) 18 else 24
    val inset = 2
    val travel = width - handle - inset * 2
    val blocked = disabled || loading
    val trackColor = if (checked) theme.switchOnBackground else theme.switchOffBackground
    Row(
        modifier = modifier.clickable(enabled = !blocked) { onChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (text.isNotEmpty()) {
            Text(
                text = text,
                color = Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
        }
        Box(
            modifier = Modifier
                .width(width.dp)
                .height(height.dp)
                .background(Color(trackColor), RoundedCornerShape(999.dp))
                .padding(horizontal = inset.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (checked) {
                    Spacer(modifier = Modifier.width(travel.dp))
                }
                Box(
                    modifier = Modifier
                        .size(handle.dp)
                        .background(Color(theme.switchHandleBackground), RoundedCornerShape(999.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    if (loading) {
                        Text(
                            text = "…",
                            color = Color(if (checked) 0xFFFFFFFF else theme.brandDark),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}
