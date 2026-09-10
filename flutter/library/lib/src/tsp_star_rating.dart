import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Interactive star rating.
class TspStarRating extends StatelessWidget {
  const TspStarRating({
    super.key,
    this.value = 0,
    this.max = 5,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final int value;
  final int max;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onChanged;

  @override
  Widget build(BuildContext context) {
    final count = max.clamp(1, 10);
    final readonly = variant == 'readonly' || disabled;

    return Opacity(
      opacity: disabled ? 0.45 : 1,
      child: Semantics(
        slider: true,
        value: '$value',
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            for (var i = 1; i <= count; i++)
              GestureDetector(
                onTap: readonly ? null : () => onChanged?.call(i),
                child: Padding(
                  padding: const EdgeInsets.all(2),
                  child: Text(
                    '★',
                    style: TextStyle(
                      fontSize: 22,
                      height: 1,
                      color: i <= value ? theme.brandPrimary : theme.borderDefault,
                    ),
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
