import SwiftUI

public struct TspBadge: View {
    let text: String
    let variant: String
    let theme: StarPlanetTheme

    public init(_ text: String, variant: String = "default", theme: StarPlanetTheme = .sky) {
        self.text = text
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        Text(text)
            .font(.system(size: 13, weight: .bold))
            .foregroundColor(["primary", "success", "danger"].contains(variant) ? .white : theme.textPrimary)
            .padding(.horizontal, 10)
            .padding(.vertical, 6)
            .background(fill)
            .clipShape(Capsule())
    }

    private var fill: Color {
        switch variant {
        case "primary": return theme.brandPrimary
        case "success": return theme.success
        case "warning": return theme.warning
        case "danger": return theme.danger
        default: return theme.pageEnd
        }
    }
}
