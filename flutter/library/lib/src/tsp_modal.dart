import 'package:flutter/material.dart';
import 'star_planet_theme.dart';
import 'tsp_button.dart';

class TspModal extends StatelessWidget {
  const TspModal({
    super.key,
    required this.title,
    required this.message,
    this.confirmText = 'OK',
    this.cancelText = 'Cancel',
    this.theme = StarPlanetTheme.sky,
    this.onConfirm,
    this.onCancel,
  });

  final String title;
  final String message;
  final String confirmText;
  final String cancelText;
  final StarPlanetTheme theme;
  final VoidCallback? onConfirm;
  final VoidCallback? onCancel;

  @override
  Widget build(BuildContext context) {
    return Material(
      type: MaterialType.transparency,
      child: ColoredBox(
        color: const Color(0x47173A62),
        child: Center(
          child: Padding(
            padding: const EdgeInsets.all(18),
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: 420),
              child: Container(
                width: double.infinity,
                padding: const EdgeInsets.all(22),
                decoration: BoxDecoration(
                  color: theme.surfaceRaised,
                  borderRadius: BorderRadius.circular(24),
                  border: Border.all(color: theme.borderDefault),
                ),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(title, style: TextStyle(color: theme.textPrimary, fontSize: 20, fontWeight: FontWeight.w900)),
                    const SizedBox(height: 14),
                    Text(message, style: TextStyle(color: theme.textSecondary, height: 1.4)),
                    const SizedBox(height: 14),
                    Row(
                      children: [
                        Expanded(child: TspButton(text: cancelText, theme: theme, onTap: onCancel)),
                        const SizedBox(width: 10),
                        Expanded(
                          child: TspButton(
                            text: confirmText,
                            variant: TspButtonVariant.primary,
                            theme: theme,
                            onTap: onConfirm,
                          ),
                        ),
                      ],
                    ),
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
