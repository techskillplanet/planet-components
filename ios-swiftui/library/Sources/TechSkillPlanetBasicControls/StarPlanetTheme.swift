import SwiftUI

public struct StarPlanetTheme {
    public var pageStart = Color(red: 0.867, green: 0.957, blue: 1.0)
    public var pageEnd = Color(red: 0.976, green: 0.992, blue: 1.0)
    public var textPrimary = Color(red: 0.090, green: 0.227, blue: 0.384)
    public var textSecondary = Color(red: 0.212, green: 0.365, blue: 0.510)
    public var textTertiary = Color(red: 0.471, green: 0.584, blue: 0.682)
    public var surfaceRaised = Color.white
    public var borderDefault = Color(red: 0.784, green: 0.918, blue: 1.0)
    public var brandPrimary = Color(red: 0.192, green: 0.659, blue: 1.0)
    public var brandDark = Color(red: 0.078, green: 0.475, blue: 0.839)
    public var success = Color(red: 0.263, green: 0.812, blue: 0.780)
    public var warning = Color(red: 1.0, green: 0.820, blue: 0.400)
    public var selectedFill = Color(red: 0.910, green: 0.992, blue: 0.969)
    public var activeFill = Color(red: 1.0, green: 0.969, blue: 0.843)
    public var danger = Color(red: 1.0, green: 0.420, blue: 0.478)

    public static let sky = StarPlanetTheme()
    public static let night = StarPlanetTheme(
        pageStart: Color(red: 0.059, green: 0.102, blue: 0.180),
        pageEnd: Color(red: 0.078, green: 0.118, blue: 0.196),
        textPrimary: Color(red: 0.910, green: 0.957, blue: 1.0),
        textSecondary: Color(red: 0.722, green: 0.816, blue: 0.910),
        textTertiary: Color(red: 0.478, green: 0.580, blue: 0.690),
        surfaceRaised: Color(red: 0.118, green: 0.176, blue: 0.271),
        borderDefault: Color(red: 0.165, green: 0.247, blue: 0.361),
        selectedFill: Color(red: 0.118, green: 0.227, blue: 0.322),
        activeFill: Color(red: 0.227, green: 0.188, blue: 0.125)
    )
    public static let mint = StarPlanetTheme(
        pageStart: Color(red: 0.875, green: 0.980, blue: 0.949),
        pageEnd: Color(red: 0.973, green: 1.0, blue: 0.988),
        textPrimary: Color(red: 0.071, green: 0.247, blue: 0.227),
        textSecondary: Color(red: 0.184, green: 0.420, blue: 0.388),
        textTertiary: Color(red: 0.424, green: 0.576, blue: 0.553),
        borderDefault: Color(red: 0.741, green: 0.937, blue: 0.886),
        brandPrimary: Color(red: 0.125, green: 0.749, blue: 0.663),
        brandDark: Color(red: 0.047, green: 0.561, blue: 0.494),
        selectedFill: Color(red: 0.902, green: 1.0, blue: 0.957)
    )
}
