package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.padding
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
fun TspTooltip(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    text: String = "",
    placement: String = "top",
    visible: Boolean? = null,
    content: @Composable () -> Unit = {},
) {
    var open by remember { mutableStateOf(false) }
    val shown = visible ?: open
    Box(modifier = modifier.clickable { if (visible == null) open = !open }) {
        content()
        if (shown && text.isNotEmpty()) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(
                        when (placement) {
                            "bottom" -> Alignment.BottomCenter
                            "left" -> Alignment.CenterStart
                            "right" -> Alignment.CenterEnd
                            else -> Alignment.TopCenter
                        },
                    )
                    .padding(
                        top = if (placement == "bottom") 28.dp else 0.dp,
                        bottom = if (placement == "top") 28.dp else 0.dp,
                    )
                    .background(Color(theme.brandDark), RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            )
        }
    }
}
