import SwiftUI

public struct TspButton: View {
    public enum Variant { case primary, `default`, danger, text, link }
    let text: String
    let variant: Variant
    let disabled: Bool
    let theme: StarPlanetTheme
    let action: () -> Void

    public init(_ text: String, variant: Variant = .default, disabled: Bool = false, theme: StarPlanetTheme = .sky, action: @escaping () -> Void = {}) {
        self.text = text
        self.variant = variant
        self.disabled = disabled
        self.theme = theme
        self.action = action
    }

    public var body: some View {
        Button(action: { if !disabled { action() } }) {
            Text(text)
                .font(.system(size: 15, weight: .bold))
                .foregroundColor(textColor)
                .frame(maxWidth: .infinity, minHeight: 46)
                .background(face)
                .clipShape(Capsule())
        }
        .buttonStyle(.plain)
        .opacity(disabled ? 0.45 : 1)
        .background(Capsule().fill(theme.borderDefault).offset(y: 5))
    }

    private var face: Color {
        switch variant {
        case .primary: return theme.brandPrimary
        case .danger: return theme.danger
        case .text, .link, .default: return theme.surfaceRaised
        }
    }

    private var textColor: Color {
        switch variant {
        case .primary, .danger: return .white
        case .text, .link: return theme.brandPrimary
        case .default: return theme.textPrimary
        }
    }
}

public struct TspCard<Content: View>: View {
    let theme: StarPlanetTheme
    let content: Content

    public init(theme: StarPlanetTheme = .sky, @ViewBuilder content: () -> Content) {
        self.theme = theme
        self.content = content()
    }

    public var body: some View {
        content
            .padding(20)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 28).stroke(theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 28))
    }
}

public struct TspAlert: View {
    public enum Variant { case info, success, warning, error }
    let title: String
    let message: String
    let variant: Variant
    let theme: StarPlanetTheme

    public init(title: String, message: String, variant: Variant = .info, theme: StarPlanetTheme = .sky) {
        self.title = title
        self.message = message
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(title).font(.system(size: 15, weight: .bold)).foregroundColor(theme.textPrimary)
            Text(message).font(.system(size: 13)).foregroundColor(theme.textSecondary)
        }
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(fill)
        .overlay(RoundedRectangle(cornerRadius: 18).stroke(stroke, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }

    private var fill: Color {
        switch variant {
        case .success: return theme.selectedFill
        case .warning: return theme.activeFill
        case .error: return theme.danger.opacity(0.12)
        case .info: return theme.pageEnd
        }
    }

    private var stroke: Color {
        switch variant {
        case .success: return theme.success
        case .warning: return theme.warning
        case .error: return theme.danger
        case .info: return theme.borderDefault
        }
    }
}

public struct TspAmount: View {
    let symbol: String
    let value: String
    let cycle: String
    let symbolAfter: Bool
    let strikeThrough: Bool
    let theme: StarPlanetTheme

    public init(symbol: String = "¥", value: String, cycle: String = "", symbolAfter: Bool = false, strikeThrough: Bool = false, theme: StarPlanetTheme = .sky) {
        self.symbol = symbol
        self.value = value
        self.cycle = cycle
        self.symbolAfter = symbolAfter
        self.strikeThrough = strikeThrough
        self.theme = theme
    }

    public var body: some View {
        HStack(alignment: .lastTextBaseline, spacing: 4) {
            if !symbolAfter { Text(symbol).font(.headline) }
            Text(value).font(.system(size: 30, weight: .heavy))
            if symbolAfter { Text(symbol).font(.headline) }
            if !cycle.isEmpty { Text("/\(cycle)").font(.caption).foregroundColor(theme.textSecondary) }
        }
        .foregroundColor(theme.textPrimary)
        .strikethrough(strikeThrough)
    }
}

public struct TspStepper: View {
    let stepCount: Int
    let currentStep: Int
    let theme: StarPlanetTheme

    public init(stepCount: Int, currentStep: Int, theme: StarPlanetTheme = .sky) {
        self.stepCount = min(max(stepCount, 3), 5)
        self.currentStep = currentStep
        self.theme = theme
    }

    public var body: some View {
        HStack(spacing: 0) {
            ForEach(1...stepCount, id: \.self) { step in
                Text(step < currentStep ? "✓" : "\(step)")
                    .font(.system(size: 13, weight: .bold))
                    .foregroundColor(step <= currentStep ? .white : theme.textTertiary)
                    .frame(width: 32, height: 32)
                    .background(step < currentStep ? theme.success : step == currentStep ? theme.brandPrimary : theme.surfaceRaised)
                    .clipShape(Circle())
                if step < stepCount {
                    Rectangle().fill(step < currentStep ? theme.success : theme.borderDefault).frame(height: 2)
                }
            }
        }
    }
}

public struct TspBadge: View {
    let text: String
    let variant: String
    let theme: StarPlanetTheme

    public init(_ text: String, variant: String = "default", theme: StarPlanetTheme = .sky) {
        self.text = text
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        Text(text)
            .font(.system(size: 13, weight: .bold))
            .foregroundColor(["primary", "success", "danger"].contains(variant) ? .white : theme.textPrimary)
            .padding(.horizontal, 10)
            .padding(.vertical, 6)
            .background(fill)
            .clipShape(Capsule())
    }

    private var fill: Color {
        switch variant {
        case "primary": return theme.brandPrimary
        case "success": return theme.success
        case "warning": return theme.warning
        case "danger": return theme.danger
        default: return theme.pageEnd
        }
    }
}

public struct TspChip: View {
    let text: String
    let selected: Bool
    let disabled: Bool
    let theme: StarPlanetTheme
    let action: () -> Void

    public init(_ text: String, selected: Bool = false, disabled: Bool = false, theme: StarPlanetTheme = .sky, action: @escaping () -> Void = {}) {
        self.text = text
        self.selected = selected
        self.disabled = disabled
        self.theme = theme
        self.action = action
    }

    public var body: some View {
        Button(action: { if !disabled { action() } }) {
            Text(text).font(.system(size: 14, weight: .bold)).foregroundColor(theme.textPrimary)
                .padding(.horizontal, 14).padding(.vertical, 8)
                .background(selected ? theme.selectedFill : theme.surfaceRaised)
                .overlay(Capsule().stroke(selected ? theme.success : theme.borderDefault, lineWidth: 1))
                .clipShape(Capsule())
        }
        .buttonStyle(.plain)
        .opacity(disabled ? 0.45 : 1)
    }
}

public struct TspInput: View {
    @Binding var value: String
    let placeholder: String
    let variant: String
    let disabled: Bool
    let theme: StarPlanetTheme

    public init(value: Binding<String>, placeholder: String = "", variant: String = "default", disabled: Bool = false, theme: StarPlanetTheme = .sky) {
        self._value = value
        self.placeholder = placeholder
        self.variant = variant
        self.disabled = disabled
        self.theme = theme
    }

    public var body: some View {
        TextField(placeholder, text: $value)
            .disabled(disabled)
            .padding(.horizontal, 14)
            .frame(minHeight: 48)
            .foregroundColor(theme.textPrimary)
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 16).stroke(variant == "error" ? theme.danger : theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 16))
            .opacity(disabled ? 0.45 : 1)
    }
}

public struct TspSelect: View {
    let options: [String]
    @Binding var selectedIndex: Int
    let theme: StarPlanetTheme

    public init(options: [String], selectedIndex: Binding<Int>, theme: StarPlanetTheme = .sky) {
        self.options = options
        self._selectedIndex = selectedIndex
        self.theme = theme
    }

    public var body: some View {
        Menu {
            ForEach(options.indices, id: \.self) { index in
                Button(options[index]) { selectedIndex = index }
            }
        } label: {
            HStack {
                Text(options.indices.contains(selectedIndex) ? options[selectedIndex] : "")
                Spacer()
                Text("⌄").foregroundColor(theme.textTertiary)
            }
            .padding(.horizontal, 14)
            .frame(minHeight: 48)
            .foregroundColor(theme.textPrimary)
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 1))
        }
    }
}

public struct TspProgress: View {
    let progress: Double
    let variant: String
    let theme: StarPlanetTheme

    public init(progress: Double, variant: String = "primary", theme: StarPlanetTheme = .sky) {
        self.progress = min(max(progress, 0), 100)
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        GeometryReader { proxy in
            ZStack(alignment: .leading) {
                Capsule().fill(theme.borderDefault)
                Capsule().fill(fill).frame(width: proxy.size.width * progress / 100)
            }
        }
        .frame(height: 10)
    }

    private var fill: Color {
        switch variant {
        case "success": return theme.success
        case "warning": return theme.warning
        case "danger": return theme.danger
        default: return theme.brandPrimary
        }
    }
}

public struct TspTopBar: View {
    let title: String
    let showBack: Bool
    let theme: StarPlanetTheme
    let onBack: () -> Void

    public init(title: String, showBack: Bool = false, theme: StarPlanetTheme = .sky, onBack: @escaping () -> Void = {}) {
        self.title = title
        self.showBack = showBack
        self.theme = theme
        self.onBack = onBack
    }

    public var body: some View {
        HStack {
            Button(showBack ? "‹" : "") { onBack() }.font(.title.bold()).frame(width: 44).buttonStyle(.plain)
            Text(title).font(.headline.bold()).frame(maxWidth: .infinity)
            Color.clear.frame(width: 44)
        }
        .frame(height: 56)
        .foregroundColor(theme.textPrimary)
        .background(theme.pageStart)
    }
}

public struct TspTabItem: Identifiable {
    public let id: String
    public let icon: String
    public let title: String
    public init(id: String, icon: String, title: String) {
        self.id = id
        self.icon = icon
        self.title = title
    }
}

public struct TspBottomTab: View {
    let tabs: [TspTabItem]
    @Binding var selectedKey: String
    let theme: StarPlanetTheme

    public init(tabs: [TspTabItem], selectedKey: Binding<String>, theme: StarPlanetTheme = .sky) {
        self.tabs = tabs
        self._selectedKey = selectedKey
        self.theme = theme
    }

    public var body: some View {
        HStack {
            ForEach(tabs) { tab in
                Button {
                    selectedKey = tab.id
                } label: {
                    VStack(spacing: 2) {
                        Text(tab.icon)
                        Text(tab.title).font(.caption.bold())
                    }
                    .foregroundColor(tab.id == selectedKey ? theme.brandPrimary : theme.textTertiary)
                    .frame(maxWidth: .infinity)
                }
                .buttonStyle(.plain)
            }
        }
        .frame(minHeight: 68)
        .background(theme.surfaceRaised)
        .overlay(Rectangle().stroke(theme.borderDefault, lineWidth: 1))
    }
}

public struct TspTabs: View {
    let tabs: [String]
    @Binding var selectedIndex: Int
    let theme: StarPlanetTheme

    public init(tabs: [String], selectedIndex: Binding<Int>, theme: StarPlanetTheme = .sky) {
        self.tabs = tabs
        self._selectedIndex = selectedIndex
        self.theme = theme
    }

    public var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 6) {
                ForEach(tabs.indices, id: \.self) { index in
                    Text(tabs[index])
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(index == selectedIndex ? .white : theme.textSecondary)
                        .padding(.horizontal, 16)
                        .frame(height: 34)
                        .background(index == selectedIndex ? theme.brandPrimary : .clear)
                        .clipShape(Capsule())
                        .onTapGesture { selectedIndex = index }
                }
            }
            .padding(4)
        }
        .background(theme.pageEnd)
        .overlay(Capsule().stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(Capsule())
    }
}

public struct TspIconButton: View {
    let icon: String
    let selected: Bool
    let disabled: Bool
    let theme: StarPlanetTheme

    public init(icon: String, selected: Bool = false, disabled: Bool = false, theme: StarPlanetTheme = .sky) {
        self.icon = icon
        self.selected = selected
        self.disabled = disabled
        self.theme = theme
    }

    public var body: some View {
        Text(icon).font(.headline.bold())
            .foregroundColor(selected ? .white : theme.textPrimary)
            .frame(width: 44, height: 44)
            .background(selected ? theme.brandPrimary : theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 14).stroke(theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 14))
            .opacity(disabled ? 0.45 : 1)
    }
}

public struct TspKeyValueLabel: View {
    let label: String
    let value: String
    let theme: StarPlanetTheme
    public init(label: String, value: String, theme: StarPlanetTheme = .sky) {
        self.label = label
        self.value = value
        self.theme = theme
    }
    public var body: some View {
        HStack { Text(label).foregroundColor(theme.textSecondary); Spacer(); Text(value).fontWeight(.bold).foregroundColor(theme.textPrimary) }
    }
}

public struct TspNotification: View {
    let title: String
    let message: String
    let variant: String
    let theme: StarPlanetTheme
    public init(title: String, message: String, variant: String = "info", theme: StarPlanetTheme = .sky) {
        self.title = title
        self.message = message
        self.variant = variant
        self.theme = theme
    }
    public var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(title).fontWeight(.bold).foregroundColor(theme.textPrimary)
            Text(message).foregroundColor(theme.textSecondary)
        }
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(variant == "alert" ? theme.activeFill : theme.pageEnd)
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(variant == "alert" ? theme.warning : theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}

public struct TspTextLink: View {
    let text: String
    let inverse: Bool
    let theme: StarPlanetTheme
    public init(_ text: String, inverse: Bool = false, theme: StarPlanetTheme = .sky) {
        self.text = text
        self.inverse = inverse
        self.theme = theme
    }
    public var body: some View {
        Text(text).fontWeight(.bold).foregroundColor(inverse ? .white : theme.brandPrimary)
    }
}

public struct TspStickyFooter<Content: View>: View {
    let theme: StarPlanetTheme
    let content: Content
    public init(theme: StarPlanetTheme = .sky, @ViewBuilder content: () -> Content) {
        self.theme = theme
        self.content = content()
    }
    public var body: some View {
        content.padding(12).frame(maxWidth: .infinity).background(theme.surfaceRaised).overlay(Rectangle().stroke(theme.borderDefault, lineWidth: 1))
    }
}

public struct TspPinInput: View {
    let value: String
    let cellCount: Int
    let secure: Bool
    let theme: StarPlanetTheme
    public init(value: String, cellCount: Int = 4, secure: Bool = false, theme: StarPlanetTheme = .sky) {
        self.value = value
        self.cellCount = min(max(cellCount, 4), 6)
        self.secure = secure
        self.theme = theme
    }
    public var body: some View {
        HStack(spacing: 10) {
            ForEach(0..<cellCount, id: \.self) { index in
                Text(value.count > index ? (secure ? "•" : String(Array(value)[index])) : "")
                    .font(.headline.bold())
                    .foregroundColor(theme.textPrimary)
                    .frame(maxWidth: .infinity, minHeight: 48)
                    .background(theme.surfaceRaised)
                    .overlay(RoundedRectangle(cornerRadius: 14).stroke(theme.borderDefault, lineWidth: 1))
            }
        }
    }
}

public struct TspListItem: View {
    let title: String
    let message: String
    let trailing: String
    let selected: Bool
    let disabled: Bool
    let theme: StarPlanetTheme
    let action: () -> Void
    public init(title: String, message: String = "", trailing: String = "", selected: Bool = false, disabled: Bool = false, theme: StarPlanetTheme = .sky, action: @escaping () -> Void = {}) {
        self.title = title
        self.message = message
        self.trailing = trailing
        self.selected = selected
        self.disabled = disabled
        self.theme = theme
        self.action = action
    }
    public var body: some View {
        Button(action: { if !disabled { action() } }) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text(title).fontWeight(.bold).foregroundColor(theme.textPrimary)
                    if !message.isEmpty { Text(message).font(.subheadline).foregroundColor(theme.textSecondary) }
                }
                Spacer()
                if !trailing.isEmpty { Text(trailing).foregroundColor(theme.textTertiary) }
            }
            .padding(14)
            .frame(maxWidth: .infinity, minHeight: 64)
            .background(selected ? theme.selectedFill : theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 18).stroke(selected ? theme.success : theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 18))
        }
        .buttonStyle(.plain)
        .opacity(disabled ? 0.45 : 1)
    }
}

public struct TspEmpty: View {
    let title: String
    let message: String
    let actionText: String
    let theme: StarPlanetTheme
    public init(title: String, message: String, actionText: String = "", theme: StarPlanetTheme = .sky) {
        self.title = title
        self.message = message
        self.actionText = actionText
        self.theme = theme
    }
    public var body: some View {
        VStack(spacing: 8) {
            Text("○").foregroundColor(.white).frame(width: 50, height: 50).background(theme.brandPrimary).clipShape(RoundedRectangle(cornerRadius: 18))
            Text(title).fontWeight(.bold).foregroundColor(theme.textPrimary)
            Text(message).foregroundColor(theme.textSecondary)
            if !actionText.isEmpty { TspButton(actionText, variant: .primary, theme: theme) }
        }
        .padding(24)
        .frame(maxWidth: .infinity)
        .background(theme.surfaceRaised)
        .overlay(RoundedRectangle(cornerRadius: 22).stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 22))
    }
}

public struct TspToast: View {
    let message: String
    let variant: String
    let theme: StarPlanetTheme
    public init(message: String, variant: String = "info", theme: StarPlanetTheme = .sky) {
        self.message = message
        self.variant = variant
        self.theme = theme
    }
    public var body: some View {
        Text(message).fontWeight(.bold).foregroundColor(variant == "warning" ? theme.textPrimary : .white).padding(.horizontal, 14).padding(.vertical, 10).background(fill).clipShape(Capsule())
    }
    private var fill: Color {
        switch variant {
        case "success": return theme.success
        case "warning": return theme.warning
        case "error": return theme.danger
        default: return theme.brandDark
        }
    }
}
