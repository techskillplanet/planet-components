import SwiftUI

public struct TspEmpty: View {
    let title: String
    let message: String
    let actionText: String
    let theme: StarPlanetTheme
    public init(title: String, message: String, actionText: String = "", theme: StarPlanetTheme = .sky) {
        self.title = title
        self.message = message
        self.actionText = actionText
        self.theme = theme
    }
    public var body: some View {
        VStack(spacing: 8) {
            Text("○").foregroundColor(.white).frame(width: 50, height: 50).background(theme.brandPrimary).clipShape(RoundedRectangle(cornerRadius: 18))
            Text(title).fontWeight(.bold).foregroundColor(theme.textPrimary)
            Text(message).foregroundColor(theme.textSecondary)
            if !actionText.isEmpty { TspButton(actionText, variant: .primary, theme: theme) }
        }
        .padding(24)
        .frame(maxWidth: .infinity)
        .background(theme.surfaceRaised)
        .overlay(RoundedRectangle(cornerRadius: 22).stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 22))
    }
}
