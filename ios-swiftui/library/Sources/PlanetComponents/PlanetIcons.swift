import Foundation

public struct PlanetIconSpec: Sendable {
    public let path: String
    public let glyph: String
    public init(path: String, glyph: String) {
        self.path = path
        self.glyph = glyph
    }
}

public enum PlanetIcons {
    public static let names: [String] = ["back", "check", "close", "chevron", "warning"]

    public static func spec(named name: String) -> PlanetIconSpec {
        switch name {
        case "back": return PlanetIconSpec(path: "M15 18l-6-6 6-6", glyph: "‹")
        case "check": return PlanetIconSpec(path: "M20 6L9 17l-5-5", glyph: "✓")
        case "close": return PlanetIconSpec(path: "M18 6L6 18M6 6l12 12", glyph: "×")
        case "chevron": return PlanetIconSpec(path: "M9 18l6-6-6-6", glyph: "›")
        case "warning": return PlanetIconSpec(path: "M12 9v4 M12 17h.01 M10.3 3.9L1.8 18a2 2 0 001.7 3h16.9a2 2 0 001.7-3L12.7 3.9a2 2 0 00-3.4 0z", glyph: "!")
        case "search": return PlanetIconSpec(path: "M11 11a6 6 0 1 0 0.001 0z M21 21l-4.35-4.35", glyph: "⌕")
        case "home": return PlanetIconSpec(path: "M3 12l9-9 9 9 M5 10v10h14V10", glyph: "⌂")
        case "settings": return PlanetIconSpec(path: "M12 15a3 3 0 1 0 0-6 3 3 0 0 0 0 6z M19.4 15a1.7 1.7 0 0 0 .3 1.8l.1.1a2 2 0 1 1-2.8 2.8l-.1-.1a1.7 1.7 0 0 0-1.8-.3 1.7 1.7 0 0 0-1 1.5V21a2 2 0 1 1-4 0v-.1a1.7 1.7 0 0 0-1-1.5 1.7 1.7 0 0 0-1.8.3l-.1.1a2 2 0 1 1-2.8-2.8l.1-.1a1.7 1.7 0 0 0 .3-1.8 1.7 1.7 0 0 0-1.5-1H3a2 2 0 1 1 0-4h.1a1.7 1.7 0 0 0 1.5-1 1.7 1.7 0 0 0-.3-1.8l-.1-.1a2 2 0 1 1 2.8-2.8l.1.1a1.7 1.7 0 0 0 1.8.3H9a1.7 1.7 0 0 0 1-1.5V3a2 2 0 1 1 4 0v.1a1.7 1.7 0 0 0 1 1.5 1.7 1.7 0 0 0 1.8-.3l.1-.1a2 2 0 1 1 2.8 2.8l-.1.1a1.7 1.7 0 0 0-.3 1.8V9c0 .7.4 1.3 1 1.5H21a2 2 0 1 1 0 4h-.1a1.7 1.7 0 0 0-1.5 1z", glyph: "⚙")
        case "plus": return PlanetIconSpec(path: "M12 5v14 M5 12h14", glyph: "+")
        case "minus": return PlanetIconSpec(path: "M5 12h14", glyph: "−")
        case "info": return PlanetIconSpec(path: "M12 16v-4 M12 8h.01 M12 21a9 9 0 1 0 0-18 9 9 0 0 0 0 18z", glyph: "ℹ")
        case "star": return PlanetIconSpec(path: "M12 2l3.1 6.3 6.9 1-5 4.9 1.2 6.9L12 17.8 5.8 21.1 7 14.2 2 9.3l6.9-1z", glyph: "★")
        case "menu": return PlanetIconSpec(path: "M4 6h16 M4 12h16 M4 18h16", glyph: "☰")
        default:
            return spec(named: "check")
        }
    }
}
