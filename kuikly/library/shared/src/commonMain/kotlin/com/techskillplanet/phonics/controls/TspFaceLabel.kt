package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.text.style.TextAlign
import com.tencent.kuikly.compose.ui.text.style.TextOverflow
import com.tencent.kuikly.compose.ui.unit.TextUnit
import com.tencent.kuikly.compose.ui.unit.sp

/**
 * 定高面内单行标签（按钮 / Chip / Segmented / Badge / Tab 等）。
 *
 * 硬规则（N-14）：
 * - 父级须 `contentAlignment = Alignment.Center`（或等价 flex 居中）
 * - `lineHeight == fontSize`（H5/Kuikly 大行高会在定高盒里光学偏上）
 * - `TextAlign.Center` + `maxLines = 1` + `softWrap = false`
 */
@Composable
fun TspFaceLabel(
    text: String,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        lineHeight = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = 1,
        softWrap = false,
        overflow = TextOverflow.Clip,
        modifier = modifier,
    )
}

/** 关闭/清除等符号钮：固定热区内居中用。 */
@Composable
fun TspGlyphMark(
    glyph: String,
    color: Color,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    modifier: Modifier = Modifier,
) {
    TspFaceLabel(
        text = glyph,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        modifier = modifier,
    )
}
