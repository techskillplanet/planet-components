import SwiftUI

public struct TspTabs: View {
    let tabs: [String]
    @Binding var selectedIndex: Int
    let theme: StarPlanetTheme

    public init(tabs: [String], selectedIndex: Binding<Int>, theme: StarPlanetTheme = .sky) {
        self.tabs = tabs
        self._selectedIndex = selectedIndex
        self.theme = theme
    }

    public var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 6) {
                ForEach(tabs.indices, id: \.self) { index in
                    Text(tabs[index])
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(index == selectedIndex ? .white : theme.textSecondary)
                        .padding(.horizontal, 16)
                        .frame(height: 34)
                        .background(index == selectedIndex ? theme.brandPrimary : .clear)
                        .clipShape(Capsule())
                        .onTapGesture { selectedIndex = index }
                }
            }
            .padding(4)
        }
        .background(theme.pageEnd)
        .overlay(Capsule().stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(Capsule())
    }
}
