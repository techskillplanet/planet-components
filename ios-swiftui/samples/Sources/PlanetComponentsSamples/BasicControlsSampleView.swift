import SwiftUI
import PlanetComponents

private struct ComponentDoc: Identifiable {
    let id: String
    let category: String
}

private let componentDocs: [ComponentDoc] = [
    .init(id: "Button", category: "Actions"),
    .init(id: "Chip", category: "Actions"),
    .init(id: "IconButton", category: "Actions"),
    .init(id: "TextLink", category: "Actions"),
    .init(id: "Card", category: "Surfaces"),
    .init(id: "ListItem", category: "Surfaces"),
    .init(id: "Empty", category: "Surfaces"),
    .init(id: "Alert", category: "Feedback"),
    .init(id: "Badge", category: "Feedback"),
    .init(id: "Progress", category: "Feedback"),
    .init(id: "Notification", category: "Feedback"),
    .init(id: "Toast", category: "Feedback"),
    .init(id: "Modal", category: "Feedback"),
    .init(id: "LoadingDialog", category: "Feedback"),
    .init(id: "Input", category: "Inputs"),
    .init(id: "Select", category: "Inputs"),
    .init(id: "OptionSheet", category: "Inputs"),
    .init(id: "Switch", category: "Inputs"),
    .init(id: "Checkbox", category: "Inputs"),
    .init(id: "Radio", category: "Inputs"),
    .init(id: "SearchBar", category: "Inputs"),
    .init(id: "SegmentedControl", category: "Inputs"),
    .init(id: "StarRating", category: "Inputs"),
    .init(id: "PinInput", category: "Inputs"),
    .init(id: "Collapse", category: "Surfaces"),
    .init(id: "Divider", category: "Surfaces"),
    .init(id: "TopBar", category: "Navigation"),
    .init(id: "BottomTab", category: "Navigation"),
    .init(id: "Tabs", category: "Navigation"),
    .init(id: "StickyFooter", category: "Navigation"),
    .init(id: "RefreshLayout", category: "Navigation"),
    .init(id: "Amount", category: "Data"),
    .init(id: "KeyValueLabel", category: "Data"),
    .init(id: "Stepper", category: "Data"),
    .init(id: "DatePicker", category: "Domain"),
    .init(id: "ChildSwitcher", category: "Domain"),
    .init(id: "ScoreRuleGrid", category: "Domain"),
    .init(id: "RedeemCardGrid", category: "Domain"),
    .init(id: "CalendarHeatmap", category: "Domain"),
    .init(id: "PrintSheet", category: "Domain"),
    .init(id: "BalanceHero", category: "Domain"),
    .init(id: "CheckInStreakCard", category: "Domain"),
    .init(id: "Avatar", category: "Surfaces"),
    .init(id: "Skeleton", category: "Feedback"),
    .init(id: "Tooltip", category: "Feedback"),
    .init(id: "Slider", category: "Inputs"),
    .init(id: "TextArea", category: "Inputs"),
    .init(id: "Drawer", category: "Surfaces"),
    .init(id: "InputNumber", category: "Inputs"),
    .init(id: "Swiper", category: "Navigation"),
    .init(id: "Tag", category: "Actions"),
    .init(id: "Fab", category: "Actions"),
    .init(id: "TimePicker", category: "Inputs"),
    .init(id: "Upload", category: "Inputs"),
    .init(id: "Table", category: "Data"),
    .init(id: "Tree", category: "Data"),
    .init(id: "Cascader", category: "Data")
]

private let languageOptions: [(key: String, labelKey: String)] = [
    ("zh-CN", "sample/lang/zh-CN"),
    ("en", "sample/lang/en"),
    ("ja", "sample/lang/ja")
]

public struct BasicControlsSampleView: View {
    @State private var themeIndex = 0
    @State private var languageKey = "zh-CN"
    @State private var tab = "learn"
    @State private var selectedDoc: ComponentDoc?
    @State private var selectedIndex = 1
    @State private var selectedTab = 0
    @State private var checked = true
    @State private var expanded = false
    @State private var radioValue = "a"
    @State private var searchValue = ""
    @State private var segmentIndex = 0
    @State private var starValue = 3
    @State private var inputValue = ""
    @State private var showModal = false
    @State private var showToast = false
    @State private var toastMessage = ""
    @State private var showLoading = false
    @State private var refreshing = false
    @State private var loadingMore = false
    @State private var refreshRows = Array(1...8)
    @State private var dateValue = "2026-08-26"
    @State private var childId = "1"
    @State private var showDrawer = false
    @State private var sliderValue = 40.0
    @State private var textAreaValue = ""
    @State private var inputNumberValue = 3.0
    @State private var swiperIndex = 0
    @State private var timeValue = "09:30"
    @State private var uploadFiles: [TspUploadFile] = [TspUploadFile(id: "1", name: "readme.md")]
    @State private var treeSelectedId = "a1"
    @State private var treeExpandedIds = ["a"]
    @State private var cascaderValue: [String] = []
    private let themes: [(String, StarPlanetTheme)] = [("Sky", .sky), ("Night", .night), ("Mint", .mint), ("Sunrise", .sunrise)]

    public init() {}

    private var i18n: SampleI18n { SampleI18n(language: languageKey) }

    private var tabs: [TspTabItem] {
        [
            TspTabItem(id: "learn", icon: "⌂", title: i18n.t("sample/tab/home")),
            TspTabItem(id: "settings", icon: "⚙", title: i18n.t("sample/tab/settings"))
        ]
    }

    public var body: some View {
        let theme = themes[themeIndex].1
        let strings = i18n
        ZStack {
            VStack(spacing: 0) {
                TspTopBar(
                    title: selectedDoc.map { "Tsp\($0.id)" } ?? strings.t("sample/app/title"),
                    showBack: selectedDoc != nil,
                    theme: theme
                ) {
                    selectedDoc = nil
                }
                ScrollView {
                    VStack(alignment: .leading, spacing: 14) {
                        if let doc = selectedDoc {
                            detail(doc, theme: theme, strings: strings)
                        } else if tab == "settings" {
                            settings(theme: theme, strings: strings)
                        } else {
                            list(theme: theme, strings: strings)
                        }
                    }
                    .padding(18)
                    .padding(.bottom, 12)
                    .frame(maxWidth: .infinity, alignment: .leading)
                }
                if selectedDoc == nil {
                    TspBottomTab(tabs: tabs, selectedKey: $tab, theme: theme)
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .ignoresSafeArea(edges: [.top, .bottom])
            .background(theme.pageStart.ignoresSafeArea())
            .tspToast(visible: $showToast, message: toastMessage, variant: "success", theme: theme)
            .id(languageKey)

            if showModal {
                TspModal(
                    title: strings.t("sample/demo/modal/title"),
                    message: strings.t("sample/demo/modal/body"),
                    confirmText: strings.t("sample/common/confirm"),
                    cancelText: strings.t("sample/common/cancel"),
                    theme: theme,
                    onConfirm: { showModal = false },
                    onCancel: { showModal = false }
                )
            }
            if showLoading {
                TspLoadingDialog(
                    message: strings.t("sample/demo/loading/dialog_message"),
                    theme: theme,
                    onDismiss: { showLoading = false }
                )
            }
        }
    }

    private func settings(theme: StarPlanetTheme, strings: SampleI18n) -> some View {
        VStack(spacing: 14) {
            TspCard(theme: theme) {
                VStack(alignment: .leading, spacing: 10) {
                    Text(strings.t("sample/settings/theme/title"))
                        .font(.headline)
                        .foregroundColor(theme.textPrimary)
                    Text(strings.t("sample/settings/theme/current", themes[themeIndex].0))
                        .font(.subheadline)
                        .foregroundColor(theme.textSecondary)
                    ForEach(Array(themes.enumerated()), id: \.offset) { index, item in
                        TspButton(item.0, variant: index == themeIndex ? .primary : .default, theme: theme) {
                            themeIndex = index
                            presentToast(strings.t("sample/toast/theme_changed", item.0))
                        }
                    }
                }
            }
            TspCard(theme: theme) {
                VStack(alignment: .leading, spacing: 10) {
                    Text(strings.t("sample/settings/language/title"))
                        .font(.headline)
                        .foregroundColor(theme.textPrimary)
                    Text(strings.t("sample/settings/language/current", strings.t("sample/lang/\(languageKey)")))
                        .font(.subheadline)
                        .foregroundColor(theme.textSecondary)
                    Text(strings.t("sample/settings/language/hint"))
                        .font(.caption)
                        .foregroundColor(theme.textTertiary)
                    ForEach(languageOptions, id: \.key) { option in
                        TspButton(
                            strings.t(option.labelKey),
                            variant: option.key == languageKey ? .primary : .default,
                            theme: theme
                        ) {
                            guard option.key != languageKey else { return }
                            languageKey = option.key
                            let next = SampleI18n(language: option.key)
                            presentToast(next.t("sample/toast/language_changed", next.t(option.labelKey)))
                        }
                    }
                }
            }
        }
    }

    private func list(theme: StarPlanetTheme, strings: SampleI18n) -> some View {
        let categories = componentDocs.map(\.category).reduce(into: [String]()) { result, category in
            if !result.contains(category) { result.append(category) }
        }
        return VStack(alignment: .leading, spacing: 14) {
            ForEach(Array(categories.enumerated()), id: \.element) { index, category in
                Text(strings.t("sample/group/\(category.lowercased())"))
                    .font(.subheadline.bold())
                    .foregroundColor(theme.textSecondary)
                    .padding(.top, index == 0 ? 0 : 6)
                ForEach(componentDocs.filter { $0.category == category }) { doc in
                    TspListItem(
                        title: "Tsp\(doc.id)",
                        message: strings.t(componentDescKey(doc.id)),
                        trailing: strings.t("sample/nav/chevron"),
                        theme: theme
                    ) {
                        selectedDoc = doc
                    }
                }
            }
        }
    }

    private func detail(_ doc: ComponentDoc, theme: StarPlanetTheme, strings: SampleI18n) -> some View {
        VStack(alignment: .leading, spacing: 14) {
            TspCard(theme: theme) {
                Text(strings.t("sample/group/\(doc.category.lowercased())"))
                    .font(.caption.bold())
                    .foregroundColor(theme.brandPrimary)
                Text("Tsp\(doc.id)")
                    .font(.title.bold())
                    .foregroundColor(theme.textPrimary)
                    .padding(.top, 6)
                Text(strings.t(componentDescKey(doc.id)))
                    .foregroundColor(theme.textSecondary)
                    .padding(.top, 6)
            }
            TspCard(theme: theme) {
                Text(strings.t("sample/section/usage"))
                    .font(.headline)
                    .foregroundColor(theme.textPrimary)
                preview(doc.id, theme: theme, strings: strings)
                    .padding(.top, 10)
            }
            TspCard(theme: theme) {
                Text(strings.t("sample/section/stack_sync"))
                    .font(.headline)
                    .foregroundColor(theme.textPrimary)
                FlowLabels(
                    [
                        strings.t("sample/demo/platform/react"),
                        strings.t("sample/demo/platform/vue"),
                        strings.t("sample/demo/platform/android"),
                        strings.t("sample/demo/platform/ios"),
                        strings.t("sample/demo/platform/kuikly"),
                        strings.t("sample/demo/platform/rn")
                    ],
                    theme: theme
                )
                .padding(.top, 10)
            }
        }
    }

    @ViewBuilder
    private func preview(_ name: String, theme: StarPlanetTheme, strings: SampleI18n) -> some View {
        switch name {
        case "Button":
            VStack(spacing: 10) {
                TspButton(strings.t("sample/demo/button/primary"), variant: .primary, theme: theme)
                TspButton(strings.t("sample/demo/button/default"), theme: theme)
                TspButton(strings.t("sample/demo/button/danger"), variant: .danger, theme: theme)
                TspButton(strings.t("sample/demo/button/text"), variant: .text, theme: theme)
            }
        case "Card":
            VStack(spacing: 10) {
                TspCard(theme: theme) {
                    Text(strings.t("sample/demo/card/default_title")).foregroundColor(theme.textPrimary)
                }
                TspCard(selected: true, theme: theme) {
                    Text(strings.t("sample/demo/card/selected_title")).foregroundColor(theme.textPrimary)
                }
            }
        case "Alert":
            VStack(spacing: 10) {
                TspAlert(
                    title: strings.t("sample/demo/alert/success_title"),
                    message: strings.t("sample/demo/alert/success_body"),
                    variant: .success,
                    theme: theme
                )
                TspAlert(
                    title: strings.t("sample/demo/alert/warning_title"),
                    message: strings.t("sample/demo/alert/warning_body"),
                    variant: .warning,
                    theme: theme
                )
            }
        case "Badge":
            FlowLabels(
                [
                    strings.t("sample/demo/badge/default"),
                    strings.t("sample/demo/badge/primary"),
                    strings.t("sample/demo/badge/success"),
                    strings.t("sample/demo/badge/warning"),
                    strings.t("sample/demo/badge/danger")
                ],
                theme: theme
            )
        case "Chip":
            HStack {
                TspChip(strings.t("sample/demo/chip/ai"), theme: theme)
                TspChip(strings.t("sample/demo/chip/published"), selected: true, theme: theme)
                TspChip(strings.t("sample/demo/badge/disabled"), disabled: true, theme: theme)
            }
        case "Input":
            VStack(spacing: 10) {
                TspInput(value: $inputValue, placeholder: strings.t("sample/demo/input/hint_default"), theme: theme)
                TspInput(value: .constant(""), placeholder: strings.t("sample/demo/input/hint_error"), variant: "error", theme: theme)
            }
        case "Select", "OptionSheet":
            TspSelect(
                options: [
                    strings.t("sample/demo/select/option_all"),
                    strings.t("sample/demo/select/option_ai"),
                    strings.t("sample/demo/select/option_android")
                ],
                selectedIndex: $selectedIndex,
                theme: theme
            )
        case "Switch":
            VStack(alignment: .leading, spacing: 12) {
                TspSwitch(text: strings.t("sample/demo/switch/publish"), checked: checked, theme: theme) { checked = $0 }
                TspSwitch(text: strings.t("sample/demo/switch/sm"), checked: checked, variant: .sm, theme: theme) { checked = $0 }
                TspSwitch(text: strings.t("sample/demo/switch/loading_short"), checked: true, loading: true, theme: theme)
                TspSwitch(text: strings.t("sample/demo/switch/disabled_short"), checked: false, disabled: true, theme: theme)
            }
        case "Checkbox":
            VStack(alignment: .leading, spacing: 12) {
                TspCheckbox(text: "Agree", checked: checked, theme: theme) { checked = $0 }
                TspCheckbox(text: "Disabled", checked: true, disabled: true, theme: theme)
            }
        case "Radio":
            VStack(alignment: .leading, spacing: 12) {
                TspRadio(text: "Option A", checked: radioValue == "a", theme: theme) { _ in radioValue = "a" }
                TspRadio(text: "Option B", checked: radioValue == "b", theme: theme) { _ in radioValue = "b" }
            }
        case "SearchBar":
            TspSearchBar(value: $searchValue, placeholder: "Search…", theme: theme) { searchValue = $0 }
        case "SegmentedControl":
            TspSegmentedControl(options: ["日", "周", "月"], selectedIndex: segmentIndex, theme: theme) { index, _, _ in
                segmentIndex = index
            }
        case "StarRating":
            VStack(alignment: .leading, spacing: 12) {
                TspStarRating(value: starValue, theme: theme) { starValue = $0 }
                TspStarRating(value: 4, variant: "readonly", theme: theme)
            }
        case "Collapse":
            TspCollapse(
                title: "Details",
                message: "Expanded body uses theme tokens.",
                expanded: expanded,
                theme: theme
            ) { expanded = $0 }
        case "Divider":
            VStack(alignment: .leading, spacing: 4) {
                Text("Above").foregroundColor(theme.textSecondary)
                TspDivider(text: "Section", theme: theme)
                Text("Below").foregroundColor(theme.textSecondary)
            }
        case "Progress":
            VStack(spacing: 10) {
                TspProgress(progress: 38, theme: theme)
                TspProgress(progress: 68, variant: "success", theme: theme)
                TspProgress(progress: 82, variant: "danger", theme: theme)
            }
        case "TopBar":
            TspTopBar(title: strings.t("sample/app/title"), showBack: true, theme: theme)
        case "BottomTab":
            TspBottomTab(tabs: tabs, selectedKey: $tab, theme: theme)
        case "Tabs":
            TspTabs(
                tabs: [
                    strings.t("sample/demo/tabs/all"),
                    strings.t("sample/demo/tabs/learned"),
                    strings.t("sample/demo/tabs/todo")
                ],
                selectedIndex: $selectedTab,
                theme: theme
            )
        case "Amount":
            VStack(spacing: 10) {
                TspAmount(symbol: "$", value: "128.80", cycle: strings.t("sample/demo/amount/cycle"), theme: theme)
                TspAmount(symbol: "$", value: "199.00", strikeThrough: true, theme: theme)
            }
        case "IconButton":
            HStack {
                TspIconButton(icon: "♪", selected: true, theme: theme)
                TspIconButton(icon: "✓", theme: theme)
                TspIconButton(icon: "×", disabled: true, theme: theme)
            }
        case "KeyValueLabel":
            TspKeyValueLabel(label: strings.t("sample/demo/kv/label"), value: strings.t("sample/demo/kv/value"), theme: theme)
        case "Notification":
            TspNotification(
                title: strings.t("sample/demo/notification/title"),
                message: strings.t("sample/demo/notification/body"),
                variant: "alert",
                theme: theme
            )
        case "TextLink":
            TspTextLink(strings.t("sample/demo/link/contract"), theme: theme)
        case "Stepper":
            VStack(spacing: 10) {
                TspStepper(stepCount: 3, currentStep: 2, theme: theme)
                TspStepper(stepCount: 5, currentStep: 3, theme: theme)
            }
        case "StickyFooter":
            TspStickyFooter(theme: theme) {
                TspButton(strings.t("sample/demo/footer/action"), variant: .primary, theme: theme)
            }
        case "PinInput":
            VStack(spacing: 10) {
                TspPinInput(value: "2468", secure: true, theme: theme)
                TspPinInput(value: "123", cellCount: 6, theme: theme)
            }
        case "ListItem":
            VStack(spacing: 10) {
                TspListItem(
                    title: strings.t("sample/demo/list/app_title"),
                    message: strings.t("sample/demo/list/selected"),
                    trailing: strings.t("sample/nav/chevron"),
                    selected: true,
                    theme: theme
                )
                TspListItem(
                    title: strings.t("sample/demo/list/disabled_title"),
                    message: strings.t("sample/demo/list/disabled_body"),
                    disabled: true,
                    theme: theme
                )
            }
        case "Empty":
            TspEmpty(
                title: strings.t("sample/demo/empty/title"),
                message: strings.t("sample/demo/empty/body"),
                actionText: strings.t("sample/demo/empty/action"),
                theme: theme
            )
        case "Toast":
            TspButton(strings.t("sample/toast/success"), variant: .primary, theme: theme) {
                presentToast(strings.t("sample/toast/saved"))
            }
        case "Modal":
            TspButton(strings.t("sample/demo/modal/button"), theme: theme) { showModal = true }
        case "LoadingDialog":
            TspButton(strings.t("sample/demo/loading/dialog_button"), variant: .primary, theme: theme) {
                showLoading = true
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.6) { showLoading = false }
            }
        case "RefreshLayout":
            TspRefreshLayout(
                refreshing: refreshing,
                loadingMore: loadingMore,
                theme: theme,
                onRefresh: {
                    refreshing = true
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.8) {
                        refreshRows = Array(1...8)
                        refreshing = false
                        presentToast(strings.t("sample/toast/refreshed"))
                    }
                },
                onLoadMore: {
                    guard !loadingMore else { return }
                    loadingMore = true
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.8) {
                        let start = refreshRows.count + 1
                        refreshRows.append(contentsOf: start...(start + 3))
                        loadingMore = false
                    }
                }
            ) {
                ForEach(refreshRows, id: \.self) { row in
                    TspListItem(
                        title: "\(strings.t("sample/demo/list/app_title")) \(row)",
                        message: strings.t("sample/demo/refresh/row"),
                        theme: theme
                    )
                    .padding(.bottom, 8)
                }
            }
            .frame(height: 280)
        case "DatePicker":
            TspDatePicker(
                value: $dateValue,
                placeholder: strings.t("sample/demo/datepicker/placeholder"),
                theme: theme
            )
        case "ChildSwitcher":
            VStack(spacing: 10) {
                TspChildSwitcher(
                    items: [
                        TspChildSwitcherItem(id: "1", label: strings.t("sample/demo/child/yue"), emoji: "👧"),
                        TspChildSwitcherItem(id: "2", label: strings.t("sample/demo/child/you"), emoji: "👦")
                    ],
                    selectedId: childId,
                    theme: theme
                ) { childId = $0 }
                TspChildSwitcher(
                    items: [
                        TspChildSwitcherItem(id: "1", label: strings.t("sample/demo/child/yue")),
                        TspChildSwitcherItem(id: "2", label: strings.t("sample/demo/child/you"))
                    ],
                    selectedId: childId,
                    variant: .tabs,
                    theme: theme
                ) { childId = $0 }
            }
        case "ScoreRuleGrid":
            TspScoreRuleGrid(
                rules: [
                    TspScoreRule(id: "1", name: strings.t("sample/demo/score/homework"), icon: "📝", value: 5, count: 1, dailyLimit: 1),
                    TspScoreRule(id: "2", name: strings.t("sample/demo/score/messy"), icon: "✏️", value: -2, count: 0, dailyLimit: 2)
                ],
                columns: .auto,
                theme: theme
            ) { _ in presentToast(strings.t("sample/toast/score_plus")) }
        case "RedeemCardGrid":
            TspRedeemCardGrid(
                items: [
                    TspRedeemItem(id: "1", name: strings.t("sample/demo/redeem/snack"), icon: "🍬", cost: 15),
                    TspRedeemItem(id: "2", name: strings.t("sample/demo/redeem/game"), icon: "🎮", cost: 30)
                ],
                availablePoints: 20,
                theme: theme
            ) { _ in presentToast(strings.t("sample/toast/redeem")) }
        case "CalendarHeatmap":
            TspCalendarHeatmap(
                yearMonth: "2026-08",
                cells: [
                    TspCalendarCell(date: "2026-08-01", level: "full"),
                    TspCalendarCell(date: "2026-08-02", level: "partial"),
                    TspCalendarCell(date: "2026-08-03", level: "exempt")
                ],
                theme: theme
            )
        case "PrintSheet":
            TspPrintSheet(
                title: strings.t("sample/demo/print/title"),
                items: ["dǐng", "lù", "yàn", "xīn", "wǎn"],
                columns: 5,
                variant: .pinyin,
                theme: theme
            )
        case "BalanceHero":
            TspBalanceHero(
                total: 41,
                breakdown: [
                    "balance": 40,
                    "ruleScore": 11,
                    "streakBonus": 5,
                    "redeemTotal": 15
                ],
                theme: theme
            )
        case "CheckInStreakCard":
            TspCheckInStreakCard(
                streakDays: 7,
                totalDays: 45,
                weekProgress: 0.85,
                theme: theme
            ) {
                presentToast(strings.t("sample/toast/checkin"))
            }
        case "Avatar":
            HStack(spacing: 12) {
                TspAvatar(text: "技趣", theme: theme)
                TspAvatar(text: "SP", size: "lg", variant: "primary", theme: theme)
                TspAvatar(text: "A", size: "sm", variant: "subtle", theme: theme)
            }
        case "Skeleton":
            TspSkeleton(rows: 3, animated: true, avatar: true, theme: theme)
        case "Tooltip":
            TspTooltip(text: "Sky Planet tip", placement: "top", theme: theme) {
                TspButton("Press me", theme: theme) {}
            }
        case "Slider":
            TspSlider(value: $sliderValue, min: 0, max: 100, theme: theme)
        case "TextArea":
            TspTextArea(value: $textAreaValue, placeholder: "Write a note…", rows: 3, theme: theme)
        case "Drawer":
            VStack(alignment: .leading, spacing: 12) {
                TspButton("Open Drawer", variant: .primary, theme: theme) { showDrawer = true }
                TspDrawer(visible: showDrawer, title: "Drawer", placement: "bottom", theme: theme, onClose: { showDrawer = false }) {
                    Text("Sky Planet drawer body.")
                        .foregroundColor(theme.textPrimary)
                }
            }
        case "InputNumber":
            TspInputNumber(value: $inputNumberValue, min: 0, max: 10, theme: theme)
        case "Swiper":
            TspSwiper(items: ["Slide A", "Slide B", "Slide C"], index: $swiperIndex, theme: theme)
        case "Tag":
            HStack(spacing: 8) {
                TspTag(text: "default", theme: theme)
                TspTag(text: "primary", variant: "primary", theme: theme)
                TspTag(text: "closable", closable: true, theme: theme)
            }
        case "Fab":
            HStack(spacing: 12) {
                TspFab(icon: "+", theme: theme)
                TspFab(icon: "+", text: "新建", theme: theme)
                TspFab(icon: "✎", text: "默认", variant: "default", theme: theme)
            }
        case "TimePicker":
            TspTimePicker(value: $timeValue, placeholder: "HH:mm", theme: theme)
        case "Upload":
            TspUpload(files: uploadFiles, theme: theme, onChange: { uploadFiles = $0 })
        case "Table":
            TspTable(
                columns: [
                    TspTableColumn(title: "名称", key: "name"),
                    TspTableColumn(title: "状态", key: "status")
                ],
                rows: [
                    ["name": "Avatar", "status": "就绪"],
                    ["name": "Tag", "status": "新增"]
                ],
                variant: "striped",
                theme: theme
            )
        case "Tree":
            TspTree(
                items: [
                    TspTreeNode(id: "a", label: "星球", children: [
                        TspTreeNode(id: "a1", label: "天空"),
                        TspTreeNode(id: "a2", label: "岛屿")
                    ]),
                    TspTreeNode(id: "b", label: "玩法", children: [
                        TspTreeNode(id: "b1", label: "闯关")
                    ])
                ],
                selectedId: treeSelectedId,
                expandedIds: treeExpandedIds,
                theme: theme,
                onSelect: { id, _ in treeSelectedId = id },
                onExpand: { treeExpandedIds = $0 }
            )
        case "Cascader":
            TspCascader(
                options: [
                    TspCascaderOption(value: "asia", label: "亚洲", children: [
                        TspCascaderOption(value: "cn", label: "中国"),
                        TspCascaderOption(value: "jp", label: "日本")
                    ]),
                    TspCascaderOption(value: "eu", label: "欧洲", children: [
                        TspCascaderOption(value: "fr", label: "法国")
                    ])
                ],
                value: cascaderValue,
                placeholder: "请选择地区",
                theme: theme,
                onChange: { value, _ in cascaderValue = value }
            )
        default:
            EmptyView()
        }
    }

    private func componentDescKey(_ id: String) -> String {
        "sample/component/\(id.lowercased())/desc"
    }

    private func presentToast(_ message: String) {
        toastMessage = message
        showToast = true
    }
}

private struct FlowLabels: View {
    let values: [String]
    let theme: StarPlanetTheme
    init(_ values: [String], theme: StarPlanetTheme) {
        self.values = values
        self.theme = theme
    }
    var body: some View {
        LazyVGrid(columns: [GridItem(.adaptive(minimum: 78), spacing: 8)], alignment: .leading, spacing: 8) {
            ForEach(values, id: \.self) { value in
                TspBadge(value, variant: value.lowercased() == "primary" ? "primary" : "default", theme: theme)
            }
        }
    }
}
