package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class TspAlertVariant { Info, Success, Warning, Error }

@Composable
fun TspAlert(
    title: String,
    message: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspAlertVariant = TspAlertVariant.Info,
) {
    val fill = when (variant) {
        TspAlertVariant.Info -> theme.brandSoft
        TspAlertVariant.Success -> theme.selectedFill
        TspAlertVariant.Warning -> theme.activeFill
        TspAlertVariant.Error -> theme.dangerSoft
    }
    val stroke = when (variant) {
        TspAlertVariant.Info -> theme.brandPrimary
        TspAlertVariant.Success -> theme.success
        TspAlertVariant.Warning -> theme.warning
        TspAlertVariant.Error -> theme.danger
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(fill), RoundedCornerShape(12.dp))
            .border(1.dp, Color(stroke), RoundedCornerShape(12.dp))
            .padding(12.dp),
    ) {
        Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Text(text = message, color = Color(theme.textSecondary))
    }
}
