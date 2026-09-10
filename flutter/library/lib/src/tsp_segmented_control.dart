import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Segmented control. Options may be [String] or map-like `{label, value}`.
class TspSegmentedControl extends StatelessWidget {
  const TspSegmentedControl({
    super.key,
    this.options = const [],
    this.selectedIndex = 0,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onSelect,
  });

  final List<dynamic> options;
  final int selectedIndex;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;

  /// Called with (index, label, value).
  final void Function(int index, String label, String value)? onSelect;

  static (String label, String value) _normalize(dynamic opt, int index) {
    if (opt is String) return (opt, opt);
    if (opt is Map) {
      final label = '${opt['label'] ?? opt['title'] ?? opt['text'] ?? opt['value'] ?? ''}';
      final value = '${opt['value'] ?? index}';
      return (label, value);
    }
    return ('$opt', '$index');
  }

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: disabled ? 0.45 : 1,
      child: Container(
        width: double.infinity,
        padding: const EdgeInsets.all(4),
        decoration: BoxDecoration(
          color: variant == 'raised' ? theme.surfaceRaised : theme.surfaceSubtle,
          borderRadius: BorderRadius.circular(999),
          border: Border.all(color: theme.borderDefault),
        ),
        child: Row(
          children: [
            for (var i = 0; i < options.length; i++)
              Expanded(
                child: Builder(
                  builder: (context) {
                    final item = _normalize(options[i], i);
                    final selected = i == selectedIndex;
                    return GestureDetector(
                      onTap: disabled ? null : () => onSelect?.call(i, item.$1, item.$2),
                      child: Container(
                        constraints: const BoxConstraints(minHeight: 36),
                        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 8),
                        alignment: Alignment.center,
                        decoration: ShapeDecoration(
                          color: selected ? theme.brandPrimary : Colors.transparent,
                          shape: const StadiumBorder(),
                        ),
                        child: Text(
                          item.$1,
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: TextStyle(
                            color: selected ? Colors.white : theme.textSecondary,
                            fontWeight: FontWeight.w800,
                            fontSize: 14,
                          ),
                        ),
                      ),
                    );
                  },
                ),
              ),
          ],
        ),
      ),
    );
  }
}
