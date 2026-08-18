package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
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
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

@Composable
fun TspSelect(
    options: List<Any>,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    disabled: Boolean = false,
    bottomInset: Float = 0f,
    onSelect: (Int, Any) -> Unit = { _, _ -> },
) {
    val open = remember { mutableStateOf(false) }
    val label = options.getOrNull(selectedIndex)?.let(::tspOptionText).orEmpty()
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(theme.surfaceRaised), RoundedCornerShape(14.dp))
                .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(14.dp))
                .clickable(enabled = !disabled) { open.value = true }
                .padding(horizontal = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                color = Color(if (disabled) theme.textTertiary else theme.textPrimary),
                fontWeight = FontWeight.Bold,
            )
            TspChevronRight(color = Color(theme.textTertiary))
        }
        TspOptionSheet(
            title = "请选择",
            options = options,
            selectedIndex = selectedIndex,
            visible = open.value,
            theme = theme,
            bottomInset = bottomInset,
            onCancel = { open.value = false },
            onSelect = { index, option ->
                open.value = false
                onSelect(index, option)
            },
        )
    }
}
