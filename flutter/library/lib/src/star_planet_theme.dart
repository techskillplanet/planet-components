import 'package:flutter/material.dart';

/// Sky Planet theme — values aligned with RN `theme.js` / shared tokens.
class StarPlanetTheme {
  const StarPlanetTheme({
    this.pageStart = const Color(0xFFDDF4FF),
    this.pageEnd = const Color(0xFFF9FDFF),
    this.textPrimary = const Color(0xFF173A62),
    this.textSecondary = const Color(0xFF365D82),
    this.textTertiary = const Color(0xFF7895AE),
    this.surfaceRaised = Colors.white,
    this.borderDefault = const Color(0xFFC8EAFF),
    this.brandPrimary = const Color(0xFF31A8FF),
    this.brandDark = const Color(0xFF1479D6),
    this.success = const Color(0xFF43CFC7),
    this.warning = const Color(0xFFFFD166),
    this.selectedFill = const Color(0xFFE8FDF7),
    this.activeFill = const Color(0xFFFFF7D7),
    this.danger = const Color(0xFFFF6B7A),
    this.switchOffBackground = const Color(0xFFC8EAFF),
    this.switchOffBorder = const Color(0xFFC8EAFF),
    this.switchOffText = const Color(0xFF365D82),
    this.switchOnBackground = const Color(0xFF31A8FF),
    this.switchOnBorder = const Color(0xFF31A8FF),
    this.switchOnText = Colors.white,
    this.switchHandleBackground = const Color(0xFFFEFFFF),
    this.switchHandleBorder = const Color(0xFFC8EAFF),
    this.switchHandleCheckedBorder = const Color(0xFF31A8FF),
    this.switchLoadingSpinner = const Color(0xFF31A8FF),
  });

  final Color pageStart;
  final Color pageEnd;
  final Color textPrimary;
  final Color textSecondary;
  final Color textTertiary;
  final Color surfaceRaised;
  final Color borderDefault;
  final Color brandPrimary;
  final Color brandDark;
  final Color success;
  final Color warning;
  final Color selectedFill;
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
    borderDefault: Color(0xFF2A3F5C),
    selectedFill: Color(0xFF1E3A52),
    activeFill: Color(0xFF3A3020),
  );
  static const mint = StarPlanetTheme(
    pageStart: Color(0xFFDFFAF2),
    pageEnd: Color(0xFFF8FFFC),
    textPrimary: Color(0xFF123F3A),
    textSecondary: Color(0xFF2F6B63),
    textTertiary: Color(0xFF6C938D),
    borderDefault: Color(0xFFBDEFE2),
    brandPrimary: Color(0xFF20BFA9),
    brandDark: Color(0xFF0C8F7E),
    success: Color(0xFF35C58B),
    selectedFill: Color(0xFFE6FFF4),
  );
}
