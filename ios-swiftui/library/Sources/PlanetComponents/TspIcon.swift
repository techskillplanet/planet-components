import SwiftUI

/// Named Planet icon from the shared catalog (glyph rendering; path in `PlanetIcons`).
public struct TspIcon: View {
    let name: String
    let size: CGFloat
    let label: String?
    let color: Color?
    let theme: StarPlanetTheme

    public init(
        name: String = "check",
        size: CGFloat = 20,
        label: String? = nil,
        color: Color? = nil,
        theme: StarPlanetTheme = .sky
    ) {
        self.name = name
        self.size = size
        self.label = label
        self.color = color
        self.theme = theme
    }

    public var body: some View {
        let spec = PlanetIcons.spec(named: name)
        Text(spec.glyph)
            .font(.system(size: size, weight: .semibold))
            .foregroundColor(color ?? theme.textPrimary)
            .frame(width: size, height: size)
            .accessibilityHidden(label == nil)
            .accessibilityLabel(label ?? "")
    }
}
