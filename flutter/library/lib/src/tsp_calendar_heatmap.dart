import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspCalendarCell {
  const TspCalendarCell({required this.date, this.level = 'none'});

  final String date;
  final String level;
}

typedef TspCalendarDaySelected = void Function(String date, String level);

/// Month attendance heatmap.
class TspCalendarHeatmap extends StatelessWidget {
  const TspCalendarHeatmap({
    super.key,
    this.yearMonth,
    this.cells = const [],
    this.showLegend = true,
    this.theme = StarPlanetTheme.sky,
    this.onSelectDay,
  });

  final String? yearMonth;
  final List<TspCalendarCell> cells;
  final bool showLegend;
  final StarPlanetTheme theme;
  final TspCalendarDaySelected? onSelectDay;

  static const _weekdays = ['一', '二', '三', '四', '五', '六', '日'];
  static const _levels = [
    ('full', '全勤'),
    ('partial', '部分'),
    ('none', '未打'),
    ('exempt', '豁免'),
  ];

  static ({int year, int month}) _parseYearMonth(String? raw) {
    final parts = (raw ?? '').split('-');
    if (parts.length >= 2) {
      final y = int.tryParse(parts[0]);
      final m = int.tryParse(parts[1]);
      if (y != null && m != null && m >= 1 && m <= 12) return (year: y, month: m);
    }
    final now = DateTime.now();
    return (year: now.year, month: now.month);
  }

  ({Color bg, Color border, Color fg}) _colors(String level) {
    switch (level) {
      case 'full':
        return (bg: theme.success, border: theme.success, fg: Colors.white);
      case 'partial':
        return (bg: theme.warning, border: theme.warning, fg: theme.textPrimary);
      case 'exempt':
        return (bg: theme.brandPrimary, border: theme.brandPrimary, fg: Colors.white);
      default:
        return (bg: theme.pageEnd, border: theme.borderDefault, fg: theme.textTertiary);
    }
  }

  @override
  Widget build(BuildContext context) {
    final ym = _parseYearMonth(yearMonth);
    final levelMap = {for (final c in cells) c.date: c.level};
    final first = DateTime(ym.year, ym.month, 1);
    final daysInMonth = DateTime(ym.year, ym.month + 1, 0).day;
    final startPad = (first.weekday + 6) % 7; // Monday-first
    final slots = <({int day, String date, String level})?>[];
    for (var i = 0; i < startPad; i++) {
      slots.add(null);
    }
    for (var d = 1; d <= daysInMonth; d++) {
      final date =
          '${ym.year}-${ym.month.toString().padLeft(2, '0')}-${d.toString().padLeft(2, '0')}';
      slots.add((day: d, date: date, level: levelMap[date] ?? 'none'));
    }

    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        if (showLegend)
          Wrap(
            spacing: 6,
            runSpacing: 6,
            children: _levels.map((item) {
              final colors = _colors(item.$1);
              return Container(
                constraints: const BoxConstraints(minHeight: 28),
                padding: const EdgeInsets.symmetric(horizontal: 10),
                alignment: Alignment.center,
                decoration: BoxDecoration(
                  color: colors.bg,
                  borderRadius: BorderRadius.circular(999),
                  border: Border.all(color: colors.border),
                ),
                child: Text(
                  item.$2,
                  style: TextStyle(color: colors.fg, fontSize: 12, fontWeight: FontWeight.w800),
                ),
              );
            }).toList(),
          ),
        if (showLegend) const SizedBox(height: 10),
        Row(
          children: _weekdays
              .map(
                (w) => Expanded(
                  child: Text(
                    w,
                    textAlign: TextAlign.center,
                    style: TextStyle(color: theme.textTertiary, fontSize: 11, fontWeight: FontWeight.w800),
                  ),
                ),
              )
              .toList(),
        ),
        const SizedBox(height: 4),
        LayoutBuilder(
          builder: (context, constraints) {
            final gap = 4.0;
            final cellSize = (constraints.maxWidth - gap * 6) / 7;
            return Wrap(
              spacing: gap,
              runSpacing: gap,
              children: [
                for (final slot in slots)
                  SizedBox(
                    width: cellSize,
                    height: cellSize,
                    child: slot == null
                        ? const SizedBox.shrink()
                        : Builder(
                            builder: (_) {
                              final colors = _colors(slot.level);
                              return GestureDetector(
                                onTap: () => onSelectDay?.call(slot.date, slot.level),
                                child: DecoratedBox(
                                  decoration: BoxDecoration(
                                    color: colors.bg,
                                    borderRadius: BorderRadius.circular(8),
                                    border: Border.all(color: colors.border),
                                  ),
                                  child: Center(
                                    child: Text(
                                      '${slot.day}',
                                      style: TextStyle(
                                        color: colors.fg,
                                        fontSize: 13,
                                        fontWeight: FontWeight.w800,
                                      ),
                                    ),
                                  ),
                                ),
                              );
                            },
                          ),
                  ),
              ],
            );
          },
        ),
      ],
    );
  }
}
