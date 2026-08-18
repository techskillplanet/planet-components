import SwiftUI

public struct TspStepper: View {
    let stepCount: Int
    let currentStep: Int
    let theme: StarPlanetTheme

    public init(stepCount: Int, currentStep: Int, theme: StarPlanetTheme = .sky) {
        self.stepCount = min(max(stepCount, 3), 5)
        self.currentStep = currentStep
        self.theme = theme
    }

    public var body: some View {
        HStack(spacing: 0) {
            ForEach(1...stepCount, id: \.self) { step in
                Text(step < currentStep ? "✓" : "\(step)")
                    .font(.system(size: 13, weight: .bold))
                    .foregroundColor(step <= currentStep ? .white : theme.textTertiary)
                    .frame(width: 32, height: 32)
                    .background(step < currentStep ? theme.success : step == currentStep ? theme.brandPrimary : theme.surfaceRaised)
                    .clipShape(Circle())
                if step < stepCount {
                    Rectangle().fill(step < currentStep ? theme.success : theme.borderDefault).frame(height: 2)
                }
            }
        }
    }
}
