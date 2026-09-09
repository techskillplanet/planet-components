import SwiftUI

/// Themed YYYY-MM-DD date field.
public struct TspDatePicker: View {
    @Binding var value: String
    let min: String?
    let max: String?
    let placeholder: String
    let disabled: Bool
    let theme: StarPlanetTheme
    let onChange: ((String) -> Void)?

    public init(
        value: Binding<String>,
        min: String? = nil,
        max: String? = nil,
        placeholder: String = "",
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onChange: ((String) -> Void)? = nil
    ) {
        self._value = value
        self.min = min
        self.max = max
        self.placeholder = placeholder
        self.disabled = disabled
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        DatePicker(
            "",
            selection: dateBinding,
            in: dateRange,
            displayedComponents: .date
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
        .accessibilityLabel(placeholder.isEmpty ? "date" : placeholder)
    }

    private var dateBinding: Binding<Date> {
        Binding(
            get: { Self.parse(value) ?? Date() },
            set: { next in
                let formatted = Self.format(next)
                value = formatted
                onChange?(formatted)
            }
        )
    }

    private var dateRange: ClosedRange<Date> {
        let lower = min.flatMap(Self.parse) ?? Date.distantPast
        let upper = max.flatMap(Self.parse) ?? Date.distantFuture
        return lower...Swift.max(lower, upper)
    }

    private static let formatter: DateFormatter = {
        let f = DateFormatter()
        f.calendar = Calendar(identifier: .gregorian)
        f.locale = Locale(identifier: "en_US_POSIX")
        f.dateFormat = "yyyy-MM-dd"
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
