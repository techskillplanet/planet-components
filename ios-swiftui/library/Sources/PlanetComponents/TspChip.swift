import SwiftUI

public struct TspChip: View {
    let text: String
    let selected: Bool
    let disabled: Bool
    let theme: StarPlanetTheme
    let action: () -> Void

    public init(_ text: String, selected: Bool = false, disabled: Bool = false, theme: StarPlanetTheme = .sky, action: @escaping () -> Void = {}) {
        self.text = text
        self.selected = selected
        self.disabled = disabled
        self.theme = theme
        self.action = action
    }

    public var body: some View {
        Button(action: { if !disabled { action() } }) {
            Text(text).font(.system(size: 14, weight: .bold)).foregroundColor(theme.textPrimary)
                .padding(.horizontal, 14).padding(.vertical, 8)
                .background(selected ? theme.selectedFill : theme.surfaceRaised)
                .overlay(Capsule().stroke(selected ? theme.success : theme.borderDefault, lineWidth: 1))
                .clipShape(Capsule())
        }
        .buttonStyle(.plain)
        .opacity(disabled ? 0.45 : 1)
    }
}
