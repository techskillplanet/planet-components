import SwiftUI

/// Confirmation dialog — aligns with RN `TspModal` (mask + panel + dual actions).
public struct TspModal: View {
    let title: String
    let message: String
    let confirmText: String
    let cancelText: String
    let theme: StarPlanetTheme
    let onConfirm: () -> Void
    let onCancel: () -> Void

    public init(
        title: String,
        message: String,
        confirmText: String = "OK",
        cancelText: String = "Cancel",
        theme: StarPlanetTheme = .sky,
        onConfirm: @escaping () -> Void = {},
        onCancel: @escaping () -> Void = {}
    ) {
        self.title = title
        self.message = message
        self.confirmText = confirmText
        self.cancelText = cancelText
        self.theme = theme
        self.onConfirm = onConfirm
        self.onCancel = onCancel
    }

    public var body: some View {
        ZStack {
            Color(red: 0.090, green: 0.227, blue: 0.384).opacity(0.28)
                .ignoresSafeArea()
                .onTapGesture(perform: onCancel)
            VStack(alignment: .leading, spacing: 14) {
                Text(title)
                    .font(.system(size: 20, weight: .black))
                    .foregroundColor(theme.textPrimary)
                Text(message)
                    .font(.system(size: 15))
                    .foregroundColor(theme.textSecondary)
                    .fixedSize(horizontal: false, vertical: true)
                HStack(spacing: 10) {
                    TspButton(cancelText, theme: theme, action: onCancel)
                    TspButton(confirmText, variant: .primary, theme: theme, action: onConfirm)
                }
            }
            .padding(22)
            .frame(maxWidth: 420)
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 24).stroke(theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 24))
            .padding(.horizontal, 18)
        }
    }
}
