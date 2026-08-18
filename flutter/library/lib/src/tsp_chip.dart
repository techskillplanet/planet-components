import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspChip extends StatelessWidget {
  const TspChip({super.key, required this.text, this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final String text;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => GestureDetector(
        onTap: disabled ? null : onTap,
        child: Opacity(
          opacity: disabled ? .45 : 1,
          child: Container(
            padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 8),
            decoration: ShapeDecoration(
              color: selected ? theme.selectedFill : theme.surfaceRaised,
              shape: StadiumBorder(side: BorderSide(color: selected ? theme.success : theme.borderDefault)),
            ),
            child: Text(text, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
          ),
        ),
      );
}
