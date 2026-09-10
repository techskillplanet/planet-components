import SwiftUI

/// Segmented control. Options may be strings or `(label, value)` pairs via [TspSegmentOption].
public struct TspSegmentOption: Hashable {
    public var label: String
    public var value: String

    public init(label: String, value: String? = nil) {
        self.label = label
        self.value = value ?? label
    }
}

public struct TspSegmentedControl: View {
    public var options: [TspSegmentOption]
    public var selectedIndex: Int
    public var disabled: Bool
    public var variant: String
    public var theme: StarPlanetTheme
    public var onSelect: (Int, String, String) -> Void

    public init(
        options: [String],
        selectedIndex: Int = 0,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onSelect: @escaping (Int, String, String) -> Void = { _, _, _ in }
    ) {
        self.options = options.map { TspSegmentOption(label: $0, value: $0) }
        self.selectedIndex = selectedIndex
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onSelect = onSelect
    }

    public init(
        options: [TspSegmentOption],
        selectedIndex: Int = 0,
        disabled: Bool = false,
        variant: String = "default",
        theme: StarPlanetTheme = .sky,
        onSelect: @escaping (Int, String, String) -> Void = { _, _, _ in }
    ) {
        self.options = options
        self.selectedIndex = selectedIndex
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onSelect = onSelect
    }

    public var body: some View {
        HStack(spacing: 4) {
            ForEach(Array(options.enumerated()), id: \.offset) { index, item in
                let selected = index == selectedIndex
                Button {
                    guard !disabled else { return }
                    onSelect(index, item.label, item.value)
                } label: {
                    Text(item.label)
                        .font(.system(size: 14, weight: .heavy))
                        .foregroundColor(selected ? .white : theme.textSecondary)
                        .lineLimit(1)
                        .frame(maxWidth: .infinity)
                        .padding(.horizontal, 10)
                        .frame(minHeight: 36)
                        .background(selected ? theme.brandPrimary : Color.clear)
                        .clipShape(Capsule())
                }
                .buttonStyle(.plain)
                .disabled(disabled)
            }
        }
        .padding(4)
        .background(variant == "raised" ? theme.surfaceRaised : theme.surfaceSubtle)
        .overlay(Capsule().stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(Capsule())
        .opacity(disabled ? 0.45 : 1)
    }
}
