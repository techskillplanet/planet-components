import SwiftUI

/// Checkbox with brand checkmark box + label.
public struct TspCheckbox: View {
    public var text: String
    public var checked: Bool
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onChange: (Bool) -> Void

    public init(
        text: String = "",
        checked: Bool = false,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (Bool) -> Void = { _ in }
    ) {
        self.text = text
        self.checked = checked
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        Button {
            guard !disabled else { return }
            onChange(!checked)
        } label: {
            HStack(spacing: 10) {
                ZStack {
                    RoundedRectangle(cornerRadius: 6, style: .continuous)
                        .fill(boxFill)
                        .overlay(
                            RoundedRectangle(cornerRadius: 6, style: .continuous)
                                .stroke(checked ? theme.brandPrimary : theme.borderDefault, lineWidth: 2)
                        )
                        .frame(width: 22, height: 22)
                    if checked {
                        Text("✓")
                            .font(.system(size: 14, weight: .heavy))
                            .foregroundColor(variant == "subtle" ? theme.brandPrimary : .white)
                    }
                }
                if !text.isEmpty {
                    Text(text)
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(theme.textPrimary)
                        .lineLimit(1)
                }
            }
            .padding(.vertical, 6)
        }
        .buttonStyle(.plain)
        .disabled(disabled)
        .opacity(disabled ? 0.45 : 1)
        .accessibilityLabel(text.isEmpty ? "Checkbox" : text)
        .accessibilityAddTraits(.isButton)
        .accessibilityValue(Text(checked ? "Checked" : "Unchecked"))
    }

    private var boxFill: Color {
        guard checked else { return theme.surfaceRaised }
        return variant == "subtle" ? theme.brandSubtle : theme.brandPrimary
    }
}
