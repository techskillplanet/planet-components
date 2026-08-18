import SwiftUI

public struct TspStickyFooter<Content: View>: View {
    let theme: StarPlanetTheme
    let content: Content
    public init(theme: StarPlanetTheme = .sky, @ViewBuilder content: () -> Content) {
        self.theme = theme
        self.content = content()
    }
    public var body: some View {
        content.padding(12).frame(maxWidth: .infinity).background(theme.surfaceRaised).overlay(Rectangle().stroke(theme.borderDefault, lineWidth: 1))
    }
}
