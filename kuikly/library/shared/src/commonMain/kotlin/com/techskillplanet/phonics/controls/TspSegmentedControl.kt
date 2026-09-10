package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspSegmentedControl(
    options: List<String>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    disabled: Boolean = false,
    variant: String = "default",
    onSelect: (Int, String) -> Unit = { _, _ -> },
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedVariant = variant
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceSubtle), RoundedCornerShape(999.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        options.forEachIndexed { index, label ->
            val selected = index == selectedIndex
            Text(
                text = label,
                color = when {
                    disabled -> Color(theme.textTertiary)
                    selected -> Color.White
                    else -> Color(theme.textSecondary)
                },
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Color(
                            when {
                                selected && !disabled -> theme.brandPrimary
                                else -> theme.surfaceSubtle
                            },
                        ),
                        RoundedCornerShape(999.dp),
                    )
                    .clickable(enabled = !disabled) { onSelect(index, label) }
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
            )
        }
    }
}
