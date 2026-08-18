import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspIconButton extends StatelessWidget {
  const TspIconButton({super.key, required this.icon, this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final IconData icon;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => Opacity(
        opacity: disabled ? .45 : 1,
        child: GestureDetector(
          onTap: disabled ? null : onTap,
          child: Container(
            width: 44,
            height: 44,
            alignment: Alignment.center,
            decoration: BoxDecoration(
              color: selected ? theme.brandPrimary : theme.surfaceRaised,
              borderRadius: BorderRadius.circular(14),
              border: Border.all(color: theme.borderDefault),
            ),
            child: Icon(icon, size: 22, color: selected ? Colors.white : theme.textPrimary),
          ),
        ),
      );
}
