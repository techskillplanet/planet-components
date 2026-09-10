import SwiftUI

/// Placeholder skeleton rows with optional avatar + pulse / opacity animation.
public struct TspSkeleton: View {
    public var rows: Int
    public var animated: Bool
    public var avatar: Bool
    public var variant: String
    public var theme: StarPlanetTheme

    @Environment(\.accessibilityReduceMotion) private var reduceMotion
    @State private var pulse = false

    public init(
        rows: Int = 3,
        animated: Bool = true,
        avatar: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky
    ) {
        self.rows = rows
        self.animated = animated
        self.avatar = avatar
        self.variant = variant
        self.theme = theme
    }

    private var count: Int { min(12, max(1, rows)) }

    private var shouldAnimate: Bool { animated && !reduceMotion }

    public var body: some View {
        HStack(alignment: .top, spacing: 12) {
            if avatar {
                Circle()
                    .fill(fill)
                    .frame(width: 40, height: 40)
            }
            VStack(alignment: .leading, spacing: 10) {
                ForEach(0..<count, id: \.self) { i in
                    RoundedRectangle(cornerRadius: 8, style: .continuous)
                        .fill(fill)
                        .frame(height: 12)
                        .frame(maxWidth: i == count - 1 ? .infinity : .infinity)
                        .padding(.trailing, i == count - 1 ? 48 : 0)
                }
            }
        }
        .opacity(shouldAnimate ? (pulse ? 1 : 0.45) : 0.7)
        .onAppear { startIfNeeded() }
        .onChange(of: animated) { _ in startIfNeeded() }
        .accessibilityHidden(true)
    }

    private var fill: Color {
        pulse && variant == "pulse" ? theme.brandSubtle : theme.borderDefault.opacity(0.55)
    }

    private func startIfNeeded() {
        guard shouldAnimate else {
            pulse = false
            return
        }
        withAnimation(.easeInOut(duration: 1.0).repeatForever(autoreverses: true)) {
            pulse = true
        }
    }
}
