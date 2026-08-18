package com.techskillplanet.phonics.samples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.techskillplanet.phonics.controls.TspAlert
import com.techskillplanet.phonics.controls.TspAmount
import com.techskillplanet.phonics.controls.TspBadge
import com.techskillplanet.phonics.controls.TspBadgeVariant
import com.techskillplanet.phonics.controls.TspBottomTab
import com.techskillplanet.phonics.controls.TspButton
import com.techskillplanet.phonics.controls.TspButtonVariant
import com.techskillplanet.phonics.controls.TspCard
import com.techskillplanet.phonics.controls.TspChip
import com.techskillplanet.phonics.controls.TspEmpty
import com.techskillplanet.phonics.controls.TspIconButton
import com.techskillplanet.phonics.controls.TspInput
import com.techskillplanet.phonics.controls.TspInputVariant
import com.techskillplanet.phonics.controls.TspKeyValueLabel
import com.techskillplanet.phonics.controls.TspListItem
import com.techskillplanet.phonics.controls.TspLoadingDialog
import com.techskillplanet.phonics.controls.TspModal
import com.techskillplanet.phonics.controls.TspNotification
import com.techskillplanet.phonics.controls.TspNotificationVariant
import com.techskillplanet.phonics.controls.TspOptionSheet
import com.techskillplanet.phonics.controls.TspPinInput
import com.techskillplanet.phonics.controls.TspProgress
import com.techskillplanet.phonics.controls.TspRefreshLayout
import com.techskillplanet.phonics.controls.TspSelect
import com.techskillplanet.phonics.controls.TspStepper
import com.techskillplanet.phonics.controls.TspStickyFooter
import com.techskillplanet.phonics.controls.TspSwitch
import com.techskillplanet.phonics.controls.TspTabItem
import com.techskillplanet.phonics.controls.TspTabs
import com.techskillplanet.phonics.controls.TspTextLink
import com.techskillplanet.phonics.controls.TspToast
import com.techskillplanet.phonics.controls.TspToastVariant
import com.techskillplanet.phonics.controls.TspTopBar
import com.techskillplanet.phonics.theme.PhonicsColors
import com.techskillplanet.phonics.theme.PhonicsTheme
import com.tencent.kuikly.compose.foundation.background
import com.tencent.kuikly.compose.foundation.layout.Arrangement
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.lazy.LazyColumn
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
    ComponentDoc("LoadingDialog", "Feedback", "加载弹窗，用于阻塞式等待、紧凑加载和可取消提示。"),
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
    ComponentDoc("RefreshLayout", "Data", "下拉刷新和加载更多容器，用于长列表数据刷新。"),
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
        TspTabItem("learn", "⌂", "学习"),
        TspTabItem("settings", "⚙", "设置"),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(theme.pageStart)),
    ) {
        TspTopBar(title = title, theme = theme, showBack = selectedDoc.value != null) {
            selectedDoc.value = null
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            when {
                selectedDoc.value != null -> item {
                    ComponentDetail(selectedDoc.value!!, theme)
                }
                tab.value == "settings" -> item {
                    SettingsList(theme, themeKey.value, languageKey.value) { key, type ->
                        if (type == "theme") themeKey.value = key else languageKey.value = key
                    }
                }
                else -> componentDocs.groupBy { it.category }.forEach { (category, docs) ->
                    item {
                        Text(text = category, color = Color(theme.textSecondary), fontWeight = FontWeight.Bold)
                    }
                    docs.forEach { doc ->
                        item {
                            TspListItem(
                                title = "Tsp${doc.name}",
                                message = doc.description,
                                trailing = "›",
                                theme = theme,
                            ) {
                                selectedDoc.value = doc
                            }
                        }
                    }
                }
            }
        }
        if (selectedDoc.value == null) {
            TspBottomTab(tabs = tabs, selectedKey = tab.value, theme = theme) { key ->
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
    TspCard(theme = theme) {
        Text(text = "Theme Switch", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(PhonicsTheme.Sky, PhonicsTheme.Night, PhonicsTheme.Mint).forEach { item ->
                TspButton(
                    text = item.key,
                    theme = theme,
                    modifier = Modifier.weight(1f),
                    variant = if (item.key == themeKey) TspButtonVariant.Primary else TspButtonVariant.Default,
                ) {
                    onSelect(item.key, "theme")
                }
            }
        }
    }
    TspCard(theme = theme) {
        Text(text = "Language Switch", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("zh-CN" to "简体中文", "en" to "English", "ja" to "日本語").forEach { item ->
                TspButton(
                    text = item.second,
                    theme = theme,
                    modifier = Modifier.weight(1f),
                    variant = if (item.first == languageKey) TspButtonVariant.Primary else TspButtonVariant.Default,
                ) {
                    onSelect(item.first, "language")
                }
            }
        }
    }
}

@Composable
private fun ComponentDetail(doc: ComponentDoc, theme: PhonicsColors) {
    TspCard(theme = theme) {
        Text(text = doc.category, color = Color(theme.brandPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tsp${doc.name}", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = doc.description, color = Color(theme.textSecondary))
    }
    TspCard(theme = theme) {
        Text(text = "使用案例", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        ComponentPreview(doc.name, theme)
    }
    TspCard(theme = theme) {
        Text(text = "技术栈同步", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("React", "Vue", "Android", "iOS", "Kuikly", "RN").forEach {
                TspChip(text = it, theme = theme)
            }
        }
    }
}

@Composable
private fun ComponentPreview(name: String, theme: PhonicsColors) {
    val checked = remember { mutableStateOf(true) }
    val input = remember { mutableStateOf("Kuikly") }
    val selectedIndex = remember { mutableStateOf(1) }
    val sheetVisible = remember { mutableStateOf(true) }
    val tabIndex = remember { mutableStateOf(0) }
    val toastVisible = remember { mutableStateOf(true) }
    val modalVisible = remember { mutableStateOf(true) }
    val loadingVisible = remember { mutableStateOf(true) }
    val refreshing = remember { mutableStateOf(false) }
    val loadingMore = remember { mutableStateOf(false) }
    val options = listOf("天空蓝", "夜空", "薄荷")
    when (name) {
        "Button" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspButton(text = "primary", theme = theme, variant = TspButtonVariant.Primary) {}
            TspButton(text = "default", theme = theme) {}
            TspButton(text = "danger", theme = theme, variant = TspButtonVariant.Danger) {}
        }
        "Chip" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TspChip(text = "Default", theme = theme)
            TspChip(text = "Selected", theme = theme)
        }
        "Badge" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TspBadge(text = "Default", theme = theme)
            TspBadge(text = "Primary", theme = theme, variant = TspBadgeVariant.Primary)
            TspBadge(text = "Danger", theme = theme, variant = TspBadgeVariant.Danger)
        }
        "Card" -> TspCard(theme = theme) {
            Text(text = "Selected card", color = Color(theme.textPrimary))
        }
        "Alert" -> TspAlert(title = "Success", message = "Theme is applied.", theme = theme)
        "Progress" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspProgress(progress = 0.38f, theme = theme)
            TspProgress(progress = 0.68f, theme = theme)
        }
        "TopBar" -> TspTopBar(title = "基础组件", theme = theme, showBack = true) {}
        "BottomTab" -> TspBottomTab(
            tabs = listOf(TspTabItem("learn", "⌂", "学习"), TspTabItem("settings", "⚙", "设置")),
            selectedKey = "learn",
            theme = theme,
        ) {}
        "Amount" -> TspAmount(symbol = "$", value = "128.80", cycle = "month", theme = theme)
        "IconButton" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TspIconButton(icon = "♪", theme = theme) {}
            TspIconButton(icon = "✓", theme = theme, selected = true) {}
        }
        "KeyValueLabel" -> TspKeyValueLabel(label = "Progress", value = "12/48", theme = theme)
        "Notification" -> TspNotification(title = "通知", message = "继续学习。", theme = theme, variant = TspNotificationVariant.Alert)
        "TextLink" -> TspTextLink(text = "Text Link", theme = theme) {}
        "Stepper" -> TspStepper(stepCount = 5, currentStep = 3, theme = theme, modifier = Modifier.fillMaxWidth())
        "StickyFooter" -> TspStickyFooter(theme = theme) {
            TspButton(text = "Sticky Footer", theme = theme, variant = TspButtonVariant.Primary) {}
        }
        "PinInput" -> TspPinInput(value = "2468", cellCount = 6, theme = theme)
        "ListItem" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspListItem(title = "默认列表项", message = "描述文案", trailing = "›", theme = theme)
            TspListItem(title = "选中列表项", message = "选中状态", selected = true, theme = theme)
            TspListItem(title = "禁用列表项", message = "不可点", disabled = true, theme = theme)
        }
        "Empty" -> TspEmpty(title = "空状态", message = "暂无记录。", actionText = "操作", theme = theme)
        "Input" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspInput(value = input.value, placeholder = "请输入", theme = theme) { input.value = it }
            TspInput(value = "", placeholder = "错误态", variant = TspInputVariant.Error, theme = theme)
            TspInput(value = "不可编辑", disabled = true, theme = theme)
        }
        "Select" -> TspSelect(
            options = options,
            selectedIndex = selectedIndex.value,
            theme = theme,
        ) { index, _ -> selectedIndex.value = index }
        "OptionSheet" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspButton(text = if (sheetVisible.value) "关闭 OptionSheet" else "打开 OptionSheet", theme = theme) {
                sheetVisible.value = !sheetVisible.value
            }
            TspOptionSheet(
                title = "选择主题",
                options = options,
                selectedIndex = selectedIndex.value,
                visible = sheetVisible.value,
                theme = theme,
                onCancel = { sheetVisible.value = false },
                onSelect = { index, _ ->
                    selectedIndex.value = index
                    sheetVisible.value = false
                },
            )
        }
        "Switch" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspSwitch(text = "夜间模式", checked = checked.value, theme = theme) { checked.value = it }
            TspSwitch(text = "加载中", checked = true, loading = true, theme = theme)
            TspSwitch(text = "禁用", checked = false, disabled = true, variant = "sm", theme = theme)
        }
        "Tabs" -> TspTabs(
            tabs = listOf("全部", "进行中", "已完成"),
            selectedIndex = tabIndex.value,
            theme = theme,
        ) { index, _ -> tabIndex.value = index }
        "Toast" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspButton(text = if (toastVisible.value) "隐藏 Toast" else "显示 Toast", theme = theme) {
                toastVisible.value = !toastVisible.value
            }
            if (toastVisible.value) {
                TspToast(message = "已保存", variant = TspToastVariant.Success, theme = theme)
            }
        }
        "Modal" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspButton(text = if (modalVisible.value) "关闭 Modal" else "打开 Modal", theme = theme) {
                modalVisible.value = !modalVisible.value
            }
            TspModal(
                title = "确认操作",
                message = "要应用当前主题吗？",
                visible = modalVisible.value,
                theme = theme,
                onConfirm = { modalVisible.value = false },
                onCancel = { modalVisible.value = false },
            )
        }
        "LoadingDialog" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspButton(text = if (loadingVisible.value) "关闭加载" else "打开加载", theme = theme) {
                loadingVisible.value = !loadingVisible.value
            }
            TspLoadingDialog(
                visible = loadingVisible.value,
                message = "加载中...",
                dismissible = true,
                theme = theme,
                onDismiss = { loadingVisible.value = false },
            )
        }
        "RefreshLayout" -> TspRefreshLayout(
            theme = theme,
            refreshing = refreshing.value,
            loadingMore = loadingMore.value,
            onRefresh = {
                refreshing.value = true
                loadingMore.value = false
            },
            onLoadMore = { loadingMore.value = true },
        ) {
            TspListItem(title = "列表内容", message = "下拉刷新 / 加载更多", theme = theme) {
                refreshing.value = false
                loadingMore.value = false
            }
        }
        else -> TspListItem(title = "未覆盖预览", message = name, theme = theme)
    }
}
