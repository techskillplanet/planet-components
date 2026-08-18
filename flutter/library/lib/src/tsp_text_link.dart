import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspTextLink extends StatelessWidget {
  const TspTextLink({super.key, required this.text, this.inverse = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final String text;
  final bool inverse;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => GestureDetector(
        onTap: onTap,
        child: Text(text, style: TextStyle(color: inverse ? Colors.white : theme.brandPrimary, fontWeight: FontWeight.w800)),
      );
}
