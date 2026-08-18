import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspStickyFooter extends StatelessWidget {
  const TspStickyFooter({super.key, required this.child, this.theme = StarPlanetTheme.sky});
  final Widget child;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => Container(
        padding: const EdgeInsets.all(12),
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          border: Border.all(color: theme.borderDefault),
        ),
        child: child,
      );
}
