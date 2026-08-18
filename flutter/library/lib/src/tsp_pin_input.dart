import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspPinInput extends StatelessWidget {
  const TspPinInput({super.key, required this.value, this.cellCount = 4, this.secure = false, this.theme = StarPlanetTheme.sky});
  final String value;
  final int cellCount;
  final bool secure;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final count = cellCount.clamp(4, 6);
    return Row(children: [
      for (var i = 0; i < count; i++) ...[
        if (i > 0) const SizedBox(width: 10),
        Expanded(
          child: Container(
            height: 48,
            alignment: Alignment.center,
            decoration: BoxDecoration(color: theme.surfaceRaised, borderRadius: BorderRadius.circular(14), border: Border.all(color: theme.borderDefault)),
            child: Text(i < value.length ? (secure ? '•' : value[i]) : '', style: TextStyle(color: theme.textPrimary, fontSize: 18, fontWeight: FontWeight.w900)),
          ),
        ),
      ],
    ]);
  }
}
