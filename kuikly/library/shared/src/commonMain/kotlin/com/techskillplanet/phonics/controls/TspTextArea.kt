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
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.TextStyle
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspTextArea(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String = "",
    rows: Int = 3,
    maxLength: Int = Int.MAX_VALUE,
    disabled: Boolean = false,
    variant: String = "default",
    onChange: (String) -> Unit = {},
) {
    val error = variant == "error"
    val borderColor = when {
        error -> theme.danger
        else -> theme.borderDefault
    }
    val fieldHeight = (rows.coerceAtLeast(1) * 22 + 24).dp
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(fieldHeight)
            .background(
                Color(if (disabled) theme.surfaceMuted else theme.surfaceRaised),
                RoundedCornerShape(14.dp),
            )
            .border(1.dp, Color(borderColor), RoundedCornerShape(14.dp))
            .padding(12.dp),
    ) {
        if (value.isEmpty() && placeholder.isNotEmpty()) {
            Text(text = placeholder, color = Color(theme.textTertiary))
        }
        BasicTextField(
            value = value,
            onValueChange = { next ->
                if (!disabled) {
                    val clipped = if (maxLength > 0 && next.length > maxLength) next.take(maxLength) else next
                    onChange(clipped)
                }
            },
            enabled = !disabled,
            singleLine = false,
            textStyle = TextStyle(
                color = if (disabled) Color(theme.textTertiary) else Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
            ),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
