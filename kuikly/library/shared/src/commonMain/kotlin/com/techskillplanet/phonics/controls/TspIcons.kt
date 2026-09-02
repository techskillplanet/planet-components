package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.Dp
import com.tencent.kuikly.compose.ui.unit.dp
import com.tencent.kuikly.compose.ui.unit.sp

/** Shared glyph icons used by Tsp* controls (one file for all marks). */

@Composable
fun TspBackChevron(color: Color, iconSize: Dp = 24.dp) {
    TspGlyphIcon(glyph = "‹", color = color, iconSize = iconSize)
}

@Composable
fun TspChevronRight(color: Color, iconSize: Dp = 16.dp) {
    TspGlyphIcon(glyph = "›", color = color, iconSize = iconSize)
}

@Composable
fun TspCheckIcon(color: Color, iconSize: Dp = 16.dp) {
    TspGlyphIcon(glyph = "✓", color = color, iconSize = iconSize)
}

@Composable
fun TspEmptyMark(color: Color, iconSize: Dp = 20.dp) {
    TspGlyphIcon(glyph = "∅", color = color, iconSize = iconSize)
}

@Composable
fun TspSpinnerMark(color: Color, iconSize: Dp = 12.dp) {
    TspGlyphIcon(glyph = "◌", color = color, iconSize = iconSize)
}

@Composable
fun TspHomeIcon(color: Color, iconSize: Dp = 18.dp) {
    TspGlyphIcon(glyph = "⌂", color = color, iconSize = iconSize)
}

@Composable
fun TspSettingsIcon(color: Color, iconSize: Dp = 18.dp) {
    TspGlyphIcon(glyph = "⚙", color = color, iconSize = iconSize)
}

@Composable
private fun TspGlyphIcon(glyph: String, color: Color, iconSize: Dp) {
    Box(
        modifier = Modifier.size(iconSize),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = glyph,
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = (iconSize.value * 0.75f).sp,
        )
    }
}
