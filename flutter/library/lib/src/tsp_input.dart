import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspInput extends StatelessWidget {
  const TspInput({super.key, required this.controller, this.placeholder = '', this.variant = 'default', this.disabled = false, this.theme = StarPlanetTheme.sky});
  final TextEditingController controller;
  final String placeholder;
  final String variant;
  final bool disabled;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => TextField(
        controller: controller,
        enabled: !disabled,
        style: TextStyle(color: theme.textPrimary),
        decoration: InputDecoration(
          hintText: placeholder,
          filled: true,
          fillColor: theme.surfaceRaised,
          contentPadding: const EdgeInsets.symmetric(horizontal: 14, vertical: 14),
          constraints: const BoxConstraints(minHeight: 48),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(16), borderSide: BorderSide(color: variant == 'error' ? theme.danger : theme.borderDefault)),
          enabledBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(16), borderSide: BorderSide(color: variant == 'error' ? theme.danger : theme.borderDefault)),
          disabledBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(16), borderSide: BorderSide(color: theme.borderDefault)),
          focusedBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(16), borderSide: BorderSide(color: theme.brandPrimary)),
        ),
      );
}
