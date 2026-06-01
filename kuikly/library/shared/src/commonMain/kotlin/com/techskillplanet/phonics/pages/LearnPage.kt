package com.techskillplanet.phonics.pages

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.lazy.LazyColumn
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.controls.PhonicsAlert
import com.techskillplanet.phonics.controls.PhonicsButton
import com.techskillplanet.phonics.controls.PhonicsButtonVariant
import com.techskillplanet.phonics.controls.PhonicsCard
import com.techskillplanet.phonics.controls.PhonicsTopBar
import com.techskillplanet.phonics.data.PhonemeRepository

@Composable
fun LearnPage(
    phonemeId: String,
    state: PhonicsAppState,
    onBack: () -> Unit,
    onGoCheck: (String) -> Unit,
) {
    val phoneme = PhonemeRepository.findById(phonemeId)
    val theme = state.theme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(theme.pageStart)),
    ) {
        PhonicsTopBar(
            title = "学习 ${phoneme.symbol}",
            theme = theme,
            showBack = true,
            onBack = onBack,
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            item {
                PhonicsCard(theme = theme) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(theme.selectedFill))
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = phoneme.symbol, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
                    }
                    PhonicsButton(
                        text = "播放",
                        theme = theme,
                        variant = PhonicsButtonVariant.Primary,
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = { state.audio.playPhoneme(phoneme.id) },
                    )
                    PhonicsAlert(
                        title = "口型提示",
                        message = phoneme.mouthTip,
                        theme = theme,
                        modifier = Modifier.padding(top = 16.dp),
                    )
                }
            }
            item {
                Text(
                    text = "例词练习",
                    color = Color(theme.textPrimary),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
                )
            }
            phoneme.words.forEach { word ->
                item {
                    PhonicsCard(theme = theme, modifier = Modifier.padding(bottom = 12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = word.text, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
                                Text(text = word.tip, color = Color(theme.textTertiary))
                            }
                            PhonicsButton(
                                text = "播放",
                                theme = theme,
                                modifier = Modifier.fillMaxWidth(0.32f),
                                onClick = { state.audio.playWord(word.text) },
                            )
                        }
                    }
                }
            }
        }
        PhonicsButton(
            text = "去检查",
            theme = theme,
            variant = PhonicsButtonVariant.Primary,
            modifier = Modifier.padding(16.dp),
            onClick = { onGoCheck(phoneme.id) },
        )
    }
}
