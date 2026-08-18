import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspLoadingDialog extends StatelessWidget {
  const TspLoadingDialog({
    super.key,
    this.visible = true,
    this.message = '加载中...',
    this.variant = 'default',
    this.dismissible = false,
    this.theme = StarPlanetTheme.sky,
    this.onDismiss,
  });

  final bool visible;
  final String message;
  final String variant;
  final bool dismissible;
  final StarPlanetTheme theme;
  final VoidCallback? onDismiss;

  @override
  Widget build(BuildContext context) {
    if (!visible) return const SizedBox.shrink();
    final compact = variant == 'compact';
    return Material(
      type: MaterialType.transparency,
      child: ColoredBox(
        color: const Color(0x47173A62),
        child: GestureDetector(
          behavior: HitTestBehavior.opaque,
          onTap: dismissible ? onDismiss : null,
          child: Center(
            child: GestureDetector(
              onTap: () {},
              child: Container(
                constraints: BoxConstraints(minWidth: compact ? 112 : 148),
                padding: EdgeInsets.symmetric(
                  horizontal: compact ? 18 : 28,
                  vertical: compact ? 14 : 22,
                ),
                decoration: BoxDecoration(
                  color: theme.surfaceRaised,
                  borderRadius: BorderRadius.circular(compact ? 18 : 24),
                  border: Border.all(color: theme.borderDefault),
                ),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    SizedBox(
                      width: compact ? 22 : 28,
                      height: compact ? 22 : 28,
                      child: CircularProgressIndicator(strokeWidth: 2.5, color: theme.brandPrimary),
                    ),
                    if (message.isNotEmpty) ...[
                      SizedBox(height: compact ? 8 : 12),
                      Text(
                        message,
                        textAlign: TextAlign.center,
                        style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800),
                      ),
                    ],
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
