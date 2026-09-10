import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Floating action button. Contract `default` maps to raised surface (not Dart reserved).
class TspFab extends StatelessWidget {
  const TspFab({
    super.key,
    this.icon = '+',
    this.text = '',
    this.variant = 'primary',
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onTap,
  });

  final String icon;
  final String text;
  final String variant;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  bool get _primary => variant != 'default' && variant != 'standard';

  @override
  Widget build(BuildContext context) {
    final bg = _primary ? theme.brandPrimary : theme.surfaceRaised;
    final fg = _primary ? Colors.white : theme.textPrimary;
    final extended = text.isNotEmpty;
    final lift = theme.buttonRaisedShadowEnabled ? theme.shadowControlIslandLiftY : 0.0;

    return Opacity(
      opacity: disabled ? .45 : 1,
      child: Semantics(
        button: true,
        enabled: !disabled,
        label: text.isNotEmpty ? text : icon,
        child: GestureDetector(
          onTap: disabled ? null : onTap,
          child: Container(
            height: extended ? 52 : 56,
            padding: EdgeInsets.symmetric(horizontal: extended ? 18 : 0),
            constraints: BoxConstraints(minWidth: extended ? 0 : 56),
            decoration: BoxDecoration(
              color: bg,
              borderRadius: BorderRadius.circular(extended ? 28 : 28),
              border: Border.all(color: theme.borderDefault, width: 2),
              boxShadow: lift > 0
                  ? [
                      BoxShadow(
                        color: theme.brandPrimary.withValues(alpha: 0.28),
                        offset: Offset(0, lift),
                        blurRadius: 12,
                      ),
                    ]
                  : null,
            ),
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(icon, style: TextStyle(color: fg, fontSize: 22, fontWeight: FontWeight.w800, height: 1)),
                if (extended) ...[
                  const SizedBox(width: 8),
                  Text(text, style: TextStyle(color: fg, fontSize: 15, fontWeight: FontWeight.w800)),
                ],
              ],
            ),
          ),
        ),
      ),
    );
  }
}
