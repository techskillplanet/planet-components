import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Available points hero.
class TspBalanceHero extends StatelessWidget {
  const TspBalanceHero({
    super.key,
    this.total = 0,
    this.breakdown,
    this.suffix = '分',
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
  });

  final num total;
  final Map<String, num>? breakdown;
  final String suffix;
  final String variant;
  final StarPlanetTheme theme;

  static const _labels = {
    'balance': '余额',
    'ruleScore': '规则分',
    'streakBonus': '连续奖励',
    'redeemTotal': '已兑换',
  };

  @override
  Widget build(BuildContext context) {
    final compact = variant == 'compact';
    final rows = breakdown?.entries
            .map((e) => (key: e.key, label: _labels[e.key] ?? e.key, value: e.value))
            .toList() ??
        const [];

    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(16),
      decoration: BoxDecoration(
        color: theme.surfaceRaised,
        borderRadius: BorderRadius.circular(20),
        border: Border.all(color: theme.borderDefault),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text('可用积分', style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w700)),
          const SizedBox(height: 4),
          Row(
            crossAxisAlignment: CrossAxisAlignment.baseline,
            textBaseline: TextBaseline.alphabetic,
            children: [
              Text(
                '$total',
                style: TextStyle(
                  color: theme.textPrimary,
                  fontSize: compact ? 28 : 36,
                  fontWeight: FontWeight.w900,
                ),
              ),
              if (suffix.isNotEmpty) ...[
                const SizedBox(width: 6),
                Text(suffix, style: TextStyle(color: theme.textSecondary, fontSize: 16)),
              ],
            ],
          ),
          if (rows.isNotEmpty) ...[
            const SizedBox(height: 12),
            ...rows.map(
              (row) => Padding(
                padding: const EdgeInsets.only(bottom: 6),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Text(row.label, style: TextStyle(color: theme.textSecondary, fontSize: 13)),
                    Text('${row.value}', style: TextStyle(color: theme.textPrimary, fontSize: 13, fontWeight: FontWeight.w800)),
                  ],
                ),
              ),
            ),
          ],
        ],
      ),
    );
  }
}
