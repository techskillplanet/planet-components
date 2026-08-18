import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspOptionSheet extends StatelessWidget {
  const TspOptionSheet({
    super.key,
    required this.options,
    required this.selectedIndex,
    this.title = '请选择',
    this.theme = StarPlanetTheme.sky,
    this.onSelect,
    this.onCancel,
  });

  final String title;
  final List<String> options;
  final int selectedIndex;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onSelect;
  final VoidCallback? onCancel;

  static Future<void> show(
    BuildContext context, {
    required List<String> options,
    required int selectedIndex,
    String title = '请选择',
    StarPlanetTheme theme = StarPlanetTheme.sky,
    ValueChanged<int>? onSelect,
  }) {
    return showModalBottomSheet<void>(
      context: context,
      backgroundColor: Colors.transparent,
      barrierColor: const Color(0x52173A62),
      builder: (sheetContext) => TspOptionSheet(
        title: title,
        options: options,
        selectedIndex: selectedIndex,
        theme: theme,
        onCancel: () => Navigator.pop(sheetContext),
        onSelect: (index) {
          Navigator.pop(sheetContext);
          onSelect?.call(index);
        },
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Material(
      color: Colors.transparent,
      child: Container(
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          borderRadius: const BorderRadius.vertical(top: Radius.circular(24)),
          border: Border.all(color: theme.borderDefault),
        ),
        padding: EdgeInsets.only(bottom: MediaQuery.of(context).padding.bottom + 16),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            SizedBox(
              height: 52,
              child: Row(
                children: [
                  TextButton(
                    onPressed: onCancel,
                    child: Text('取消', style: TextStyle(color: theme.brandPrimary, fontWeight: FontWeight.w800)),
                  ),
                  Expanded(
                    child: Text(title, textAlign: TextAlign.center, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
                  ),
                  const SizedBox(width: 64),
                ],
              ),
            ),
            for (var i = 0; i < options.length; i++)
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
                child: Material(
                  color: i == selectedIndex ? theme.brandPrimary : Colors.transparent,
                  borderRadius: BorderRadius.circular(14),
                  child: InkWell(
                    borderRadius: BorderRadius.circular(14),
                    onTap: () => onSelect?.call(i),
                    child: SizedBox(
                      height: 48,
                      child: Row(
                        children: [
                          SizedBox(
                            width: 28,
                            child: Text(
                              i == selectedIndex ? '✓' : '',
                              textAlign: TextAlign.center,
                              style: TextStyle(color: i == selectedIndex ? Colors.white : theme.textPrimary, fontWeight: FontWeight.w800),
                            ),
                          ),
                          Expanded(
                            child: Text(
                              options[i],
                              style: TextStyle(
                                color: i == selectedIndex ? Colors.white : theme.textPrimary,
                                fontWeight: FontWeight.w800,
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
          ],
        ),
      ),
    );
  }
}

/// Flat Switch — Label + Control > Track > Thumb > Spinner. No inner ON/OFF.
/// Semantic API matches `05-switch-contract.md` (`onChanged` ≡ `onChange`).
