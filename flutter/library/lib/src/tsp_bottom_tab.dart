import 'package:flutter/material.dart';
import 'star_planet_theme.dart';
import 'tsp_tab_item.dart';

class TspBottomTab extends StatelessWidget {
  const TspBottomTab({super.key, required this.tabs, required this.selectedKey, this.theme = StarPlanetTheme.sky, this.onSelect});
  final List<TspTabItem> tabs;
  final String selectedKey;
  final StarPlanetTheme theme;
  final ValueChanged<String>? onSelect;

  @override
  Widget build(BuildContext context) => Container(
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          border: Border(top: BorderSide(color: theme.borderDefault)),
        ),
        width: double.infinity,
        child: SafeArea(
          top: false,
          child: Padding(
            padding: const EdgeInsets.fromLTRB(12, 8, 12, 8),
            child: ConstrainedBox(
              constraints: const BoxConstraints(minHeight: 52),
              child: Row(children: [
                for (final tab in tabs)
                  Expanded(
                    child: InkWell(
                      onTap: () => onSelect?.call(tab.key),
                      child: Column(mainAxisAlignment: MainAxisAlignment.center, mainAxisSize: MainAxisSize.min, children: [
                        Icon(tab.icon, size: 22, color: tab.key == selectedKey ? theme.brandPrimary : theme.textTertiary),
                        const SizedBox(height: 2),
                        Text(
                          tab.title,
                          style: TextStyle(
                            color: tab.key == selectedKey ? theme.brandPrimary : theme.textTertiary,
                            fontWeight: FontWeight.w800,
                            fontSize: 11,
                            height: 14 / 11,
                          ),
                        ),
                      ]),
                    ),
                  ),
              ]),
            ),
          ),
        ),
      );
}
