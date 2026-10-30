package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.defaultMinSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

/**
 * 对齐 Web `.bc-tabs`：轨道铺满，单项按文字宽度（flex: 0 0 auto），不高分。
 * 选中项高 44、左右 16、字号 16 / 字重 800；轨道总高 ≈ 54（pad 4+4）。
 */
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        tabs.forEachIndexed { index, tab ->
            val selected = index == selectedIndex
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .defaultMinSize(minWidth = 44.dp)
                    .background(
                        if (selected) Color(theme.brandPrimary) else Color.Transparent,
                        RoundedCornerShape(999.dp),
                    )
                    .clickable { onSelect(index, tab) }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = tab,
                    color = if (selected) Color.White else Color(theme.textSecondary),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W800,
                    lineHeight = 22.sp,
                    maxLines = 1,
                    softWrap = false,
                )
            }
        }
    }
}
