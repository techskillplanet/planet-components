import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspToast extends StatelessWidget {
  const TspToast({
    super.key,
    required this.message,
    this.variant = 'info',
    this.theme = StarPlanetTheme.sky,
  });

  final String message;
  final String variant;
  final StarPlanetTheme theme;

  static const Duration defaultDuration = Duration(milliseconds: 1600);

  static Color _fill(String variant, StarPlanetTheme theme) {
    return switch (variant) {
      'success' => theme.success,
      'warning' => theme.warning,
      'danger' || 'error' => theme.danger,
      _ => theme.brandDark,
    };
  }

  static void show(
    BuildContext context, {
    required String message,
    String variant = 'info',
    StarPlanetTheme theme = StarPlanetTheme.sky,
    Duration duration = defaultDuration,
  }) {
    final overlay = Overlay.maybeOf(context);
    if (overlay == null) return;
    late OverlayEntry entry;
    entry = OverlayEntry(
      builder: (_) => Positioned(
        left: 18,
        right: 18,
        bottom: 24,
        child: IgnorePointer(
          child: Material(
            color: Colors.transparent,
            child: Align(
              alignment: Alignment.bottomCenter,
              child: TspToast(message: message, variant: variant, theme: theme),
            ),
          ),
        ),
      ),
    );
    overlay.insert(entry);
    Future<void>.delayed(duration, () {
      if (entry.mounted) entry.remove();
    });
  }

  @override
  Widget build(BuildContext context) {
    final fill = _fill(variant, theme);
    final textColor = variant == 'warning' ? theme.textPrimary : Colors.white;
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 10),
      decoration: ShapeDecoration(color: fill, shape: const StadiumBorder()),
      child: Text(
        message,
        textAlign: TextAlign.center,
        style: TextStyle(color: textColor, fontWeight: FontWeight.w800, fontSize: 14),
      ),
    );
  }
}
