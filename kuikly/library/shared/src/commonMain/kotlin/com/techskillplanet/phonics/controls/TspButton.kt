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
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.layout.wrapContentWidth
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp
import com.techskillplanet.phonics.theme.PhonicsColors
import com.techskillplanet.phonics.theme.PhonicsStyleProfile

/** Soft：探索气质用浅品牌底（非抬升）；落地须走组件 API（SX）。 */
enum class TspButtonVariant { Primary, Default, Soft, Danger, Text, Link }

/**
 * 对齐 Web `TspButton`：面高走 style.buttonFaceHeight（默认 46）。
 * `buttonRaisedShadowEnabled == false`（产品 `withFlatButtons` / island_flat）时不画底影。
 */
@Composable
fun TspButton(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    style: PhonicsStyleProfile = PhonicsStyleProfile.IslandRaised,
    variant: TspButtonVariant = TspButtonVariant.Default,
    size: TspControlSize = TspControlSize.Md,
    disabled: Boolean = false,
    loading: Boolean = false,
    fullWidth: Boolean = true,
    /** 覆盖面高；优先于 [size]；如计划留言 Web `.plan-notes` 强制 44 */
    faceHeightDp: Float? = null,
    /** 覆盖圆角；全宽场景常用 14 避免胶囊在宽列上「压扁」 */
    cornerRadiusDp: Float? = null,
    onClick: () -> Unit,
) {
    val inert = disabled || loading
    val flatVariant = variant == TspButtonVariant.Text || variant == TspButtonVariant.Link
    val raised = style.buttonRaisedShadowEnabled && !flatVariant
    val faceHeight = (
        faceHeightDp
            ?: size.buttonFaceHeightDp(style.buttonFaceHeight)
    ).dp
    val corner = (cornerRadiusDp ?: 999f).dp
    // N-14：定高面内 lineHeight=字号，避免 H5/CJK 光学偏上
    val labelSp = when (size) {
        TspControlSize.Sm -> 14.sp
        TspControlSize.Md -> 16.sp
        TspControlSize.Lg -> 17.sp
    }
    val lift = if (raised) style.shadowControlIslandLiftY.dp else 0.dp
    val faceColor = when {
        disabled -> Color(theme.surfaceMuted)
        variant == TspButtonVariant.Primary -> Color(theme.brandPrimary)
        variant == TspButtonVariant.Soft -> Color(theme.brandSoft)
        variant == TspButtonVariant.Danger -> Color(theme.danger)
        flatVariant -> Color.Transparent
        else -> Color(theme.surfaceRaised)
    }
    val textColor = when {
        disabled -> Color(theme.textTertiary)
        variant == TspButtonVariant.Primary || variant == TspButtonVariant.Danger -> Color.White
        variant == TspButtonVariant.Soft -> Color(theme.brandDark)
        flatVariant -> Color(theme.brandPrimary)
        else -> Color(theme.textPrimary)
    }
    val borderColor = when {
        flatVariant -> Color.Transparent
        variant == TspButtonVariant.Primary -> mixRgb(theme.brandPrimary, theme.brandDark, 0.35f)
        variant == TspButtonVariant.Soft -> mixRgb(theme.brandPrimary, theme.brandDark, 0.22f)
        variant == TspButtonVariant.Danger -> mixRgb(theme.danger, 0xFF000000, 0.30f)
        else -> Color(theme.borderDefault)
    }
    val widthModifier = if (fullWidth && !flatVariant) {
        modifier.fillMaxWidth()
    } else {
        // 非满宽：按文案收缩；禁止内层 fillMaxWidth 把 Row 剩余空间吃光（H5 会挤掉并排 Input）
        modifier.wrapContentWidth()
    }
    Box(modifier = widthModifier.height(faceHeight + lift)) {
        if (raised) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .then(
                        if (fullWidth && !flatVariant) Modifier.fillMaxWidth()
                        else Modifier.matchParentSize(),
                    )
                    .height(faceHeight)
                    .background(
                        mixRgb(theme.borderDefault, theme.brandPrimary, 0.38f),
                        RoundedCornerShape(corner),
                    ),
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .then(
                    if (fullWidth && !flatVariant) Modifier.fillMaxWidth()
                    else Modifier.wrapContentWidth(),
                )
                .height(faceHeight)
                .background(faceColor, RoundedCornerShape(corner))
                .border(1.dp, borderColor, RoundedCornerShape(corner))
                .clickable(enabled = !inert) { onClick() }
                // 满宽/缩宽一律水平垫：缩宽「Android 下载」忌字贴胶囊边；满宽居中亦不贴边
                .padding(horizontal = if (flatVariant) 10.dp else 18.dp),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (loading) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .border(2.dp, textColor, RoundedCornerShape(999.dp)),
                    )
                }
                TspFaceLabel(
                    text = text,
                    color = textColor,
                    fontSize = labelSp,
                    fontWeight = FontWeight.W800,
                )
            }
        }
    }
}

/** color-mix：overlayWeight 为 overlay 占比，其余为 base。 */
private fun mixRgb(base: Long, overlay: Long, overlayWeight: Float): Color {
    fun channel(value: Long, shift: Int): Int = ((value shr shift) and 0xFF).toInt()
    fun mix(shift: Int): Int {
        val o = channel(overlay, shift)
        val b = channel(base, shift)
        return (o * overlayWeight + b * (1f - overlayWeight)).toInt().coerceIn(0, 255)
    }
    val rgb = (mix(16) shl 16) or (mix(8) shl 8) or mix(0)
    return Color((0xFF000000L or rgb.toLong()))
}
