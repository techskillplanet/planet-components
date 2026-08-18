import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspNotification extends StatelessWidget {
  const TspNotification({super.key, required this.title, required this.message, this.variant = 'info', this.theme = StarPlanetTheme.sky});
  final String title;
  final String message;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final isAlert = variant == 'alert';
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: isAlert ? theme.activeFill : theme.pageEnd,
        borderRadius: BorderRadius.circular(18),
        border: Border.all(color: isAlert ? theme.warning : theme.borderDefault),
      ),
      child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
        const SizedBox(height: 6),
        Text(message, style: TextStyle(color: theme.textSecondary, fontSize: 13)),
      ]),
    );
  }
}
