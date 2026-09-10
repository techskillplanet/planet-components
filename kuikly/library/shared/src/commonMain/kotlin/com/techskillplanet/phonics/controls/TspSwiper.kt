package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.CircleShape
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
fun TspSwiper(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    items: List<String> = emptyList(),
    index: Int = 0,
    autoplay: Boolean = false,
    onChange: (Int) -> Unit = {},
) {
    val list = items
    var current by remember(index, list.size) {
        mutableStateOf(index.coerceIn(0, (list.size - 1).coerceAtLeast(0)))
    }
    fun go(next: Int) {
        if (list.isEmpty()) return
        val n = ((next % list.size) + list.size) % list.size
        current = n
        onChange(n)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(14.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(14.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = list.getOrNull(current).orEmpty(),
                color = Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
            )
            if (list.size > 1) {
                Text(
                    text = "‹",
                    color = Color(theme.textPrimary),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { go(current - 1) }
                        .padding(8.dp),
                )
                Text(
                    text = "›",
                    color = Color(theme.textPrimary),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { go(current + 1) }
                        .padding(8.dp),
                )
            }
        }
        if (list.size > 1) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                list.indices.forEach { i ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                Color(if (i == current) theme.brandPrimary else theme.borderDefault),
                                CircleShape,
                            )
                            .clickable { go(i) },
                    )
                }
            }
        }
    }
}
