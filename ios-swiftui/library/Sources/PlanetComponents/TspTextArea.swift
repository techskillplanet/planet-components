import SwiftUI

/// Multi-line text input.
public struct TspTextArea: View {
    @Binding public var value: String
    public var placeholder: String
    public var rows: Int
    public var maxLength: Int?
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onChange: (String) -> Void

    public init(
        value: Binding<String>,
        placeholder: String = "",
        rows: Int = 3,
        maxLength: Int? = nil,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (String) -> Void = { _ in }
    ) {
        self._value = value
        self.placeholder = placeholder
        self.rows = rows
        self.maxLength = maxLength
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onChange = onChange
    }

    private var isDisabled: Bool { disabled || variant == "disabled" }
    private var borderColor: Color {
        variant == "error" ? theme.danger : theme.borderDefault
    }
    private var minHeight: CGFloat { CGFloat(max(1, min(20, rows))) * 22 + 24 }

    public var body: some View {
        ZStack(alignment: .topLeading) {
            if value.isEmpty {
                Text(placeholder)
                    .font(.system(size: 15, weight: .semibold))
                    .foregroundColor(theme.textTertiary)
                    .padding(.horizontal, 14)
                    .padding(.vertical, 12)
                    .allowsHitTesting(false)
            }
            TextEditor(text: $value)
                .font(.system(size: 15, weight: .semibold))
                .foregroundColor(theme.textPrimary)
                .frame(minHeight: minHeight)
                .padding(.horizontal, 10)
                .padding(.vertical, 8)
                .disabled(isDisabled)
                .modifier(TextEditorClearBackground())
                .onChange(of: value) { newValue in
                    if let maxLength, newValue.count > maxLength {
                        value = String(newValue.prefix(maxLength))
                    }
                    onChange(value)
                }
        }
        .background(theme.surfaceRaised)
        .overlay(
            RoundedRectangle(cornerRadius: 16, style: .continuous)
                .stroke(borderColor, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
        .opacity(isDisabled ? 0.45 : 1)
    }
}

private struct TextEditorClearBackground: ViewModifier {
    func body(content: Content) -> some View {
        if #available(iOS 16.0, macOS 13.0, *) {
            content.scrollContentBackground(.hidden).background(Color.clear)
        } else {
            content.background(Color.clear)
        }
    }
}
