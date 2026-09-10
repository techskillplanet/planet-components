package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspStarRating(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    value: Int = 0,
    max: Int = 5,
    disabled: Boolean = false,
    variant: String = "default",
    onChange: (Int) -> Unit = {},
) {
    val count = max.coerceIn(1, 10)
    val readonly = variant == "readonly" || disabled
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (i in 1..count) {
            val filled = i <= value
            Text(
                text = "★",
                color = Color(if (filled) theme.brandPrimary else theme.borderDefault),
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(2.dp)
                    .clickable(enabled = !readonly) { onChange(i) },
            )
        }
    }
}
