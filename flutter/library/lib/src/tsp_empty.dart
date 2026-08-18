import 'package:flutter/material.dart';
import 'star_planet_theme.dart';
import 'tsp_button.dart';

class TspEmpty extends StatelessWidget {
  const TspEmpty({super.key, required this.title, required this.message, this.actionText = '', this.theme = StarPlanetTheme.sky, this.onAction});
  final String title;
  final String message;
  final String actionText;
  final StarPlanetTheme theme;
  final VoidCallback? onAction;

  @override
  Widget build(BuildContext context) => Container(
        width: double.infinity,
        padding: const EdgeInsets.all(24),
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          borderRadius: BorderRadius.circular(22),
          border: Border.all(color: theme.borderDefault),
        ),
        child: Column(children: [
          Container(
            width: 50,
            height: 50,
            alignment: Alignment.center,
            decoration: BoxDecoration(color: theme.brandPrimary, borderRadius: BorderRadius.circular(18)),
            child: const Text('○', style: TextStyle(color: Colors.white, fontSize: 22, fontWeight: FontWeight.w800)),
          ),
          const SizedBox(height: 8),
          Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900)),
          const SizedBox(height: 6),
          Text(message, textAlign: TextAlign.center, style: TextStyle(color: theme.textSecondary)),
          if (actionText.isNotEmpty) ...[
            const SizedBox(height: 12),
            TspButton(text: actionText, fullWidth: false, variant: TspButtonVariant.primary, theme: theme, onTap: onAction),
          ],
        ]),
      );
}
