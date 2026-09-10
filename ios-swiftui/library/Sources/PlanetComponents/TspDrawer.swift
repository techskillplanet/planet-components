import SwiftUI

/// Side / bottom drawer overlay.
public struct TspDrawer<Content: View>: View {
    public var visible: Bool
    public var title: String
    public var placement: String
    public var theme: StarPlanetTheme
    public var onClose: () -> Void
    public var content: Content

    public init(
        visible: Bool = false,
        title: String = "",
        placement: String = "bottom",
        theme: StarPlanetTheme = .sky,
        onClose: @escaping () -> Void = {},
        @ViewBuilder content: () -> Content
    ) {
        self.visible = visible
        self.title = title
        self.placement = placement
        self.theme = theme
        self.onClose = onClose
        self.content = content()
    }

    private var isSide: Bool { placement == "left" || placement == "right" }

    public var body: some View {
        Group {
            if visible {
                GeometryReader { geo in
                    ZStack(alignment: alignment) {
                        Color(red: 0.090, green: 0.227, blue: 0.384).opacity(0.28)
                            .ignoresSafeArea()
                            .onTapGesture(perform: onClose)

                        panel
                            .frame(maxWidth: isSide ? 300 : .infinity, maxHeight: isSide ? .infinity : nil)
                            .frame(
                                maxHeight: isSide ? .infinity : geo.size.height * 0.72,
                                alignment: .top
                            )
                    }
                }
                .ignoresSafeArea(edges: isSide ? .vertical : [])
            }
        }
    }

    private var alignment: Alignment {
        switch placement {
        case "left": return .leading
        case "right": return .trailing
        default: return .bottom
        }
    }

    private var panel: some View {
        VStack(alignment: .leading, spacing: 0) {
            if !title.isEmpty {
                HStack {
                    Text(title)
                        .font(.system(size: 17, weight: .heavy))
                        .foregroundColor(theme.textPrimary)
                    Spacer()
                    Button(action: onClose) {
                        Text("×")
                            .font(.system(size: 22, weight: .bold))
                            .foregroundColor(theme.textSecondary)
                            .frame(width: 36, height: 36)
                    }
                    .buttonStyle(.plain)
                    .accessibilityLabel("Close")
                }
                .padding(.horizontal, 16)
                .padding(.top, 14)
                .padding(.bottom, 8)
            }
            content
                .padding(.horizontal, 16)
                .padding(.bottom, 20)
                .frame(maxWidth: .infinity, maxHeight: isSide ? .infinity : nil, alignment: .topLeading)
        }
        .frame(maxWidth: .infinity, maxHeight: isSide ? .infinity : nil, alignment: .top)
        .background(theme.surfaceRaised)
        .overlay(RoundedRectangle(cornerRadius: 24, style: .continuous).stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 24, style: .continuous))
        .accessibilityAddTraits(.isModal)
    }
}
