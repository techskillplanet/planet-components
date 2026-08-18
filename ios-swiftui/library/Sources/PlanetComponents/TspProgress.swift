import SwiftUI

public struct TspProgress: View {
    let progress: Double
    let variant: String
    let theme: StarPlanetTheme

    public init(progress: Double, variant: String = "primary", theme: StarPlanetTheme = .sky) {
        self.progress = min(max(progress, 0), 100)
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        GeometryReader { proxy in
            ZStack(alignment: .leading) {
                Capsule().fill(theme.borderDefault)
                Capsule().fill(fill).frame(width: proxy.size.width * progress / 100)
            }
        }
        .frame(height: 10)
    }

    private var fill: Color {
        switch variant {
        case "success": return theme.success
        case "warning": return theme.warning
        case "danger": return theme.danger
        default: return theme.brandPrimary
        }
    }
}
