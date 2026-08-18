import SwiftUI

/// Toast view + helper to present with RN-like auto-dismiss (~1600ms).
public struct TspToast: View {
    public static let defaultDuration: TimeInterval = 1.6

    let message: String
    let variant: String
    let theme: StarPlanetTheme

    public init(message: String, variant: String = "info", theme: StarPlanetTheme = .sky) {
        self.message = message
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        Text(message)
            .fontWeight(.bold)
            .foregroundColor(variant == "warning" ? theme.textPrimary : .white)
            .padding(.horizontal, 14)
            .padding(.vertical, 10)
            .background(fill)
            .clipShape(Capsule())
    }

    private var fill: Color {
        switch variant {
        case "success": return theme.success
        case "warning": return theme.warning
        case "error": return theme.danger
        default: return theme.brandDark
        }
    }
}

/// Overlay host for toast visibility + auto dismiss.
public struct TspToastHost: ViewModifier {
    @Binding var visible: Bool
    let message: String
    let variant: String
    let theme: StarPlanetTheme
    let duration: TimeInterval

    public func body(content: Content) -> some View {
        content.overlay(alignment: .bottom) {
            if visible {
                TspToast(message: message, variant: variant, theme: theme)
                    .padding(.bottom, 90)
                    .transition(.opacity)
                    .onAppear {
                        DispatchQueue.main.asyncAfter(deadline: .now() + duration) {
                            visible = false
                        }
                    }
            }
        }
    }
}

public extension View {
    func tspToast(
        visible: Binding<Bool>,
        message: String,
        variant: String = "info",
        theme: StarPlanetTheme = .sky,
        duration: TimeInterval = TspToast.defaultDuration
    ) -> some View {
        modifier(TspToastHost(visible: visible, message: message, variant: variant, theme: theme, duration: duration))
    }
}
