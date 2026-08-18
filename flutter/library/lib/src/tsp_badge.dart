import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspBadge extends StatelessWidget {
  const TspBadge({super.key, required this.text, this.variant = 'default', this.disabled = false, this.theme = StarPlanetTheme.sky});
  final String text;
  final String variant;
  final bool disabled;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final fill = switch (variant) {
      'primary' => theme.brandPrimary,
      'success' => theme.success,
      'warning' => theme.warning,
      'danger' => theme.danger,
      _ => theme.pageEnd,
    };
    final color = ['primary', 'success', 'danger'].contains(variant) ? Colors.white : theme.textPrimary;
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: Container(
        constraints: const BoxConstraints(minHeight: 28),
        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
        alignment: Alignment.center,
        decoration: ShapeDecoration(color: fill, shape: const StadiumBorder()),
        child: Text(text, style: TextStyle(color: color, fontWeight: FontWeight.w800, fontSize: 13, height: 16 / 13)),
      ),
    );
  }
}
