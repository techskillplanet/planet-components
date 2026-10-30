package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.defaultMinSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.wrapContentWidth
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

/** Soft：浅品牌底；实心选中见 [solidSelected]。 */
enum class TspChipVariant { Default, Primary, Soft, Success, Warning, Danger }

@Composable
fun TspChip(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: TspChipVariant = TspChipVariant.Default,
    selected: Boolean = false,
    disabled: Boolean = false,
    /** 选中时实心主色 + 白字（计划分类芯片） */
    solidSelected: Boolean = false,
    /** 周几等单字：更矮更紧 */
    compact: Boolean = false,
    expand: Boolean = false,
    /** 覆盖高度（dp）；Study 大字模式传 scaled 值 */
    heightDp: Float? = null,
    /** 覆盖字号（sp）；与 [heightDp] 配套 */
    fontSizeSp: Float? = null,
    /** 圆角 dp；默认胶囊 999；听写 mode 等用 12 */
    cornerRadiusDp: Float? = null,
    /**
     * 未选中时用中性描边/主文色（borderDefault + textPrimary），
     * 对齐听写 `.tt-mode`；默认 false 保持筛选芯片浅品牌描边。
     */
    neutralUnselected: Boolean = false,
    onTap: (() -> Unit)? = null,
) {
    val solid = selected && solidSelected && !disabled
    val fillColor = when {
        disabled -> Color(theme.surfaceMuted)
        solid -> Color(theme.brandPrimary)
        selected -> Color(theme.brandSubtle)
        variant == TspChipVariant.Primary || variant == TspChipVariant.Soft -> Color(theme.brandSoft)
        variant == TspChipVariant.Success -> Color(theme.selectedFill)
        variant == TspChipVariant.Warning -> Color(theme.activeFill)
        variant == TspChipVariant.Danger -> Color(theme.dangerSoft)
        else -> Color(theme.surfaceRaised)
    }
    val strokeColor = when {
        disabled -> Color(theme.borderDefault)
        solid -> Color.Transparent
        selected -> Color(theme.brandPrimary)
        variant == TspChipVariant.Success -> Color(theme.success)
        variant == TspChipVariant.Primary || variant == TspChipVariant.Soft -> Color(theme.brandPrimary)
        variant == TspChipVariant.Warning -> Color(theme.warning)
        variant == TspChipVariant.Danger -> Color(theme.danger)
        neutralUnselected -> Color(theme.borderDefault)
        else -> Color(theme.brandPrimary).copy(alpha = 0.28f)
    }
    val textColor = when {
        disabled -> Color(theme.textTertiary)
        solid -> Color.White
        selected -> Color(theme.brandDark)
        variant == TspChipVariant.Danger -> Color(theme.danger)
        variant == TspChipVariant.Primary || variant == TspChipVariant.Soft -> Color(theme.brandDark)
        neutralUnselected -> Color(theme.textPrimary)
        else -> Color(theme.textSecondary)
    }
    val chipH = (heightDp ?: if (compact) 36f else 44f).dp
    val padH = if (compact) 10.dp else 14.dp
    val fontSz = (fontSizeSp ?: if (compact) 12f else 13f).sp
    val minW = if (compact) (heightDp ?: 36f).dp else 0.dp
    val shape = RoundedCornerShape((cornerRadiusDp ?: 999f).dp)
    val base = if (onTap != null && !disabled) modifier.clickable { onTap() } else modifier
    // 外描边 + 内铺色分两层（H5：bg/border 叠同一 Text 父级易糊边）
    Box(
        modifier = base
            .then(if (expand) Modifier.fillMaxWidth() else Modifier.wrapContentWidth())
            .height(chipH)
            .defaultMinSize(minWidth = minW)
            .border(1.dp, strokeColor, shape),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(fillColor, shape),
        )
        Text(
            text = text,
            color = textColor,
            fontSize = fontSz,
            lineHeight = fontSz,
            fontWeight = if (selected) FontWeight.Black else FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.padding(horizontal = padH),
        )
    }
}
