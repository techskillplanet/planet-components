import SwiftUI

/// Interactive star rating.
public struct TspStarRating: View {
    public var value: Int
    public var max: Int
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onChange: (Int) -> Void

    public init(
        value: Int = 0,
        max: Int = 5,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (Int) -> Void = { _ in }
    ) {
        self.value = value
        self.max = max
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        let count = Swift.min(10, Swift.max(1, max))
        let readonly = variant == "readonly" || disabled
        HStack(spacing: 4) {
            ForEach(1...count, id: \.self) { i in
                Button {
                    guard !readonly else { return }
                    onChange(i)
                } label: {
                    Text("★")
                        .font(.system(size: 22))
                        .foregroundColor(i <= value ? theme.brandPrimary : theme.borderDefault)
                        .padding(2)
                }
                .buttonStyle(.plain)
                .disabled(readonly)
                .accessibilityLabel("\(i)")
            }
        }
        .opacity(disabled ? 0.45 : 1)
        .accessibilityValue(Text("\(value)"))
    }
}
