import SwiftUI

public struct TspTextLink: View {
    let text: String
    let inverse: Bool
    let theme: StarPlanetTheme
    public init(_ text: String, inverse: Bool = false, theme: StarPlanetTheme = .sky) {
        self.text = text
        self.inverse = inverse
        self.theme = theme
    }
    public var body: some View {
        Text(text).fontWeight(.bold).foregroundColor(inverse ? .white : theme.brandPrimary)
    }
}
