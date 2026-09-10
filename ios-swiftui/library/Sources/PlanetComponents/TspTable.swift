import SwiftUI

public struct TspTableColumn: Identifiable, Equatable {
    public var id: String
    public var title: String
    public var key: String

    public init(id: String? = nil, title: String, key: String? = nil) {
        self.title = title
        let resolvedKey = key ?? title
        self.key = resolvedKey
        self.id = id ?? resolvedKey
    }
}

/// Lightweight data table with columns / rows.
public struct TspTable: View {
    public var columns: [TspTableColumn]
    public var rows: [[String: String]]
    public var variant: String
    public var emptyText: String
    public var theme: StarPlanetTheme

    public init(
        columns: [TspTableColumn] = [],
        rows: [[String: String]] = [],
        variant: String = "default",
        emptyText: String = "暂无数据",
        theme: StarPlanetTheme = .sky
    ) {
        self.columns = columns
        self.rows = rows
        self.variant = variant
        self.emptyText = emptyText
        self.theme = theme
    }

    /// Convenience for string headers + array rows (positional cells).
    public init(
        columnTitles: [String],
        rowValues: [[String]],
        variant: String = "default",
        emptyText: String = "暂无数据",
        theme: StarPlanetTheme = .sky
    ) {
        self.columns = columnTitles.enumerated().map { TspTableColumn(id: "\($0.offset)", title: $0.element, key: "\($0.offset)") }
        self.rows = rowValues.map { cells in
            Dictionary(uniqueKeysWithValues: cells.enumerated().map { ("\($0.offset)", $0.element) })
        }
        self.variant = variant
        self.emptyText = emptyText
        self.theme = theme
    }

    private var striped: Bool { variant == "striped" }

    public var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            VStack(spacing: 0) {
                headerRow
                if rows.isEmpty {
                    cellRow(texts: [emptyText], striped: false)
                } else {
                    ForEach(Array(rows.enumerated()), id: \.offset) { index, row in
                        let texts: [String] = columns.isEmpty
                            ? [row.values.joined(separator: " ")]
                            : columns.map { row[$0.key] ?? "" }
                        cellRow(texts: texts, striped: striped && index % 2 == 1)
                    }
                }
            }
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 1.5))
            .clipShape(RoundedRectangle(cornerRadius: 16))
        }
    }

    private var headerRow: some View {
        let titles = columns.isEmpty ? [""] : columns.map(\.title)
        return HStack(spacing: 0) {
            ForEach(Array(titles.enumerated()), id: \.offset) { _, title in
                Text(title)
                    .font(.system(size: 13, weight: .black))
                    .foregroundColor(theme.textSecondary)
                    .frame(width: 120, alignment: .leading)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 10)
            }
        }
        .background(theme.surfaceSubtle)
    }

    private func cellRow(texts: [String], striped: Bool) -> some View {
        HStack(spacing: 0) {
            ForEach(Array(texts.enumerated()), id: \.offset) { _, text in
                Text(text)
                    .font(.system(size: 13, weight: .semibold))
                    .foregroundColor(theme.textPrimary)
                    .frame(width: 120, alignment: .leading)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 10)
            }
        }
        .background(striped ? theme.brandSubtle.opacity(0.45) : theme.surfaceRaised)
        .overlay(alignment: .bottom) {
            Rectangle().fill(theme.borderDefault.opacity(0.7)).frame(height: 1)
        }
    }
}
