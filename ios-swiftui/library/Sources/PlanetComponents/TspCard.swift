import SwiftUI

public struct TspCard<Content: View>: View {
    let selected: Bool
    let disabled: Bool
    let theme: StarPlanetTheme
    let content: Content

    public init(
        selected: Bool = false,
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        @ViewBuilder content: () -> Content
    ) {
        self.selected = selected
        self.disabled = disabled
        self.theme = theme
        self.content = content()
    }

    public var body: some View {
        content
            .padding(20)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(selected ? theme.selectedFill : theme.surfaceRaised)
            .overlay(
                RoundedRectangle(cornerRadius: 28)
                    .stroke(selected ? theme.success : theme.borderDefault, lineWidth: 1)
            )
            .clipShape(RoundedRectangle(cornerRadius: 28))
            .opacity(disabled ? 0.45 : 1)
    }
}
