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
