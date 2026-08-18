import SwiftUI

public struct TspAlert: View {
    public enum Variant { case info, success, warning, error }
    let title: String
    let message: String
    let variant: Variant
    let theme: StarPlanetTheme

    public init(title: String, message: String, variant: Variant = .info, theme: StarPlanetTheme = .sky) {
        self.title = title
        self.message = message
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(title).font(.system(size: 15, weight: .bold)).foregroundColor(theme.textPrimary)
            Text(message).font(.system(size: 13)).foregroundColor(theme.textSecondary)
        }
        .padding(14)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(fill)
        .overlay(RoundedRectangle(cornerRadius: 18).stroke(stroke, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 18))
    }

    private var fill: Color {
        switch variant {
        case .success: return theme.selectedFill
        case .warning: return theme.activeFill
        case .error: return theme.danger.opacity(0.12)
        case .info: return theme.pageEnd
        }
    }

    private var stroke: Color {
        switch variant {
        case .success: return theme.success
        case .warning: return theme.warning
        case .error: return theme.danger
        case .info: return theme.borderDefault
        }
    }
}
