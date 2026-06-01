import SwiftUI
import TechSkillPlanetBasicControls

private struct ComponentDoc: Identifiable {
    let id: String
    let category: String
    let description: String
}

private let componentDocs: [ComponentDoc] = [
    .init(id: "Button", category: "Actions", description: "主要操作按钮，支持主按钮、默认、危险、文本和链接样式。"),
    .init(id: "Chip", category: "Actions", description: "可点击标签，用于筛选、选择和轻量操作。"),
    .init(id: "IconButton", category: "Actions", description: "图标按钮，用于工具栏、快捷动作和选中态。"),
    .init(id: "TextLink", category: "Actions", description: "文本链接按钮，适合弱操作和辅助跳转。"),
    .init(id: "Card", category: "Surfaces", description: "内容容器，用于承载分组内容、选中态和弱化背景。"),
    .init(id: "ListItem", category: "Surfaces", description: "列表项，支持描述、尾部内容、选中和禁用。"),
    .init(id: "Empty", category: "Surfaces", description: "空状态展示，支持说明文案和操作按钮。"),
    .init(id: "Alert", category: "Feedback", description: "页面内提示，适合成功、警告、错误和普通信息。"),
    .init(id: "Badge", category: "Feedback", description: "短文本状态徽标，用于标记数量、状态或风险等级。"),
    .init(id: "Progress", category: "Feedback", description: "进度条，支持主色、成功、警告和危险色。"),
    .init(id: "Notification", category: "Feedback", description: "通知卡片，用于强调当前任务或状态提醒。"),
    .init(id: "Toast", category: "Feedback", description: "轻提示，用于短时反馈。"),
    .init(id: "Modal", category: "Feedback", description: "确认弹窗，支持确认和取消按钮。"),
    .init(id: "Input", category: "Inputs", description: "单行输入框，支持错误态、禁用态和受控输入。"),
    .init(id: "Select", category: "Inputs", description: "选择入口，移动端默认打开底部 OptionSheet。"),
    .init(id: "OptionSheet", category: "Inputs", description: "移动端底部选择弹窗，和 Select 共用选项渲染逻辑。"),
    .init(id: "Switch", category: "Inputs", description: "二元开关，支持加载、禁用和开关文案。"),
    .init(id: "PinInput", category: "Inputs", description: "验证码或密码输入，支持 4 到 6 位和安全显示。"),
    .init(id: "TopBar", category: "Navigation", description: "顶部导航栏，支持标题、返回按钮和背景色覆盖。"),
    .init(id: "BottomTab", category: "Navigation", description: "一级页面底部 Tab，通常承载 3 到 5 个入口。"),
    .init(id: "Tabs", category: "Navigation", description: "内容区分段切换，适合页面内筛选和分类。"),
    .init(id: "StickyFooter", category: "Navigation", description: "固定底部操作区，用于主操作按钮。"),
    .init(id: "Amount", category: "Data", description: "金额或数值展示，支持币种前后置、周期和删除线。"),
    .init(id: "KeyValueLabel", category: "Data", description: "键值对展示，用于摘要信息和表单确认。"),
    .init(id: "Stepper", category: "Data", description: "步骤进度，支持 3 到 5 步。")
]

public struct BasicControlsSampleView: View {
    @State private var themeIndex = 0
    @State private var languageIndex = 0
    @State private var tab = "learn"
    @State private var selectedDoc: ComponentDoc?
    @State private var selectedIndex = 1
    @State private var selectedTab = 0
    @State private var checked = true
    @State private var inputValue = ""
    @State private var showModal = false
    @State private var showToast = false
    private let themes: [(String, StarPlanetTheme)] = [("Sky", .sky), ("Night", .night), ("Mint", .mint)]
    private let languages: [(String, String, String)] = [("zh-CN", "简体中文", "基础组件"), ("en", "English", "Basic Controls"), ("ja", "日本語", "基本コンポーネント")]
    private let tabs = [TspTabItem(id: "learn", icon: "⌂", title: "学习"), TspTabItem(id: "settings", icon: "⚙", title: "设置")]

    public init() {}

    public var body: some View {
        let theme = themes[themeIndex].1
        VStack(spacing: 0) {
            TspTopBar(title: selectedDoc.map { "Tsp\($0.id)" } ?? languages[languageIndex].2, showBack: selectedDoc != nil, theme: theme) {
                selectedDoc = nil
            }
            ScrollView {
                VStack(alignment: .leading, spacing: 14) {
                    if let doc = selectedDoc {
                        detail(doc, theme: theme)
                    } else if tab == "settings" {
                        settings(theme: theme)
                    } else {
                        list(theme: theme)
                    }
                }
                .padding(18)
                .padding(.bottom, selectedDoc == nil ? 80 : 12)
            }
            if selectedDoc == nil {
                TspBottomTab(tabs: tabs, selectedKey: $tab, theme: theme)
            }
        }
        .background(theme.pageStart)
        .alert("确认", isPresented: $showModal) {
            Button("取消", role: .cancel) {}
            Button("确定") {}
        } message: {
            Text("组件弹窗完整显示。")
        }
        .overlay(alignment: .bottom) {
            if showToast {
                TspToast(message: "已保存", variant: "success", theme: theme).padding(.bottom, 90)
            }
        }
    }

    private func settings(theme: StarPlanetTheme) -> some View {
        VStack(spacing: 14) {
            TspCard(theme: theme) {
                VStack(alignment: .leading, spacing: 10) {
                    Text("Theme Switch").font(.headline).foregroundColor(theme.textPrimary)
                    ForEach(Array(themes.enumerated()), id: \.offset) { index, item in
                        TspButton(item.0, variant: index == themeIndex ? .primary : .default, theme: theme) { themeIndex = index }
                    }
                }
            }
            TspCard(theme: theme) {
                VStack(alignment: .leading, spacing: 10) {
                    Text("Language Switch").font(.headline).foregroundColor(theme.textPrimary)
                    ForEach(Array(languages.enumerated()), id: \.offset) { index, item in
                        TspButton(item.1, variant: index == languageIndex ? .primary : .default, theme: theme) { languageIndex = index }
                    }
                }
            }
        }
    }

    private func list(theme: StarPlanetTheme) -> some View {
        VStack(alignment: .leading, spacing: 14) {
            // Keep the sample entry structure aligned with Web/RN/Android/Kuikly.
            ForEach(Array(Set(componentDocs.map(\.category))).sorted(), id: \.self) { category in
                Text(category).font(.subheadline.bold()).foregroundColor(theme.textSecondary)
                ForEach(componentDocs.filter { $0.category == category }) { doc in
                    TspListItem(title: "Tsp\(doc.id)", message: doc.description, trailing: "›", theme: theme) {
                        selectedDoc = doc
                    }
                }
            }
        }
    }

    private func detail(_ doc: ComponentDoc, theme: StarPlanetTheme) -> some View {
        VStack(alignment: .leading, spacing: 14) {
            TspCard(theme: theme) {
                Text(doc.category).font(.caption.bold()).foregroundColor(theme.brandPrimary)
                Text("Tsp\(doc.id)").font(.title.bold()).foregroundColor(theme.textPrimary).padding(.top, 6)
                Text(doc.description).foregroundColor(theme.textSecondary).padding(.top, 6)
            }
            TspCard(theme: theme) {
                Text("使用案例").font(.headline).foregroundColor(theme.textPrimary)
                preview(doc.id, theme: theme).padding(.top, 10)
            }
            TspCard(theme: theme) {
                Text("技术栈同步").font(.headline).foregroundColor(theme.textPrimary)
                FlowLabels(["React", "Vue", "Android", "iOS", "Kuikly", "RN"], theme: theme).padding(.top, 10)
            }
        }
    }

    @ViewBuilder
    private func preview(_ name: String, theme: StarPlanetTheme) -> some View {
        switch name {
        case "Button":
            VStack(spacing: 10) { TspButton("primary", variant: .primary, theme: theme); TspButton("default", theme: theme); TspButton("danger", variant: .danger, theme: theme); TspButton("text", variant: .text, theme: theme) }
        case "Card":
            TspCard(theme: theme) { Text("Selected card").foregroundColor(theme.textPrimary) }
        case "Alert":
            VStack(spacing: 10) { TspAlert(title: "success", message: "Theme is applied.", variant: .success, theme: theme); TspAlert(title: "warning", message: "Check required fields.", variant: .warning, theme: theme) }
        case "Badge":
            FlowLabels(["default", "primary", "success", "warning", "danger"], theme: theme)
        case "Chip":
            HStack { TspChip("Default", theme: theme); TspChip("Selected", selected: true, theme: theme); TspChip("Disabled", disabled: true, theme: theme) }
        case "Input":
            VStack(spacing: 10) { TspInput(value: $inputValue, placeholder: "Input", theme: theme); TspInput(value: .constant(""), placeholder: "Required", variant: "error", theme: theme) }
        case "Select", "OptionSheet":
            TspSelect(options: ["A", "B", "C"], selectedIndex: $selectedIndex, theme: theme)
        case "Switch":
            Toggle("Switch", isOn: $checked).foregroundColor(theme.textPrimary)
        case "Progress":
            VStack(spacing: 10) { TspProgress(progress: 38, theme: theme); TspProgress(progress: 68, variant: "success", theme: theme); TspProgress(progress: 82, variant: "danger", theme: theme) }
        case "TopBar":
            TspTopBar(title: "基础组件", showBack: true, theme: theme)
        case "BottomTab":
            TspBottomTab(tabs: tabs, selectedKey: $tab, theme: theme)
        case "Tabs":
            TspTabs(tabs: ["全部", "已学", "未学"], selectedIndex: $selectedTab, theme: theme)
        case "Amount":
            VStack(spacing: 10) { TspAmount(symbol: "$", value: "128.80", cycle: "month", theme: theme); TspAmount(symbol: "$", value: "199.00", strikeThrough: true, theme: theme) }
        case "IconButton":
            HStack { TspIconButton(icon: "♪", selected: true, theme: theme); TspIconButton(icon: "✓", theme: theme); TspIconButton(icon: "×", disabled: true, theme: theme) }
        case "KeyValueLabel":
            TspKeyValueLabel(label: "Progress", value: "12/48", theme: theme)
        case "Notification":
            TspNotification(title: "通知", message: "继续学习。", variant: "alert", theme: theme)
        case "TextLink":
            TspTextLink("Text Link", theme: theme)
        case "Stepper":
            VStack(spacing: 10) { TspStepper(stepCount: 3, currentStep: 2, theme: theme); TspStepper(stepCount: 5, currentStep: 3, theme: theme) }
        case "StickyFooter":
            TspStickyFooter(theme: theme) { TspButton("Sticky Footer", variant: .primary, theme: theme) }
        case "PinInput":
            VStack(spacing: 10) { TspPinInput(value: "2468", secure: true, theme: theme); TspPinInput(value: "123", cellCount: 6, theme: theme) }
        case "ListItem":
            VStack(spacing: 10) { TspListItem(title: "列表项", message: "选中状态", trailing: "›", selected: true, theme: theme); TspListItem(title: "不可点击", message: "禁用状态", disabled: true, theme: theme) }
        case "Empty":
            TspEmpty(title: "空状态", message: "暂无记录。", actionText: "操作", theme: theme)
        case "Toast":
            TspButton("Show Toast", variant: .primary, theme: theme) { showToast = true; DispatchQueue.main.asyncAfter(deadline: .now() + 1.4) { showToast = false } }
        case "Modal":
            TspButton("Open Modal", theme: theme) { showModal = true }
        default:
            EmptyView()
        }
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
                TspBadge(value, variant: value == "primary" ? "primary" : "default", theme: theme)
            }
        }
    }
}
