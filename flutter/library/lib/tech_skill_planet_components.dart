import 'package:flutter/material.dart';

/// Sky Planet theme — values aligned with RN `theme.js` / shared tokens.
class StarPlanetTheme {
  const StarPlanetTheme({
    this.pageStart = const Color(0xFFDDF4FF),
    this.pageEnd = const Color(0xFFF9FDFF),
    this.textPrimary = const Color(0xFF173A62),
    this.textSecondary = const Color(0xFF365D82),
    this.textTertiary = const Color(0xFF7895AE),
    this.surfaceRaised = Colors.white,
    this.borderDefault = const Color(0xFFC8EAFF),
    this.brandPrimary = const Color(0xFF31A8FF),
    this.brandDark = const Color(0xFF1479D6),
    this.success = const Color(0xFF43CFC7),
    this.warning = const Color(0xFFFFD166),
    this.selectedFill = const Color(0xFFE8FDF7),
    this.activeFill = const Color(0xFFFFF7D7),
    this.danger = const Color(0xFFFF6B7A),
    this.switchOffBackground = const Color(0xFFC8EAFF),
    this.switchOffBorder = const Color(0xFFC8EAFF),
    this.switchOffText = const Color(0xFF365D82),
    this.switchOnBackground = const Color(0xFF31A8FF),
    this.switchOnBorder = const Color(0xFF31A8FF),
    this.switchOnText = Colors.white,
    this.switchHandleBackground = const Color(0xFFFEFFFF),
    this.switchHandleBorder = const Color(0xFFC8EAFF),
    this.switchHandleCheckedBorder = const Color(0xFF31A8FF),
    this.switchLoadingSpinner = const Color(0xFF31A8FF),
  });

  final Color pageStart;
  final Color pageEnd;
  final Color textPrimary;
  final Color textSecondary;
  final Color textTertiary;
  final Color surfaceRaised;
  final Color borderDefault;
  final Color brandPrimary;
  final Color brandDark;
  final Color success;
  final Color warning;
  final Color selectedFill;
  final Color activeFill;
  final Color danger;
  final Color switchOffBackground;
  final Color switchOffBorder;
  final Color switchOffText;
  final Color switchOnBackground;
  final Color switchOnBorder;
  final Color switchOnText;
  final Color switchHandleBackground;
  final Color switchHandleBorder;
  final Color switchHandleCheckedBorder;
  final Color switchLoadingSpinner;

  static const sky = StarPlanetTheme();
  static const night = StarPlanetTheme(
    pageStart: Color(0xFF0F1A2E),
    pageEnd: Color(0xFF141E32),
    textPrimary: Color(0xFFE8F4FF),
    textSecondary: Color(0xFFB8D0E8),
    textTertiary: Color(0xFF7A94B0),
    surfaceRaised: Color(0xFF1E2D45),
    borderDefault: Color(0xFF2A3F5C),
    selectedFill: Color(0xFF1E3A52),
    activeFill: Color(0xFF3A3020),
  );
  static const mint = StarPlanetTheme(
    pageStart: Color(0xFFDFFAF2),
    pageEnd: Color(0xFFF8FFFC),
    textPrimary: Color(0xFF123F3A),
    textSecondary: Color(0xFF2F6B63),
    textTertiary: Color(0xFF6C938D),
    borderDefault: Color(0xFFBDEFE2),
    brandPrimary: Color(0xFF20BFA9),
    brandDark: Color(0xFF0C8F7E),
    success: Color(0xFF35C58B),
    selectedFill: Color(0xFFE6FFF4),
  );
}

enum TspButtonVariant { primary, standard, danger, text, link }
enum TspAlertVariant { info, success, warning, error }

class TspButton extends StatefulWidget {
  const TspButton({
    super.key,
    required this.text,
    this.variant = TspButtonVariant.standard,
    this.disabled = false,
    this.fullWidth = true,
    this.theme = StarPlanetTheme.sky,
    this.onTap,
  });

  final String text;
  final TspButtonVariant variant;
  final bool disabled;
  final bool fullWidth;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  State<TspButton> createState() => _TspButtonState();
}

class _TspButtonState extends State<TspButton> {
  bool _pressed = false;

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
      opacity: widget.disabled ? .45 : 1,
      child: GestureDetector(
        onTap: widget.disabled ? null : widget.onTap,
        onTapDown: widget.disabled || isFlat ? null : (_) => setState(() => _pressed = true),
        onTapUp: widget.disabled || isFlat ? null : (_) => setState(() => _pressed = false),
        onTapCancel: widget.disabled || isFlat ? null : () => setState(() => _pressed = false),
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
                    child: Text(
                      widget.text,
                      style: TextStyle(color: textColor, fontWeight: FontWeight.w800, fontSize: 15, height: 20 / 15),
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

class TspCard extends StatelessWidget {
  const TspCard({super.key, required this.child, this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky});
  final Widget child;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: Container(
        width: double.infinity,
        padding: const EdgeInsets.all(20),
        decoration: BoxDecoration(
          color: selected ? theme.selectedFill : theme.surfaceRaised,
          borderRadius: BorderRadius.circular(28),
          border: Border.all(color: selected ? theme.success : theme.borderDefault),
        ),
        child: child,
      ),
    );
  }
}

class TspAlert extends StatelessWidget {
  const TspAlert({super.key, required this.title, required this.message, this.variant = TspAlertVariant.info, this.theme = StarPlanetTheme.sky});
  final String title;
  final String message;
  final TspAlertVariant variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final fill = switch (variant) {
      TspAlertVariant.success => theme.selectedFill,
      TspAlertVariant.warning => theme.activeFill,
      TspAlertVariant.error => theme.danger.withValues(alpha: .12),
      TspAlertVariant.info => theme.pageEnd,
    };
    final stroke = switch (variant) {
      TspAlertVariant.success => theme.success,
      TspAlertVariant.warning => theme.warning,
      TspAlertVariant.error => theme.danger,
      TspAlertVariant.info => theme.borderDefault,
    };
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(color: fill, borderRadius: BorderRadius.circular(18), border: Border.all(color: stroke)),
      child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
        const SizedBox(height: 6),
        Text(message, style: TextStyle(color: theme.textSecondary, fontSize: 13)),
      ]),
    );
  }
}

class TspBadge extends StatelessWidget {
  const TspBadge({super.key, required this.text, this.variant = 'default', this.disabled = false, this.theme = StarPlanetTheme.sky});
  final String text;
  final String variant;
  final bool disabled;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final fill = switch (variant) {
      'primary' => theme.brandPrimary,
      'success' => theme.success,
      'warning' => theme.warning,
      'danger' => theme.danger,
      _ => theme.pageEnd,
    };
    final color = ['primary', 'success', 'danger'].contains(variant) ? Colors.white : theme.textPrimary;
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: Container(
        constraints: const BoxConstraints(minHeight: 28),
        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
        alignment: Alignment.center,
        decoration: ShapeDecoration(color: fill, shape: const StadiumBorder()),
        child: Text(text, style: TextStyle(color: color, fontWeight: FontWeight.w800, fontSize: 13, height: 16 / 13)),
      ),
    );
  }
}

class TspChip extends StatelessWidget {
  const TspChip({super.key, required this.text, this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final String text;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => GestureDetector(
        onTap: disabled ? null : onTap,
        child: Opacity(
          opacity: disabled ? .45 : 1,
          child: Container(
            padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 8),
            decoration: ShapeDecoration(
              color: selected ? theme.selectedFill : theme.surfaceRaised,
              shape: StadiumBorder(side: BorderSide(color: selected ? theme.success : theme.borderDefault)),
            ),
            child: Text(text, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
          ),
        ),
      );
}

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

class TspSelect extends StatelessWidget {
  const TspSelect({super.key, required this.options, required this.selectedIndex, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onSelect});
  final List<String> options;
  final int selectedIndex;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onSelect;

  @override
  Widget build(BuildContext context) {
    final index = options.isEmpty ? 0 : selectedIndex.clamp(0, options.length - 1);
    final label = options.isEmpty ? '' : options[index];
    return Opacity(
      opacity: disabled ? .45 : 1,
      child: GestureDetector(
        onTap: disabled
            ? null
            : () => TspOptionSheet.show(
                  context,
                  options: options,
                  selectedIndex: index,
                  theme: theme,
                  onSelect: onSelect,
                ),
        child: Container(
          constraints: const BoxConstraints(minHeight: 48),
          padding: const EdgeInsets.symmetric(horizontal: 14),
          decoration: BoxDecoration(
            color: theme.surfaceRaised,
            borderRadius: BorderRadius.circular(16),
            border: Border.all(color: theme.borderDefault),
          ),
          child: Row(
            children: [
              Expanded(child: Text(label, style: TextStyle(color: theme.textPrimary))),
              Text('⌄', style: TextStyle(color: theme.textTertiary, fontSize: 16)),
            ],
          ),
        ),
      ),
    );
  }
}

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
class TspSwitch extends StatefulWidget {
  const TspSwitch({
    super.key,
    required this.checked,
    this.text = '',
    this.checkedText = 'ON',
    this.uncheckedText = 'OFF',
    this.loading = false,
    this.disabled = false,
    this.variant = 'md',
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final bool checked;
  final String text;
  final String checkedText;
  final String uncheckedText;
  final bool loading;
  final bool disabled;
  final String variant;
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onChanged;

  @override
  State<TspSwitch> createState() => _TspSwitchState();
}

class _TspSwitchState extends State<TspSwitch> with TickerProviderStateMixin {
  late final AnimationController _thumb;
  late final AnimationController _spin;

  bool get _isSm => widget.variant == 'sm' || widget.variant == 'small';
  double get _width => _isSm ? 40 : 52;
  double get _height => _isSm ? 22 : 28;
  double get _handle => _isSm ? 18 : 24;
  double get _inset => 2;
  double get _travel => _width - _handle - _inset * 2;

  @override
  void initState() {
    super.initState();
    _thumb = AnimationController(vsync: this, duration: const Duration(milliseconds: 180), value: widget.checked ? 1 : 0);
    _spin = AnimationController(vsync: this, duration: const Duration(milliseconds: 700));
    if (widget.loading) _spin.repeat();
  }

  @override
  void didUpdateWidget(covariant TspSwitch oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (oldWidget.checked != widget.checked) {
      _thumb.animateTo(widget.checked ? 1 : 0, curve: Curves.easeOut);
    }
    if (oldWidget.loading != widget.loading) {
      if (widget.loading) {
        _spin.repeat();
      } else {
        _spin.stop();
        _spin.value = 0;
      }
    }
  }

  @override
  void dispose() {
    _thumb.dispose();
    _spin.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final theme = widget.theme;
    final blocked = widget.disabled || widget.loading;
    final trackBg = widget.checked ? theme.brandPrimary : theme.borderDefault;
    final spinnerColor = widget.checked ? Colors.white : theme.brandDark;
    final controlOpacity = widget.disabled ? 0.5 : (widget.loading ? 0.7 : 1.0);

    return GestureDetector(
      onTap: blocked ? null : () => widget.onChanged?.call(!widget.checked),
      child: Row(
        children: [
          if (widget.text.isNotEmpty)
            Expanded(
              child: Text(
                widget.text,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800, fontSize: 14),
              ),
            ),
          if (widget.text.isNotEmpty) const SizedBox(width: 12),
          Opacity(
            opacity: controlOpacity,
            child: AnimatedBuilder(
              animation: Listenable.merge([_thumb, _spin]),
              builder: (context, _) {
                final dx = _travel * _thumb.value;
                return SizedBox(
                  width: _width,
                  height: _height,
                  child: DecoratedBox(
                    decoration: BoxDecoration(
                      color: trackBg,
                      borderRadius: BorderRadius.circular(999),
                    ),
                    child: Stack(
                      children: [
                        Positioned(
                          left: _inset + dx,
                          top: _inset,
                          width: _handle,
                          height: _handle,
                          child: DecoratedBox(
                            decoration: const BoxDecoration(
                              color: Colors.white,
                              shape: BoxShape.circle,
                            ),
                            child: widget.loading
                                ? Center(
                                    child: SizedBox(
                                      width: _handle * 0.5,
                                      height: _handle * 0.5,
                                      child: CircularProgressIndicator(
                                        strokeWidth: 2,
                                        color: spinnerColor,
                                      ),
                                    ),
                                  )
                                : null,
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class TspProgress extends StatelessWidget {
  const TspProgress({super.key, required this.progress, this.variant = 'primary', this.theme = StarPlanetTheme.sky});
  final double progress;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final fill = switch (variant) {
      'success' => theme.success,
      'warning' => theme.warning,
      'danger' => theme.danger,
      _ => theme.brandPrimary,
    };
    return ClipRRect(
      borderRadius: BorderRadius.circular(999),
      child: SizedBox(
        height: 10,
        child: LinearProgressIndicator(
          minHeight: 10,
          value: (progress / 100).clamp(0, 1),
          backgroundColor: theme.borderDefault,
          color: fill,
        ),
      ),
    );
  }
}

class TspTopBar extends StatelessWidget {
  const TspTopBar({super.key, required this.title, this.showBack = false, this.backgroundColor, this.theme = StarPlanetTheme.sky, this.onBack});
  final String title;
  final bool showBack;
  final Color? backgroundColor;
  final StarPlanetTheme theme;
  final VoidCallback? onBack;

  @override
  Widget build(BuildContext context) {
    final topInset = MediaQuery.of(context).padding.top;
    return Container(
      width: double.infinity,
      padding: EdgeInsets.only(top: topInset),
      decoration: BoxDecoration(
        color: backgroundColor ?? theme.surfaceRaised,
        border: Border(bottom: BorderSide(color: theme.borderDefault)),
      ),
      child: SizedBox(
        height: 56,
        child: Stack(
          alignment: Alignment.center,
          children: [
            if (showBack)
              Positioned(
                left: 0,
                top: 0,
                bottom: 0,
                width: 44,
                child: GestureDetector(
                  behavior: HitTestBehavior.opaque,
                  onTap: onBack ?? () => Navigator.maybePop(context),
                  child: Center(child: Text('‹', style: TextStyle(color: theme.brandPrimary, fontSize: 28, height: 1, fontWeight: FontWeight.w400))),
                ),
              ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 48),
              child: Text(
                title,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                textAlign: TextAlign.center,
                style: TextStyle(color: theme.textPrimary, fontSize: 20, fontWeight: FontWeight.w900),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class TspTabItem {
  const TspTabItem({required this.key, required this.icon, required this.title});
  final String key;
  final IconData icon;
  final String title;
}

class TspBottomTab extends StatelessWidget {
  const TspBottomTab({super.key, required this.tabs, required this.selectedKey, this.theme = StarPlanetTheme.sky, this.onSelect});
  final List<TspTabItem> tabs;
  final String selectedKey;
  final StarPlanetTheme theme;
  final ValueChanged<String>? onSelect;

  @override
  Widget build(BuildContext context) => Container(
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          border: Border(top: BorderSide(color: theme.borderDefault)),
        ),
        width: double.infinity,
        child: SafeArea(
          top: false,
          child: Padding(
            padding: const EdgeInsets.fromLTRB(12, 8, 12, 8),
            child: ConstrainedBox(
              constraints: const BoxConstraints(minHeight: 52),
              child: Row(children: [
                for (final tab in tabs)
                  Expanded(
                    child: InkWell(
                      onTap: () => onSelect?.call(tab.key),
                      child: Column(mainAxisAlignment: MainAxisAlignment.center, mainAxisSize: MainAxisSize.min, children: [
                        Icon(tab.icon, size: 22, color: tab.key == selectedKey ? theme.brandPrimary : theme.textTertiary),
                        const SizedBox(height: 2),
                        Text(
                          tab.title,
                          style: TextStyle(
                            color: tab.key == selectedKey ? theme.brandPrimary : theme.textTertiary,
                            fontWeight: FontWeight.w800,
                            fontSize: 11,
                            height: 14 / 11,
                          ),
                        ),
                      ]),
                    ),
                  ),
              ]),
            ),
          ),
        ),
      );
}

class TspTabs extends StatelessWidget {
  const TspTabs({super.key, required this.tabs, required this.selectedIndex, this.theme = StarPlanetTheme.sky, this.onSelect});
  final List<String> tabs;
  final int selectedIndex;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onSelect;

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(4),
      decoration: BoxDecoration(
        color: theme.pageEnd,
        borderRadius: BorderRadius.circular(999),
        border: Border.all(color: theme.borderDefault),
      ),
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        child: Row(
          children: [
            for (var i = 0; i < tabs.length; i++)
              GestureDetector(
                onTap: () => onSelect?.call(i),
                child: Container(
                  constraints: const BoxConstraints(minHeight: 34),
                  padding: const EdgeInsets.symmetric(horizontal: 16),
                  alignment: Alignment.center,
                  decoration: ShapeDecoration(
                    color: i == selectedIndex ? theme.brandPrimary : Colors.transparent,
                    shape: const StadiumBorder(),
                  ),
                  child: Text(
                    tabs[i],
                    style: TextStyle(
                      color: i == selectedIndex ? Colors.white : theme.textSecondary,
                      fontWeight: FontWeight.w800,
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

class TspAmount extends StatelessWidget {
  const TspAmount({super.key, required this.value, this.symbol = '¥', this.cycle = '', this.symbolAfter = false, this.strikeThrough = false, this.theme = StarPlanetTheme.sky});
  final String symbol;
  final String value;
  final String cycle;
  final bool symbolAfter;
  final bool strikeThrough;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final style = TextStyle(color: theme.textPrimary, decoration: strikeThrough ? TextDecoration.lineThrough : null);
    return Row(crossAxisAlignment: CrossAxisAlignment.end, children: [
      if (!symbolAfter) Text(symbol, style: style.copyWith(fontWeight: FontWeight.w800, fontSize: 18)),
      if (!symbolAfter) const SizedBox(width: 4),
      Text(value, style: style.copyWith(fontSize: 30, fontWeight: FontWeight.w900, height: 32 / 30)),
      if (symbolAfter) ...[const SizedBox(width: 4), Text(symbol, style: style.copyWith(fontWeight: FontWeight.w800, fontSize: 18))],
      if (cycle.isNotEmpty) ...[const SizedBox(width: 4), Padding(padding: const EdgeInsets.only(bottom: 2), child: Text('/$cycle', style: TextStyle(color: theme.textSecondary, fontSize: 13)))],
    ]);
  }
}

class TspIconButton extends StatelessWidget {
  const TspIconButton({super.key, required this.icon, this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final IconData icon;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => Opacity(
        opacity: disabled ? .45 : 1,
        child: GestureDetector(
          onTap: disabled ? null : onTap,
          child: Container(
            width: 44,
            height: 44,
            alignment: Alignment.center,
            decoration: BoxDecoration(
              color: selected ? theme.brandPrimary : theme.surfaceRaised,
              borderRadius: BorderRadius.circular(14),
              border: Border.all(color: theme.borderDefault),
            ),
            child: Icon(icon, size: 22, color: selected ? Colors.white : theme.textPrimary),
          ),
        ),
      );
}

class TspKeyValueLabel extends StatelessWidget {
  const TspKeyValueLabel({super.key, required this.label, required this.value, this.theme = StarPlanetTheme.sky});
  final String label;
  final String value;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => Row(mainAxisAlignment: MainAxisAlignment.spaceBetween, children: [
        Text(label, style: TextStyle(color: theme.textSecondary)),
        const SizedBox(width: 16),
        Flexible(child: Text(value, textAlign: TextAlign.right, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800))),
      ]);
}

class TspNotification extends StatelessWidget {
  const TspNotification({super.key, required this.title, required this.message, this.variant = 'info', this.theme = StarPlanetTheme.sky});
  final String title;
  final String message;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final isAlert = variant == 'alert';
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(14),
      decoration: BoxDecoration(
        color: isAlert ? theme.activeFill : theme.pageEnd,
        borderRadius: BorderRadius.circular(18),
        border: Border.all(color: isAlert ? theme.warning : theme.borderDefault),
      ),
      child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
        Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
        const SizedBox(height: 6),
        Text(message, style: TextStyle(color: theme.textSecondary, fontSize: 13)),
      ]),
    );
  }
}

class TspTextLink extends StatelessWidget {
  const TspTextLink({super.key, required this.text, this.inverse = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final String text;
  final bool inverse;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => GestureDetector(
        onTap: onTap,
        child: Text(text, style: TextStyle(color: inverse ? Colors.white : theme.brandPrimary, fontWeight: FontWeight.w800)),
      );
}

class TspStepper extends StatelessWidget {
  const TspStepper({super.key, required this.stepCount, required this.currentStep, this.theme = StarPlanetTheme.sky});
  final int stepCount;
  final int currentStep;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final count = stepCount.clamp(3, 5);
    final current = currentStep.clamp(1, count);
    return Row(children: [
      for (var step = 1; step <= count; step++) ...[
        Container(
          width: 32,
          height: 32,
          alignment: Alignment.center,
          decoration: BoxDecoration(
            shape: BoxShape.circle,
            color: step < current ? theme.success : step == current ? theme.brandPrimary : theme.surfaceRaised,
            border: Border.all(color: step == current ? theme.brandPrimary : theme.borderDefault),
          ),
          child: Text(step < current ? '✓' : '$step', style: TextStyle(color: step <= current ? Colors.white : theme.textTertiary, fontWeight: FontWeight.w800)),
        ),
        if (step < count) Expanded(child: Container(height: 2, color: step < current ? theme.success : theme.borderDefault)),
      ],
    ]);
  }
}

class TspStickyFooter extends StatelessWidget {
  const TspStickyFooter({super.key, required this.child, this.theme = StarPlanetTheme.sky});
  final Widget child;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => Container(
        padding: const EdgeInsets.all(12),
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          border: Border.all(color: theme.borderDefault),
        ),
        child: child,
      );
}

class TspPinInput extends StatelessWidget {
  const TspPinInput({super.key, required this.value, this.cellCount = 4, this.secure = false, this.theme = StarPlanetTheme.sky});
  final String value;
  final int cellCount;
  final bool secure;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) {
    final count = cellCount.clamp(4, 6);
    return Row(children: [
      for (var i = 0; i < count; i++) ...[
        if (i > 0) const SizedBox(width: 10),
        Expanded(
          child: Container(
            height: 48,
            alignment: Alignment.center,
            decoration: BoxDecoration(color: theme.surfaceRaised, borderRadius: BorderRadius.circular(14), border: Border.all(color: theme.borderDefault)),
            child: Text(i < value.length ? (secure ? '•' : value[i]) : '', style: TextStyle(color: theme.textPrimary, fontSize: 18, fontWeight: FontWeight.w900)),
          ),
        ),
      ],
    ]);
  }
}

class TspListItem extends StatelessWidget {
  const TspListItem({super.key, required this.title, this.message = '', this.trailing = '', this.selected = false, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final String title;
  final String message;
  final String trailing;
  final bool selected;
  final bool disabled;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => Opacity(
        opacity: disabled ? .45 : 1,
        child: GestureDetector(
          onTap: disabled ? null : onTap,
          child: Container(
            constraints: const BoxConstraints(minHeight: 64),
            padding: const EdgeInsets.all(14),
            decoration: BoxDecoration(
              color: selected ? theme.selectedFill : theme.surfaceRaised,
              borderRadius: BorderRadius.circular(18),
              border: Border.all(color: selected ? theme.success : theme.borderDefault),
            ),
            child: Row(children: [
              Expanded(
                child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                  Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w800)),
                  if (message.isNotEmpty) ...[const SizedBox(height: 4), Text(message, style: TextStyle(color: theme.textSecondary))],
                ]),
              ),
              if (trailing.isNotEmpty) Text(trailing, style: TextStyle(color: theme.textTertiary, fontSize: 20)),
            ]),
          ),
        ),
      );
}

class TspEmpty extends StatelessWidget {
  const TspEmpty({super.key, required this.title, required this.message, this.actionText = '', this.theme = StarPlanetTheme.sky, this.onAction});
  final String title;
  final String message;
  final String actionText;
  final StarPlanetTheme theme;
  final VoidCallback? onAction;

  @override
  Widget build(BuildContext context) => Container(
        width: double.infinity,
        padding: const EdgeInsets.all(24),
        decoration: BoxDecoration(
          color: theme.surfaceRaised,
          borderRadius: BorderRadius.circular(22),
          border: Border.all(color: theme.borderDefault),
        ),
        child: Column(children: [
          Container(
            width: 50,
            height: 50,
            alignment: Alignment.center,
            decoration: BoxDecoration(color: theme.brandPrimary, borderRadius: BorderRadius.circular(18)),
            child: const Text('○', style: TextStyle(color: Colors.white, fontSize: 22, fontWeight: FontWeight.w800)),
          ),
          const SizedBox(height: 8),
          Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900)),
          const SizedBox(height: 6),
          Text(message, textAlign: TextAlign.center, style: TextStyle(color: theme.textSecondary)),
          if (actionText.isNotEmpty) ...[
            const SizedBox(height: 12),
            TspButton(text: actionText, fullWidth: false, variant: TspButtonVariant.primary, theme: theme, onTap: onAction),
          ],
        ]),
      );
}

class TspToast extends StatelessWidget {
  const TspToast({
    super.key,
    required this.message,
    this.variant = 'info',
    this.theme = StarPlanetTheme.sky,
  });

  final String message;
  final String variant;
  final StarPlanetTheme theme;

  static const Duration defaultDuration = Duration(milliseconds: 1600);

  static Color _fill(String variant, StarPlanetTheme theme) {
    return switch (variant) {
      'success' => theme.success,
      'warning' => theme.warning,
      'danger' || 'error' => theme.danger,
      _ => theme.brandDark,
    };
  }

  static void show(
    BuildContext context, {
    required String message,
    String variant = 'info',
    StarPlanetTheme theme = StarPlanetTheme.sky,
    Duration duration = defaultDuration,
  }) {
    final overlay = Overlay.maybeOf(context);
    if (overlay == null) return;
    late OverlayEntry entry;
    entry = OverlayEntry(
      builder: (_) => Positioned(
        left: 18,
        right: 18,
        bottom: 24,
        child: IgnorePointer(
          child: Material(
            color: Colors.transparent,
            child: Align(
              alignment: Alignment.bottomCenter,
              child: TspToast(message: message, variant: variant, theme: theme),
            ),
          ),
        ),
      ),
    );
    overlay.insert(entry);
    Future<void>.delayed(duration, () {
      if (entry.mounted) entry.remove();
    });
  }

  @override
  Widget build(BuildContext context) {
    final fill = _fill(variant, theme);
    final textColor = variant == 'warning' ? theme.textPrimary : Colors.white;
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 10),
      decoration: ShapeDecoration(color: fill, shape: const StadiumBorder()),
      child: Text(
        message,
        textAlign: TextAlign.center,
        style: TextStyle(color: textColor, fontWeight: FontWeight.w800, fontSize: 14),
      ),
    );
  }
}

class TspModal extends StatelessWidget {
  const TspModal({
    super.key,
    required this.title,
    required this.message,
    this.confirmText = 'OK',
    this.cancelText = 'Cancel',
    this.theme = StarPlanetTheme.sky,
    this.onConfirm,
    this.onCancel,
  });

  final String title;
  final String message;
  final String confirmText;
  final String cancelText;
  final StarPlanetTheme theme;
  final VoidCallback? onConfirm;
  final VoidCallback? onCancel;

  @override
  Widget build(BuildContext context) {
    return Material(
      type: MaterialType.transparency,
      child: ColoredBox(
        color: const Color(0x47173A62),
        child: Center(
          child: Padding(
            padding: const EdgeInsets.all(18),
            child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: 420),
              child: Container(
                width: double.infinity,
                padding: const EdgeInsets.all(22),
                decoration: BoxDecoration(
                  color: theme.surfaceRaised,
                  borderRadius: BorderRadius.circular(24),
                  border: Border.all(color: theme.borderDefault),
                ),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(title, style: TextStyle(color: theme.textPrimary, fontSize: 20, fontWeight: FontWeight.w900)),
                    const SizedBox(height: 14),
                    Text(message, style: TextStyle(color: theme.textSecondary, height: 1.4)),
                    const SizedBox(height: 14),
                    Row(
                      children: [
                        Expanded(child: TspButton(text: cancelText, theme: theme, onTap: onCancel)),
                        const SizedBox(width: 10),
                        Expanded(
                          child: TspButton(
                            text: confirmText,
                            variant: TspButtonVariant.primary,
                            theme: theme,
                            onTap: onConfirm,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class TspModalButton extends StatelessWidget {
  const TspModalButton({super.key, required this.theme, this.onResult});
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onResult;

  @override
  Widget build(BuildContext context) => TspButton(
        text: 'Open Modal',
        theme: theme,
        onTap: () => showDialog<void>(
          context: context,
          barrierColor: Colors.transparent,
          builder: (dialogContext) => TspModal(
            title: '确认',
            message: '组件弹窗完整显示。',
            confirmText: '确定',
            cancelText: '取消',
            theme: theme,
            onCancel: () => Navigator.pop(dialogContext),
            onConfirm: () {
              Navigator.pop(dialogContext);
              onResult?.call(true);
            },
          ),
        ),
      );
}
