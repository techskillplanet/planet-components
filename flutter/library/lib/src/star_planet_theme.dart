import 'package:flutter/material.dart';

/// Sky Planet theme — values aligned with React Web `theme.js` / shared tokens.
class StarPlanetTheme {
  const StarPlanetTheme({
    this.pageStart = const Color(0xFFDDF4FF),
    this.pageEnd = const Color(0xFFF9FDFF),
    this.textPrimary = const Color(0xFF173A62),
    this.textSecondary = const Color(0xFF365D82),
    this.textTertiary = const Color(0xFF7895AE),
    this.surfaceRaised = Colors.white,
    this.surfaceSubtle = const Color(0xFFF6FBFF),
    this.borderDefault = const Color(0xFFC8EAFF),
    this.brandPrimary = const Color(0xFF31A8FF),
    this.brandDark = const Color(0xFF1479D6),
    this.brandSubtle = const Color(0xFFE5F6FF),
    /// Sky-cyan feedback only — not selected surface.
    this.success = const Color(0xFF2BB8E6),
    this.successSubtle = const Color(0xFFE8F7FC),
    this.warning = const Color(0xFFFFD166),
    /// Selection = sky brandSubtle (never aurora green).
    this.selectedFill = const Color(0xFFE5F6FF),
    this.selectedBorder = const Color(0xFF31A8FF),
    this.emphasisFill = const Color(0xFFF6FBFF),
    this.activeFill = const Color(0xFFFFF7D7),
    this.danger = const Color(0xFFFF6B7A),
    this.switchOffBackground = const Color(0xFFC8EAFF),
    this.switchOffBorder = const Color(0xFFC8EAFF),
    this.switchOffText = const Color(0xFF365D82),
    this.switchOnBackground = const Color(0xFF31A8FF),
    this.switchOnBorder = const Color(0xFF31A8FF),
    this.switchOnText = Colors.white,
    this.switchHandleBackground = Colors.white,
    this.switchHandleBorder = const Color(0xFFC8EAFF),
    this.switchHandleCheckedBorder = const Color(0xFF31A8FF),
    this.switchLoadingSpinner = Colors.white,
  });

  final Color pageStart;
  final Color pageEnd;
  final Color textPrimary;
  final Color textSecondary;
  final Color textTertiary;
  final Color surfaceRaised;
  final Color surfaceSubtle;
  final Color borderDefault;
  final Color brandPrimary;
  final Color brandDark;
  final Color brandSubtle;
  final Color success;
  final Color successSubtle;
  final Color warning;
  final Color selectedFill;
  final Color selectedBorder;
  final Color emphasisFill;
  final Color activeFill;
  final Color danger;
  final Color switchOffBackground;
  final Color switchOffBorder;
  final Color switchOffText;
  final Color switchOnBackground;
  final Color switchOnBorder;
  final Color switchOnText;
  final Color switchHandleBackground;
  final Color switchHandleBorder;
  final Color switchHandleCheckedBorder;
  final Color switchLoadingSpinner;

  static const sky = StarPlanetTheme();

  static const night = StarPlanetTheme(
    pageStart: Color(0xFF0F1A2E),
    pageEnd: Color(0xFF141E32),
    textPrimary: Color(0xFFE8F4FF),
    textSecondary: Color(0xFFB8D0E8),
    textTertiary: Color(0xFF7A94B0),
    surfaceRaised: Color(0xFF1E2D45),
    surfaceSubtle: Color(0xFF22324A),
    borderDefault: Color(0xFF2A3F5C),
    brandSubtle: Color(0xFF1E3A5F),
    success: Color(0xFF2BB8E6),
    successSubtle: Color(0xFF1A3348),
    selectedFill: Color(0xFF1E3A5F),
    selectedBorder: Color(0xFF31A8FF),
    emphasisFill: Color(0xFF22324A),
    activeFill: Color(0xFF3A3020),
    switchOffBackground: Color(0xFF2A3F5C),
    switchOffBorder: Color(0xFF2A3F5C),
    switchOffText: Color(0xFFB8D0E8),
    switchHandleBorder: Color(0xFF2A3F5C),
  );

  static const mint = StarPlanetTheme(
    pageStart: Color(0xFFDFFAF2),
    pageEnd: Color(0xFFF8FFFC),
    textPrimary: Color(0xFF123F3A),
    textSecondary: Color(0xFF2F6B63),
    textTertiary: Color(0xFF6C938D),
    surfaceSubtle: Color(0xFFF3FFFA),
    borderDefault: Color(0xFFBDEFE2),
    brandPrimary: Color(0xFF20BFA9),
    brandDark: Color(0xFF0C8F7E),
    brandSubtle: Color(0xFFE6FFF4),
    success: Color(0xFF35C58B),
    successSubtle: Color(0xFFE8FBF3),
    selectedFill: Color(0xFFE6FFF4),
    selectedBorder: Color(0xFF20BFA9),
    emphasisFill: Color(0xFFF3FFFA),
    switchOffBackground: Color(0xFFBDEFE2),
    switchOffBorder: Color(0xFFBDEFE2),
    switchOffText: Color(0xFF2F6B63),
    switchOnBackground: Color(0xFF20BFA9),
    switchOnBorder: Color(0xFF20BFA9),
    switchHandleBorder: Color(0xFFBDEFE2),
    switchHandleCheckedBorder: Color(0xFF20BFA9),
  );

  static const sunrise = StarPlanetTheme(
    pageStart: Color(0xFFFFE8D6),
    pageEnd: Color(0xFFFFFDF8),
    textPrimary: Color(0xFF4A2B1A),
    textSecondary: Color(0xFF80523A),
    textTertiary: Color(0xFFAA8068),
    surfaceSubtle: Color(0xFFFFF8F2),
    borderDefault: Color(0xFFFFD1AD),
    brandPrimary: Color(0xFFFF8A3D),
    brandDark: Color(0xFFD85C12),
    brandSubtle: Color(0xFFFFF1E7),
    success: Color(0xFF2BB8E6),
    successSubtle: Color(0xFFE8F7FC),
    selectedFill: Color(0xFFFFF1E7),
    selectedBorder: Color(0xFFFF8A3D),
    emphasisFill: Color(0xFFFFF8F2),
    danger: Color(0xFFE24C5C),
    switchOffBackground: Color(0xFFFFD1AD),
    switchOffBorder: Color(0xFFFFD1AD),
    switchOffText: Color(0xFF80523A),
    switchOnBackground: Color(0xFFFF8A3D),
    switchOnBorder: Color(0xFFFF8A3D),
    switchHandleBorder: Color(0xFFFFD1AD),
    switchHandleCheckedBorder: Color(0xFFFF8A3D),
  );

  /// Built-in color keys aligned with React `starPlanetThemes`.
  static const themeKeys = ['sky', 'night', 'mint', 'sunrise'];

  static StarPlanetTheme resolve(String colorKey) {
    switch (colorKey) {
      case 'night':
        return night;
      case 'mint':
        return mint;
      case 'sunrise':
        return sunrise;
      case 'sky':
      default:
        return sky;
    }
  }
}
