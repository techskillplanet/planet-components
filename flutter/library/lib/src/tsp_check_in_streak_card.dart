import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Continuous check-in summary.
class TspCheckInStreakCard extends StatelessWidget {
  const TspCheckInStreakCard({
    super.key,
    this.streakDays = 0,
    this.totalDays = 0,
    this.weekProgress = 0,
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onOpen,
  });

  final num streakDays;
  final num totalDays;
  final num weekProgress;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onOpen;

  @override
  Widget build(BuildContext context) {
    final pct = (weekProgress * 100).round().clamp(0, 100);
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: GestureDetector(
        onTap: disabled ? null : onOpen,
        child: Container(
          width: double.infinity,
          padding: const EdgeInsets.all(14),
          decoration: BoxDecoration(
            color: theme.surfaceRaised,
            borderRadius: BorderRadius.circular(18),
            border: Border.all(color: theme.borderDefault),
          ),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('连续打卡', style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900, fontSize: 16)),
              const SizedBox(height: 8),
              Wrap(
                spacing: 16,
                runSpacing: 8,
                children: [
                  Text('连续 $streakDays 天', style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w700)),
                  Text('累计 $totalDays 天', style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w700)),
                  Text('本周 $pct%', style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w700)),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
