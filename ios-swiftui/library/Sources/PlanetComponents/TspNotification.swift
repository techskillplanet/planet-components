import SwiftUI

public struct TspNotification: View {
    let title: String
    let message: String
    let variant: String
    let theme: StarPlanetTheme
    public init(title: String, message: String, variant: String = "info", theme: StarPlanetTheme = .sky) {
        self.title = title
        self.message = message
        self.variant = variant
        self.theme = theme
    }
    public var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(title).fontWeight(.bold).foregroundColor(theme.textPrimary)
            Text(message).foregroundColor(theme.textSecondary)
        }
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(variant == "alert" ? theme.activeFill : theme.pageEnd)
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(variant == "alert" ? theme.warning : theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}
