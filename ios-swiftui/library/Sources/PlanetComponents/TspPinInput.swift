import SwiftUI

public struct TspPinInput: View {
    let value: String
    let cellCount: Int
    let secure: Bool
    let theme: StarPlanetTheme
    public init(value: String, cellCount: Int = 4, secure: Bool = false, theme: StarPlanetTheme = .sky) {
        self.value = value
        self.cellCount = min(max(cellCount, 4), 6)
        self.secure = secure
        self.theme = theme
    }
    public var body: some View {
        HStack(spacing: 10) {
            ForEach(0..<cellCount, id: \.self) { index in
                Text(value.count > index ? (secure ? "•" : String(Array(value)[index])) : "")
                    .font(.headline.bold())
                    .foregroundColor(theme.textPrimary)
                    .frame(maxWidth: .infinity, minHeight: 48)
                    .background(theme.surfaceRaised)
                    .overlay(RoundedRectangle(cornerRadius: 14).stroke(theme.borderDefault, lineWidth: 1))
            }
        }
    }
}
