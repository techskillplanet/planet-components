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

enum class TspStickyFooterVariant { Default, Subtle }

@Composable
fun TspStickyFooter(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspStickyFooterVariant = TspStickyFooterVariant.Default,
    bottomInset: Float = 0f,
    content: @Composable () -> Unit,
) {
    val fill = if (variant == TspStickyFooterVariant.Subtle) theme.surfaceMuted else theme.surfaceRaised
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(fill))
            .padding(bottom = bottomInset.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(theme.borderDefault)),
        )
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            content()
        }
    }
}
