import SwiftUI

/// A4-style dictation / fill-in print layout.
public struct TspPrintSheet: View {
    public enum Variant { case pinyin, meaning }

    let title: String
    let items: [String]
    let columns: Int
    let footerFields: [String]
    let variant: Variant
    let theme: StarPlanetTheme

    public init(
        title: String = "",
        items: [String] = [],
        columns: Int = 5,
        footerFields: [String] = ["姓名", "日期", "得分"],
        variant: Variant = .pinyin,
        theme: StarPlanetTheme = .sky
    ) {
        self.title = title
        self.items = items
        self.columns = max(1, columns)
        self.footerFields = footerFields
        self.variant = variant
        self.theme = theme
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            if !title.isEmpty {
                Text(title)
                    .font(.system(size: 16, weight: .black))
                    .foregroundColor(theme.textPrimary)
                    .frame(maxWidth: .infinity)
            }
            LazyVGrid(
                columns: Array(repeating: GridItem(.flexible(), spacing: 6), count: columns),
                spacing: 6
            ) {
                ForEach(Array(items.enumerated()), id: \.offset) { _, prompt in
                    VStack(spacing: 6) {
                        Text(prompt)
                            .font(.system(size: 11))
                            .foregroundColor(theme.textSecondary)
                            .multilineTextAlignment(.center)
                            .frame(minHeight: 16)
                        Rectangle()
                            .fill(Color.clear)
                            .frame(height: 22)
                            .overlay(alignment: .top) {
                                Rectangle().fill(Color.gray.opacity(0.55)).frame(height: 1)
                            }
                    }
                    .padding(4)
                    .frame(minHeight: 52)
                    .overlay(Rectangle().stroke(Color(.sRGB, red: 0.2, green: 0.2, blue: 0.2, opacity: 1), lineWidth: 1))
                }
            }
            if !footerFields.isEmpty {
                HStack {
                    ForEach(footerFields, id: \.self) { field in
                        Text("\(field)：________")
                            .font(.system(size: 12, weight: .semibold))
                            .foregroundColor(theme.textPrimary)
                        if field != footerFields.last {
                            Spacer(minLength: 8)
                        }
                    }
                }
            }
        }
        .padding(12)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color.white)
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 16))
        .accessibilityLabel("print-sheet-\(String(describing: variant))")
    }
}
