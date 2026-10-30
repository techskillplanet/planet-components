package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.foundation.text.BasicTextField
import com.tencent.kuikly.compose.foundation.text.KeyboardOptions
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.TextStyle
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.input.KeyboardType
import com.tencent.kuikly.compose.ui.text.input.PasswordVisualTransformation
import com.tencent.kuikly.compose.ui.text.input.VisualTransformation
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspInputVariant { Default, Error }

/**
 * 对齐 Web `.bc-input`：高 48、圆角 16、字号 16。
 *
 * [secure]=true 时：
 * - `KeyboardType.Password` → H5 原生 `input type=password`（仅 VisualTransformation 在 H5 不遮罩）
 * - `PasswordVisualTransformation` → 原生端密文
 * 显隐切换须显式切回 `KeyboardType.Text`，否则 DOM type 可能停在 password。
 */
@Composable
fun TspInput(
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    variant: TspInputVariant = TspInputVariant.Default,
    size: TspControlSize = TspControlSize.Md,
    disabled: Boolean = false,
    secure: Boolean = false,
    contentPaddingEnd: Int = 14,
    /** 覆盖高度；优先于 [size] */
    heightDp: Float? = null,
    onChange: (String) -> Unit = {},
) {
    val borderColor = if (variant == TspInputVariant.Error) theme.danger else theme.borderDefault
    val faceH = (heightDp ?: size.inputHeightDp()).dp
    val fontSp = size.inputFontSp().sp
    val lineSp = (size.inputFontSp() + 6f).sp
    val shape = RoundedCornerShape(16.dp)
    // H5：bg+border 勿叠在同一可编辑层上，否则常出现只剩左边框竖线、面消失
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(faceH)
            .border(1.dp, Color(borderColor), shape),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Color(if (disabled) theme.surfaceMuted else theme.surfaceRaised),
                    shape,
                ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = contentPaddingEnd.dp),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (value.isEmpty() && placeholder.isNotEmpty()) {
                Text(
                    text = placeholder,
                    color = Color(theme.textTertiary),
                    fontSize = fontSp,
                    lineHeight = lineSp,
                    fontWeight = FontWeight.Normal,
                )
            }
            BasicTextField(
                value = value,
                onValueChange = { next -> if (!disabled) onChange(next) },
                enabled = !disabled,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (secure) KeyboardType.Password else KeyboardType.Text,
                ),
                visualTransformation = if (secure) PasswordVisualTransformation() else VisualTransformation.None,
                textStyle = TextStyle(
                    color = if (disabled) Color(theme.textTertiary) else Color(theme.textPrimary),
                    fontSize = fontSp,
                    lineHeight = lineSp,
                    fontWeight = FontWeight.Normal,
                ),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
