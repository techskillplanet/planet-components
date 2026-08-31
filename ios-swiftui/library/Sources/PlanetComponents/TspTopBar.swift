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
            Group {
                if showBack {
                    Button(action: onBack) {
                        Text("‹")
                            .font(.title.bold())
                            .frame(width: 44, height: 44)
                            .contentShape(Rectangle())
                    }
                    .buttonStyle(.plain)
                } else {
                    Color.clear.frame(width: 44, height: 44)
                }
            }
            Text(title)
                .font(.headline.bold())
                .lineLimit(1)
                .frame(maxWidth: .infinity)
            Color.clear.frame(width: 44, height: 44)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 56)
        .padding(.top, SafeAreaHelper.topInset)
        .foregroundColor(theme.textPrimary)
        .background(
            theme.surfaceRaised
                .ignoresSafeArea(edges: .top)
        )
        .overlay(alignment: .bottom) {
            Rectangle().fill(theme.borderDefault).frame(height: 1)
        }
    }
}

enum SafeAreaHelper {
    static var topInset: CGFloat {
#if canImport(UIKit)
        if let inset = windowSafeArea?.top, inset > 0 { return inset }
        return 59
#else
        return 0
#endif
    }

    static var bottomInset: CGFloat {
#if canImport(UIKit)
        if let inset = windowSafeArea?.bottom, inset > 0 { return inset }
        return 34
#else
        return 0
#endif
    }

#if canImport(UIKit)
    private static var windowSafeArea: UIEdgeInsets? {
        let windows = UIApplication.shared.connectedScenes
            .compactMap { $0 as? UIWindowScene }
            .flatMap(\.windows)
        if let key = windows.first(where: \.isKeyWindow) {
            return key.safeAreaInsets
        }
        return windows.first(where: { $0.safeAreaInsets.top > 0 || $0.safeAreaInsets.bottom > 0 })?.safeAreaInsets
    }
#endif
}
