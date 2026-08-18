import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspCard extends StatelessWidget {
  const TspCard({super.key, required this.child, this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky});
  final Widget child;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: Container(
        width: double.infinity,
        padding: const EdgeInsets.all(20),
        decoration: BoxDecoration(
          color: selected ? theme.selectedFill : theme.surfaceRaised,
          borderRadius: BorderRadius.circular(28),
          border: Border.all(color: selected ? theme.success : theme.borderDefault),
        ),
        child: child,
      ),
    );
  }
}
