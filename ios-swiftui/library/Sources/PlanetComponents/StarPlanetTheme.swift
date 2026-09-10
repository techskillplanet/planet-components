import SwiftUI

/// Island control style profile — aligns with React Web `starPlanetStyleProfiles`.
public struct StarPlanetStyleProfile: Equatable {
    public var key: String
    public var buttonRaisedShadowEnabled: Bool
    public var shadowControlIslandLiftY: CGFloat
    public var shadowControlPressedY: CGFloat
    public var pressedDropY: CGFloat
    public var hoverLiftY: CGFloat
    public var buttonFaceHeight: CGFloat
    public var cardIslandShadow: String

    public var buttonHeight: CGFloat { buttonFaceHeight + shadowControlIslandLiftY }

    public init(
        key: String,
        buttonRaisedShadowEnabled: Bool = true,
        shadowControlIslandLiftY: CGFloat = 6,
        shadowControlPressedY: CGFloat = 2,
        pressedDropY: CGFloat = 2,
        hoverLiftY: CGFloat = -1,
        buttonFaceHeight: CGFloat = 46,
        cardIslandShadow: String = "0 14px 34px rgba(49, 168, 255, 0.20), 0 2px 8px rgba(23, 58, 98, 0.06)"
    ) {
        self.key = key
        self.buttonRaisedShadowEnabled = buttonRaisedShadowEnabled
        self.shadowControlIslandLiftY = shadowControlIslandLiftY
        self.shadowControlPressedY = shadowControlPressedY
        self.pressedDropY = pressedDropY
        self.hoverLiftY = hoverLiftY
        self.buttonFaceHeight = buttonFaceHeight
        self.cardIslandShadow = cardIslandShadow
    }

    public static let islandRaised = StarPlanetStyleProfile(key: "island_raised")

    public static let islandFlat = StarPlanetStyleProfile(
        key: "island_flat",
        buttonRaisedShadowEnabled: false,
        shadowControlIslandLiftY: 0,
        shadowControlPressedY: 0,
        pressedDropY: 0,
        hoverLiftY: 0,
        buttonFaceHeight: 46,
        cardIslandShadow: "none"
    )

    public static let profileKeys = ["island_raised", "island_flat"]

    public static func resolve(_ styleProfile: String) -> StarPlanetStyleProfile {
        switch styleProfile {
        case "island_flat": return .islandFlat
        default: return .islandRaised
        }
    }
}

public struct StarPlanetTheme {
    public var colorKey: String
    public var styleProfile: String
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
    // Style profile defaults match `island_raised`.
    public var buttonRaisedShadowEnabled: Bool
    public var shadowControlIslandLiftY: CGFloat
    public var shadowControlPressedY: CGFloat
    public var pressedDropY: CGFloat
    public var hoverLiftY: CGFloat
    public var buttonFaceHeight: CGFloat
    public var cardIslandShadow: String

    public var buttonHeight: CGFloat { buttonFaceHeight + shadowControlIslandLiftY }

    public init(
        colorKey: String = "sky",
        styleProfile: String = "island_raised",
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
        switchSpinner: Color = .white,
        buttonRaisedShadowEnabled: Bool = true,
        shadowControlIslandLiftY: CGFloat = 6,
        shadowControlPressedY: CGFloat = 2,
        pressedDropY: CGFloat = 2,
        hoverLiftY: CGFloat = -1,
        buttonFaceHeight: CGFloat = 46,
        cardIslandShadow: String = "0 14px 34px rgba(49, 168, 255, 0.20), 0 2px 8px rgba(23, 58, 98, 0.06)"
    ) {
        self.colorKey = colorKey
        self.styleProfile = styleProfile
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
        self.buttonRaisedShadowEnabled = buttonRaisedShadowEnabled
        self.shadowControlIslandLiftY = shadowControlIslandLiftY
        self.shadowControlPressedY = shadowControlPressedY
        self.pressedDropY = pressedDropY
        self.hoverLiftY = hoverLiftY
        self.buttonFaceHeight = buttonFaceHeight
        self.cardIslandShadow = cardIslandShadow
    }

    public func withStyle(_ profile: StarPlanetStyleProfile) -> StarPlanetTheme {
        var next = self
        next.styleProfile = profile.key
        next.buttonRaisedShadowEnabled = profile.buttonRaisedShadowEnabled
        next.shadowControlIslandLiftY = profile.shadowControlIslandLiftY
        next.shadowControlPressedY = profile.shadowControlPressedY
        next.pressedDropY = profile.pressedDropY
        next.hoverLiftY = profile.hoverLiftY
        next.buttonFaceHeight = profile.buttonFaceHeight
        next.cardIslandShadow = profile.cardIslandShadow
        return next
    }

    public static let sky = StarPlanetTheme(colorKey: "sky")

    public static let night = StarPlanetTheme(
        colorKey: "night",
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
        colorKey: "mint",
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
        colorKey: "sunrise",
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

    /// Resolve color theme only (backward compatible). Defaults to raised style.
    public static func resolve(_ colorKey: String) -> StarPlanetTheme {
        switch colorKey {
        case "night": return .night
        case "mint": return .mint
        case "sunrise": return .sunrise
        default: return .sky
        }
    }

    /// Merge color + style profile — aligns with React `resolveTheme(colorKey, styleProfile)`.
    public static func resolve(color: String = "sky", style: String = "island_raised") -> StarPlanetTheme {
        resolve(color).withStyle(StarPlanetStyleProfile.resolve(style))
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
