package com.techskillplanet.phonics.samples

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.techskillplanet.phonics.controls.TspAlert
import com.techskillplanet.phonics.controls.TspAlertVariant
import com.techskillplanet.phonics.controls.TspAmount
import com.techskillplanet.phonics.controls.TspBadge
import com.techskillplanet.phonics.controls.TspBadgeVariant
import com.techskillplanet.phonics.controls.TspBottomTab
import com.techskillplanet.phonics.controls.TspButton
import com.techskillplanet.phonics.controls.TspButtonVariant
import com.techskillplanet.phonics.controls.TspCard
import com.techskillplanet.phonics.controls.TspCardVariant
import com.techskillplanet.phonics.controls.TspChip
import com.techskillplanet.phonics.controls.TspChipVariant
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
import com.techskillplanet.phonics.controls.TspProgressVariant
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
import com.tencent.kuikly.compose.foundation.layout.Box
import com.tencent.kuikly.compose.foundation.layout.Column
import com.tencent.kuikly.compose.foundation.layout.PaddingValues
import com.tencent.kuikly.compose.foundation.layout.Row
import com.tencent.kuikly.compose.foundation.layout.Spacer
import com.tencent.kuikly.compose.foundation.layout.fillMaxSize
import com.tencent.kuikly.compose.foundation.layout.fillMaxWidth
import com.tencent.kuikly.compose.foundation.layout.height
import com.tencent.kuikly.compose.foundation.layout.padding
import com.tencent.kuikly.compose.foundation.lazy.LazyColumn
import com.tencent.kuikly.compose.foundation.shape.RoundedCornerShape
import com.tencent.kuikly.compose.material3.Text
import com.tencent.kuikly.compose.ui.Alignment
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
    ComponentDoc("Button", "group.actions", "component.button.desc"),
    ComponentDoc("Chip", "group.actions", "component.chip.desc"),
    ComponentDoc("IconButton", "group.actions", "component.iconbutton.desc"),
    ComponentDoc("TextLink", "group.actions", "component.textlink.desc"),
    ComponentDoc("Card", "group.surfaces", "component.card.desc"),
    ComponentDoc("ListItem", "group.surfaces", "component.listitem.desc"),
    ComponentDoc("Empty", "group.surfaces", "component.empty.desc"),
    ComponentDoc("Alert", "group.feedback", "component.alert.desc"),
    ComponentDoc("Badge", "group.feedback", "component.badge.desc"),
    ComponentDoc("Progress", "group.feedback", "component.progress.desc"),
    ComponentDoc("Notification", "group.feedback", "component.notification.desc"),
    ComponentDoc("Toast", "group.feedback", "component.toast.desc"),
    ComponentDoc("Modal", "group.feedback", "component.modal.desc"),
    ComponentDoc("LoadingDialog", "group.feedback", "component.loadingdialog.desc"),
    ComponentDoc("Input", "group.inputs", "component.input.desc"),
    ComponentDoc("Select", "group.inputs", "component.select.desc"),
    ComponentDoc("OptionSheet", "group.inputs", "component.optionsheet.desc"),
    ComponentDoc("Switch", "group.inputs", "component.switch.desc"),
    ComponentDoc("PinInput", "group.inputs", "component.pininput.desc"),
    ComponentDoc("TopBar", "group.navigation", "component.topbar.desc"),
    ComponentDoc("BottomTab", "group.navigation", "component.bottomtab.desc"),
    ComponentDoc("Tabs", "group.navigation", "component.tabs.desc"),
    ComponentDoc("StickyFooter", "group.navigation", "component.stickyfooter.desc"),
    ComponentDoc("Amount", "group.data", "component.amount.desc"),
    ComponentDoc("KeyValueLabel", "group.data", "component.keyvaluelabel.desc"),
    ComponentDoc("Stepper", "group.data", "component.stepper.desc"),
    ComponentDoc("RefreshLayout", "group.data", "component.refreshlayout.desc"),
)

@Composable
fun BasicControlsSample(
    statusBarHeight: Float = 0f,
    navigationBarHeight: Float = 0f,
    initialComponent: String = "",
    initialTab: String = "learn",
) {
    val themeKey = remember { mutableStateOf(PhonicsTheme.Sky.key) }
    val languageKey = remember { mutableStateOf("zh-CN") }
    val tab = remember { mutableStateOf(if (initialTab == "settings") "settings" else "learn") }
    val selectedDoc = remember {
        mutableStateOf(componentDocs.firstOrNull { it.name.equals(initialComponent, ignoreCase = true) })
    }
    val theme = PhonicsTheme.get(themeKey.value)
    val title = selectedDoc.value?.let { "Tsp${it.name}" } ?: sampleText(languageKey.value, "app.title")
    val tabs = listOf(
        TspTabItem("learn", "home", sampleText(languageKey.value, "tab.learn")),
        TspTabItem("settings", "settings", sampleText(languageKey.value, "tab.settings")),
    )
    val showBottomTab = selectedDoc.value == null
    val showStickyFooter = selectedDoc.value?.name == "StickyFooter"
    val bottomReserve = when {
        showBottomTab || showStickyFooter -> 56f + navigationBarHeight
        else -> navigationBarHeight
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(theme.pageStart)),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TspTopBar(
                title = title,
                theme = theme,
                showBack = selectedDoc.value != null,
                topInset = statusBarHeight,
            ) {
                selectedDoc.value = null
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 18.dp),
                contentPadding = PaddingValues(
                    top = 18.dp,
                    bottom = bottomReserve.dp + 18.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                when {
                    selectedDoc.value != null -> item {
                        ComponentDetail(selectedDoc.value!!, theme, languageKey.value, navigationBarHeight)
                    }
                    tab.value == "settings" -> item {
                        SettingsList(theme, themeKey.value, languageKey.value) { key, type ->
                            if (type == "theme") themeKey.value = key else languageKey.value = key
                        }
                    }
                    else -> componentDocs.groupBy { it.category }.forEach { (category, docs) ->
                        item {
                            Text(text = sampleText(languageKey.value, category), color = Color(theme.textSecondary), fontWeight = FontWeight.Bold)
                        }
                        docs.forEach { doc ->
                            item {
                                TspListItem(
                                    title = "Tsp${doc.name}",
                                    message = sampleText(languageKey.value, doc.description),
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
        }
        if (showStickyFooter) {
            TspStickyFooter(
                theme = theme,
                modifier = Modifier.align(Alignment.BottomCenter),
                bottomInset = navigationBarHeight,
            ) {
                TspButton(text = "Sticky Footer", theme = theme, variant = TspButtonVariant.Primary) {}
            }
        } else if (showBottomTab) {
            TspBottomTab(
                tabs = tabs,
                selectedKey = tab.value,
                theme = theme,
                modifier = Modifier.align(Alignment.BottomCenter),
                bottomInset = navigationBarHeight,
            ) { key ->
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
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    TspCard(theme = theme) {
        Text(text = sampleText(languageKey, "settings.theme"), color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
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
        Text(text = sampleText(languageKey, "settings.language"), color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = sampleText(languageKey, "settings.language.current").replace("%s", languageLabel(languageKey)),
            color = Color(theme.textSecondary),
        )
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
}

@Composable
private fun ComponentDetail(doc: ComponentDoc, theme: PhonicsColors, language: String, bottomInset: Float) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    TspCard(theme = theme) {
        Text(text = sampleText(language, doc.category), color = Color(theme.brandPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tsp${doc.name}", color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = sampleText(language, doc.description), color = Color(theme.textSecondary))
    }
    TspCard(theme = theme) {
        Text(text = sampleText(language, "section.preview"), color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        ComponentPreview(doc.name, theme, bottomInset)
    }
    TspCard(theme = theme) {
        Text(text = sampleText(language, "section.stack"), color = Color(theme.textPrimary), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("React", "Vue", "Android").forEach {
                TspChip(text = it, theme = theme)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("iOS", "Kuikly", "RN").forEach {
                TspChip(text = it, theme = theme)
            }
        }
    }
    }
}

@Composable
private fun ComponentPreview(name: String, theme: PhonicsColors, bottomInset: Float) {
    val checked = remember { mutableStateOf(true) }
    val input = remember { mutableStateOf("Kuikly") }
    val selectedIndex = remember { mutableStateOf(1) }
    val sheetVisible = remember { mutableStateOf(false) }
    val tabIndex = remember { mutableStateOf(0) }
    val toastVisible = remember { mutableStateOf(false) }
    val modalVisible = remember { mutableStateOf(false) }
    val loadingVisible = remember { mutableStateOf(false) }
    val refreshing = remember { mutableStateOf(false) }
    val loadingMore = remember { mutableStateOf(false) }
    val options = listOf("天空蓝", "夜空", "薄荷")
    when (name) {
        "Button" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspButton(text = "primary", theme = theme, variant = TspButtonVariant.Primary) {}
            TspButton(text = "default", theme = theme) {}
            TspButton(text = "danger", theme = theme, variant = TspButtonVariant.Danger) {}
            TspButton(text = "text", theme = theme, variant = TspButtonVariant.Text) {}
            TspButton(text = "link", theme = theme, variant = TspButtonVariant.Link) {}
            TspButton(text = "disabled", theme = theme, variant = TspButtonVariant.Primary, disabled = true) {}
        }
        "Chip" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TspChip(text = "Default", theme = theme)
                TspChip(text = "Primary", theme = theme, variant = TspChipVariant.Primary, selected = true)
                TspChip(text = "Success", theme = theme, variant = TspChipVariant.Success)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TspChip(text = "Warning", theme = theme, variant = TspChipVariant.Warning)
                TspChip(text = "Danger", theme = theme, variant = TspChipVariant.Danger)
                TspChip(text = "Disabled", theme = theme, disabled = true)
            }
        }
        "Badge" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TspBadge(text = "Default", theme = theme)
                TspBadge(text = "Primary", theme = theme, variant = TspBadgeVariant.Primary)
                TspBadge(text = "Success", theme = theme, variant = TspBadgeVariant.Success)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TspBadge(text = "Warning", theme = theme, variant = TspBadgeVariant.Warning)
                TspBadge(text = "Danger", theme = theme, variant = TspBadgeVariant.Danger)
            }
        }
        "Card" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspCard(theme = theme) {
                Text(text = "Default card", color = Color(theme.textPrimary))
            }
            TspCard(theme = theme, variant = TspCardVariant.Subtle) {
                Text(text = "Subtle card", color = Color(theme.textSecondary))
            }
            TspCard(theme = theme, selected = true) {
                Text(text = "Selected card", color = Color(theme.textPrimary))
            }
            TspCard(theme = theme, disabled = true) {
                Text(text = "Disabled card", color = Color(theme.textTertiary))
            }
        }
        "Alert" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspAlert(title = "Info", message = "Theme is applied.", theme = theme)
            TspAlert(title = "Success", message = "Saved.", theme = theme, variant = TspAlertVariant.Success)
            TspAlert(title = "Warning", message = "Check input.", theme = theme, variant = TspAlertVariant.Warning)
            TspAlert(title = "Error", message = "Request failed.", theme = theme, variant = TspAlertVariant.Error)
        }
        "Progress" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspProgress(progress = 0.38f, theme = theme)
            TspProgress(progress = 0.68f, theme = theme, variant = TspProgressVariant.Success)
            TspProgress(progress = 0.52f, theme = theme, variant = TspProgressVariant.Warning)
            TspProgress(progress = 0.24f, theme = theme, variant = TspProgressVariant.Danger)
        }
        "TopBar" -> TspTopBar(title = "基础组件", theme = theme, showBack = true, immersive = false) {}
        "BottomTab" -> TspBottomTab(
            tabs = listOf(TspTabItem("learn", "home", "学习"), TspTabItem("settings", "settings", "设置")),
            selectedKey = "learn",
            theme = theme,
        ) {}
        "Amount" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspAmount(symbol = "$", value = "128.80", cycle = "month", theme = theme)
            TspAmount(symbol = "¥", value = "99.00", symbolAfter = true, theme = theme)
            TspAmount(symbol = "$", value = "56.00", strikeThrough = true, theme = theme)
        }
        "IconButton" -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TspIconButton(icon = "Go", theme = theme) {}
            TspIconButton(icon = "OK", theme = theme, selected = true) {}
            TspIconButton(icon = "*", theme = theme, primary = true) {}
            TspIconButton(icon = "X", theme = theme, disabled = true) {}
        }
        "KeyValueLabel" -> TspKeyValueLabel(label = "Progress", value = "12/48", theme = theme)
        "Notification" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspNotification(title = "通知", message = "继续学习。", theme = theme)
            TspNotification(title = "提醒", message = "请检查输入。", theme = theme, variant = TspNotificationVariant.Alert)
        }
        "TextLink" -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            TspTextLink(text = "Text Link", theme = theme) {}
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(theme.brandPrimary), RoundedCornerShape(12.dp))
                    .padding(12.dp),
            ) {
                TspTextLink(text = "Inverse Link", theme = theme, inverse = true) {}
            }
        }
        "Stepper" -> TspStepper(stepCount = 5, currentStep = 3, theme = theme, modifier = Modifier.fillMaxWidth())
        "StickyFooter" -> Text(text = "页面底部已展示真实 StickyFooter", color = Color(theme.textSecondary))
        "PinInput" -> Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TspPinInput(value = "2468", cellCount = 6, theme = theme)
            TspPinInput(value = "1234", cellCount = 4, secure = true, theme = theme)
        }
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
            bottomInset = bottomInset,
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
                bottomInset = bottomInset,
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
            TspToast(message = "Info toast", variant = TspToastVariant.Info, theme = theme)
            TspToast(message = "已保存", variant = TspToastVariant.Success, theme = theme)
            TspToast(message = "Warning toast", variant = TspToastVariant.Warning, theme = theme)
            TspToast(message = "Error toast", variant = TspToastVariant.Error, theme = theme)
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
                confirmText = "确定",
                cancelText = "取消",
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

private fun languageLabel(language: String): String = when (language) {
    "en" -> "English"
    "ja" -> "日本語"
    else -> "简体中文"
}

private fun sampleText(language: String, key: String): String {
    val zh = mapOf(
        "app.title" to "基础组件",
        "tab.learn" to "学习",
        "tab.settings" to "设置",
        "settings.theme" to "主题",
        "settings.language" to "语言",
        "settings.language.current" to "当前语言：%s",
        "section.preview" to "使用案例",
        "section.stack" to "技术栈同步",
        "group.actions" to "操作",
        "group.surfaces" to "表面",
        "group.feedback" to "反馈",
        "group.inputs" to "输入",
        "group.navigation" to "导航",
        "group.data" to "数据",
        "component.button.desc" to "主要操作按钮，支持主按钮、默认、危险、文本和链接样式。",
        "component.chip.desc" to "可点击标签，用于筛选、选择和轻量操作。",
        "component.iconbutton.desc" to "图标按钮，用于工具栏、快捷动作和选中态。",
        "component.textlink.desc" to "文本链接按钮，适合弱操作和辅助跳转。",
        "component.card.desc" to "内容容器，用于承载分组内容、选中态和弱化背景。",
        "component.listitem.desc" to "列表项，支持描述、尾部内容、选中和禁用。",
        "component.empty.desc" to "空状态展示，支持说明文案和操作按钮。",
        "component.alert.desc" to "页面内提示，适合成功、警告、错误和普通信息。",
        "component.badge.desc" to "短文本状态徽标，用于标记数量、状态或风险等级。",
        "component.progress.desc" to "进度条，支持主色、成功、警告和危险色。",
        "component.notification.desc" to "通知卡片，用于强调当前任务或状态提醒。",
        "component.toast.desc" to "轻提示，用于短时反馈。",
        "component.modal.desc" to "确认弹窗，支持确认和取消按钮。",
        "component.loadingdialog.desc" to "加载弹窗，用于阻塞式等待、紧凑加载和可取消提示。",
        "component.input.desc" to "单行输入框，支持错误态、禁用态和受控输入。",
        "component.select.desc" to "选择入口，移动端默认打开底部 OptionSheet。",
        "component.optionsheet.desc" to "移动端底部选择弹窗，和 Select 共用选项渲染逻辑。",
        "component.switch.desc" to "二元开关，支持加载、禁用和开关文案。",
        "component.pininput.desc" to "验证码或密码输入，支持 4 到 6 位和安全显示。",
        "component.topbar.desc" to "顶部导航栏，支持标题、返回按钮和背景色覆盖。",
        "component.bottomtab.desc" to "一级页面底部 Tab，通常承载 3 到 5 个入口。",
        "component.tabs.desc" to "内容区分段切换，适合页面内筛选和分类。",
        "component.stickyfooter.desc" to "固定底部操作区，用于主操作按钮。",
        "component.amount.desc" to "金额或数值展示，支持币种前后置、周期和删除线。",
        "component.keyvaluelabel.desc" to "键值对展示，用于摘要信息和表单确认。",
        "component.stepper.desc" to "步骤进度，支持 3 到 5 步。",
        "component.refreshlayout.desc" to "下拉刷新和加载更多容器，用于长列表数据刷新。",
    )
    val en = mapOf(
        "app.title" to "Basic Controls",
        "tab.learn" to "Learn",
        "tab.settings" to "Settings",
        "settings.theme" to "Theme",
        "settings.language" to "Language",
        "settings.language.current" to "Current language: %s",
        "section.preview" to "Examples",
        "section.stack" to "Stack parity",
        "group.actions" to "Actions",
        "group.surfaces" to "Surfaces",
        "group.feedback" to "Feedback",
        "group.inputs" to "Inputs",
        "group.navigation" to "Navigation",
        "group.data" to "Data",
        "component.button.desc" to "Primary actions with primary, default, danger, text and link variants.",
        "component.chip.desc" to "Tappable tags for filters, selection and light actions.",
        "component.iconbutton.desc" to "Icon buttons for toolbars, quick actions and selected states.",
        "component.textlink.desc" to "Text links for secondary actions and helper navigation.",
        "component.card.desc" to "Surface container for grouped content, selection and subtle backgrounds.",
        "component.listitem.desc" to "List rows with description, trailing content, selected and disabled states.",
        "component.empty.desc" to "Empty states with message and action button.",
        "component.alert.desc" to "Inline alerts for info, success, warning and error.",
        "component.badge.desc" to "Short status badges for counts, states or risk levels.",
        "component.progress.desc" to "Progress bars with primary, success, warning and danger colors.",
        "component.notification.desc" to "Notification cards for tasks and status reminders.",
        "component.toast.desc" to "Lightweight toasts for short feedback.",
        "component.modal.desc" to "Confirm dialogs with confirm and cancel actions.",
        "component.loadingdialog.desc" to "Blocking loading dialog for submit and sync waits.",
        "component.input.desc" to "Single-line inputs with error, disabled and controlled states.",
        "component.select.desc" to "Selection entry that opens the bottom OptionSheet on mobile.",
        "component.optionsheet.desc" to "Bottom sheet picker sharing option rendering with Select.",
        "component.switch.desc" to "Binary switches with loading, disabled and on/off labels.",
        "component.pininput.desc" to "PIN input with 4 to 6 cells and secure display.",
        "component.topbar.desc" to "Top bar with title, back button and background override.",
        "component.bottomtab.desc" to "Primary bottom tabs, usually 3 to 5 entries.",
        "component.tabs.desc" to "In-page segmented tabs for filters and categories.",
        "component.stickyfooter.desc" to "Sticky footer area for primary actions.",
        "component.amount.desc" to "Amount display with symbol placement, cycle and strike-through.",
        "component.keyvaluelabel.desc" to "Key-value pairs for summaries and confirmations.",
        "component.stepper.desc" to "Step progress for 3 to 5 steps.",
        "component.refreshlayout.desc" to "Pull-to-refresh and load-more container for scrollable lists.",
    )
    val ja = mapOf(
        "app.title" to "基本コンポーネント",
        "tab.learn" to "学習",
        "tab.settings" to "設定",
        "settings.theme" to "テーマ",
        "settings.language" to "言語",
        "settings.language.current" to "現在の言語：%s",
        "section.preview" to "使用例",
        "section.stack" to "技術スタック同期",
        "group.actions" to "操作",
        "group.surfaces" to "サーフェス",
        "group.feedback" to "フィードバック",
        "group.inputs" to "入力",
        "group.navigation" to "ナビゲーション",
        "group.data" to "データ",
        "component.button.desc" to "主/標準/危険/テキスト/リンクの主要操作ボタン。",
        "component.chip.desc" to "フィルタ、選択、軽量操作用のタグ。",
        "component.iconbutton.desc" to "ツールバー、クイック操作、選択状態用アイコンボタン。",
        "component.textlink.desc" to "弱い操作や補助遷移向けテキストリンク。",
        "component.card.desc" to "グループ内容、選択、弱い背景向けコンテナ。",
        "component.listitem.desc" to "説明、末尾、選択、無効をサポートするリスト項目。",
        "component.empty.desc" to "説明と操作ボタン付き空状態。",
        "component.alert.desc" to "情報/成功/警告/エラー向けインラインアラート。",
        "component.badge.desc" to "数量/状態/リスク表示用バッジ。",
        "component.progress.desc" to "主/成功/警告/危険色のプログレス。",
        "component.notification.desc" to "タスクや状態通知カード。",
        "component.toast.desc" to "短いフィードバック用 Toast。",
        "component.modal.desc" to "確認/取消ボタン付きモーダル。",
        "component.loadingdialog.desc" to "送信・同期待ち向けのブロッキング Loading ダイアログ。",
        "component.input.desc" to "エラー/無効/制御入力対応の単行入力。",
        "component.select.desc" to "モバイルで OptionSheet を開く選択入口。",
        "component.optionsheet.desc" to "Select と共有するボトム選択シート。",
        "component.switch.desc" to "読込/無効/ON-OFF ラベル付きスイッチ。",
        "component.pininput.desc" to "4〜6 桁とセキュア表示の PIN 入力。",
        "component.topbar.desc" to "タイトル/戻る/背景上書き対応 TopBar。",
        "component.bottomtab.desc" to "通常 3〜5 項目のボトム Tab。",
        "component.tabs.desc" to "ページ内フィルタ/分類用 Tabs。",
        "component.stickyfooter.desc" to "主操作向け固定フッター。",
        "component.amount.desc" to "記号位置/周期/打ち消し線付き金額表示。",
        "component.keyvaluelabel.desc" to "概要/確認向けキー値表示。",
        "component.stepper.desc" to "3〜5 ステップの進捗。",
        "component.refreshlayout.desc" to "プル刷新と追加読み込みに対応したスクロール容器。",
    )
    val table = when (language) {
        "en" -> en
        "ja" -> ja
        else -> zh
    }
    return table[key] ?: zh[key] ?: key
}


