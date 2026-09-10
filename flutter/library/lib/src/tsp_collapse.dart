import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Expandable collapse / accordion panel.
class TspCollapse extends StatelessWidget {
  const TspCollapse({
    super.key,
    this.title = '',
    this.message = '',
    this.expanded = false,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final String title;
  final String message;
  final bool expanded;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onChanged;

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: disabled ? 0.45 : 1,
      child: Container(
        decoration: BoxDecoration(
          color: variant == 'subtle' ? theme.surfaceSubtle : theme.surfaceRaised,
          borderRadius: BorderRadius.circular(16),
          border: Border.all(color: expanded ? theme.brandPrimary : theme.borderDefault),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            GestureDetector(
              onTap: disabled ? null : () => onChanged?.call(!expanded),
              behavior: HitTestBehavior.opaque,
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 14),
                child: Row(
                  children: [
                    Expanded(
                      child: Text(
                        title,
                        maxLines: 2,
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800, fontSize: 15),
                      ),
                    ),
                    const SizedBox(width: 12),
                    Container(
                      width: 28,
                      height: 28,
                      alignment: Alignment.center,
                      decoration: BoxDecoration(
                        shape: BoxShape.circle,
                        border: Border.all(color: theme.borderDefault),
                      ),
                      child: Text(
                        expanded ? '−' : '+',
                        style: TextStyle(color: theme.brandPrimary, fontWeight: FontWeight.w800),
                      ),
                    ),
                  ],
                ),
              ),
            ),
            if (expanded)
              Padding(
                padding: const EdgeInsets.fromLTRB(16, 0, 16, 14),
                child: Text(
                  message,
                  style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w600, fontSize: 14),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
