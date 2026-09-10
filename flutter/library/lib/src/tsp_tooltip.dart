import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Simple overlay tooltip — tap to toggle when [visible] is null.
class TspTooltip extends StatefulWidget {
  const TspTooltip({
    super.key,
    this.text = '',
    this.placement = 'top',
    this.visible,
    this.theme = StarPlanetTheme.sky,
    required this.child,
  });

  final String text;
  final String placement;
  final bool? visible;
  final StarPlanetTheme theme;
  final Widget child;

  @override
  State<TspTooltip> createState() => _TspTooltipState();
}

class _TspTooltipState extends State<TspTooltip> {
  bool _open = false;

  bool get _shown => widget.visible ?? _open;

  @override
  Widget build(BuildContext context) {
    final bubble = _shown && widget.text.isNotEmpty
        ? Container(
            margin: const EdgeInsets.all(4),
            padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
            decoration: BoxDecoration(
              color: widget.theme.textPrimary,
              borderRadius: BorderRadius.circular(10),
              boxShadow: [
                BoxShadow(
                  color: widget.theme.brandPrimary.withValues(alpha: 0.18),
                  blurRadius: 10,
                  offset: const Offset(0, 4),
                ),
              ],
            ),
            child: Text(
              widget.text,
              style: const TextStyle(color: Colors.white, fontSize: 12, fontWeight: FontWeight.w700),
            ),
          )
        : null;

    Widget stack;
    switch (widget.placement) {
      case 'bottom':
        stack = Column(mainAxisSize: MainAxisSize.min, children: [widget.child, if (bubble != null) bubble]);
      case 'left':
        stack = Row(mainAxisSize: MainAxisSize.min, children: [if (bubble != null) bubble, widget.child]);
      case 'right':
        stack = Row(mainAxisSize: MainAxisSize.min, children: [widget.child, if (bubble != null) bubble]);
      case 'top':
      default:
        stack = Column(mainAxisSize: MainAxisSize.min, children: [if (bubble != null) bubble, widget.child]);
    }

    return GestureDetector(
      onTap: widget.visible == null ? () => setState(() => _open = !_open) : null,
      behavior: HitTestBehavior.opaque,
      child: stack,
    );
  }
}
