import SwiftUI

/// Simple overlay / popover tooltip.
public struct TspTooltip<Content: View>: View {
    public var text: String
    public var placement: String
    public var visible: Bool?
    public var theme: StarPlanetTheme
    public var content: Content

    @State private var open = false

    public init(
        text: String = "",
        placement: String = "top",
        visible: Bool? = nil,
        theme: StarPlanetTheme = .sky,
        @ViewBuilder content: () -> Content
    ) {
        self.text = text
        self.placement = placement
        self.visible = visible
        self.theme = theme
        self.content = content()
    }

    private var shown: Bool { visible ?? open }

    public var body: some View {
        let bubble = Group {
            if shown && !text.isEmpty {
                Text(text)
                    .font(.system(size: 12, weight: .bold))
                    .foregroundColor(.white)
                    .padding(.horizontal, 10)
                    .padding(.vertical, 6)
                    .background(theme.textPrimary)
                    .clipShape(RoundedRectangle(cornerRadius: 10, style: .continuous))
                    .shadow(color: theme.brandPrimary.opacity(0.18), radius: 8, y: 4)
                    .accessibilityAddTraits(.isStaticText)
            }
        }

        Group {
            switch placement {
            case "bottom":
                VStack(spacing: 4) { content; bubble }
            case "left":
                HStack(spacing: 4) { bubble; content }
            case "right":
                HStack(spacing: 4) { content; bubble }
            default:
                VStack(spacing: 4) { bubble; content }
            }
        }
        .onTapGesture {
            if visible == nil {
                open.toggle()
            }
        }
    }
}
