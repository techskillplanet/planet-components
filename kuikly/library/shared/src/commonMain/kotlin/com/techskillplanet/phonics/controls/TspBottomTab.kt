package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspBottomTab(
    tabs: List<TspTabItem>,
    selectedKey: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    bottomInset: Float = 0f,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised))
            .padding(bottom = bottomInset.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(theme.borderDefault)),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEach { tab ->
                val active = tab.key == selectedKey
                val color = Color(if (active) theme.brandPrimary else theme.textTertiary)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onSelect(tab.key) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TspTabGlyph(name = tab.icon, color = color)
                    Text(text = tab.label, color = color, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun TspTabGlyph(name: String, color: Color) {
    when (name) {
        "home", "⌂" -> TspHomeIcon(color = color)
        "settings", "⚙" -> TspSettingsIcon(color = color)
        else -> Text(text = name, color = color, fontSize = 16.sp)
    }
}
