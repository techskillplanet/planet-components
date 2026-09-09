import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Contract name is `default`; Dart uses `standard` because `default` is reserved.
enum TspButtonVariant { primary, standard, danger, text, link }

class TspButton extends StatefulWidget {
  const TspButton({
    super.key,
    required this.text,
    this.variant = TspButtonVariant.standard,
    this.disabled = false,
    this.loading = false,
    this.fullWidth = true,
    this.theme = StarPlanetTheme.sky,
    this.onTap,
  });

  final String text;
  final TspButtonVariant variant;
  final bool disabled;
  final bool loading;
  final bool fullWidth;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  State<TspButton> createState() => _TspButtonState();
}

class _TspButtonState extends State<TspButton> {
  bool _pressed = false;

  bool get _inert => widget.disabled || widget.loading;

  @override
  Widget build(BuildContext context) {
    const faceHeight = 46.0;
    const shadowLift = 5.0;
    const pressedDrop = 2.0;
    final theme = widget.theme;
    final isFlat = widget.variant == TspButtonVariant.text || widget.variant == TspButtonVariant.link;
    final faceColor = switch (widget.variant) {
      TspButtonVariant.primary => theme.brandPrimary,
      TspButtonVariant.danger => theme.danger,
      TspButtonVariant.text || TspButtonVariant.link => Colors.transparent,
      TspButtonVariant.standard => theme.surfaceRaised,
    };
    final textColor = switch (widget.variant) {
      TspButtonVariant.primary || TspButtonVariant.danger => Colors.white,
      TspButtonVariant.text || TspButtonVariant.link => theme.brandPrimary,
      TspButtonVariant.standard => theme.textPrimary,
    };
    final borderColor = isFlat ? Colors.transparent : theme.borderDefault;
    final faceTop = (!isFlat && _pressed) ? pressedDrop : 0.0;
    final button = Opacity(
      opacity: widget.disabled ? .45 : (widget.loading ? .7 : 1),
      child: GestureDetector(
        onTap: _inert ? null : widget.onTap,
        onTapDown: _inert || isFlat ? null : (_) => setState(() => _pressed = true),
        onTapUp: _inert || isFlat ? null : (_) => setState(() => _pressed = false),
        onTapCancel: _inert || isFlat ? null : () => setState(() => _pressed = false),
        child: SizedBox(
          height: isFlat ? faceHeight : faceHeight + shadowLift,
          child: Stack(
            children: [
              if (!isFlat)
                Positioned(
                  left: 0,
                  right: 0,
                  bottom: 0,
                  height: faceHeight,
                  child: DecoratedBox(
                    decoration: ShapeDecoration(color: theme.borderDefault, shape: const StadiumBorder()),
                  ),
                ),
              Positioned(
                left: 0,
                right: 0,
                top: faceTop,
                height: faceHeight,
                child: DecoratedBox(
                  decoration: ShapeDecoration(
                    color: faceColor,
                    shape: StadiumBorder(side: BorderSide(color: borderColor)),
                  ),
                  child: Center(
                    child: Row(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        if (widget.loading) ...[
                          SizedBox(
                            width: 16,
                            height: 16,
                            child: CircularProgressIndicator(strokeWidth: 2, color: textColor),
                          ),
                          const SizedBox(width: 8),
                        ],
                        Text(
                          widget.text,
                          style: TextStyle(color: textColor, fontWeight: FontWeight.w800, fontSize: 15, height: 20 / 15),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
    return widget.fullWidth ? SizedBox(width: double.infinity, child: button) : button;
  }
}
