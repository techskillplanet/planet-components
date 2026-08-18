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
fun TspTabs(
    tabs: List<String>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    onSelect: (Int, String) -> Unit = { _, _ -> },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.pageEnd), RoundedCornerShape(999.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.forEachIndexed { index, tab ->
            val selected = index == selectedIndex
            Text(
                text = tab,
                color = if (selected) Color.White else Color(theme.textSecondary),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Color(if (selected) theme.brandPrimary else theme.pageEnd),
                        RoundedCornerShape(999.dp),
                    )
                    .clickable { onSelect(index, tab) }
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
            )
        }
    }
}
