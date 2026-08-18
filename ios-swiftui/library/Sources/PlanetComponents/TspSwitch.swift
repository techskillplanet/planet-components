import SwiftUI

/// Flat TspSwitch — custom track/thumb (no system Toggle). Matches RN `TspSwitch` + `05-switch-contract.md`.
public struct TspSwitch: View {
    public enum Variant {
        case md
        case sm
    }

    private struct SizeSpec {
        let width: CGFloat
        let height: CGFloat
        let handle: CGFloat
        let inset: CGFloat
        var travel: CGFloat { width - handle - inset * 2 }
    }

    private static let sizes: [Variant: SizeSpec] = [
        .md: SizeSpec(width: 52, height: 28, handle: 24, inset: 2),
        .sm: SizeSpec(width: 40, height: 22, handle: 18, inset: 2),
    ]

    let text: String
    let checked: Bool
    /// API compatibility with other stacks; not rendered in flat UI.
    let checkedText: String
    /// API compatibility with other stacks; not rendered in flat UI.
    let uncheckedText: String
    let loading: Bool
    let disabled: Bool
    let variant: Variant
    let theme: StarPlanetTheme
    let onChange: (Bool) -> Void

    public init(
        text: String = "",
        checked: Bool = false,
        checkedText: String = "ON",
        uncheckedText: String = "OFF",
        loading: Bool = false,
        disabled: Bool = false,
        variant: Variant = .md,
        theme: StarPlanetTheme = .sky,
        onChange: @escaping (Bool) -> Void = { _ in }
    ) {
        self.text = text
        self.checked = checked
        self.checkedText = checkedText
        self.uncheckedText = uncheckedText
        self.loading = loading
        self.disabled = disabled
        self.variant = variant
        self.theme = theme
        self.onChange = onChange
    }

    private var size: SizeSpec { Self.sizes[variant]! }
    private var blocked: Bool { disabled || loading }
    private var controlOpacity: Double { disabled ? 0.5 : (loading ? 0.7 : 1) }
    private var trackColor: Color { checked ? theme.switchOnBackground : theme.switchOffBackground }
    private var spinnerColor: Color { checked ? .white : theme.brandDark }

    public var body: some View {
        Button {
            guard !blocked else { return }
            onChange(!checked)
        } label: {
            HStack(alignment: .center, spacing: 12) {
                if !text.isEmpty {
                    Text(text)
                        .font(.system(size: 14, weight: .heavy))
                        .foregroundColor(theme.textPrimary)
                        .lineLimit(1)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                track
                    .opacity(controlOpacity)
            }
        }
        .buttonStyle(.plain)
        .disabled(blocked)
        .accessibilityLabel(text.isEmpty ? "Switch" : text)
        .accessibilityAddTraits(.isButton)
        .accessibilityValue(Text(checked ? "On" : "Off"))
    }

    private var track: some View {
        ZStack(alignment: .leading) {
            Capsule()
                .fill(trackColor)
                .frame(width: size.width, height: size.height)
            Circle()
                .fill(theme.switchHandleBackground)
                .frame(width: size.handle, height: size.handle)
                .overlay {
                    if loading {
                        Circle()
                            .trim(from: 0.15, to: 0.85)
                            .stroke(spinnerColor, style: StrokeStyle(lineWidth: 2, lineCap: .round))
                            .frame(
                                width: max(8, size.handle * 0.55),
                                height: max(8, size.handle * 0.55)
                            )
                            .modifier(Spinning(duration: 0.7))
                    }
                }
                .offset(x: size.inset + (checked ? size.travel : 0))
                .animation(.easeInOut(duration: 0.18), value: checked)
        }
        .frame(width: size.width, height: size.height)
    }
}

private struct Spinning: ViewModifier {
    let duration: Double
    @State private var rotating = false

    func body(content: Content) -> some View {
        content
            .rotationEffect(.degrees(rotating ? 360 : 0))
            .animation(.linear(duration: duration).repeatForever(autoreverses: false), value: rotating)
            .onAppear { rotating = true }
    }
}
