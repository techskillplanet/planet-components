import SwiftUI

/// Bottom option sheet — aligns with RN `TspOptionSheet`.
public struct TspOptionSheet: View {
    let title: String
    let options: [String]
    let selectedIndex: Int
    let theme: StarPlanetTheme
    let onSelect: (Int) -> Void
    let onCancel: () -> Void

    public init(
        title: String = "请选择",
        options: [String],
        selectedIndex: Int = 0,
        theme: StarPlanetTheme = .sky,
        onSelect: @escaping (Int) -> Void = { _ in },
        onCancel: @escaping () -> Void = {}
    ) {
        self.title = title
        self.options = options
        self.selectedIndex = selectedIndex
        self.theme = theme
        self.onSelect = onSelect
        self.onCancel = onCancel
    }

    public var body: some View {
        ZStack(alignment: .bottom) {
            Color(red: 0.090, green: 0.227, blue: 0.384).opacity(0.32)
                .ignoresSafeArea()
                .onTapGesture(perform: onCancel)
            VStack(spacing: 0) {
                HStack {
                    Button("取消", action: onCancel)
                        .font(.system(size: 15, weight: .heavy))
                        .foregroundColor(theme.brandPrimary)
                        .frame(width: 64, alignment: .leading)
                    Text(title)
                        .font(.system(size: 15, weight: .heavy))
                        .foregroundColor(theme.textPrimary)
                        .frame(maxWidth: .infinity)
                    Color.clear.frame(width: 64)
                }
                .frame(minHeight: 52)
                .padding(.horizontal, 16)

                ForEach(options.indices, id: \.self) { index in
                    Button {
                        onSelect(index)
                    } label: {
                        HStack(spacing: 0) {
                            Text(index == selectedIndex ? "✓" : "")
                                .font(.system(size: 15, weight: .heavy))
                                .foregroundColor(index == selectedIndex ? .white : theme.textPrimary)
                                .frame(width: 28)
                            Text(options[index])
                                .font(.system(size: 15, weight: .heavy))
                                .foregroundColor(index == selectedIndex ? .white : theme.textPrimary)
                            Spacer()
                        }
                        .padding(.horizontal, 12)
                        .frame(maxWidth: .infinity, minHeight: 48)
                        .background(index == selectedIndex ? theme.brandPrimary : Color.clear)
                        .clipShape(RoundedRectangle(cornerRadius: 14))
                    }
                    .buttonStyle(.plain)
                    .padding(.horizontal, 10)
                    .padding(.vertical, 2)
                }
            }
            .padding(.bottom, 16)
            .frame(maxWidth: .infinity)
            .background(theme.surfaceRaised)
            .overlay(
                RoundedRectangle(cornerRadius: 24)
                    .stroke(theme.borderDefault, lineWidth: 1)
            )
            .clipShape(RoundedRectangle(cornerRadius: 24, style: .continuous))
            .padding(.top, 8)
        }
    }
}
