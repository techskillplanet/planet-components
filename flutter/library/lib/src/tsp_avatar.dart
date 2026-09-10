import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Circular avatar — initials or network/asset image.
class TspAvatar extends StatelessWidget {
  const TspAvatar({
    super.key,
    this.text = '',
    this.src,
    this.size = 'md',
    this.variant = 'default',
    this.theme = StarPlanetTheme.sky,
  });

  final String text;
  final String? src;
  final String size;
  final String variant;
  final StarPlanetTheme theme;

  double get _dim {
    switch (size) {
      case 'sm':
        return 32;
      case 'lg':
        return 56;
      default:
        return 40;
    }
  }

  String get _initials {
    final t = text.trim();
    if (t.isEmpty) return '?';
    final parts = t.split(RegExp(r'\s+')).where((p) => p.isNotEmpty).toList();
    if (parts.length >= 2) {
      return (parts[0][0] + parts[1][0]).toUpperCase();
    }
    return t.substring(0, t.length >= 2 ? 2 : 1).toUpperCase();
  }

  Color get _bg {
    switch (variant) {
      case 'primary':
        return theme.brandPrimary;
      case 'subtle':
        return theme.brandSubtle;
      default:
        return theme.surfaceSubtle;
    }
  }

  Color get _fg {
    switch (variant) {
      case 'primary':
        return Colors.white;
      case 'subtle':
        return theme.brandPrimary;
      default:
        return theme.textPrimary;
    }
  }

  @override
  Widget build(BuildContext context) {
    final dim = _dim;
    final hasImage = src != null && src!.isNotEmpty;
    return Semantics(
      label: text.isEmpty ? 'Avatar' : text,
      image: true,
      child: Container(
        width: dim,
        height: dim,
        alignment: Alignment.center,
        decoration: BoxDecoration(
          shape: BoxShape.circle,
          color: _bg,
          border: Border.all(color: theme.borderDefault, width: 1.5),
        ),
        clipBehavior: Clip.antiAlias,
        child: hasImage
            ? Image.network(
                src!,
                width: dim,
                height: dim,
                fit: BoxFit.cover,
                errorBuilder: (_, __, ___) => _Initials(text: _initials, color: _fg, fontSize: dim * 0.38),
              )
            : _Initials(text: _initials, color: _fg, fontSize: dim * 0.38),
      ),
    );
  }
}

class _Initials extends StatelessWidget {
  const _Initials({required this.text, required this.color, required this.fontSize});
  final String text;
  final Color color;
  final double fontSize;

  @override
  Widget build(BuildContext context) {
    return Text(
      text,
      style: TextStyle(color: color, fontSize: fontSize, fontWeight: FontWeight.w800, height: 1),
    );
  }
}
