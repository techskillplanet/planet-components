import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspTabs extends StatelessWidget {
  const TspTabs({super.key, required this.tabs, required this.selectedIndex, this.theme = StarPlanetTheme.sky, this.onSelect});
  final List<String> tabs;
  final int selectedIndex;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onSelect;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(4),
      decoration: BoxDecoration(
        color: theme.pageEnd,
        borderRadius: BorderRadius.circular(999),
        border: Border.all(color: theme.borderDefault),
      ),
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        child: Row(
          children: [
            for (var i = 0; i < tabs.length; i++)
              GestureDetector(
                onTap: () => onSelect?.call(i),
                child: Container(
                  constraints: const BoxConstraints(minHeight: 34),
                  padding: const EdgeInsets.symmetric(horizontal: 16),
                  alignment: Alignment.center,
                  decoration: ShapeDecoration(
                    color: i == selectedIndex ? theme.brandPrimary : Colors.transparent,
                    shape: const StadiumBorder(),
                  ),
                  child: Text(
                    tabs[i],
                    style: TextStyle(
                      color: i == selectedIndex ? Colors.white : theme.textSecondary,
                      fontWeight: FontWeight.w800,
                    ),
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
