import SwiftUI
#if canImport(UIKit)
import UIKit
#endif

public struct TspTopBar: View {
    let title: String
    let showBack: Bool
    let theme: StarPlanetTheme
    let onBack: () -> Void

    public init(title: String, showBack: Bool = false, theme: StarPlanetTheme = .sky, onBack: @escaping () -> Void = {}) {
        self.title = title
        self.showBack = showBack
        self.theme = theme
        self.onBack = onBack
    }

    public var body: some View {
        HStack {
            Button(showBack ? "‹" : "") { onBack() }.font(.title.bold()).frame(width: 44).buttonStyle(.plain)
            Text(title).font(.headline.bold()).frame(maxWidth: .infinity)
            Color.clear.frame(width: 44)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 56)
        .padding(.top, topSafeAreaInset)
        .foregroundColor(theme.textPrimary)
        .background(
            theme.surfaceRaised
                .ignoresSafeArea(edges: .top)
        )
        .overlay(alignment: .bottom) {
            Rectangle().fill(theme.borderDefault).frame(height: 1)
        }
    }

    private var topSafeAreaInset: CGFloat {
#if canImport(UIKit)
        UIApplication.shared.connectedScenes
            .compactMap { ($0 as? UIWindowScene)?.keyWindow?.safeAreaInsets.top }
            .first ?? 0
#else
        0
#endif
    }
}
