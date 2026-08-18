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
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.TextStyle
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspInputVariant { Default, Error }

@Composable
fun TspInput(
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    variant: TspInputVariant = TspInputVariant.Default,
    disabled: Boolean = false,
    onChange: (String) -> Unit = {},
) {
    val borderColor = if (variant == TspInputVariant.Error) theme.danger else theme.borderDefault
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                Color(if (disabled) theme.surfaceMuted else theme.surfaceRaised),
                RoundedCornerShape(14.dp),
            )
            .border(1.dp, Color(borderColor), RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (value.isEmpty() && placeholder.isNotEmpty()) {
            Text(text = placeholder, color = Color(theme.textTertiary))
        }
        BasicTextField(
            value = value,
            onValueChange = { next -> if (!disabled) onChange(next) },
            enabled = !disabled,
            singleLine = true,
            textStyle = TextStyle(
                color = if (disabled) Color(theme.textTertiary) else Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
