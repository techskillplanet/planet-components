import SwiftUI

public struct TspButton: View {
    public enum Variant { case primary, `default`, danger, text, link }
    let text: String
    let variant: Variant
    let disabled: Bool
    let loading: Bool
    let theme: StarPlanetTheme
    let action: () -> Void

    public init(
        _ text: String,
        variant: Variant = .default,
        disabled: Bool = false,
        loading: Bool = false,
        theme: StarPlanetTheme = .sky,
        action: @escaping () -> Void = {}
    ) {
        self.text = text
        self.variant = variant
        self.disabled = disabled
        self.loading = loading
        self.theme = theme
        self.action = action
    }

    private var inert: Bool { disabled || loading }

    public var body: some View {
        Button(action: { if !inert { action() } }) {
            HStack(spacing: 8) {
                if loading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle(tint: textColor))
                        .scaleEffect(0.85)
                }
                Text(text)
                    .font(.system(size: 15, weight: .bold))
                    .foregroundColor(textColor)
            }
            .frame(maxWidth: .infinity, minHeight: 46)
            .background(face)
            .clipShape(Capsule())
        }
        .buttonStyle(.plain)
        .opacity(inert ? 0.45 : 1)
        .disabled(inert)
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
