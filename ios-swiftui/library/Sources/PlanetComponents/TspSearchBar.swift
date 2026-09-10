import SwiftUI

/// Pill search field.
public struct TspSearchBar: View {
    @Binding public var value: String
    public var placeholder: String
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onChange: (String) -> Void

    public init(
        value: Binding<String>,
        placeholder: String = "Search…",
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (String) -> Void = { _ in }
    ) {
        self._value = value
        self.placeholder = placeholder
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        HStack(spacing: 8) {
            Text("⌕")
                .font(.system(size: 16, weight: .bold))
                .foregroundColor(theme.textTertiary)
            TextField(placeholder, text: $value)
                .font(.system(size: 15, weight: .semibold))
                .foregroundColor(theme.textPrimary)
                .disabled(disabled)
                .onChange(of: value) { newValue in
                    onChange(newValue)
                }
        }
        .padding(.horizontal, 14)
        .frame(minHeight: 44)
        .background(theme.surfaceRaised)
        .overlay(
            Capsule().stroke(variant == "error" ? theme.danger : theme.borderDefault, lineWidth: 1)
        )
        .clipShape(Capsule())
        .opacity(disabled ? 0.45 : 1)
    }
}
