import SwiftUI

public struct TspScoreRule: Identifiable, Hashable {
    public var id: String
    public var name: String
    public var icon: String?
    public var value: Int
    public var count: Int
    public var dailyLimit: Int?

    public init(
        id: String,
        name: String,
        icon: String? = nil,
        value: Int = 0,
        count: Int = 0,
        dailyLimit: Int? = nil
    ) {
        self.id = id
        self.name = name
        self.icon = icon
        self.value = value
        self.count = count
        self.dailyLimit = dailyLimit
    }
}

/// Score rule cards with optional +1.
public struct TspScoreRuleGrid: View {
    public enum Variant { case `default`, readOnly }
    public enum Columns { case auto, one, two }

    let rules: [TspScoreRule]
    let columns: Columns
    let variant: Variant
    let disabled: Bool
    let theme: StarPlanetTheme
    let onIncrement: ((TspScoreRule) -> Void)?

    public init(
        rules: [TspScoreRule],
        columns: Columns = .auto,
        variant: Variant = .default,
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onIncrement: ((TspScoreRule) -> Void)? = nil
    ) {
        self.rules = rules
        self.columns = columns
        self.variant = variant
        self.disabled = disabled
        self.theme = theme
        self.onIncrement = onIncrement
    }

    private var readOnly: Bool { variant == .readOnly || disabled }

    private var gridColumns: [GridItem] {
        switch columns {
        case .one:
            return [GridItem(.flexible(), spacing: 12)]
        case .two, .auto:
            return [GridItem(.flexible(), spacing: 12), GridItem(.flexible(), spacing: 12)]
        }
    }

    public var body: some View {
        LazyVGrid(columns: gridColumns, spacing: 12) {
            ForEach(rules) { rule in
                card(rule)
            }
        }
    }

    private func card(_ rule: TspScoreRule) -> some View {
        let atLimit = rule.dailyLimit.map { rule.count >= $0 } ?? false
        let meta = rule.dailyLimit.map { "已记 \(rule.count)/\($0)" } ?? "已记 \(rule.count)"
        let valueText = rule.value > 0 ? "+\(rule.value)" : "\(rule.value)"

        return VStack(alignment: .leading, spacing: 8) {
            HStack(spacing: 6) {
                if let icon = rule.icon {
                    Text(icon)
                }
                Text(rule.name)
                    .font(.system(size: 13, weight: .heavy))
                    .foregroundColor(theme.textPrimary)
                    .lineLimit(1)
            }
            Text(valueText)
                .font(.system(size: 22, weight: .black))
                .foregroundColor(rule.value >= 0 ? theme.success : theme.danger)
            Text(meta)
                .font(.system(size: 12))
                .foregroundColor(theme.textTertiary)
            if !readOnly {
                Button("+1") {
                    onIncrement?(rule)
                }
                .font(.system(size: 13, weight: .black))
                .foregroundColor(theme.brandDark)
                .frame(maxWidth: .infinity, minHeight: 44)
                .background(theme.selectedFill)
                .overlay(Capsule().stroke(theme.borderDefault, lineWidth: 1))
                .clipShape(Capsule())
                .disabled(atLimit || disabled)
                .opacity(atLimit || disabled ? 0.4 : 1)
            }
        }
        .padding(12)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(theme.surfaceRaised)
        .overlay(RoundedRectangle(cornerRadius: 18).stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }
}
