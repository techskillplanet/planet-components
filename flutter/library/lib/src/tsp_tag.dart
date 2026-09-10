import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Closable / selectable tag (Chip remains for filter chips).
class TspTag extends StatelessWidget {
  const TspTag({
    super.key,
    this.text = '',
    this.closable = false,
    this.selected = false,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onClose,
    this.onTap,
  });

  final String text;
  final bool closable;
  final bool selected;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final VoidCallback? onClose;
  final VoidCallback? onTap;

  Color get _bg {
    if (selected) return theme.selectedFill;
    switch (variant) {
      case 'primary':
        return theme.brandSubtle;
      case 'success':
        return theme.successSubtle;
      case 'warning':
        return theme.activeFill;
      case 'danger':
        return const Color(0xFFFFE8EB);
      default:
        return theme.surfaceRaised;
    }
  }

  Color get _fg {
    switch (variant) {
      case 'primary':
        return theme.brandPrimary;
      case 'success':
        return theme.success;
      case 'warning':
        return const Color(0xFFB8860B);
      case 'danger':
        return theme.danger;
      default:
        return theme.textPrimary;
    }
  }

  Color get _border {
    if (selected) return theme.selectedBorder;
    switch (variant) {
      case 'primary':
        return theme.brandPrimary;
      case 'success':
        return theme.success;
      case 'warning':
        return theme.warning;
      case 'danger':
        return theme.danger;
      default:
        return theme.borderDefault;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: GestureDetector(
        onTap: disabled ? null : onTap,
        child: Container(
          padding: EdgeInsets.fromLTRB(12, 6, closable ? 6 : 12, 6),
          decoration: BoxDecoration(
            color: _bg,
            borderRadius: BorderRadius.circular(999),
            border: Border.all(color: _border, width: 1.5),
          ),
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                text,
                style: TextStyle(color: _fg, fontWeight: FontWeight.w800, fontSize: 13),
              ),
              if (closable) ...[
                const SizedBox(width: 2),
                GestureDetector(
                  onTap: disabled
                      ? null
                      : () {
                          onClose?.call();
                        },
                  behavior: HitTestBehavior.opaque,
                  child: Padding(
                    padding: const EdgeInsets.all(4),
                    child: Text(
                      '×',
                      style: TextStyle(color: _fg, fontSize: 14, fontWeight: FontWeight.w700, height: 1),
                    ),
                  ),
                ),
              ],
            ],
          ),
        ),
      ),
    );
  }
}
