package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
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

enum class TspPrintSheetVariant { Pinyin, Meaning }

@Composable
fun TspPrintSheet(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    title: String = "",
    items: List<String> = emptyList(),
    columns: Int = 5,
    footerFields: List<String> = listOf("姓名", "日期", "得分"),
    variant: TspPrintSheetVariant = TspPrintSheetVariant.Pinyin,
) {
    val cols = columns.coerceAtLeast(1)
    val meaning = variant == TspPrintSheetVariant.Meaning
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        items.chunked(cols).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                rowItems.forEach { prompt ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(if (meaning) 56.dp else 52.dp)
                            .border(1.dp, Color(0xFF333333), RoundedCornerShape(0.dp))
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            text = prompt,
                            color = Color(theme.textSecondary),
                            fontSize = if (meaning) 12.sp else 11.sp,
                            fontWeight = if (meaning) FontWeight.Bold else FontWeight.Medium,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFF999999)),
                        )
                    }
                }
                repeat(cols - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        if (footerFields.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                footerFields.forEach { field ->
                    Text(
                        text = "$field：________",
                        color = Color(theme.textPrimary),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}
