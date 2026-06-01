package com.techskillplanet.phonics.pages

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.lazy.LazyColumn
import com.tencent.kuikly.compose.foundation.lazy.grid.GridCells
import com.tencent.kuikly.compose.foundation.lazy.grid.LazyVerticalGrid
import com.tencent.kuikly.compose.foundation.lazy.grid.items
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.controls.BottomTabItem
import com.techskillplanet.phonics.controls.PhonicsBottomTab
import com.techskillplanet.phonics.controls.PhonicsButton
import com.techskillplanet.phonics.controls.PhonicsButtonVariant
import com.techskillplanet.phonics.controls.PhonicsCard
import com.techskillplanet.phonics.controls.PhonicsChip
import com.techskillplanet.phonics.controls.PhonicsProgress
import com.techskillplanet.phonics.data.Phoneme
import com.techskillplanet.phonics.data.PhonemeRepository

@Composable
fun MapPage(
    state: PhonicsAppState,
    onStartLearn: (String) -> Unit,
    onOpenSettings: () -> Unit,
) {
    val theme = state.theme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(theme.pageStart)),
    ) {
        Header(theme = theme)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                ProgressCard(
                    completed = state.completed.size,
                    total = PhonemeRepository.phonemes.size,
                    state = state,
                )
            }
            PhonemeRepository.groups.forEach { group ->
                item {
                    PhonicsCard(theme = theme) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = group.title,
                                modifier = Modifier.weight(1f),
                                color = Color(theme.textPrimary),
                                fontWeight = FontWeight.Bold,
                            )
                            PhonicsChip(text = "${group.items.size} 关", theme = theme)
                        }
                        PhonemeGrid(
                            items = group.items,
                            state = state,
                        )
                    }
                }
            }
        }
        PhonicsButton(
            text = "开始学习 ${PhonemeRepository.findById(state.selectedPhonemeId.value).symbol}",
            theme = theme,
            variant = PhonicsButtonVariant.Primary,
            modifier = Modifier.padding(16.dp),
            onClick = { onStartLearn(state.selectedPhonemeId.value) },
        )
        PhonicsBottomTab(
            tabs = listOf(
                BottomTabItem("learn", "⌂", "学习"),
                BottomTabItem("settings", "⚙", "设置"),
            ),
            selectedKey = "learn",
            theme = theme,
            onSelect = { if (it == "settings") onOpenSettings() },
        )
    }
}

@Composable
private fun Header(theme: com.techskillplanet.phonics.theme.PhonicsColors) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(Color(theme.brandPrimary), RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "☺", color = Color.White)
        }
        Column(modifier = Modifier.padding(start = 14.dp)) {
            Text(text = "音标星球", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
            Text(text = "48 个音标 · 纯单机", color = Color(theme.textTertiary))
        }
    }
}

@Composable
private fun ProgressCard(completed: Int, total: Int, state: PhonicsAppState) {
    val theme = state.theme
    PhonicsCard(theme = theme) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "已通过 $completed / $total",
                modifier = Modifier.weight(1f),
                color = Color(theme.textPrimary),
                fontWeight = FontWeight.Bold,
            )
            PhonicsChip(text = "${completed * 100 / total}%", theme = theme)
        }
        PhonicsProgress(
            progress = completed.toFloat() / total.toFloat(),
            theme = theme,
            modifier = Modifier.padding(top = 12.dp),
        )
        Text(
            text = "每天 5 分钟，学 1 个音标，再做 3 题检查。",
            color = Color(theme.textTertiary),
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}

@Composable
private fun PhonemeGrid(items: List<Phoneme>, state: PhonicsAppState) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(items) { phoneme ->
            val theme = state.theme
            val selected = state.selectedPhonemeId.value == phoneme.id
            Box(
                modifier = Modifier
                    .background(
                        Color(if (selected) theme.activeFill else theme.surfaceRaised),
                        RoundedCornerShape(14.dp),
                    )
                    .border(
                        1.dp,
                        Color(if (selected) theme.warning else theme.borderDefault),
                        RoundedCornerShape(14.dp),
                    )
                    .padding(vertical = 18.dp)
                    .clickable { state.selectPhoneme(phoneme.id) },
                contentAlignment = Alignment.Center,
            ) {
                Text(text = phoneme.symbol, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
            }
        }
    }
}
