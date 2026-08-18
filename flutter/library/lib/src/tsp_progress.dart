import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspProgress extends StatelessWidget {
  const TspProgress({super.key, required this.progress, this.variant = 'primary', this.theme = StarPlanetTheme.sky});
  final double progress;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final fill = switch (variant) {
      'success' => theme.success,
      'warning' => theme.warning,
      'danger' => theme.danger,
      _ => theme.brandPrimary,
    };
    return ClipRRect(
      borderRadius: BorderRadius.circular(999),
      child: SizedBox(
        height: 10,
        child: LinearProgressIndicator(
          minHeight: 10,
          value: (progress / 100).clamp(0, 1),
          backgroundColor: theme.borderDefault,
          color: fill,
        ),
      ),
    );
  }
}
