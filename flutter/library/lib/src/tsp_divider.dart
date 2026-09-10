import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Horizontal hairline divider.
class TspDivider extends StatelessWidget {
  const TspDivider({
    super.key,
    this.text = '',
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
  });

  /// Accessibility label only (not rendered as visible text).
  final String text;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    return Semantics(
      label: text.isEmpty ? null : text,
      child: Opacity(
        opacity: disabled ? 0.4 : 1,
        child: Container(
          margin: const EdgeInsets.symmetric(vertical: 12),
          height: 1,
          color: variant == 'strong' ? theme.textTertiary : theme.borderDefault,
        ),
      ),
    );
  }
}
