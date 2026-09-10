import SwiftUI

/// Closable / selectable tag (Chip remains for filter chips).
public struct TspTag: View {
    public var text: String
    public var closable: Bool
    public var selected: Bool
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onClose: () -> Void
    public var onTap: () -> Void

    public init(
        text: String = "",
        closable: Bool = false,
        selected: Bool = false,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onClose: @escaping () -> Void = {},
        onTap: @escaping () -> Void = {}
    ) {
        self.text = text
        self.closable = closable
        self.selected = selected
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onClose = onClose
        self.onTap = onTap
    }

    private var bg: Color {
        if selected { return theme.selectedFill }
        switch variant {
        case "primary": return theme.brandSubtle
        case "success": return theme.successSubtle
        case "warning": return theme.activeFill
        case "danger": return Color(hex: 0xFFE8EB)
        default: return theme.surfaceRaised
        }
    }

    private var fg: Color {
        switch variant {
        case "primary": return theme.brandPrimary
        case "success": return theme.success
        case "warning": return Color(hex: 0xB8860B)
        case "danger": return theme.danger
        default: return theme.textPrimary
        }
    }

    private var border: Color {
        if selected { return theme.selectedBorder }
        switch variant {
        case "primary": return theme.brandPrimary
        case "success": return theme.success
        case "warning": return theme.warning
        case "danger": return theme.danger
        default: return theme.borderDefault
        }
    }

    public var body: some View {
        HStack(spacing: 2) {
            Text(text)
                .font(.system(size: 13, weight: .heavy))
                .foregroundColor(fg)
            if closable {
                Button(action: { if !disabled { onClose() } }) {
                    Text("×")
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(fg)
                        .padding(4)
                }
                .buttonStyle(.plain)
                .disabled(disabled)
                .accessibilityLabel("Remove")
            }
        }
        .padding(.leading, 12)
        .padding(.trailing, closable ? 6 : 12)
        .padding(.vertical, 6)
        .background(bg)
        .overlay(Capsule().stroke(border, lineWidth: 1.5))
        .clipShape(Capsule())
        .opacity(disabled ? 0.45 : 1)
        .onTapGesture { if !disabled { onTap() } }
    }
}
