import SwiftUI

/// Loading overlay — aligns with RN `TspLoadingDialog`.
public struct TspLoadingDialog: View {
    public enum Variant {
        case `default`
        case compact
    }

    let message: String
    let variant: Variant
    let dismissible: Bool
    let theme: StarPlanetTheme
    let onDismiss: () -> Void

    public init(
        message: String = "加载中...",
        variant: Variant = .default,
        dismissible: Bool = false,
        theme: StarPlanetTheme = .sky,
        onDismiss: @escaping () -> Void = {}
    ) {
        self.message = message
        self.variant = variant
        self.dismissible = dismissible
        self.theme = theme
        self.onDismiss = onDismiss
    }

    public var body: some View {
        let compact = variant == .compact
        ZStack {
            Color(red: 0.090, green: 0.227, blue: 0.384).opacity(0.28)
                .ignoresSafeArea()
                .onTapGesture {
                    if dismissible { onDismiss() }
                }
            VStack(spacing: compact ? 8 : 12) {
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: theme.brandPrimary))
                    .scaleEffect(compact ? 0.9 : 1.15)
                if !message.isEmpty {
                    Text(message)
                        .font(.system(size: 15, weight: .heavy))
                        .foregroundColor(theme.textPrimary)
                        .multilineTextAlignment(.center)
                }
            }
            .padding(.horizontal, compact ? 18 : 28)
            .padding(.vertical, compact ? 14 : 22)
            .frame(minWidth: compact ? 112 : 148)
            .background(theme.surfaceRaised)
            .overlay(
                RoundedRectangle(cornerRadius: compact ? 18 : 24)
                    .stroke(theme.borderDefault, lineWidth: 1)
            )
            .clipShape(RoundedRectangle(cornerRadius: compact ? 18 : 24))
        }
    }
}
