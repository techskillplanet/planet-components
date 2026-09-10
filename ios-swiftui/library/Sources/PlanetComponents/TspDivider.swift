import SwiftUI

/// Horizontal hairline divider.
public struct TspDivider: View {
    public var text: String
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme

    public init(
        text: String = "",
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky
    ) {
        self.text = text
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        Rectangle()
            .fill(variant == "strong" ? theme.textTertiary : theme.borderDefault)
            .frame(height: 1)
            .padding(.vertical, 12)
            .opacity(disabled ? 0.4 : 1)
            .accessibilityLabel(text.isEmpty ? "Divider" : text)
            .accessibilityAddTraits(.isStaticText)
    }
}
