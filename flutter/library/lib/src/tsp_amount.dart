import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspAmount extends StatelessWidget {
  const TspAmount({super.key, required this.value, this.symbol = '¥', this.cycle = '', this.symbolAfter = false, this.strikeThrough = false, this.theme = StarPlanetTheme.sky});
  final String symbol;
  final String value;
  final String cycle;
  final bool symbolAfter;
  final bool strikeThrough;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final style = TextStyle(color: theme.textPrimary, decoration: strikeThrough ? TextDecoration.lineThrough : null);
    return Row(crossAxisAlignment: CrossAxisAlignment.end, children: [
      if (!symbolAfter) Text(symbol, style: style.copyWith(fontWeight: FontWeight.w800, fontSize: 18)),
      if (!symbolAfter) const SizedBox(width: 4),
      Text(value, style: style.copyWith(fontSize: 30, fontWeight: FontWeight.w900, height: 32 / 30)),
      if (symbolAfter) ...[const SizedBox(width: 4), Text(symbol, style: style.copyWith(fontWeight: FontWeight.w800, fontSize: 18))],
      if (cycle.isNotEmpty) ...[const SizedBox(width: 4), Padding(padding: const EdgeInsets.only(bottom: 2), child: Text('/$cycle', style: TextStyle(color: theme.textSecondary, fontSize: 13)))],
    ]);
  }
}
