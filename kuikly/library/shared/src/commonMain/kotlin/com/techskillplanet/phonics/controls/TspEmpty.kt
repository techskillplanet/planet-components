package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
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
fun TspEmpty(
    title: String,
    message: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    actionText: String = "",
    onAction: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(Color(theme.brandSoft), RoundedCornerShape(999.dp)),
            contentAlignment = Alignment.Center,
        ) {
            TspEmptyMark(color = Color(theme.brandPrimary), iconSize = 20.dp)
        }
        Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Text(text = message, color = Color(theme.textSecondary))
        if (actionText.isNotEmpty()) {
            TspButton(
                text = actionText,
                theme = theme,
                variant = TspButtonVariant.Primary,
                onClick = onAction,
            )
        }
    }
}
