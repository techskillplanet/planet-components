import SwiftUI

/// Continuous check-in summary.
public struct TspCheckInStreakCard: View {
    let streakDays: Int
    let totalDays: Int
    let weekProgress: Double
    let disabled: Bool
    let theme: StarPlanetTheme
    let onOpen: (() -> Void)?

    public init(
        streakDays: Int = 0,
        totalDays: Int = 0,
        weekProgress: Double = 0,
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onOpen: (() -> Void)? = nil
    ) {
        self.streakDays = streakDays
        self.totalDays = totalDays
        self.weekProgress = weekProgress
        self.disabled = disabled
        self.theme = theme
        self.onOpen = onOpen
    }

    private var pct: Int {
        Int(max(0, min(100, (weekProgress * 100).rounded())))
    }

    public var body: some View {
        Button {
            guard !disabled else { return }
            onOpen?()
        } label: {
            VStack(alignment: .leading, spacing: 8) {
                Text("连续打卡")
                    .font(.system(size: 16, weight: .black))
                    .foregroundColor(theme.textPrimary)
                HStack(spacing: 16) {
                    Text("连续 \(streakDays) 天")
                    Text("累计 \(totalDays) 天")
                    Text("本周 \(pct)%")
                }
                .font(.system(size: 13, weight: .bold))
                .foregroundColor(theme.textSecondary)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(14)
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 18).stroke(theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 18))
        }
        .buttonStyle(.plain)
        .disabled(disabled)
        .opacity(disabled ? 0.45 : 1)
    }
}
