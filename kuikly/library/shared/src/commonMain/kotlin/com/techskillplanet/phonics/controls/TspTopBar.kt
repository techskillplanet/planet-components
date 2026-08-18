package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspTopBar(
    title: String,
    theme: PhonicsColors,
    showBack: Boolean,
    backgroundColor: Long? = null,
    topInset: Float = 0f,
    immersive: Boolean = true,
    onBack: () -> Unit,
) {
    val inset = if (immersive) topInset.dp else 0.dp
    val fill = Color(backgroundColor ?: theme.surfaceRaised)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(fill)
            .padding(top = inset),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(enabled = showBack) { onBack() },
                    contentAlignment = Alignment.Center,
                ) {
                    if (showBack) {
                        TspBackChevron(color = Color(theme.brandPrimary), iconSize = 28.dp)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.size(48.dp))
            }
            Text(
                text = title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 52.dp)
                    .fillMaxWidth(),
                color = Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(theme.borderDefault)),
        )
    }
}
