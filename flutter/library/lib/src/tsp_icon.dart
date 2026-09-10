import 'package:flutter/material.dart';
import 'planet_icons.dart';
import 'star_planet_theme.dart';

/// Named Planet icon using catalog glyphs (path data available via [planetIcons]).
class TspIcon extends StatelessWidget {
  const TspIcon({
    super.key,
    this.name = 'check',
    this.size = 20,
    this.color,
    this.label,
    this.theme = StarPlanetTheme.sky,
  });

  final String name;
  final double size;
  final Color? color;
  final String? label;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final spec = planetIcons[name] ?? planetIcons['check']!;
    final child = Text(
      spec.glyph,
      textAlign: TextAlign.center,
      style: TextStyle(
        fontSize: size,
        height: 1.1,
        color: color ?? theme.textPrimary,
        fontWeight: FontWeight.w600,
      ),
    );
    if (label == null || label!.isEmpty) {
      return ExcludeSemantics(child: SizedBox(width: size, height: size, child: Center(child: child)));
    }
    return Semantics(
      label: label,
      image: true,
      child: SizedBox(width: size, height: size, child: Center(child: child)),
    );
  }
}
