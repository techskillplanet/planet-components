import SwiftUI

/// Continuous value slider driven by Sky Planet tokens.
public struct TspSlider: View {
    @Binding public var value: Double
    public var min: Double
    public var max: Double
    public var step: Double
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onChange: (Double) -> Void

    public init(
        value: Binding<Double>,
        min: Double = 0,
        max: Double = 100,
        step: Double = 1,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (Double) -> Void = { _ in }
    ) {
        self._value = value
        self.min = min
        self.max = max
        self.step = step
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onChange = onChange
    }

    private var isDisabled: Bool { disabled || variant == "disabled" }
    private var lo: Double { min }
    private var hi: Double { max <= min ? min + 1 : max }

    public var body: some View {
        Slider(
            value: Binding(
                get: { value.clamped(to: lo...hi) },
                set: { newValue in
                    let snapped: Double
                    if step > 0 {
                        let steps = ((newValue - lo) / step).rounded()
                        snapped = (lo + steps * step).clamped(to: lo...hi)
                    } else {
                        snapped = newValue.clamped(to: lo...hi)
                    }
                    value = snapped
                    onChange(snapped)
                }
            ),
            in: lo...hi
        )
        .tint(theme.brandPrimary)
        .disabled(isDisabled)
        .opacity(isDisabled ? 0.45 : 1)
        .accessibilityValue(Text("\(Int(value))"))
    }
}

private extension Double {
    func clamped(to range: ClosedRange<Double>) -> Double {
        Swift.min(Swift.max(self, range.lowerBound), range.upperBound)
    }
}
