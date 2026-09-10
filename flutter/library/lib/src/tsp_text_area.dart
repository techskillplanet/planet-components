import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Multi-line text field.
class TspTextArea extends StatefulWidget {
  const TspTextArea({
    super.key,
    this.value = '',
    this.placeholder = '',
    this.rows = 3,
    this.maxLength,
    this.disabled = false,
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final String value;
  final String placeholder;
  final int rows;
  final int? maxLength;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<String>? onChanged;

  @override
  State<TspTextArea> createState() => _TspTextAreaState();
}

class _TspTextAreaState extends State<TspTextArea> {
  late final TextEditingController _controller;

  @override
  void initState() {
    super.initState();
    _controller = TextEditingController(text: widget.value);
  }

  @override
  void didUpdateWidget(covariant TspTextArea oldWidget) {
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
    final isDisabled = widget.disabled || widget.variant == 'disabled';
    final borderColor = widget.variant == 'error' ? theme.danger : theme.borderDefault;
    final minLines = widget.rows.clamp(1, 20);

    return Opacity(
      opacity: isDisabled ? 0.45 : 1,
      child: TextField(
        controller: _controller,
        enabled: !isDisabled,
        maxLines: null,
        minLines: minLines,
        maxLength: widget.maxLength,
        onChanged: widget.onChanged,
        style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w600, fontSize: 15, height: 1.4),
        decoration: InputDecoration(
          hintText: widget.placeholder,
          hintStyle: TextStyle(color: theme.textTertiary, fontWeight: FontWeight.w600),
          filled: true,
          fillColor: theme.surfaceRaised,
          counterText: widget.maxLength == null ? '' : null,
          contentPadding: const EdgeInsets.symmetric(horizontal: 14, vertical: 12),
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(16),
            borderSide: BorderSide(color: borderColor),
          ),
          enabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(16),
            borderSide: BorderSide(color: borderColor),
          ),
          disabledBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(16),
            borderSide: BorderSide(color: theme.borderDefault),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(16),
            borderSide: BorderSide(color: widget.variant == 'error' ? theme.danger : theme.brandPrimary, width: 1.5),
          ),
        ),
      ),
    );
  }
}
