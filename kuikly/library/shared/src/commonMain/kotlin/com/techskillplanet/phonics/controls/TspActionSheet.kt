package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.defaultMinSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.draw.clip
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.tencent.kuikly.compose.ui.window.Dialog
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspActionSheetItem(
    val id: String,
    val label: String,
    val enabled: Boolean = true,
    val destructive: Boolean = false,
)

/**
 * Action Sheet 面板（无 Dialog）：供壳层 [Hoist] 盖底栏复用。
 */
@Composable
fun TspActionSheetPanel(
    theme: PhonicsColors,
    items: List<TspActionSheetItem>,
    modifier: Modifier = Modifier,
    title: String = "",
    cancelText: String = "取消",
    bottomInsetDp: Float = 10f,
    onDismiss: () -> Unit = {},
    onSelect: (String) -> Unit = {},
) {
    if (items.isEmpty()) return
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = bottomInsetDp.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        val cardShape = RoundedCornerShape(14.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(theme.surfaceRaised), cardShape)
                .clip(cardShape),
        ) {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    color = Color(theme.textTertiary),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                )
                SheetHairline(theme)
            }
            items.forEachIndexed { index, item ->
                if (index > 0) SheetHairline(theme)
                val labelColor = when {
                    !item.enabled -> Color(theme.textTertiary)
                    item.destructive -> Color(theme.danger)
                    else -> Color(theme.textPrimary)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 52.dp)
                        .clickable(enabled = item.enabled) { onSelect(item.id) }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = item.label,
                        color = labelColor,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 52.dp)
                .background(Color(theme.surfaceRaised), RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .clickable(onClick = onDismiss)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = cancelText,
                color = Color(theme.brandDark),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

/**
 * iOS 风格 Action Sheet（Dialog 版）。
 * 与 [TspOptionSheet]（带选中勾的选择器）不同。
 * 壳内盖底栏请用 [TspActionSheetPanel] + 宿主 hoist。
 */
@Composable
fun TspActionSheet(
    theme: PhonicsColors,
    items: List<TspActionSheetItem>,
    modifier: Modifier = Modifier,
    title: String = "",
    cancelText: String = "取消",
    visible: Boolean = true,
    bottomInsetDp: Float = 10f,
    dismissOnSelect: Boolean = true,
    onDismiss: () -> Unit = {},
    onSelect: (String) -> Unit = {},
) {
    if (!visible || items.isEmpty()) return
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0x88000000))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.BottomCenter,
        ) {
            // 阻断蒙层点击穿透到面板外逻辑：点面板空白不关
            Box(modifier = Modifier.clickable { }) {
                TspActionSheetPanel(
                    theme = theme,
                    items = items,
                    title = title,
                    cancelText = cancelText,
                    bottomInsetDp = bottomInsetDp,
                    onDismiss = onDismiss,
                    onSelect = { id ->
                        onSelect(id)
                        if (dismissOnSelect) onDismiss()
                    },
                )
            }
        }
    }
}

@Composable
private fun SheetHairline(theme: PhonicsColors) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(theme.borderDefault).copy(alpha = 0.35f)),
    )
}
