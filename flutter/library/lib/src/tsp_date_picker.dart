import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Themed YYYY-MM-DD date field. Opens platform date picker on tap.
class TspDatePicker extends StatelessWidget {
  const TspDatePicker({
    super.key,
    this.value = '',
    this.min,
    this.max,
    this.placeholder = 'YYYY-MM-DD',
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final String value;
  final String? min;
  final String? max;
  final String placeholder;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<String>? onChanged;

  static DateTime? _parse(String? raw) {
    if (raw == null || raw.isEmpty) return null;
    final parts = raw.split('-');
    if (parts.length != 3) return null;
    final y = int.tryParse(parts[0]);
    final m = int.tryParse(parts[1]);
    final d = int.tryParse(parts[2]);
    if (y == null || m == null || d == null) return null;
    return DateTime(y, m, d);
  }

  static String _format(DateTime date) {
    final m = date.month.toString().padLeft(2, '0');
    final d = date.day.toString().padLeft(2, '0');
    return '${date.year}-$m-$d';
  }

  Future<void> _pick(BuildContext context) async {
    final now = DateTime.now();
    final initial = _parse(value) ?? now;
    final first = _parse(min) ?? DateTime(1900);
    final last = _parse(max) ?? DateTime(2100);
    final picked = await showDatePicker(
      context: context,
      initialDate: initial.isBefore(first) ? first : (initial.isAfter(last) ? last : initial),
      firstDate: first,
      lastDate: last,
    );
    if (picked != null) onChanged?.call(_format(picked));
  }

  @override
  Widget build(BuildContext context) {
    final label = value.isEmpty ? placeholder : value;
    final isPlaceholder = value.isEmpty;
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: GestureDetector(
        onTap: disabled ? null : () => _pick(context),
        child: Container(
          width: double.infinity,
          constraints: const BoxConstraints(minHeight: 48),
          padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 14),
          decoration: BoxDecoration(
            color: theme.surfaceRaised,
            borderRadius: BorderRadius.circular(16),
            border: Border.all(color: theme.borderDefault, width: 2),
          ),
          child: Text(
            label,
            style: TextStyle(
              color: isPlaceholder ? theme.textTertiary : theme.textPrimary,
              fontWeight: FontWeight.w700,
              fontSize: 15,
            ),
          ),
        ),
      ),
    );
  }
}
