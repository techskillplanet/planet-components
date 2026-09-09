import SwiftUI

/// Available points hero.
public struct TspBalanceHero: View {
    public enum Variant { case `default`, compact }

    private static let breakdownLabels: [String: String] = [
        "balance": "余额",
        "ruleScore": "规则分",
        "streakBonus": "连续奖励",
        "redeemTotal": "已兑换",
    ]

    let total: Int
    let breakdown: [String: Int]?
    let suffix: String
    let variant: Variant
    let theme: StarPlanetTheme

    public init(
        total: Int = 0,
        breakdown: [String: Int]? = nil,
        suffix: String = "分",
        variant: Variant = .default,
        theme: StarPlanetTheme = .sky
    ) {
        self.total = total
        self.breakdown = breakdown
        self.suffix = suffix
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("可用积分")
                .font(.system(size: 14, weight: .bold))
                .foregroundColor(theme.textSecondary)
            HStack(alignment: .firstTextBaseline, spacing: 6) {
                Text("\(total)")
                    .font(.system(size: variant == .compact ? 28 : 36, weight: .black))
                    .foregroundColor(theme.textPrimary)
                if !suffix.isEmpty {
                    Text(suffix)
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(theme.textSecondary)
                }
            }
            if let breakdown, !breakdown.isEmpty {
                VStack(spacing: 6) {
                    ForEach(Array(breakdown.keys.sorted()), id: \.self) { key in
                        HStack {
                            Text(Self.breakdownLabels[key] ?? key)
                            Spacer()
                            Text("\(breakdown[key] ?? 0)")
                                .fontWeight(.heavy)
                        }
                        .font(.system(size: 13))
                        .foregroundColor(theme.textSecondary)
                    }
                }
                .padding(.top, 8)
            }
        }
        .padding(16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(theme.surfaceRaised)
        .overlay(RoundedRectangle(cornerRadius: 20).stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 20))
    }
}
