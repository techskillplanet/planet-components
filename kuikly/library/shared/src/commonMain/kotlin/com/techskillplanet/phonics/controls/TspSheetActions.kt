package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors
import com.techskillplanet.phonics.theme.PhonicsStyleProfile

/**
 * 双列底栏：取消 | 确认。weight 槽内 fullWidth，避免 H5 第二钮被挤没。
 */
@Composable
fun TspSheetActions(
    theme: PhonicsColors,
    confirmText: String,
    modifier: Modifier = Modifier,
    cancelText: String = "取消",
    confirmEnabled: Boolean = true,
    confirmLoading: Boolean = false,
    style: PhonicsStyleProfile = PhonicsStyleProfile.IslandFlat,
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TspButton(
            text = cancelText,
            theme = theme,
            style = style,
            variant = TspButtonVariant.Default,
            size = TspControlSize.Md,
            modifier = Modifier.weight(1f),
            fullWidth = true,
            disabled = confirmLoading,
            onClick = onCancel,
        )
        TspButton(
            text = confirmText,
            theme = theme,
            style = style,
            variant = TspButtonVariant.Primary,
            size = TspControlSize.Md,
            modifier = Modifier.weight(1f),
            fullWidth = true,
            disabled = !confirmEnabled,
            loading = confirmLoading,
            onClick = onConfirm,
        )
    }
}
