import SwiftUI

public struct TspBottomTab: View {
    let tabs: [TspTabItem]
    @Binding var selectedKey: String
    let theme: StarPlanetTheme

    public init(tabs: [TspTabItem], selectedKey: Binding<String>, theme: StarPlanetTheme = .sky) {
        self.tabs = tabs
        self._selectedKey = selectedKey
        self.theme = theme
    }

    public var body: some View {
        HStack {
            ForEach(tabs) { tab in
                Button {
                    selectedKey = tab.id
                } label: {
                    VStack(spacing: 2) {
                        Text(tab.icon)
                        Text(tab.title).font(.system(size: 11, weight: .bold))
                    }
                    .foregroundColor(tab.id == selectedKey ? theme.brandPrimary : theme.textTertiary)
                    .frame(maxWidth: .infinity)
                }
                .buttonStyle(.plain)
            }
        }
        .frame(minHeight: 52)
        .padding(.top, 8)
        .padding(.bottom, 8)
        .padding(.horizontal, 12)
        .background(theme.surfaceRaised)
        .overlay(alignment: .top) {
            Rectangle().fill(theme.borderDefault).frame(height: 1)
        }
    }
}
