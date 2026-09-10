import SwiftUI

/// Floating action button.
public struct TspFab: View {
    public var icon: String
    public var text: String
    public var variant: String
    public var disabled: Bool
    public var theme: StarPlanetTheme
    public var onTap: () -> Void

    public init(
        icon: String = "+",
        text: String = "",
        variant: String = "primary",
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onTap: @escaping () -> Void = {}
    ) {
        self.icon = icon
        self.text = text
        self.variant = variant
        self.disabled = disabled
        self.theme = theme
        self.onTap = onTap
    }

    private var isPrimary: Bool { variant != "default" && variant != "standard" }
    private var extended: Bool { !text.isEmpty }

    public var body: some View {
        Button(action: onTap) {
            HStack(spacing: 8) {
                Text(icon)
                    .font(.system(size: 22, weight: .heavy))
                if extended {
                    Text(text)
                        .font(.system(size: 15, weight: .heavy))
                }
            }
            .foregroundColor(isPrimary ? .white : theme.textPrimary)
            .padding(.horizontal, extended ? 18 : 0)
            .frame(minWidth: extended ? nil : 56, minHeight: extended ? 52 : 56)
            .background(isPrimary ? theme.brandPrimary : theme.surfaceRaised)
            .overlay(Capsule().stroke(theme.borderDefault, lineWidth: 2))
            .clipShape(Capsule())
            .shadow(
                color: theme.buttonRaisedShadowEnabled
                    ? theme.brandPrimary.opacity(0.28)
                    : .clear,
                radius: theme.buttonRaisedShadowEnabled ? 8 : 0,
                y: theme.buttonRaisedShadowEnabled ? theme.shadowControlIslandLiftY : 0
            )
        }
        .buttonStyle(.plain)
        .disabled(disabled)
        .opacity(disabled ? 0.45 : 1)
        .accessibilityLabel(text.isEmpty ? icon : text)
    }
}
