package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
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

@Composable
fun TspSearchBar(
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    placeholder: String = "Search…",
    disabled: Boolean = false,
    variant: String = "default",
    onChange: (String) -> Unit = {},
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedVariant = variant
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(Color(theme.surfaceRaised), RoundedCornerShape(999.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp))
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(text = "⌕", color = Color(theme.textTertiary))
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
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
}
