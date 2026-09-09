import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspRedeemItem {
  const TspRedeemItem({
    this.id,
    this.name = '',
    this.icon,
    this.cost = 0,
  });

  final Object? id;
  final String name;
  final String? icon;
  final num cost;
}

/// Redeem item cards.
class TspRedeemCardGrid extends StatelessWidget {
  const TspRedeemCardGrid({
    super.key,
    this.items = const [],
    this.availablePoints = 0,
    this.frozen = false,
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onRedeem,
  });

  final List<TspRedeemItem> items;
  final num availablePoints;
  final bool frozen;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<TspRedeemItem>? onRedeem;

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        final gap = 12.0;
        final singleCol = constraints.maxWidth <= 480;
        final cardWidth = singleCol ? constraints.maxWidth : (constraints.maxWidth - gap) / 2;

        return Wrap(
          spacing: gap,
          runSpacing: gap,
          children: [
            if (frozen)
              SizedBox(
                width: constraints.maxWidth,
                child: Container(
                  padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                  decoration: BoxDecoration(
                    color: theme.activeFill,
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Text(
                    '今日已冻结，暂不可兑换',
                    style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800, fontSize: 13),
                  ),
                ),
              ),
            ...items.map((item) {
              final cost = item.cost;
              final insufficient = availablePoints < cost;
              final blocked = frozen || disabled || insufficient;
              return SizedBox(
                width: cardWidth,
                child: Opacity(
                  opacity: blocked ? .5 : 1,
                  child: GestureDetector(
                    onTap: blocked ? null : () => onRedeem?.call(item),
                    child: Container(
                      constraints: const BoxConstraints(minHeight: 120),
                      padding: const EdgeInsets.all(14),
                      decoration: BoxDecoration(
                        color: theme.surfaceRaised,
                        borderRadius: BorderRadius.circular(18),
                        border: Border.all(color: theme.borderDefault, width: 2),
                      ),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(item.icon ?? '🎁', style: const TextStyle(fontSize: 28)),
                          const SizedBox(height: 8),
                          Text(
                            item.name,
                            style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800),
                          ),
                          const SizedBox(height: 4),
                          Text(
                            '$cost 分',
                            style: TextStyle(color: theme.brandPrimary, fontWeight: FontWeight.w900),
                          ),
                          if (insufficient && !frozen) ...[
                            const SizedBox(height: 4),
                            Text('积分不足', style: TextStyle(color: theme.danger, fontSize: 12)),
                          ],
                        ],
                      ),
                    ),
                  ),
                ),
              );
            }),
          ],
        );
      },
    );
  }
}
