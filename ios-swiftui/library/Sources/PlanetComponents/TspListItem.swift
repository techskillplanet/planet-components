import SwiftUI

public struct TspListItem: View {
    let title: String
    let message: String
    let trailing: String
    let selected: Bool
    let disabled: Bool
    let theme: StarPlanetTheme
    let action: () -> Void
    public init(title: String, message: String = "", trailing: String = "", selected: Bool = false, disabled: Bool = false, theme: StarPlanetTheme = .sky, action: @escaping () -> Void = {}) {
        self.title = title
        self.message = message
        self.trailing = trailing
        self.selected = selected
        self.disabled = disabled
        self.theme = theme
        self.action = action
    }
    public var body: some View {
        Button(action: { if !disabled { action() } }) {
            HStack(alignment: .center, spacing: 12) {
                VStack(alignment: .leading, spacing: 4) {
                    Text(title).fontWeight(.bold).foregroundColor(theme.textPrimary)
                    if !message.isEmpty {
                        Text(message)
                            .font(.subheadline)
                            .foregroundColor(theme.textSecondary)
                            .multilineTextAlignment(.leading)
                            .fixedSize(horizontal: false, vertical: true)
                    }
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                if !trailing.isEmpty {
                    Text(trailing)
                        .foregroundColor(theme.textTertiary)
                        .frame(width: 20, alignment: .trailing)
                }
            }
            .padding(14)
            .frame(maxWidth: .infinity, minHeight: 64, alignment: .leading)
            .background(selected ? theme.selectedFill : theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 18).stroke(selected ? theme.success : theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 18))
        }
        .buttonStyle(.plain)
        .opacity(disabled ? 0.45 : 1)
    }
}
