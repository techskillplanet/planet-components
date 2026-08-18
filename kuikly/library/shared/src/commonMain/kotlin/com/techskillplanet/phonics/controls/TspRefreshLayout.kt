package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
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

@Composable
fun TspRefreshLayout(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    refreshing: Boolean = false,
    loadingMore: Boolean = false,
    disabled: Boolean = false,
    onRefresh: () -> Unit = {},
    onLoadMore: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (refreshing) {
            Text(text = "刷新中…", color = Color(theme.brandPrimary), fontWeight = FontWeight.Bold)
        }
        if (!disabled) {
            Text(
                text = "刷新",
                color = Color(theme.brandPrimary),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onRefresh() },
            )
        }
        content()
        if (loadingMore) {
            Text(
                text = "加载更多…",
                color = Color(theme.textSecondary),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(theme.pageEnd), RoundedCornerShape(12.dp))
                    .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(12.dp))
                    .padding(12.dp),
            )
        } else if (!disabled) {
            Text(
                text = "加载更多",
                color = Color(theme.textSecondary),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onLoadMore() },
            )
        }
    }
}
