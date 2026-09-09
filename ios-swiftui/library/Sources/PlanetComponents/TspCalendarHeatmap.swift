import SwiftUI

public struct TspCalendarCell: Hashable {
    public var date: String
    public var level: String

    public init(date: String, level: String = "none") {
        self.date = date
        self.level = level
    }
}

/// Month attendance heatmap.
public struct TspCalendarHeatmap: View {
    private static let weekdays = ["一", "二", "三", "四", "五", "六", "日"]
    private static let levels: [(level: String, label: String)] = [
        ("full", "全勤"),
        ("partial", "部分"),
        ("none", "未打"),
        ("exempt", "豁免"),
    ]

    let yearMonth: String
    let cells: [TspCalendarCell]
    let showLegend: Bool
    let theme: StarPlanetTheme
    let onSelectDay: ((String, String) -> Void)?

    public init(
        yearMonth: String = "",
        cells: [TspCalendarCell] = [],
        showLegend: Bool = true,
        theme: StarPlanetTheme = .sky,
        onSelectDay: ((String, String) -> Void)? = nil
    ) {
        self.yearMonth = yearMonth
        self.cells = cells
        self.showLegend = showLegend
        self.theme = theme
        self.onSelectDay = onSelectDay
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            if showLegend {
                HStack(spacing: 6) {
                    ForEach(Self.levels, id: \.level) { item in
                        Text(item.label)
                            .font(.system(size: 12, weight: .heavy))
                            .foregroundColor(colors(for: item.level).fg)
                            .padding(.horizontal, 10)
                            .frame(minHeight: 28)
                            .background(colors(for: item.level).bg)
                            .overlay(Capsule().stroke(colors(for: item.level).border, lineWidth: 1))
                            .clipShape(Capsule())
                    }
                }
            }
            HStack(spacing: 4) {
                ForEach(Self.weekdays, id: \.self) { weekday in
                    Text(weekday)
                        .font(.system(size: 11, weight: .heavy))
                        .foregroundColor(theme.textTertiary)
                        .frame(maxWidth: .infinity)
                }
            }
            LazyVGrid(columns: Array(repeating: GridItem(.flexible(), spacing: 4), count: 7), spacing: 4) {
                ForEach(Array(slots.enumerated()), id: \.offset) { _, slot in
                    if let slot {
                        Button {
                            onSelectDay?(slot.date, slot.level)
                        } label: {
                            Text("\(slot.day)")
                                .font(.system(size: 13, weight: .heavy))
                                .foregroundColor(colors(for: slot.level).fg)
                                .frame(maxWidth: .infinity)
                                .aspectRatio(1, contentMode: .fit)
                                .background(colors(for: slot.level).bg)
                                .overlay(RoundedRectangle(cornerRadius: 8).stroke(colors(for: slot.level).border, lineWidth: 1))
                                .clipShape(RoundedRectangle(cornerRadius: 8))
                        }
                        .buttonStyle(.plain)
                    } else {
                        Color.clear
                            .frame(maxWidth: .infinity)
                            .aspectRatio(1, contentMode: .fit)
                    }
                }
            }
        }
    }

    private struct DaySlot {
        let day: Int
        let date: String
        let level: String
    }

    private var slots: [DaySlot?] {
        let parsed = Self.parseYearMonth(yearMonth)
        let levelMap = Dictionary(uniqueKeysWithValues: cells.map { ($0.date, $0.level) })
        var calendar = Calendar(identifier: .gregorian)
        calendar.firstWeekday = 2
        guard let first = calendar.date(from: DateComponents(year: parsed.year, month: parsed.month, day: 1)),
              let range = calendar.range(of: .day, in: .month, for: first) else {
            return []
        }
        let weekday = calendar.component(.weekday, from: first)
        let startPad = (weekday + 5) % 7
        var result: [DaySlot?] = Array(repeating: nil, count: startPad)
        for day in range {
            let date = String(format: "%04d-%02d-%02d", parsed.year, parsed.month, day)
            result.append(DaySlot(day: day, date: date, level: levelMap[date] ?? "none"))
        }
        return result
    }

    private func colors(for level: String) -> (bg: Color, border: Color, fg: Color) {
        switch level {
        case "full":
            return (theme.success, theme.success, .white)
        case "partial":
            return (theme.warning, theme.warning, theme.textPrimary)
        case "exempt":
            return (theme.brandPrimary, theme.brandPrimary, .white)
        default:
            return (theme.pageEnd, theme.borderDefault, theme.textTertiary)
        }
    }

    private static func parseYearMonth(_ yearMonth: String) -> (year: Int, month: Int) {
        let parts = yearMonth.split(separator: "-").compactMap { Int($0) }
        if parts.count >= 2 {
            return (parts[0], parts[1])
        }
        let now = Date()
        let cal = Calendar(identifier: .gregorian)
        return (cal.component(.year, from: now), cal.component(.month, from: now))
    }
}
