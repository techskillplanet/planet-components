package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
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

enum class TspLoadingDialogVariant { Default, Compact }

@Composable
fun TspLoadingDialog(
    visible: Boolean,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    message: String = "加载中...",
    variant: TspLoadingDialogVariant = TspLoadingDialogVariant.Default,
    dismissible: Boolean = false,
    onDismiss: () -> Unit = {},
) {
    if (!visible) return
    val compact = variant == TspLoadingDialogVariant.Compact
    Box(
        modifier = modifier
            .background(Color(0x66000000))
            .clickable(enabled = dismissible) { onDismiss() }
            .padding(if (compact) 12.dp else 24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
                .clickable { }
                .padding(if (compact) 12.dp else 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(if (compact) 8.dp else 12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(if (compact) 18.dp else 28.dp)
                    .background(Color(theme.brandPrimary), RoundedCornerShape(999.dp)),
            )
            if (message.isNotEmpty()) {
                Text(text = message, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
            }
        }
    }
}
