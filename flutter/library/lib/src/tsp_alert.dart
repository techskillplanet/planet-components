import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

enum TspAlertVariant { info, success, warning, error }


class TspAlert extends StatelessWidget {
  const TspAlert({super.key, required this.title, required this.message, this.variant = TspAlertVariant.info, this.theme = StarPlanetTheme.sky});
  final String title;
  final String message;
  final TspAlertVariant variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final fill = switch (variant) {
      TspAlertVariant.success => theme.selectedFill,
      TspAlertVariant.warning => theme.activeFill,
      TspAlertVariant.error => theme.danger.withValues(alpha: .12),
      TspAlertVariant.info => theme.pageEnd,
    };
    final stroke = switch (variant) {
      TspAlertVariant.success => theme.success,
      TspAlertVariant.warning => theme.warning,
      TspAlertVariant.error => theme.danger,
      TspAlertVariant.info => theme.borderDefault,
    };
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(color: fill, borderRadius: BorderRadius.circular(18), border: Border.all(color: stroke)),
      child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
        const SizedBox(height: 6),
        Text(message, style: TextStyle(color: theme.textSecondary, fontSize: 13)),
      ]),
    );
  }
}
