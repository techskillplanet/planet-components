import SwiftUI

/// Circular avatar — initials or remote image URL.
public struct TspAvatar: View {
    public var text: String
    public var src: String?
    public var size: String
    public var variant: String
    public var theme: StarPlanetTheme

    public init(
        text: String = "",
        src: String? = nil,
        size: String = "md",
        variant: String = "default",
        theme: StarPlanetTheme = .sky
    ) {
        self.text = text
        self.src = src
        self.size = size
        self.variant = variant
        self.theme = theme
    }

    private var dim: CGFloat {
        switch size {
        case "sm": return 32
        case "lg": return 56
        default: return 40
        }
    }

    private var initials: String {
        let trimmed = text.trimmingCharacters(in: .whitespacesAndNewlines)
        guard !trimmed.isEmpty else { return "?" }
        let parts = trimmed.split(whereSeparator: { $0.isWhitespace }).map(String.init)
        if parts.count >= 2 {
            let a = parts[0].prefix(1)
            let b = parts[1].prefix(1)
            return String(a + b).uppercased()
        }
        return String(trimmed.prefix(2)).uppercased()
    }

    private var bg: Color {
        switch variant {
        case "primary": return theme.brandPrimary
        case "subtle": return theme.brandSubtle
        default: return theme.surfaceSubtle
        }
    }

    private var fg: Color {
        switch variant {
        case "primary": return .white
        case "subtle": return theme.brandPrimary
        default: return theme.textPrimary
        }
    }

    public var body: some View {
        Group {
            if let src, !src.isEmpty, let url = URL(string: src) {
                AsyncImage(url: url) { phase in
                    switch phase {
                    case .success(let image):
                        image.resizable().scaledToFill()
                    default:
                        initialsView
                    }
                }
            } else {
                initialsView
            }
        }
        .frame(width: dim, height: dim)
        .background(bg)
        .clipShape(Circle())
        .overlay(Circle().stroke(theme.borderDefault, lineWidth: 1.5))
        .accessibilityLabel(text.isEmpty ? "Avatar" : text)
    }

    private var initialsView: some View {
        Text(initials)
            .font(.system(size: dim * 0.38, weight: .heavy))
            .foregroundColor(fg)
            .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
