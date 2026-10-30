package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors

/** Solid：实心主色+白字（Ant 默认段控）；Soft：浅品牌底+品牌字（书页 Tab / 筛选）. */
enum class TspSegmentedAppearance { Solid, Soft }

@Composable
fun TspSegmentedControl(
    options: List<String>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    disabled: Boolean = false,
    appearance: TspSegmentedAppearance = TspSegmentedAppearance.Solid,
    /** @deprecated 用 [appearance]；保留字符串以免旧调用崩 */
    variant: String = "default",
    onSelect: (Int, String) -> Unit = { _, _ -> },
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedVariant = variant
    if (appearance == TspSegmentedAppearance.Soft) {
        // 等分独立胶囊（非一条轨道），对齐书页 BookTab
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            options.forEachIndexed { index, label ->
                val selected = index == selectedIndex
                val shape = RoundedCornerShape(999.dp)
                val bg = when {
                    disabled -> theme.surfaceMuted
                    selected -> theme.brandSubtle
                    else -> theme.surfaceRaised
                }
                val stroke = when {
                    disabled -> theme.borderDefault
                    selected -> theme.brandPrimary
                    else -> theme.borderDefault
                }
                val fg = when {
                    disabled -> theme.textTertiary
                    selected -> theme.brandDark
                    else -> theme.textPrimary
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .border(1.dp, Color(stroke), shape)
                        .clickable(enabled = !disabled) { onSelect(index, label) },
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color(bg), shape),
                    )
                    Text(
                        text = label,
                        color = Color(fg),
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        softWrap = false,
                    )
                }
            }
        }
        return
    }
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
