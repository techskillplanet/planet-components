package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
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
fun TspListItem(
    title: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    message: String = "",
    trailing: String = "",
    selected: Boolean = false,
    disabled: Boolean = false,
    onTap: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color(if (selected) theme.selectedFill else theme.surfaceRaised),
                RoundedCornerShape(16.dp),
            )
            .border(
                1.dp,
                Color(if (selected) theme.success else theme.borderDefault),
                RoundedCornerShape(16.dp),
            )
            .clickable(enabled = !disabled) { onTap() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
                fontWeight = FontWeight.Bold,
            )
            if (message.isNotEmpty()) {
                Text(text = message, color = Color(theme.textSecondary))
            }
        }
        if (trailing.isNotEmpty()) {
            Text(text = trailing, color = Color(theme.textTertiary))
        }
    }
}
