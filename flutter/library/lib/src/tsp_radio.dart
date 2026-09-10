import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Single radio option (group exclusivity is app-owned).
class TspRadio extends StatelessWidget {
  const TspRadio({
    super.key,
    this.text = '',
    this.checked = false,
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final String text;
  final bool checked;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onChanged;

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: disabled ? 0.45 : 1,
      child: GestureDetector(
        onTap: disabled ? null : () => onChanged?.call(true),
        behavior: HitTestBehavior.opaque,
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Container(
              width: 22,
              height: 22,
              alignment: Alignment.center,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                color: theme.surfaceRaised,
                border: Border.all(color: checked ? theme.brandPrimary : theme.borderDefault, width: 2),
              ),
              child: checked
                  ? Container(
                      width: 10,
                      height: 10,
                      decoration: BoxDecoration(shape: BoxShape.circle, color: theme.brandPrimary),
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
