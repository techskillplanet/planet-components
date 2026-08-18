import 'package:flutter/material.dart';
import 'star_planet_theme.dart';
import 'tsp_option_sheet.dart';

class TspSelect extends StatelessWidget {
  const TspSelect({super.key, required this.options, required this.selectedIndex, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onSelect});
  final List<String> options;
  final int selectedIndex;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onSelect;

  @override
  Widget build(BuildContext context) {
    final index = options.isEmpty ? 0 : selectedIndex.clamp(0, options.length - 1);
    final label = options.isEmpty ? '' : options[index];
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: GestureDetector(
        onTap: disabled
            ? null
            : () => TspOptionSheet.show(
                  context,
                  options: options,
                  selectedIndex: index,
                  theme: theme,
                  onSelect: onSelect,
                ),
        child: Container(
          constraints: const BoxConstraints(minHeight: 48),
          padding: const EdgeInsets.symmetric(horizontal: 14),
          decoration: BoxDecoration(
            color: theme.surfaceRaised,
            borderRadius: BorderRadius.circular(16),
            border: Border.all(color: theme.borderDefault),
          ),
          child: Row(
            children: [
              Expanded(child: Text(label, style: TextStyle(color: theme.textPrimary))),
              Text('⌄', style: TextStyle(color: theme.textTertiary, fontSize: 16)),
            ],
          ),
        ),
      ),
    );
  }
}
