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
        HStack(spacing: 0) {
            ForEach(tabs) { tab in
                Button {
                    selectedKey = tab.id
                } label: {
                    VStack(spacing: 2) {
                        Text(tab.icon)
                            .font(.system(size: 20))
                            .frame(height: 22)
                        Text(tab.title)
                            .font(.system(size: 11, weight: .bold))
                            .lineLimit(1)
                    }
                    .foregroundColor(tab.id == selectedKey ? theme.brandPrimary : theme.textTertiary)
                    .frame(maxWidth: .infinity)
                    .contentShape(Rectangle())
                }
                .buttonStyle(.plain)
                .frame(maxWidth: .infinity)
            }
        }
        .frame(maxWidth: .infinity)
        .frame(height: 52)
        .padding(.top, 8)
        // Keep the icon row above the home indicator; paint chrome into the safe area.
        .padding(.bottom, SafeAreaHelper.bottomInset)
        .background(
            theme.surfaceRaised
                .ignoresSafeArea(edges: .bottom)
        )
        .overlay(alignment: .top) {
            Rectangle().fill(theme.borderDefault).frame(height: 1)
        }
    }
}
