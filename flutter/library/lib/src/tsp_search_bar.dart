import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Pill search field.
class TspSearchBar extends StatefulWidget {
  const TspSearchBar({
    super.key,
    this.value = '',
    this.placeholder = 'Search…',
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final String value;
  final String placeholder;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<String>? onChanged;

  @override
  State<TspSearchBar> createState() => _TspSearchBarState();
}

class _TspSearchBarState extends State<TspSearchBar> {
  late final TextEditingController _controller;

  @override
  void initState() {
    super.initState();
    _controller = TextEditingController(text: widget.value);
  }

  @override
  void didUpdateWidget(covariant TspSearchBar oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.value != _controller.text) {
      _controller.value = TextEditingValue(
        text: widget.value,
        selection: TextSelection.collapsed(offset: widget.value.length),
      );
    }
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final theme = widget.theme;
    return Opacity(
      opacity: widget.disabled ? 0.45 : 1,
      child: Container(
        constraints: const BoxConstraints(minHeight: 44),
        padding: const EdgeInsets.symmetric(horizontal: 14),
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          borderRadius: BorderRadius.circular(999),
          border: Border.all(color: widget.variant == 'error' ? theme.danger : theme.borderDefault),
        ),
        child: Row(
          children: [
            Text('⌕', style: TextStyle(color: theme.textTertiary, fontSize: 16, fontWeight: FontWeight.w700)),
            const SizedBox(width: 8),
            Expanded(
              child: TextField(
                controller: _controller,
                enabled: !widget.disabled,
                onChanged: widget.onChanged,
                style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w600, fontSize: 15),
                decoration: InputDecoration(
                  isDense: true,
                  border: InputBorder.none,
                  hintText: widget.placeholder,
                  hintStyle: TextStyle(color: theme.textTertiary, fontWeight: FontWeight.w600),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
