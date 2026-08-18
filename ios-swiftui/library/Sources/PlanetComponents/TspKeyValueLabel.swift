import SwiftUI

public struct TspKeyValueLabel: View {
    let label: String
    let value: String
    let theme: StarPlanetTheme
    public init(label: String, value: String, theme: StarPlanetTheme = .sky) {
        self.label = label
        self.value = value
        self.theme = theme
    }
    public var body: some View {
        HStack { Text(label).foregroundColor(theme.textSecondary); Spacer(); Text(value).fontWeight(.bold).foregroundColor(theme.textPrimary) }
    }
}
