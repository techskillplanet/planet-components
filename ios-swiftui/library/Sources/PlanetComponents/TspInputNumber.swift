import SwiftUI

/// Numeric stepper with − / + controls.
public struct TspInputNumber: View {
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
        min: Double = -.infinity,
        max: Double = .infinity,
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
    private var current: Double { value.clamped(to: min...max) }
    private var stepValue: Double { step == 0 ? 1 : step }
    private var canDec: Bool { !isDisabled && current > min }
    private var canInc: Bool { !isDisabled && current < max }

    public var body: some View {
        HStack(spacing: 8) {
            stepButton(label: "−", enabled: canDec) {
                apply(current - stepValue)
            }
            Text(displayValue)
                .font(.system(size: 16, weight: .heavy))
                .foregroundColor(theme.textPrimary)
                .frame(minWidth: 48)
                .multilineTextAlignment(.center)
                .accessibilityLabel("Value")
                .accessibilityValue(Text(displayValue))
            stepButton(label: "+", enabled: canInc) {
                apply(current + stepValue)
            }
        }
        .padding(.horizontal, 6)
        .padding(.vertical, 4)
        .background(theme.surfaceRaised)
        .overlay(
            RoundedRectangle(cornerRadius: 16, style: .continuous)
                .stroke(theme.borderDefault, lineWidth: 1)
        )
        .clipShape(RoundedRectangle(cornerRadius: 16, style: .continuous))
        .opacity(isDisabled ? 0.45 : 1)
    }

    private var displayValue: String {
        if current.rounded() == current {
            return String(Int(current))
        }
        return String(format: "%g", current)
    }

    private func apply(_ next: Double) {
        let clamped = next.clamped(to: min...max)
        value = clamped
        onChange(clamped)
    }

    @ViewBuilder
    private func stepButton(label: String, enabled: Bool, action: @escaping () -> Void) -> some View {
        Button(action: action) {
            Text(label)
                .font(.system(size: 20, weight: .heavy))
                .foregroundColor(enabled ? theme.brandPrimary : theme.textTertiary)
                .frame(width: 40, height: 40)
                .background(enabled ? theme.brandSubtle : theme.surfaceSubtle)
                .overlay(
                    RoundedRectangle(cornerRadius: 12, style: .continuous)
                        .stroke(theme.borderDefault, lineWidth: 1)
                )
                .clipShape(RoundedRectangle(cornerRadius: 12, style: .continuous))
        }
        .buttonStyle(.plain)
        .disabled(!enabled)
        .accessibilityLabel(label == "−" ? "Decrease" : "Increase")
    }
}

private extension Double {
    func clamped(to range: ClosedRange<Double>) -> Double {
        if range.lowerBound.isInfinite && range.upperBound.isInfinite { return self }
        if range.lowerBound.isInfinite { return Swift.min(self, range.upperBound) }
        if range.upperBound.isInfinite { return Swift.max(self, range.lowerBound) }
        return Swift.min(Swift.max(self, range.lowerBound), range.upperBound)
    }
}
