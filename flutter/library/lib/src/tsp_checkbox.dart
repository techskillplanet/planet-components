import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Checkbox with brand checkmark box + label.
class TspCheckbox extends StatelessWidget {
  const TspCheckbox({
    super.key,
    this.text = '',
    this.checked = false,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final String text;
  final bool checked;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onChanged;

  @override
  Widget build(BuildContext context) {
    final boxBg = checked
        ? (variant == 'subtle' ? theme.brandSubtle : theme.brandPrimary)
        : theme.surfaceRaised;
    final border = checked ? theme.brandPrimary : theme.borderDefault;

    return Opacity(
      opacity: disabled ? 0.45 : 1,
      child: GestureDetector(
        onTap: disabled ? null : () => onChanged?.call(!checked),
        behavior: HitTestBehavior.opaque,
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Container(
              width: 22,
              height: 22,
              alignment: Alignment.center,
              decoration: BoxDecoration(
                color: boxBg,
                borderRadius: BorderRadius.circular(6),
                border: Border.all(color: border, width: 2),
              ),
              child: checked
                  ? Text(
                      '✓',
                      style: TextStyle(
                        color: variant == 'subtle' ? theme.brandPrimary : Colors.white,
                        fontSize: 14,
                        fontWeight: FontWeight.w800,
                        height: 1,
                      ),
                    )
                  : null,
            ),
            if (text.isNotEmpty) ...[
              const SizedBox(width: 10),
              Flexible(
                child: Text(
                  text,
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                  style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w700, fontSize: 14),
                ),
              ),
            ],
          ],
        ),
      ),
    );
  }
}
