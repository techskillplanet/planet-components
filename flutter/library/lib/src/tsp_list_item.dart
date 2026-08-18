import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspListItem extends StatelessWidget {
  const TspListItem({super.key, required this.title, this.message = '', this.trailing = '', this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final String title;
  final String message;
  final String trailing;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => Opacity(
        opacity: disabled ? .45 : 1,
        child: GestureDetector(
          onTap: disabled ? null : onTap,
          child: Container(
            constraints: const BoxConstraints(minHeight: 64),
            padding: const EdgeInsets.all(14),
            decoration: BoxDecoration(
              color: selected ? theme.selectedFill : theme.surfaceRaised,
              borderRadius: BorderRadius.circular(18),
              border: Border.all(color: selected ? theme.success : theme.borderDefault),
            ),
            child: Row(children: [
              Expanded(
                child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                  Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
                  if (message.isNotEmpty) ...[const SizedBox(height: 4), Text(message, style: TextStyle(color: theme.textSecondary))],
                ]),
              ),
              if (trailing.isNotEmpty) Text(trailing, style: TextStyle(color: theme.textTertiary, fontSize: 20)),
            ]),
          ),
        ),
      );
}
