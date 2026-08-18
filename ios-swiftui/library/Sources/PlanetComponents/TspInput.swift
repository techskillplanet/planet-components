import SwiftUI

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
