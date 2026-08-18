import SwiftUI

public struct TspAmount: View {
    let symbol: String
    let value: String
    let cycle: String
    let symbolAfter: Bool
    let strikeThrough: Bool
    let theme: StarPlanetTheme

    public init(symbol: String = "¥", value: String, cycle: String = "", symbolAfter: Bool = false, strikeThrough: Bool = false, theme: StarPlanetTheme = .sky) {
        self.symbol = symbol
        self.value = value
        self.cycle = cycle
        self.symbolAfter = symbolAfter
        self.strikeThrough = strikeThrough
        self.theme = theme
    }

    public var body: some View {
        HStack(alignment: .lastTextBaseline, spacing: 4) {
            if !symbolAfter { Text(symbol).font(.headline) }
            Text(value).font(.system(size: 30, weight: .heavy))
            if symbolAfter { Text(symbol).font(.headline) }
            if !cycle.isEmpty { Text("/\(cycle)").font(.caption).foregroundColor(theme.textSecondary) }
        }
        .foregroundColor(theme.textPrimary)
        .strikethrough(strikeThrough)
    }
}
