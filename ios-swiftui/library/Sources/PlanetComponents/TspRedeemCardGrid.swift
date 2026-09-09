import SwiftUI

public struct TspRedeemItem: Identifiable, Hashable {
    public var id: String
    public var name: String
    public var icon: String
    public var cost: Int

    public init(id: String, name: String, icon: String = "🎁", cost: Int = 0) {
        self.id = id
        self.name = name
        self.icon = icon
        self.cost = cost
    }
}

/// Redeem item cards.
public struct TspRedeemCardGrid: View {
    let items: [TspRedeemItem]
    let availablePoints: Int
    let frozen: Bool
    let disabled: Bool
    let theme: StarPlanetTheme
    let onRedeem: ((TspRedeemItem) -> Void)?

    public init(
        items: [TspRedeemItem],
        availablePoints: Int = 0,
        frozen: Bool = false,
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onRedeem: ((TspRedeemItem) -> Void)? = nil
    ) {
        self.items = items
        self.availablePoints = availablePoints
        self.frozen = frozen
        self.disabled = disabled
        self.theme = theme
        self.onRedeem = onRedeem
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            if frozen {
                Text("今日已冻结，暂不可兑换")
                    .font(.system(size: 13, weight: .heavy))
                    .foregroundColor(theme.textPrimary)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 8)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .background(theme.activeFill)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
            }
            LazyVGrid(columns: [GridItem(.flexible(), spacing: 12), GridItem(.flexible(), spacing: 12)], spacing: 12) {
                ForEach(items) { item in
                    card(item)
                }
            }
        }
    }

    private func card(_ item: TspRedeemItem) -> some View {
        let insufficient = availablePoints < item.cost
        let blocked = frozen || disabled || insufficient
        return Button {
            guard !blocked else { return }
            onRedeem?(item)
        } label: {
            VStack(alignment: .leading, spacing: 4) {
                Text(item.icon).font(.system(size: 28))
                Text(item.name)
                    .font(.system(size: 14, weight: .heavy))
                    .foregroundColor(theme.textPrimary)
                    .lineLimit(2)
                Text("\(item.cost) 分")
                    .font(.system(size: 14, weight: .black))
                    .foregroundColor(theme.brandPrimary)
                if insufficient && !frozen {
                    Text("积分不足")
                        .font(.system(size: 12))
                        .foregroundColor(theme.danger)
                }
            }
            .frame(maxWidth: .infinity, minHeight: 120, alignment: .topLeading)
            .padding(14)
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 18).stroke(theme.borderDefault, lineWidth: 2))
            .clipShape(RoundedRectangle(cornerRadius: 18))
            .opacity(blocked ? 0.5 : 1)
        }
        .buttonStyle(.plain)
        .disabled(blocked)
    }
}
