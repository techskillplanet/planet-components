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

@Composable
fun TspAmount(
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    symbol: String = "¥",
    cycle: String = "",
    symbolAfter: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        if (!symbolAfter) {
            Text(text = symbol, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(text = value, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        if (symbolAfter) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = symbol, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        }
        if (cycle.isNotEmpty()) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "/$cycle", color = Color(theme.textSecondary))
        }
    }
}
