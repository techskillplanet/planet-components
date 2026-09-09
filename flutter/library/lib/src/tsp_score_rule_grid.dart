import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspScoreRule {
  const TspScoreRule({
    this.id,
    this.name = '',
    this.icon,
    this.value = 0,
    this.count = 0,
    this.dailyLimit,
  });

  final Object? id;
  final String name;
  final String? icon;
  final num value;
  final num count;
  final num? dailyLimit;
}

/// Score rule cards with optional +1.
class TspScoreRuleGrid extends StatelessWidget {
  const TspScoreRuleGrid({
    super.key,
    this.rules = const [],
    this.columns = 'auto',
    this.variant = 'default',
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onIncrement,
  });

  final List<TspScoreRule> rules;
  final Object columns;
  final String variant;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<TspScoreRule>? onIncrement;

  int get _colCount {
    if (columns == 1 || columns == '1') return 1;
    if (columns == 2 || columns == '2') return 2;
    return 2;
  }

  @override
  Widget build(BuildContext context) {
    final readOnly = variant == 'readOnly' || disabled;
    final colCount = _colCount;

    return LayoutBuilder(
      builder: (context, constraints) {
        final gap = 12.0;
        final useSingle = columns == 1 || columns == '1' || (columns == 'auto' && constraints.maxWidth < 481);
        final cols = useSingle ? 1 : colCount;
        final cardWidth = cols == 1 ? constraints.maxWidth : (constraints.maxWidth - gap) / 2;

        return Wrap(
          spacing: gap,
          runSpacing: gap,
          children: rules.map((rule) {
            final value = rule.value;
            final count = rule.count;
            final limit = rule.dailyLimit;
            final atLimit = limit != null && count >= limit;
            final valueText = value > 0 ? '+$value' : '$value';
            final meta = limit == null ? '已记 $count' : '已记 $count/$limit';

            return SizedBox(
              width: cardWidth,
              child: Container(
                padding: const EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: theme.surfaceRaised,
                  borderRadius: BorderRadius.circular(18),
                  border: Border.all(color: theme.borderDefault),
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Row(
                      children: [
                        if (rule.icon != null) ...[
                          Text(rule.icon!),
                          const SizedBox(width: 6),
                        ],
                        Expanded(
                          child: Text(
                            rule.name,
                            style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800, fontSize: 13),
                          ),
                        ),
                      ],
                    ),
                    const SizedBox(height: 8),
                    Text(
                      valueText,
                      style: TextStyle(
                        color: value >= 0 ? theme.success : theme.danger,
                        fontSize: 22,
                        fontWeight: FontWeight.w900,
                      ),
                    ),
                    const SizedBox(height: 4),
                    Text(meta, style: TextStyle(color: theme.textTertiary, fontSize: 12)),
                    if (!readOnly) ...[
                      const SizedBox(height: 8),
                      Opacity(
                        opacity: (atLimit || disabled) ? .4 : 1,
                        child: GestureDetector(
                          onTap: (atLimit || disabled) ? null : () => onIncrement?.call(rule),
                          child: Container(
                            width: double.infinity,
                            constraints: const BoxConstraints(minHeight: 44),
                            alignment: Alignment.center,
                            decoration: BoxDecoration(
                              color: theme.selectedFill,
                              borderRadius: BorderRadius.circular(999),
                              border: Border.all(color: theme.borderDefault),
                            ),
                            child: Text(
                              '+1',
                              style: TextStyle(color: theme.brandDark, fontWeight: FontWeight.w900),
                            ),
                          ),
                        ),
                      ),
                    ],
                  ],
                ),
              ),
            );
          }).toList(),
        );
      },
    );
  }
}
