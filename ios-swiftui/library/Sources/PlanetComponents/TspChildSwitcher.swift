import SwiftUI

public struct TspChildSwitcherItem: Identifiable, Hashable {
    public var id: String
    public var label: String
    public var iconSrc: String?
    public var emoji: String?

    public init(id: String, label: String, iconSrc: String? = nil, emoji: String? = nil) {
        self.id = id
        self.label = label
        self.iconSrc = iconSrc
        self.emoji = emoji
    }
}

/// Single-select child chips/tabs.
public struct TspChildSwitcher: View {
    public enum Variant { case chip, tabs }

    let items: [TspChildSwitcherItem]
    let selectedId: String?
    let variant: Variant
    let disabled: Bool
    let theme: StarPlanetTheme
    let onChange: ((String) -> Void)?

    public init(
        items: [TspChildSwitcherItem],
        selectedId: String? = nil,
        variant: Variant = .chip,
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onChange: ((String) -> Void)? = nil
    ) {
        self.items = items
        self.selectedId = selectedId
        self.variant = variant
        self.disabled = disabled
        self.theme = theme
        self.onChange = onChange
    }

    public var body: some View {
        Group {
            if variant == .tabs {
                HStack(spacing: 8) {
                    ForEach(items) { item in
                        chip(item, flex: true)
                    }
                }
            } else {
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 8) {
                        ForEach(items) { item in
                            chip(item, flex: false)
                        }
                    }
                    .padding(.vertical, 4)
                }
            }
        }
        .opacity(disabled ? 0.45 : 1)
    }

    @ViewBuilder
    private func chip(_ item: TspChildSwitcherItem, flex: Bool) -> some View {
        let selected = item.id == selectedId
        Button {
            guard !disabled else { return }
            onChange?(item.id)
        } label: {
            HStack(spacing: 8) {
                if let iconSrc = item.iconSrc, let url = URL(string: iconSrc) {
                    AsyncImage(url: url) { image in
                        image.resizable().scaledToFill()
                    } placeholder: {
                        Color.white
                    }
                    .frame(width: 22, height: 22)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                } else if let emoji = item.emoji {
                    Text(emoji).font(.system(size: 16))
                }
                Text(item.label)
                    .font(.system(size: 14, weight: .heavy))
                    .lineLimit(1)
            }
            .foregroundColor(selected ? .white : theme.textSecondary)
            .padding(.horizontal, 14)
            .frame(maxWidth: flex ? .infinity : nil, minHeight: 44)
            .background(selected ? theme.brandPrimary : theme.surfaceRaised)
            .overlay(
                RoundedRectangle(cornerRadius: variant == .tabs ? 14 : 999)
                    .stroke(selected ? theme.brandPrimary : theme.borderDefault, lineWidth: 1)
            )
            .clipShape(RoundedRectangle(cornerRadius: variant == .tabs ? 14 : 999))
        }
        .buttonStyle(.plain)
        .disabled(disabled)
    }
}
