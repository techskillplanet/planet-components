package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.foundation.text.BasicTextField
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.TextStyle
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

/**
 * 检索条（对齐 Ant Mobile / Vant Search：圆角条 + 放大镜 + 可清除）。
 * 默认文案家常「搜索」，勿用英文 Search… 进产品面。
 */
@Composable
fun TspSearchBar(
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    placeholder: String = "搜索",
    disabled: Boolean = false,
    /** 预留皮肤槽；当前仅 default */
    variant: String = "default",
    showClear: Boolean = true,
    onChange: (String) -> Unit = {},
    onClear: (() -> Unit)? = null,
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedVariant = variant
    val shape = RoundedCornerShape(999.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(Color(theme.surfaceRaised), shape)
            .border(1.dp, Color(theme.borderDefault), shape)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = "⌕", color = Color(theme.textTertiary), fontSize = 16.sp)
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            if (value.isEmpty() && placeholder.isNotEmpty()) {
                Text(text = placeholder, color = Color(theme.textTertiary), fontSize = 14.sp)
            }
            BasicTextField(
                value = value,
                onValueChange = { next -> if (!disabled) onChange(next) },
                enabled = !disabled,
                singleLine = true,
                textStyle = TextStyle(
                    color = if (disabled) Color(theme.textTertiary) else Color(theme.textPrimary),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
        if (showClear && value.isNotEmpty() && !disabled) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clickable {
                        onChange("")
                        onClear?.invoke()
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "×",
                    color = Color(theme.textTertiary),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                )
            }
        }
    }
}
