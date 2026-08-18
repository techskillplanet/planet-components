import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspStepper extends StatelessWidget {
  const TspStepper({super.key, required this.stepCount, required this.currentStep, this.theme = StarPlanetTheme.sky});
  final int stepCount;
  final int currentStep;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final count = stepCount.clamp(3, 5);
    final current = currentStep.clamp(1, count);
    return Row(children: [
      for (var step = 1; step <= count; step++) ...[
        Container(
          width: 32,
          height: 32,
          alignment: Alignment.center,
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            color: step < current ? theme.success : step == current ? theme.brandPrimary : theme.surfaceRaised,
            border: Border.all(color: step == current ? theme.brandPrimary : theme.borderDefault),
          ),
          child: Text(step < current ? '✓' : '$step', style: TextStyle(color: step <= current ? Colors.white : theme.textTertiary, fontWeight: FontWeight.w800)),
        ),
        if (step < count) Expanded(child: Container(height: 2, color: step < current ? theme.success : theme.borderDefault)),
      ],
    ]);
  }
}
