package com.techskillplanet.phonics.controls

import androidx.compose.runtime.Composable
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.border
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.layout.size
import com.tencent.kuikly.compose.foundation.layout.width
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp
import com.techskillplanet.phonics.theme.PhonicsColors

enum class PhonicsButtonVariant { Primary, Default, Danger }
enum class PhonicsNotificationVariant { Info, Alert }

data class BottomTabItem(
    val key: String,
    val icon: String,
    val label: String,
)

@Composable
fun PhonicsButton(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: PhonicsButtonVariant = PhonicsButtonVariant.Default,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val faceColor = when (variant) {
        PhonicsButtonVariant.Primary -> Color(theme.brandPrimary)
        PhonicsButtonVariant.Default -> Color(theme.surfaceRaised)
        PhonicsButtonVariant.Danger -> Color(theme.danger)
    }
    val textColor = if (variant == PhonicsButtonVariant.Default) {
        Color(theme.textPrimary)
    } else {
        Color.White
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(faceColor, RoundedCornerShape(999.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp))
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = textColor, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PhonicsCard(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised), RoundedCornerShape(16.dp))
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(16.dp))
            .padding(16.dp),
    ) {
        content()
    }
}

@Composable
fun PhonicsChip(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(Color(theme.activeFill), RoundedCornerShape(999.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PhonicsProgress(
    progress: Float,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(theme.borderDefault), RoundedCornerShape(999.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(8.dp)
                .background(Color(theme.brandPrimary), RoundedCornerShape(999.dp)),
        )
    }
}

@Composable
fun PhonicsTopBar(
    title: String,
    theme: PhonicsColors,
    showBack: Boolean,
    onBack: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(theme.pageStart))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clickable(enabled = showBack) { onBack() },
            contentAlignment = Alignment.CenterStart,
        ) {
            if (showBack) Text(text = "‹", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        }
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = Color(theme.textPrimary),
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(44.dp))
    }
}

@Composable
fun PhonicsBottomTab(
    tabs: List<BottomTabItem>,
    selectedKey: String,
    theme: PhonicsColors,
    onSelect: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(theme.surfaceRaised))
            .border(1.dp, Color(theme.borderDefault)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.forEach { tab ->
            val active = tab.key == selectedKey
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelect(tab.key) },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = tab.icon, color = Color(if (active) theme.brandPrimary else theme.textTertiary))
                Text(text = tab.label, color = Color(if (active) theme.brandPrimary else theme.textTertiary))
            }
        }
    }
}

@Composable
fun PhonicsAlert(
    title: String,
    message: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.selectedFill), RoundedCornerShape(12.dp))
            .border(1.dp, Color(theme.success), RoundedCornerShape(12.dp))
            .padding(12.dp),
    ) {
        Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Text(text = message, color = Color(theme.textSecondary))
    }
}

@Composable
fun PhonicsAmount(
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    symbol: String = "¥",
    cycle: String = "",
    symbolAfter: Boolean = false,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
    ) {
        if (!symbolAfter) {
            Text(text = symbol, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(text = value, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        if (symbolAfter) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = symbol, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        }
        if (cycle.isNotEmpty()) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "/$cycle", color = Color(theme.textSecondary))
        }
    }
}

@Composable
fun PhonicsIconButton(
    icon: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .background(
                Color(if (selected) theme.activeFill else theme.surfaceRaised),
                RoundedCornerShape(999.dp),
            )
            .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(999.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = icon, color = Color(if (selected) theme.brandPrimary else theme.textPrimary), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PhonicsKeyValueLabel(
    label: String,
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, modifier = Modifier.weight(1f), color = Color(theme.textSecondary))
        Text(text = value, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PhonicsNotification(
    title: String,
    message: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    variant: PhonicsNotificationVariant = PhonicsNotificationVariant.Info,
) {
    val fill = if (variant == PhonicsNotificationVariant.Alert) theme.warning else theme.activeFill
    val stroke = if (variant == PhonicsNotificationVariant.Alert) theme.warning else theme.borderDefault
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(fill), RoundedCornerShape(18.dp))
            .border(1.dp, Color(stroke), RoundedCornerShape(18.dp))
            .padding(16.dp),
    ) {
        Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = message, color = Color(theme.textSecondary))
    }
}

@Composable
fun PhonicsTextLink(
    text: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    inverse: Boolean = false,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        modifier = modifier.clickable { onClick() },
        color = Color(if (inverse) 0xFFFFFFFF else theme.brandPrimary),
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun PhonicsStepper(
    stepCount: Int,
    currentStep: Int,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
) {
    val safeCount = stepCount.coerceIn(3, 5)
    val safeStep = currentStep.coerceIn(1, safeCount)
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(safeCount) { index ->
            val step = index + 1
            val done = step < safeStep
            val active = step == safeStep
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        Color(
                            when {
                                done -> theme.success
                                active -> theme.brandPrimary
                                else -> theme.surfaceRaised
                            },
                        ),
                        RoundedCornerShape(999.dp),
                    )
                    .border(1.dp, Color(if (active) theme.brandPrimary else theme.borderDefault), RoundedCornerShape(999.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = if (done) "✓" else "$step",
                    color = Color(if (done || active) 0xFFFFFFFF else theme.textTertiary),
                    fontWeight = FontWeight.Bold,
                )
            }
            if (index < safeCount - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .background(Color(if (step < safeStep) theme.success else theme.borderDefault)),
                )
            }
        }
    }
}

@Composable
fun PhonicsStickyFooter(
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(theme.surfaceRaised))
            .border(1.dp, Color(theme.borderDefault))
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        content()
    }
}

@Composable
fun PhonicsPinInput(
    value: String,
    theme: PhonicsColors,
    modifier: Modifier = Modifier,
    cellCount: Int = 6,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        repeat(cellCount.coerceIn(4, 8)) { index ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .background(Color(theme.surfaceRaised), RoundedCornerShape(12.dp))
                    .border(1.dp, Color(theme.borderDefault), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = value.getOrNull(index)?.toString() ?: "",
                    color = Color(theme.textPrimary),
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
