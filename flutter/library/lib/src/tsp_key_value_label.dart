import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspKeyValueLabel extends StatelessWidget {
  const TspKeyValueLabel({super.key, required this.label, required this.value, this.theme = StarPlanetTheme.sky});
  final String label;
  final String value;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
        Text(label, style: TextStyle(color: theme.textSecondary)),
        const SizedBox(width: 16),
        Flexible(child: Text(value, textAlign: TextAlign.right, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800))),
      ]);
}
