import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Continuous value slider driven by Sky Planet tokens.
class TspSlider extends StatelessWidget {
  const TspSlider({
    super.key,
    this.value = 0,
    this.min = 0,
    this.max = 100,
    this.step = 1,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final double value;
  final double min;
  final double max;
  final double step;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<double>? onChanged;

  @override
  Widget build(BuildContext context) {
    final lo = min;
    final hi = max <= min ? min + 1 : max;
    final current = value.clamp(lo, hi);
    final isDisabled = disabled || variant == 'disabled';

    return Opacity(
      opacity: isDisabled ? 0.45 : 1,
      child: SliderTheme(
        data: SliderTheme.of(context).copyWith(
          activeTrackColor: theme.brandPrimary,
          inactiveTrackColor: theme.borderDefault,
          thumbColor: theme.surfaceRaised,
          overlayColor: theme.brandPrimary.withValues(alpha: 0.16),
          trackHeight: 6,
          thumbShape: const RoundSliderThumbShape(enabledThumbRadius: 10, elevation: 2),
        ),
        child: Slider(
          value: current.toDouble(),
          min: lo,
          max: hi,
          divisions: step > 0 ? ((hi - lo) / step).round().clamp(1, 1000) : null,
          onChanged: isDisabled ? null : onChanged,
        ),
      ),
    );
  }
}
