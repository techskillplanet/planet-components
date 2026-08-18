package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.window.Dialog
import com.techskillplanet.phonics.theme.PhonicsColors

internal fun tspOptionText(option: Any): String = option.toString()

@Composable
fun TspOptionSheet(
    options: List<Any>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    title: String = "请选择",
    selectedIndex: Int = 0,
    visible: Boolean = false,
    bottomInset: Float = 0f,
    onSelect: (Int, Any) -> Unit = { _, _ -> },
    onCancel: () -> Unit = {},
) {
    if (!visible) return
    Dialog(onDismissRequest = onCancel) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0x66000000))
            .clickable { onCancel() },
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(theme.surfaceRaised), RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                .clickable { }
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .padding(bottom = bottomInset.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "取消",
                    color = Color(theme.brandPrimary),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onCancel() },
                )
                Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
                Text(text = " ", color = Color(theme.textPrimary))
            }
            options.forEachIndexed { index, option ->
                val selected = index == selectedIndex
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(if (selected) theme.brandPrimary else theme.surfaceRaised),
                            RoundedCornerShape(12.dp),
                        )
                        .clickable { onSelect(index, option) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (selected) {
                        TspCheckIcon(color = Color.White)
                    } else {
                        Box(modifier = Modifier.size(16.dp))
                    }
                    Text(
                        text = tspOptionText(option),
                        color = if (selected) Color.White else Color(theme.textPrimary),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
    }
}
