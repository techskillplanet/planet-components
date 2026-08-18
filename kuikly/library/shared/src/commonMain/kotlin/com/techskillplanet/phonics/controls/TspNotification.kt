package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
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

enum class TspNotificationVariant { Info, Alert }

@Composable
fun TspNotification(
    title: String,
    message: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspNotificationVariant = TspNotificationVariant.Info,
) {
    val fill = if (variant == TspNotificationVariant.Alert) theme.activeFill else theme.pageEnd
    val stroke = if (variant == TspNotificationVariant.Alert) theme.warning else theme.borderDefault
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(fill), RoundedCornerShape(18.dp))
            .border(1.dp, Color(stroke), RoundedCornerShape(18.dp))
            .padding(16.dp),
    ) {
        Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = message, color = Color(theme.textSecondary))
    }
}
