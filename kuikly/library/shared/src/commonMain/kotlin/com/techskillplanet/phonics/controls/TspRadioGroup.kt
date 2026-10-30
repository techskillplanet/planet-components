package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspRadioOption(
    val id: String,
    val label: String,
    val enabled: Boolean = true,
)

/**
 * 单选组：按 id 选中；点一项即切换（不会把已选项点成未选）。
 */
@Composable
fun TspRadioGroup(
    theme: PhonicsColors,
    options: List<TspRadioOption>,
    selectedId: String?,
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEach { opt ->
            TspRadio(
                theme = theme,
                text = opt.label,
                checked = opt.id == selectedId,
                disabled = !opt.enabled,
                onChange = { if (it) onChange(opt.id) },
            )
        }
    }
}
