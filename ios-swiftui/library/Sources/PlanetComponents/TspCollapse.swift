import SwiftUI

/// Expandable collapse / accordion panel.
public struct TspCollapse: View {
    public var title: String
    public var message: String
    public var expanded: Bool
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onChange: (Bool) -> Void

    public init(
        title: String = "",
        message: String = "",
        expanded: Bool = false,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (Bool) -> Void = { _ in }
    ) {
        self.title = title
        self.message = message
        self.expanded = expanded
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Button {
                guard !disabled else { return }
                onChange(!expanded)
            } label: {
                HStack(spacing: 12) {
                    Text(title)
                        .font(.system(size: 15, weight: .heavy))
                        .foregroundColor(theme.textPrimary)
                        .multilineTextAlignment(.leading)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    Text(expanded ? "−" : "+")
                        .font(.system(size: 16, weight: .heavy))
                        .foregroundColor(theme.brandPrimary)
                        .frame(width: 28, height: 28)
                        .overlay(Circle().stroke(theme.borderDefault, lineWidth: 1))
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 14)
            }
            .buttonStyle(.plain)
            .disabled(disabled)

            if expanded {
                Text(message)
                    .font(.system(size: 14, weight: .semibold))
                    .foregroundColor(theme.textSecondary)
                    .padding(.horizontal, 16)
                    .padding(.bottom, 14)
            }
        }
        .background(variant == "subtle" ? theme.surfaceSubtle : theme.surfaceRaised)
        .overlay(
            RoundedRectangle(cornerRadius: 16, style: .continuous)
                .stroke(expanded ? theme.brandPrimary : theme.borderDefault, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
        .opacity(disabled ? 0.45 : 1)
        .accessibilityAddTraits(.isButton)
    }
}
