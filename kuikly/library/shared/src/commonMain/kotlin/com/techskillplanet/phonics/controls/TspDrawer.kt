package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.window.Dialog
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspDrawer(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    visible: Boolean = false,
    title: String = "",
    placement: String = "bottom",
    onClose: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    if (!visible) return
    val align = if (placement == "top") Alignment.TopCenter else Alignment.BottomCenter
    val shape = if (placement == "top") {
        RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp)
    } else {
        RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
    }
    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0x66000000))
                .clickable { onClose() },
            contentAlignment = align,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(theme.surfaceRaised), shape)
                    .clickable { }
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                if (title.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
                        Text(
                            text = "×",
                            color = Color(theme.textSecondary),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { onClose() },
                        )
                    }
                }
                content()
            }
        }
    }
}
