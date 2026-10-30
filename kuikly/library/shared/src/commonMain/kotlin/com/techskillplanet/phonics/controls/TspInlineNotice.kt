package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.offset
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.draw.clip
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.Dp
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp

enum class TspInlineNoticeTone { Warning, Error, Info }

/**
 * 行内提示条。外层 Box 铺底 + 文案 lineHeight=字号（防 H5 字贴顶底空一大截）。
 */
@Composable
fun TspInlineNotice(
    message: String,
    modifier: Modifier = Modifier,
    tone: TspInlineNoticeTone = TspInlineNoticeTone.Warning,
    textAlign: TextAlign = TextAlign.Start,
    fontSizeSp: Int = 12,
    radius: Dp = 10.dp,
    onClick: (() -> Unit)? = null,
) {
    val text = message.trim()
    if (text.isEmpty()) return
    val bg = when (tone) {
        TspInlineNoticeTone.Warning -> Color(0xFFFFF4DF)
        TspInlineNoticeTone.Error -> Color(0xFFFFE8F2)
        TspInlineNoticeTone.Info -> Color(0xFFD9F0FF)
    }
    val fg = when (tone) {
        TspInlineNoticeTone.Warning -> Color(0xFF9A6700)
        TspInlineNoticeTone.Error -> Color(0xFFB42318)
        TspInlineNoticeTone.Info -> Color(0xFF173A62)
    }
    val shape = RoundedCornerShape(radius)
    val fontSize = fontSizeSp.sp
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(bg, shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = fg,
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            lineHeight = fontSize,
            textAlign = textAlign,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-1).dp),
        )
    }
}
