import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Themed HH:mm time field. Opens platform time picker on tap.
class TspTimePicker extends StatelessWidget {
  const TspTimePicker({
    super.key,
    this.value = '',
    this.placeholder = 'HH:mm',
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final String value;
  final String placeholder;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<String>? onChanged;

  static TimeOfDay? _parse(String? raw) {
    if (raw == null || raw.isEmpty) return null;
    final parts = raw.split(':');
    if (parts.length < 2) return null;
    final h = int.tryParse(parts[0]);
    final m = int.tryParse(parts[1]);
    if (h == null || m == null) return null;
    if (h < 0 || h > 23 || m < 0 || m > 59) return null;
    return TimeOfDay(hour: h, minute: m);
  }

  static String _format(TimeOfDay t) {
    final h = t.hour.toString().padLeft(2, '0');
    final m = t.minute.toString().padLeft(2, '0');
    return '$h:$m';
  }

  Future<void> _pick(BuildContext context) async {
    final initial = _parse(value) ?? TimeOfDay.now();
    final picked = await showTimePicker(
      context: context,
      initialTime: initial,
      builder: (context, child) {
        return Theme(
          data: Theme.of(context).copyWith(
            colorScheme: ColorScheme.light(
              primary: theme.brandPrimary,
              onPrimary: Colors.white,
              surface: theme.surfaceRaised,
              onSurface: theme.textPrimary,
            ),
          ),
          child: child!,
        );
      },
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
