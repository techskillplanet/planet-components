import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// A4-style dictation / fill-in print layout.
class TspPrintSheet extends StatelessWidget {
  const TspPrintSheet({
    super.key,
    this.title = '',
    this.items = const [],
    this.columns = 5,
    this.footerFields = const ['姓名', '日期', '得分'],
    this.variant = 'pinyin',
    this.theme = StarPlanetTheme.sky,
  });

  final String title;
  final List<Object> items;
  final int columns;
  final List<String> footerFields;
  final String variant;
  final StarPlanetTheme theme;

  String _promptOf(Object item) {
    if (item is String) return item;
    if (item is Map) return '${item['prompt'] ?? ''}';
    return item.toString();
  }

  @override
  Widget build(BuildContext context) {
    final cols = columns < 1 ? 1 : columns;
    final isMeaning = variant == 'meaning';

    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(12),
      decoration: BoxDecoration(
        color: theme.surfaceRaised,
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: theme.borderDefault),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          if (title.isNotEmpty)
            Padding(
              padding: const EdgeInsets.only(bottom: 12),
              child: Text(
                title,
                textAlign: TextAlign.center,
                style: TextStyle(color: theme.textPrimary, fontSize: 16, fontWeight: FontWeight.w900),
              ),
            ),
          LayoutBuilder(
            builder: (context, constraints) {
              final gap = 6.0;
              final cellWidth = (constraints.maxWidth - gap * (cols - 1)) / cols;
              return Wrap(
                spacing: gap,
                runSpacing: gap,
                children: items.map((item) {
                  final prompt = _promptOf(item);
                  return SizedBox(
                    width: cellWidth,
                    child: Container(
                      constraints: const BoxConstraints(minHeight: 52),
                      padding: const EdgeInsets.all(4),
                      decoration: BoxDecoration(
                        border: Border.all(color: const Color(0xFF333333)),
                      ),
                      child: Column(
                        children: [
                          Text(
                            prompt,
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              color: theme.textSecondary,
                              fontSize: isMeaning ? 12 : 11,
                              fontWeight: isMeaning ? FontWeight.w700 : FontWeight.w400,
                            ),
                          ),
                          const SizedBox(height: 6),
                          Container(
                            height: isMeaning ? 28 : 22,
                            decoration: const BoxDecoration(
                              border: Border(top: BorderSide(color: Color(0xFF999999))),
                            ),
                          ),
                        ],
                      ),
                    ),
                  );
                }).toList(),
              );
            },
          ),
          if (footerFields.isNotEmpty) ...[
            const SizedBox(height: 12),
            Wrap(
              alignment: WrapAlignment.spaceBetween,
              spacing: 8,
              runSpacing: 8,
              children: footerFields
                  .map(
                    (field) => Text(
                      '$field：________',
                      style: TextStyle(color: theme.textPrimary, fontSize: 12, fontWeight: FontWeight.w700),
                    ),
                  )
                  .toList(),
            ),
          ],
        ],
      ),
    );
  }
}
