package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.draw.clip
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

/**
 * 勾选：装饰层与对钩**兄弟**（勿把对钩画在 bordered 父级上 — 防 C1/L18 首帧糊偏）。
 * 无 [text] 时不撑满宽（防 L5 协议行左侧大空白热区）。
 */
@Composable
fun TspCheckbox(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    text: String = "",
    checked: Boolean = false,
    disabled: Boolean = false,
    variant: String = "default",
    size: TspControlSize = TspControlSize.Md,
    /** 覆盖盒边长（dp）；优先于 [size] */
    boxSizeDp: Float? = null,
    /** false：纯展示，由外层处理点击（如登录协议行） */
    interactive: Boolean = true,
    onChange: (Boolean) -> Unit = {},
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedVariant = variant
    val box = (boxSizeDp ?: size.checkboxBoxDp()).dp
    val borderW = if (box.value <= 16f) 1.5.dp else 2.dp
    val shape = RoundedCornerShape(if (box.value <= 16f) 3.dp else 4.dp)
    val stroke = when {
        disabled -> Color(theme.borderDefault)
        checked -> Color(theme.brandPrimary)
        else -> Color(theme.textSecondary)
    }
    val fill = when {
        disabled && checked -> Color(theme.surfaceMuted)
        checked -> Color(theme.brandPrimary)
        else -> Color.White
    }
    val inner = ((box.value - 2f * borderW.value).coerceAtLeast(8f)).dp
    val hasLabel = text.isNotEmpty()
    val rowModifier = modifier
        .then(if (hasLabel) Modifier.fillMaxWidth() else Modifier)
        .then(
            if (interactive && !disabled) {
                Modifier.clickable { onChange(!checked) }
            } else {
                Modifier
            },
        )
        .then(if (hasLabel) Modifier.padding(vertical = 4.dp) else Modifier)

    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(if (hasLabel) 10.dp else 0.dp),
    ) {
        Box(
            modifier = Modifier.size(box),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(shape)
                    .background(fill, shape)
                    .border(borderW, stroke, shape),
            )
            if (checked) {
                TspFaceLabel(
                    text = "✓",
                    color = Color.White,
                    fontSize = (inner.value * 0.72f).sp,
                    fontWeight = FontWeight.Black,
                )
            }
        }
        if (hasLabel) {
            Text(
                text = text,
                color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
