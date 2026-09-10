package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.CircleShape
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
@Suppress("UNUSED_PARAMETER")
fun TspSkeleton(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    rows: Int = 3,
    animated: Boolean = true,
    avatar: Boolean = false,
    variant: String = "default",
) {
    val count = rows.coerceIn(1, 12)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        if (avatar) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(theme.surfaceSubtle), CircleShape),
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            repeat(count) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(if (index == count - 1) 0.62f else 1f)
                        .height(14.dp)
                        .background(Color(theme.surfaceSubtle), RoundedCornerShape(8.dp)),
                )
            }
        }
    }
}
