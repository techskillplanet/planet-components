import SwiftUI
import Combine

/// Simple carousel with page dots (manual paging; works on iOS + macOS).
public struct TspSwiper: View {
    public var items: [String]
    @Binding public var index: Int
    public var autoplay: Bool
    public var theme: StarPlanetTheme
    public var onChange: (Int) -> Void

    @Environment(\.accessibilityReduceMotion) private var reduceMotion

    public init(
        items: [String] = [],
        index: Binding<Int> = .constant(0),
        autoplay: Bool = false,
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (Int) -> Void = { _ in }
    ) {
        self.items = items
        self._index = index
        self.autoplay = autoplay
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        VStack(spacing: 10) {
            if items.isEmpty {
                emptySlide
            } else {
                ZStack {
                    ForEach(items.indices, id: \.self) { i in
                        Text(items[i])
                            .font(.system(size: 16, weight: .heavy))
                            .foregroundColor(theme.textPrimary)
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                            .background(theme.brandSubtle)
                            .overlay(
                                RoundedRectangle(cornerRadius: 18, style: .continuous)
                                    .stroke(theme.borderDefault, lineWidth: 1)
                            )
                            .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
                            .opacity(i == safeIndex ? 1 : 0)
                            .accessibilityHidden(i != safeIndex)
                    }
                }
                .frame(minHeight: 160)
                .gesture(
                    DragGesture(minimumDistance: 24)
                        .onEnded { value in
                            guard items.count > 1 else { return }
                            if value.translation.width < -40 {
                                go(safeIndex + 1)
                            } else if value.translation.width > 40 {
                                go(safeIndex - 1)
                            }
                        }
                )

                if items.count > 1 {
                    HStack(spacing: 6) {
                        ForEach(items.indices, id: \.self) { i in
                            Capsule()
                                .fill(i == safeIndex ? theme.brandPrimary : theme.borderDefault)
                                .frame(width: i == safeIndex ? 18 : 8, height: 8)
                                .onTapGesture { go(i) }
                                .accessibilityLabel("Slide \(i + 1)")
                        }
                    }
                }
            }
        }
        .accessibilityElement(children: .contain)
        .accessibilityLabel("Carousel")
        .onReceive(autoplayPublisher) { _ in
            guard items.count > 1 else { return }
            go(safeIndex + 1)
        }
    }

    private var emptySlide: some View {
        Text("—")
            .font(.system(size: 16, weight: .heavy))
            .foregroundColor(theme.textTertiary)
            .frame(maxWidth: .infinity, minHeight: 160)
            .background(theme.surfaceSubtle)
            .overlay(
                RoundedRectangle(cornerRadius: 18, style: .continuous)
                    .stroke(theme.borderDefault, lineWidth: 1)
            )
            .clipShape(RoundedRectangle(cornerRadius: 18, style: .continuous))
    }

    private var safeIndex: Int {
        guard !items.isEmpty else { return 0 }
        return min(max(0, index), items.count - 1)
    }

    private func go(_ next: Int) {
        guard !items.isEmpty else { return }
        let n = ((next % items.count) + items.count) % items.count
        index = n
        onChange(n)
    }

    private var autoplayPublisher: AnyPublisher<Date, Never> {
        guard autoplay, !reduceMotion, items.count > 1 else {
            return Empty().eraseToAnyPublisher()
        }
        return Timer.publish(every: 3.2, on: .main, in: .common)
            .autoconnect()
            .eraseToAnyPublisher()
    }
}
