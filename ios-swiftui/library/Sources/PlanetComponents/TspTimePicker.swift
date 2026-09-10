import SwiftUI

/// Themed HH:mm time field.
public struct TspTimePicker: View {
    @Binding public var value: String
    public var placeholder: String
    public var disabled: Bool
    public var theme: StarPlanetTheme
    public var onChange: ((String) -> Void)?

    public init(
        value: Binding<String>,
        placeholder: String = "HH:mm",
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onChange: ((String) -> Void)? = nil
    ) {
        self._value = value
        self.placeholder = placeholder
        self.disabled = disabled
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        DatePicker(
            "",
            selection: timeBinding,
            displayedComponents: .hourAndMinute
        )
        .labelsHidden()
        .datePickerStyle(.compact)
        .padding(.horizontal, 14)
        .frame(maxWidth: .infinity, minHeight: 44, alignment: .leading)
        .background(theme.surfaceRaised)
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 2))
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .opacity(disabled ? 0.45 : 1)
        .disabled(disabled)
        .accessibilityLabel(placeholder.isEmpty ? "time" : placeholder)
    }

    private var timeBinding: Binding<Date> {
        Binding(
            get: { Self.parse(value) ?? Date() },
            set: { next in
                let formatted = Self.format(next)
                value = formatted
                onChange?(formatted)
            }
        )
    }

    private static let formatter: DateFormatter = {
        let f = DateFormatter()
        f.calendar = Calendar(identifier: .gregorian)
        f.locale = Locale(identifier: "en_US_POSIX")
        f.dateFormat = "HH:mm"
        return f
    }()

    private static func parse(_ raw: String?) -> Date? {
        guard let raw, !raw.isEmpty else { return nil }
        return formatter.date(from: raw)
    }

    private static func format(_ date: Date) -> String {
        formatter.string(from: date)
    }
}
