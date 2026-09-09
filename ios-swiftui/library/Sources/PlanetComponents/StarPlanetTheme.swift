import SwiftUI

public struct StarPlanetTheme {
    public var pageStart: Color
    public var pageEnd: Color
    public var textPrimary: Color
    public var textSecondary: Color
    public var textTertiary: Color
    public var surfaceRaised: Color
    public var surfaceSubtle: Color
    public var borderDefault: Color
    public var brandPrimary: Color
    public var brandDark: Color
    public var brandSubtle: Color
    /// Sky-cyan feedback only — not selected surface.
    public var success: Color
    public var successSubtle: Color
    public var warning: Color
    /// Selection = brandSubtle (never aurora green on sky).
    public var selectedFill: Color
    public var selectedBorder: Color
    public var emphasisFill: Color
    public var activeFill: Color
    public var danger: Color
    /// Flat switch track off — aligns with RN `switchOffBg`.
    public var switchOffBackground: Color
    public var switchOffBorder: Color
    public var switchOffText: Color
    /// Flat switch track on — aligns with RN `switchOnBg`.
    public var switchOnBackground: Color
    public var switchOnBorder: Color
    public var switchOnText: Color
    /// Flat switch thumb — aligns with RN `switchHandleBg`.
    public var switchHandleBackground: Color
    public var switchHandleBorder: Color
    public var switchHandleCheckedBorder: Color
    public var switchSpinner: Color

    public init(
        pageStart: Color = Color(hex: 0xDDF4FF),
        pageEnd: Color = Color(hex: 0xF9FDFF),
        textPrimary: Color = Color(hex: 0x173A62),
        textSecondary: Color = Color(hex: 0x365D82),
        textTertiary: Color = Color(hex: 0x7895AE),
        surfaceRaised: Color = .white,
        surfaceSubtle: Color = Color(hex: 0xF6FBFF),
        borderDefault: Color = Color(hex: 0xC8EAFF),
        brandPrimary: Color = Color(hex: 0x31A8FF),
        brandDark: Color = Color(hex: 0x1479D6),
        brandSubtle: Color = Color(hex: 0xE5F6FF),
        success: Color = Color(hex: 0x2BB8E6),
        successSubtle: Color = Color(hex: 0xE8F7FC),
        warning: Color = Color(hex: 0xFFD166),
        selectedFill: Color = Color(hex: 0xE5F6FF),
        selectedBorder: Color = Color(hex: 0x31A8FF),
        emphasisFill: Color = Color(hex: 0xF6FBFF),
        activeFill: Color = Color(hex: 0xFFF7D7),
        danger: Color = Color(hex: 0xFF6B7A),
        switchOffBackground: Color = Color(hex: 0xC8EAFF),
        switchOffBorder: Color = Color(hex: 0xC8EAFF),
        switchOffText: Color = Color(hex: 0x365D82),
        switchOnBackground: Color = Color(hex: 0x31A8FF),
        switchOnBorder: Color = Color(hex: 0x31A8FF),
        switchOnText: Color = .white,
        switchHandleBackground: Color = .white,
        switchHandleBorder: Color = Color(hex: 0xC8EAFF),
        switchHandleCheckedBorder: Color = Color(hex: 0x31A8FF),
        switchSpinner: Color = .white
    ) {
        self.pageStart = pageStart
        self.pageEnd = pageEnd
        self.textPrimary = textPrimary
        self.textSecondary = textSecondary
        self.textTertiary = textTertiary
        self.surfaceRaised = surfaceRaised
        self.surfaceSubtle = surfaceSubtle
        self.borderDefault = borderDefault
        self.brandPrimary = brandPrimary
        self.brandDark = brandDark
        self.brandSubtle = brandSubtle
        self.success = success
        self.successSubtle = successSubtle
        self.warning = warning
        self.selectedFill = selectedFill
        self.selectedBorder = selectedBorder
        self.emphasisFill = emphasisFill
        self.activeFill = activeFill
        self.danger = danger
        self.switchOffBackground = switchOffBackground
        self.switchOffBorder = switchOffBorder
        self.switchOffText = switchOffText
        self.switchOnBackground = switchOnBackground
        self.switchOnBorder = switchOnBorder
        self.switchOnText = switchOnText
        self.switchHandleBackground = switchHandleBackground
        self.switchHandleBorder = switchHandleBorder
        self.switchHandleCheckedBorder = switchHandleCheckedBorder
        self.switchSpinner = switchSpinner
    }

    public static let sky = StarPlanetTheme()

    public static let night = StarPlanetTheme(
        pageStart: Color(hex: 0x0F1A2E),
        pageEnd: Color(hex: 0x141E32),
        textPrimary: Color(hex: 0xE8F4FF),
        textSecondary: Color(hex: 0xB8D0E8),
        textTertiary: Color(hex: 0x7A94B0),
        surfaceRaised: Color(hex: 0x1E2D45),
        surfaceSubtle: Color(hex: 0x22324A),
        borderDefault: Color(hex: 0x2A3F5C),
        brandSubtle: Color(hex: 0x1E3A5F),
        successSubtle: Color(hex: 0x1A3348),
        selectedFill: Color(hex: 0x1E3A5F),
        emphasisFill: Color(hex: 0x22324A),
        activeFill: Color(hex: 0x3A3020),
        switchOffBackground: Color(hex: 0x2A3F5C),
        switchOffBorder: Color(hex: 0x2A3F5C),
        switchOffText: Color(hex: 0xB8D0E8),
        switchHandleBorder: Color(hex: 0x2A3F5C)
    )

    public static let mint = StarPlanetTheme(
        pageStart: Color(hex: 0xDFFAF2),
        pageEnd: Color(hex: 0xF8FFFC),
        textPrimary: Color(hex: 0x123F3A),
        textSecondary: Color(hex: 0x2F6B63),
        textTertiary: Color(hex: 0x6C938D),
        surfaceSubtle: Color(hex: 0xF3FFFA),
        borderDefault: Color(hex: 0xBDEFE2),
        brandPrimary: Color(hex: 0x20BFA9),
        brandDark: Color(hex: 0x0C8F7E),
        brandSubtle: Color(hex: 0xE6FFF4),
        success: Color(hex: 0x35C58B),
        successSubtle: Color(hex: 0xE8FBF3),
        selectedFill: Color(hex: 0xE6FFF4),
        selectedBorder: Color(hex: 0x20BFA9),
        emphasisFill: Color(hex: 0xF3FFFA),
        switchOffBackground: Color(hex: 0xBDEFE2),
        switchOffBorder: Color(hex: 0xBDEFE2),
        switchOffText: Color(hex: 0x2F6B63),
        switchOnBackground: Color(hex: 0x20BFA9),
        switchOnBorder: Color(hex: 0x20BFA9),
        switchHandleBorder: Color(hex: 0xBDEFE2),
        switchHandleCheckedBorder: Color(hex: 0x20BFA9)
    )

    public static let sunrise = StarPlanetTheme(
        pageStart: Color(hex: 0xFFE8D6),
        pageEnd: Color(hex: 0xFFFDF8),
        textPrimary: Color(hex: 0x4A2B1A),
        textSecondary: Color(hex: 0x80523A),
        textTertiary: Color(hex: 0xAA8068),
        surfaceSubtle: Color(hex: 0xFFF8F2),
        borderDefault: Color(hex: 0xFFD1AD),
        brandPrimary: Color(hex: 0xFF8A3D),
        brandDark: Color(hex: 0xD85C12),
        brandSubtle: Color(hex: 0xFFF1E7),
        successSubtle: Color(hex: 0xE8F7FC),
        selectedFill: Color(hex: 0xFFF1E7),
        selectedBorder: Color(hex: 0xFF8A3D),
        emphasisFill: Color(hex: 0xFFF8F2),
        danger: Color(hex: 0xE24C5C),
        switchOffBackground: Color(hex: 0xFFD1AD),
        switchOffBorder: Color(hex: 0xFFD1AD),
        switchOffText: Color(hex: 0x80523A),
        switchOnBackground: Color(hex: 0xFF8A3D),
        switchOnBorder: Color(hex: 0xFF8A3D),
        switchHandleBorder: Color(hex: 0xFFD1AD),
        switchHandleCheckedBorder: Color(hex: 0xFF8A3D)
    )

    /// Built-in color keys aligned with React `starPlanetThemes`.
    public static let themeKeys = ["sky", "night", "mint", "sunrise"]

    public static func resolve(_ colorKey: String) -> StarPlanetTheme {
        switch colorKey {
        case "night": return .night
        case "mint": return .mint
        case "sunrise": return .sunrise
        default: return .sky
        }
    }
}

extension Color {
    @usableFromInline
    init(hex: UInt32, opacity: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255,
            green: Double((hex >> 8) & 0xFF) / 255,
            blue: Double(hex & 0xFF) / 255,
            opacity: opacity
        )
    }
}
