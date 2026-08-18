import SwiftUI

/// Scroll container with pull-to-refresh and load-more — aligns with RN `TspRefreshLayout`.
public struct TspRefreshLayout<Content: View>: View {
    let refreshing: Bool
    let loadingMore: Bool
    let disabled: Bool
    let theme: StarPlanetTheme
    let onRefresh: () -> Void
    let onLoadMore: () -> Void
    let content: Content

    public init(
        refreshing: Bool = false,
        loadingMore: Bool = false,
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onRefresh: @escaping () -> Void = {},
        onLoadMore: @escaping () -> Void = {},
        @ViewBuilder content: () -> Content
    ) {
        self.refreshing = refreshing
        self.loadingMore = loadingMore
        self.disabled = disabled
        self.theme = theme
        self.onRefresh = onRefresh
        self.onLoadMore = onLoadMore
        self.content = content()
    }

    public var body: some View {
        ScrollView {
            LazyVStack(spacing: 0) {
                content
                Color.clear
                    .frame(height: 1)
                    .onAppear {
                        guard !disabled, !refreshing, !loadingMore else { return }
                        onLoadMore()
                    }
                if loadingMore {
                    HStack(spacing: 8) {
                        ProgressView()
                            .progressViewStyle(CircularProgressViewStyle(tint: theme.brandPrimary))
                        Text("加载更多…")
                            .font(.system(size: 13))
                            .foregroundColor(theme.textSecondary)
                    }
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 14)
                }
            }
        }
        .disabled(disabled)
        .refreshable {
            guard !disabled else { return }
            onRefresh()
            try? await Task.sleep(nanoseconds: 400_000_000)
        }
    }
}
