package com.techskillplanet.phonics.samples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.techskillplanet.phonics.controls.BottomTabItem
import com.techskillplanet.phonics.controls.PhonicsAlert
import com.techskillplanet.phonics.controls.PhonicsAmount
import com.techskillplanet.phonics.controls.PhonicsBottomTab
import com.techskillplanet.phonics.controls.PhonicsButton
import com.techskillplanet.phonics.controls.PhonicsButtonVariant
import com.techskillplanet.phonics.controls.PhonicsCard
import com.techskillplanet.phonics.controls.PhonicsChip
import com.techskillplanet.phonics.controls.PhonicsIconButton
import com.techskillplanet.phonics.controls.PhonicsKeyValueLabel
import com.techskillplanet.phonics.controls.PhonicsNotification
import com.techskillplanet.phonics.controls.PhonicsNotificationVariant
import com.techskillplanet.phonics.controls.PhonicsPinInput
import com.techskillplanet.phonics.controls.PhonicsProgress
import com.techskillplanet.phonics.controls.PhonicsStepper
import com.techskillplanet.phonics.controls.PhonicsStickyFooter
import com.techskillplanet.phonics.controls.PhonicsTextLink
import com.techskillplanet.phonics.controls.PhonicsTopBar
import com.techskillplanet.phonics.theme.PhonicsColors
import com.techskillplanet.phonics.theme.PhonicsTheme
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.clickable
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Modifier
import com.tencent.kuikly.compose.ui.graphics.Color
import com.tencent.kuikly.compose.ui.text.font.FontWeight
import com.tencent.kuikly.compose.ui.unit.dp

private data class ComponentDoc(
    val name: String,
    val category: String,
    val description: String,
)

private val componentDocs = listOf(
    ComponentDoc("Button", "Actions", "主要操作按钮，支持主按钮、默认、危险、文本和链接样式。"),
    ComponentDoc("Chip", "Actions", "可点击标签，用于筛选、选择和轻量操作。"),
    ComponentDoc("IconButton", "Actions", "图标按钮，用于工具栏、快捷动作和选中态。"),
    ComponentDoc("TextLink", "Actions", "文本链接按钮，适合弱操作和辅助跳转。"),
    ComponentDoc("Card", "Surfaces", "内容容器，用于承载分组内容、选中态和弱化背景。"),
    ComponentDoc("ListItem", "Surfaces", "列表项，支持描述、尾部内容、选中和禁用。"),
    ComponentDoc("Empty", "Surfaces", "空状态展示，支持说明文案和操作按钮。"),
    ComponentDoc("Alert", "Feedback", "页面内提示，适合成功、警告、错误和普通信息。"),
    ComponentDoc("Badge", "Feedback", "短文本状态徽标，用于标记数量、状态或风险等级。"),
    ComponentDoc("Progress", "Feedback", "进度条，支持主色、成功、警告和危险色。"),
    ComponentDoc("Notification", "Feedback", "通知卡片，用于强调当前任务或状态提醒。"),
    ComponentDoc("Toast", "Feedback", "轻提示，用于短时反馈。"),
    ComponentDoc("Modal", "Feedback", "确认弹窗，支持确认和取消按钮。"),
    ComponentDoc("Input", "Inputs", "单行输入框，支持错误态、禁用态和受控输入。"),
    ComponentDoc("Select", "Inputs", "选择入口，移动端默认打开底部 OptionSheet。"),
    ComponentDoc("OptionSheet", "Inputs", "移动端底部选择弹窗，和 Select 共用选项渲染逻辑。"),
    ComponentDoc("Switch", "Inputs", "二元开关，支持加载、禁用和开关文案。"),
    ComponentDoc("PinInput", "Inputs", "验证码或密码输入，支持 4 到 6 位和安全显示。"),
    ComponentDoc("TopBar", "Navigation", "顶部导航栏，支持标题、返回按钮和背景色覆盖。"),
    ComponentDoc("BottomTab", "Navigation", "一级页面底部 Tab，通常承载 3 到 5 个入口。"),
    ComponentDoc("Tabs", "Navigation", "内容区分段切换，适合页面内筛选和分类。"),
    ComponentDoc("StickyFooter", "Navigation", "固定底部操作区，用于主操作按钮。"),
    ComponentDoc("Amount", "Data", "金额或数值展示，支持币种前后置、周期和删除线。"),
    ComponentDoc("KeyValueLabel", "Data", "键值对展示，用于摘要信息和表单确认。"),
    ComponentDoc("Stepper", "Data", "步骤进度，支持 3 到 5 步。"),
)

@Composable
fun BasicControlsSample() {
    val themeKey = remember { mutableStateOf(PhonicsTheme.Sky.key) }
    val languageKey = remember { mutableStateOf("zh-CN") }
    val tab = remember { mutableStateOf("learn") }
    val selectedDoc = remember { mutableStateOf<ComponentDoc?>(null) }
    val theme = PhonicsTheme.get(themeKey.value)
    val title = selectedDoc.value?.let { "Tsp${it.name}" } ?: when (languageKey.value) {
        "en" -> "Basic Controls"
        "ja" -> "基本コンポーネント"
        else -> "基础组件"
    }
    val tabs = listOf(
        BottomTabItem("learn", "⌂", "学习"),
        BottomTabItem("settings", "⚙", "设置"),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(theme.pageStart)),
    ) {
        PhonicsTopBar(title = title, theme = theme, showBack = selectedDoc.value != null) {
            selectedDoc.value = null
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            when {
                selectedDoc.value != null -> ComponentDetail(selectedDoc.value!!, theme)
                tab.value == "settings" -> SettingsList(theme, themeKey.value, languageKey.value) { key, type ->
                    if (type == "theme") themeKey.value = key else languageKey.value = key
                }
                else -> ComponentList(theme) { doc -> selectedDoc.value = doc }
            }
        }
        if (selectedDoc.value == null) {
            PhonicsBottomTab(tabs = tabs, selectedKey = tab.value, theme = theme) { key ->
                tab.value = key
            }
        }
    }
}

@Composable
private fun SettingsList(
    theme: PhonicsColors,
    themeKey: String,
    languageKey: String,
    onSelect: (String, String) -> Unit,
) {
    PhonicsCard(theme = theme) {
        Text(text = "Theme Switch", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(PhonicsTheme.Sky, PhonicsTheme.Night, PhonicsTheme.Mint).forEach { item ->
                PhonicsButton(
                    text = item.key,
                    theme = theme,
                    modifier = Modifier.weight(1f),
                    variant = if (item.key == themeKey) PhonicsButtonVariant.Primary else PhonicsButtonVariant.Default,
                ) {
                    onSelect(item.key, "theme")
                }
            }
        }
    }
    PhonicsCard(theme = theme) {
        Text(text = "Language Switch", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("zh-CN" to "简体中文", "en" to "English", "ja" to "日本語").forEach { item ->
                PhonicsButton(
                    text = item.second,
                    theme = theme,
                    modifier = Modifier.weight(1f),
                    variant = if (item.first == languageKey) PhonicsButtonVariant.Primary else PhonicsButtonVariant.Default,
                ) {
                    onSelect(item.first, "language")
                }
            }
        }
    }
}

@Composable
private fun ComponentList(theme: PhonicsColors, onSelect: (ComponentDoc) -> Unit) {
    // Keep this grouped list consistent with the other platform samples.
    componentDocs.groupBy { it.category }.forEach { (category, docs) ->
        Text(text = category, color = Color(theme.textSecondary), fontWeight = FontWeight.Bold)
        docs.forEach { doc ->
            SampleListItem(title = "Tsp${doc.name}", message = doc.description, theme = theme) {
                onSelect(doc)
            }
        }
    }
}

@Composable
private fun ComponentDetail(doc: ComponentDoc, theme: PhonicsColors) {
    PhonicsCard(theme = theme) {
        Text(text = doc.category, color = Color(theme.brandPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tsp${doc.name}", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = doc.description, color = Color(theme.textSecondary))
    }
    PhonicsCard(theme = theme) {
        Text(text = "使用案例", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        ComponentPreview(doc.name, theme)
    }
    PhonicsCard(theme = theme) {
        Text(text = "技术栈同步", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("React", "Vue", "Android", "iOS", "Kuikly", "RN").forEach {
                PhonicsChip(text = it, theme = theme)
            }
        }
    }
}

@Composable
private fun ComponentPreview(name: String, theme: PhonicsColors) {
    when (name) {
        "Button" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PhonicsButton(text = "primary", theme = theme, variant = PhonicsButtonVariant.Primary) {}
            PhonicsButton(text = "default", theme = theme) {}
            PhonicsButton(text = "danger", theme = theme, variant = PhonicsButtonVariant.Danger) {}
        }
        "Chip", "Badge" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PhonicsChip(text = "Default", theme = theme)
            PhonicsChip(text = "Selected", theme = theme)
        }
        "Card" -> PhonicsCard(theme = theme) {
            Text(text = "Selected card", color = Color(theme.textPrimary))
        }
        "Alert" -> PhonicsAlert(title = "Success", message = "Theme is applied.", theme = theme)
        "Progress" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            PhonicsProgress(progress = 0.38f, theme = theme)
            PhonicsProgress(progress = 0.68f, theme = theme)
        }
        "TopBar" -> PhonicsTopBar(title = "基础组件", theme = theme, showBack = true) {}
        "BottomTab" -> PhonicsBottomTab(
            tabs = listOf(BottomTabItem("learn", "⌂", "学习"), BottomTabItem("settings", "⚙", "设置")),
            selectedKey = "learn",
            theme = theme,
        ) {}
        "Amount" -> PhonicsAmount(symbol = "$", value = "128.80", cycle = "month", theme = theme)
        "IconButton" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PhonicsIconButton(icon = "♪", theme = theme) {}
            PhonicsIconButton(icon = "✓", theme = theme, selected = true) {}
        }
        "KeyValueLabel" -> PhonicsKeyValueLabel(label = "Progress", value = "12/48", theme = theme)
        "Notification" -> PhonicsNotification(title = "通知", message = "继续学习。", theme = theme, variant = PhonicsNotificationVariant.Alert)
        "TextLink" -> PhonicsTextLink(text = "Text Link", theme = theme) {}
        "Stepper" -> PhonicsStepper(stepCount = 5, currentStep = 3, theme = theme, modifier = Modifier.fillMaxWidth())
        "StickyFooter" -> PhonicsStickyFooter(theme = theme) {
            PhonicsButton(text = "Sticky Footer", theme = theme, variant = PhonicsButtonVariant.Primary) {}
        }
        "PinInput" -> PhonicsPinInput(value = "2468", cellCount = 6, theme = theme)
        "ListItem" -> SampleListItem(title = "列表项", message = "选中状态", theme = theme) {}
        "Empty" -> SampleEmpty(theme)
        else -> SampleListItem(title = "待补真实交互", message = "当前 Kuikly 组件以视觉样例占位，后续接入平台能力。", theme = theme) {}
    }
}

@Composable
private fun SampleListItem(
    title: String,
    message: String,
    theme: PhonicsColors,
    onClick: () -> Unit,
) {
    PhonicsCard(
        theme = theme,
        modifier = Modifier.clickable { onClick() },
    ) {
        Text(text = title, color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = message, color = Color(theme.textSecondary))
    }
}

@Composable
private fun SampleEmpty(theme: PhonicsColors) {
    PhonicsCard(theme = theme) {
        Text(text = "空状态", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = "暂无记录。", color = Color(theme.textSecondary))
        Spacer(modifier = Modifier.height(10.dp))
        PhonicsButton(text = "操作", theme = theme, variant = PhonicsButtonVariant.Primary) {}
    }
}
