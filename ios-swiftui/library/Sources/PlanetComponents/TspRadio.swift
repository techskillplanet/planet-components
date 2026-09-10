import SwiftUI

/// Single radio option (group exclusivity is app-owned).
public struct TspRadio: View {
    public var text: String
    public var checked: Bool
    public var disabled: Bool
    public var theme: StarPlanetTheme
    public var onChange: (Bool) -> Void

    public init(
        text: String = "",
        checked: Bool = false,
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (Bool) -> Void = { _ in }
    ) {
        self.text = text
        self.checked = checked
        self.disabled = disabled
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        Button {
            guard !disabled else { return }
            onChange(true)
        } label: {
            HStack(spacing: 10) {
                ZStack {
                    Circle()
                        .fill(theme.surfaceRaised)
                        .overlay(Circle().stroke(checked ? theme.brandPrimary : theme.borderDefault, lineWidth: 2))
                        .frame(width: 22, height: 22)
                    if checked {
                        Circle()
                            .fill(theme.brandPrimary)
                            .frame(width: 10, height: 10)
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
        .accessibilityLabel(text.isEmpty ? "Radio" : text)
        .accessibilityValue(Text(checked ? "Selected" : "Not selected"))
    }
}
