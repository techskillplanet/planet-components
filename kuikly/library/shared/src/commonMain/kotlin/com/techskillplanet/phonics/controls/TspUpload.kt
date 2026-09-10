package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

data class TspUploadFile(
    val id: String,
    val name: String,
)

@Composable
fun TspUpload(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    files: List<TspUploadFile> = emptyList(),
    multiple: Boolean = true,
    disabled: Boolean = false,
    accept: String = "",
    onChange: (List<TspUploadFile>) -> Unit = {},
    onRemove: (TspUploadFile) -> Unit = {},
    onPickRequest: (() -> Unit)? = null,
) {
    @Suppress("UNUSED_VARIABLE")
    val unusedAccept = accept
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "选择文件",
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(
                    Color(if (disabled) theme.surfaceMuted else theme.brandPrimary),
                    RoundedCornerShape(999.dp),
                )
                .clickable(enabled = !disabled) {
                    if (onPickRequest != null) {
                        onPickRequest()
                    } else {
                        val demo = TspUploadFile(
                            id = "demo-${files.size + 1}",
                            name = "file-${files.size + 1}.txt",
                        )
                        onChange(if (multiple) files + demo else listOf(demo))
                    }
                }
                .padding(horizontal = 16.dp, vertical = 10.dp),
        )
        if (files.isEmpty()) {
            Text(text = "尚未选择文件", color = Color(theme.textTertiary), fontWeight = FontWeight.Bold)
        } else {
            files.forEach { file ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(theme.surfaceSubtle), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = file.name,
                        color = Color(theme.textPrimary),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = "×",
                        color = Color(theme.textSecondary),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable(enabled = !disabled) {
                            onRemove(file)
                            onChange(files.filter { it.id != file.id })
                        },
                    )
                }
            }
        }
    }
}
