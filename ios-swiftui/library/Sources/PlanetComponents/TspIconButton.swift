import SwiftUI

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
