import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Numeric stepper with − / + controls.
class TspInputNumber extends StatelessWidget {
  const TspInputNumber({
    super.key,
    this.value = 0,
    this.min = double.negativeInfinity,
    this.max = double.infinity,
    this.step = 1,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final num value;
  final num min;
  final num max;
  final num step;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<num>? onChanged;

  num _clamp(num v) {
    if (v < min) return min;
    if (v > max) return max;
    return v;
  }

  @override
  Widget build(BuildContext context) {
    final current = _clamp(value);
    final s = step == 0 ? 1 : step;
    final isDisabled = disabled || variant == 'disabled';
    final canDec = !isDisabled && current > min;
    final canInc = !isDisabled && current < max;

    Widget btn(String label, {required bool enabled, required VoidCallback onTap}) {
      return GestureDetector(
        onTap: enabled ? onTap : null,
        behavior: HitTestBehavior.opaque,
        child: Container(
          width: 40,
          height: 40,
          alignment: Alignment.center,
          decoration: BoxDecoration(
            color: enabled ? theme.brandSubtle : theme.surfaceSubtle,
            borderRadius: BorderRadius.circular(12),
            border: Border.all(color: theme.borderDefault),
          ),
          child: Text(
            label,
            style: TextStyle(
              color: enabled ? theme.brandPrimary : theme.textTertiary,
              fontSize: 20,
              fontWeight: FontWeight.w800,
              height: 1,
            ),
          ),
        ),
      );
    }

    return Opacity(
      opacity: isDisabled ? 0.45 : 1,
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 4),
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          borderRadius: BorderRadius.circular(16),
          border: Border.all(color: theme.borderDefault),
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            btn('−', enabled: canDec, onTap: () => onChanged?.call(_clamp(current - s))),
            ConstrainedBox(
              constraints: const BoxConstraints(minWidth: 48),
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 10),
                child: Text(
                  '$current',
                  textAlign: TextAlign.center,
                  style: TextStyle(color: theme.textPrimary, fontSize: 16, fontWeight: FontWeight.w800),
                ),
              ),
            ),
            btn('+', enabled: canInc, onTap: () => onChanged?.call(_clamp(current + s))),
          ],
        ),
      ),
    );
  }
}
