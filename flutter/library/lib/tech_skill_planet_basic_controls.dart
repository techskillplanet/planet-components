import 'package:flutter/material.dart';

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
    selectedFill: Color(0xFFE6FFF4),
  );
}

enum TspButtonVariant { primary, standard, danger, text, link }
enum TspAlertVariant { info, success, warning, error }

Color _variantColor(String variant, StarPlanetTheme theme) {
  return switch (variant) {
    'success' => theme.success,
    'warning' => theme.warning,
    'danger' || 'error' => theme.danger,
    'primary' => theme.brandPrimary,
    _ => theme.borderDefault,
  };
}

class TspButton extends StatelessWidget {
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
  Widget build(BuildContext context) {
    final faceColor = switch (variant) {
      TspButtonVariant.primary => theme.brandPrimary,
      TspButtonVariant.danger => theme.danger,
      TspButtonVariant.text || TspButtonVariant.link || TspButtonVariant.standard => theme.surfaceRaised,
    };
    final textColor = switch (variant) {
      TspButtonVariant.primary || TspButtonVariant.danger => Colors.white,
      TspButtonVariant.text || TspButtonVariant.link => theme.brandPrimary,
      TspButtonVariant.standard => theme.textPrimary,
    };
    final button = Opacity(
      opacity: disabled ? .45 : 1,
      child: GestureDetector(
        onTap: disabled ? null : onTap,
        child: Stack(
          children: [
            Positioned.fill(top: 5, child: DecoratedBox(decoration: ShapeDecoration(color: theme.borderDefault, shape: const StadiumBorder()))),
            Container(
              height: 46,
              alignment: Alignment.center,
              decoration: ShapeDecoration(color: faceColor, shape: const StadiumBorder()),
              child: Text(text, style: TextStyle(color: textColor, fontWeight: FontWeight.w800)),
            ),
          ],
        ),
      ),
    );
    return fullWidth ? SizedBox(width: double.infinity, child: button) : button;
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
      opacity: disabled ? .55 : 1,
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
        Text(message, style: TextStyle(color: theme.textSecondary)),
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
  Widget build(BuildContext context) => Opacity(
        opacity: disabled ? .45 : 1,
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
          decoration: ShapeDecoration(color: _variantColor(variant, theme).withValues(alpha: .16), shape: const StadiumBorder()),
          child: Text(text, style: TextStyle(color: variant == 'default' ? theme.textSecondary : _variantColor(variant, theme), fontWeight: FontWeight.w800, fontSize: 12)),
        ),
      );
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
              color: selected ? theme.brandPrimary : theme.surfaceRaised,
              shape: StadiumBorder(side: BorderSide(color: selected ? theme.brandPrimary : theme.borderDefault)),
            ),
            child: Text(text, style: TextStyle(color: selected ? Colors.white : theme.textPrimary, fontWeight: FontWeight.w800)),
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
          contentPadding: const EdgeInsets.symmetric(horizontal: 14, vertical: 12),
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(14), borderSide: BorderSide(color: variant == 'error' ? theme.danger : theme.borderDefault)),
          enabledBorder: OutlineInputBorder(borderRadius: BorderRadius.circular(14), borderSide: BorderSide(color: variant == 'error' ? theme.danger : theme.borderDefault)),
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
  Widget build(BuildContext context) => DropdownButtonFormField<int>(
        initialValue: selectedIndex.clamp(0, options.length - 1),
        items: [for (var i = 0; i < options.length; i++) DropdownMenuItem(value: i, child: Text(options[i]))],
        onChanged: disabled ? null : (value) => value == null ? null : onSelect?.call(value),
        decoration: InputDecoration(
          filled: true,
          fillColor: theme.surfaceRaised,
          border: OutlineInputBorder(borderRadius: BorderRadius.circular(14), borderSide: BorderSide(color: theme.borderDefault)),
        ),
      );
}

class TspOptionSheet extends StatelessWidget {
  const TspOptionSheet({super.key, required this.options, required this.selectedIndex, this.title = '请选择', this.theme = StarPlanetTheme.sky, this.onSelect});
  final String title;
  final List<String> options;
  final int selectedIndex;
  final StarPlanetTheme theme;
  final ValueChanged<int>? onSelect;

  @override
  Widget build(BuildContext context) => TspButton(
        text: 'Open OptionSheet',
        variant: TspButtonVariant.primary,
        theme: theme,
        onTap: () => showModalBottomSheet<void>(
          context: context,
          backgroundColor: theme.surfaceRaised,
          shape: const RoundedRectangleBorder(borderRadius: BorderRadius.vertical(top: Radius.circular(22))),
          builder: (_) => SafeArea(
            child: Column(mainAxisSize: MainAxisSize.min, children: [
              Padding(padding: const EdgeInsets.all(16), child: Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900))),
              for (var i = 0; i < options.length; i++)
                ListTile(
                  leading: Text(i == selectedIndex ? '✓' : '', style: TextStyle(color: theme.brandPrimary, fontWeight: FontWeight.w900)),
                  title: Text(options[i], style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w700)),
                  onTap: () {
                    Navigator.pop(context);
                    onSelect?.call(i);
                  },
                ),
            ]),
          ),
        ),
      );
}

class TspSwitch extends StatelessWidget {
  const TspSwitch({super.key, required this.checked, this.text = '', this.loading = false, this.disabled = false, this.theme = StarPlanetTheme.sky, this.onChanged});
  final bool checked;
  final String text;
  final bool loading;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<bool>? onChanged;

  @override
  Widget build(BuildContext context) => SwitchListTile(
        value: checked,
        title: Text(text, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w700)),
        subtitle: loading ? Text('Loading', style: TextStyle(color: theme.textTertiary)) : null,
        activeThumbColor: theme.brandPrimary,
        onChanged: disabled ? null : onChanged,
      );
}

class TspProgress extends StatelessWidget {
  const TspProgress({super.key, required this.progress, this.variant = 'primary', this.theme = StarPlanetTheme.sky});
  final double progress;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => ClipRRect(
        borderRadius: BorderRadius.circular(999),
        child: LinearProgressIndicator(
          minHeight: 6,
          value: (progress / 100).clamp(0, 1),
          backgroundColor: theme.borderDefault,
          color: _variantColor(variant, theme),
        ),
      );
}

class TspTopBar extends StatelessWidget {
  const TspTopBar({super.key, required this.title, this.showBack = false, this.backgroundColor, this.theme = StarPlanetTheme.sky, this.onBack});
  final String title;
  final bool showBack;
  final Color? backgroundColor;
  final StarPlanetTheme theme;
  final VoidCallback? onBack;

  @override
  Widget build(BuildContext context) => Container(
        height: 56,
        color: backgroundColor ?? theme.pageStart,
        child: Stack(alignment: Alignment.center, children: [
          if (showBack) Positioned(left: 0, child: IconButton(icon: Icon(Icons.chevron_left, color: theme.textPrimary), onPressed: onBack ?? () => Navigator.maybePop(context))),
          Text(title, style: TextStyle(color: theme.textPrimary, fontSize: 18, fontWeight: FontWeight.w900)),
        ]),
      );
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
        decoration: BoxDecoration(color: theme.surfaceRaised, border: Border(top: BorderSide(color: theme.borderDefault))),
        child: SafeArea(
          top: false,
          child: Row(children: [
            for (final tab in tabs)
              Expanded(
                child: InkWell(
                  onTap: () => onSelect?.call(tab.key),
                  child: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 8),
                    child: Column(mainAxisSize: MainAxisSize.min, children: [
                      Icon(tab.icon, color: tab.key == selectedKey ? theme.brandPrimary : theme.textTertiary),
                      Text(tab.title, style: TextStyle(color: tab.key == selectedKey ? theme.brandPrimary : theme.textTertiary, fontWeight: FontWeight.w800)),
                    ]),
                  ),
                ),
              ),
          ]),
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
  Widget build(BuildContext context) => Wrap(spacing: 8, children: [
        for (var i = 0; i < tabs.length; i++) TspChip(text: tabs[i], selected: i == selectedIndex, theme: theme, onTap: () => onSelect?.call(i)),
      ]);
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
      if (!symbolAfter) Text(symbol, style: style.copyWith(fontWeight: FontWeight.w800)),
      const SizedBox(width: 4),
      Text(value, style: style.copyWith(fontSize: 30, fontWeight: FontWeight.w900, height: 1)),
      if (symbolAfter) ...[const SizedBox(width: 4), Text(symbol, style: style.copyWith(fontWeight: FontWeight.w800))],
      if (cycle.isNotEmpty) ...[const SizedBox(width: 4), Text('/$cycle', style: TextStyle(color: theme.textSecondary))],
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
  Widget build(BuildContext context) => IconButton.filled(
        onPressed: disabled ? null : onTap,
        style: IconButton.styleFrom(backgroundColor: selected ? theme.brandPrimary : theme.surfaceRaised, foregroundColor: selected ? Colors.white : theme.textPrimary),
        icon: Icon(icon),
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
        Text(value, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900)),
      ]);
}

class TspNotification extends StatelessWidget {
  const TspNotification({super.key, required this.title, required this.message, this.variant = 'info', this.theme = StarPlanetTheme.sky});
  final String title;
  final String message;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => TspAlert(title: title, message: message, variant: variant == 'alert' ? TspAlertVariant.warning : TspAlertVariant.info, theme: theme);
}

class TspTextLink extends StatelessWidget {
  const TspTextLink({super.key, required this.text, this.inverse = false, this.theme = StarPlanetTheme.sky, this.onTap});
  final String text;
  final bool inverse;
  final StarPlanetTheme theme;
  final VoidCallback? onTap;

  @override
  Widget build(BuildContext context) => TextButton(onPressed: onTap, child: Text(text, style: TextStyle(color: inverse ? Colors.white : theme.brandPrimary, fontWeight: FontWeight.w900)));
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
        padding: const EdgeInsets.fromLTRB(20, 12, 20, 16),
        decoration: BoxDecoration(color: theme.pageEnd, border: Border(top: BorderSide(color: theme.borderDefault))),
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
  Widget build(BuildContext context) => Row(children: [
        for (var i = 0; i < cellCount; i++)
          Expanded(
            child: Container(
              margin: EdgeInsets.only(right: i == cellCount - 1 ? 0 : 8),
              height: 48,
              alignment: Alignment.center,
              decoration: BoxDecoration(color: theme.surfaceRaised, borderRadius: BorderRadius.circular(14), border: Border.all(color: theme.borderDefault)),
              child: Text(i < value.length ? (secure ? '•' : value[i]) : '', style: TextStyle(color: theme.textPrimary, fontSize: 18, fontWeight: FontWeight.w900)),
            ),
          ),
      ]);
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
  Widget build(BuildContext context) => InkWell(
        onTap: disabled ? null : onTap,
        borderRadius: BorderRadius.circular(20),
        child: TspCard(
          selected: selected,
          disabled: disabled,
          theme: theme,
          child: Row(children: [
            Expanded(
              child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
                Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900)),
                if (message.isNotEmpty) ...[const SizedBox(height: 4), Text(message, style: TextStyle(color: theme.textSecondary))],
              ]),
            ),
            if (trailing.isNotEmpty) Text(trailing, style: TextStyle(color: theme.textTertiary, fontWeight: FontWeight.w900)),
          ]),
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
  Widget build(BuildContext context) => TspCard(
        theme: theme,
        child: Column(children: [
          Icon(Icons.circle_outlined, color: theme.brandPrimary, size: 42),
          const SizedBox(height: 8),
          Text(title, style: TextStyle(color: theme.textPrimary, fontWeight: FontWeight.w900)),
          const SizedBox(height: 6),
          Text(message, style: TextStyle(color: theme.textSecondary)),
          if (actionText.isNotEmpty) ...[const SizedBox(height: 12), TspButton(text: actionText, fullWidth: false, variant: TspButtonVariant.primary, theme: theme, onTap: onAction)],
        ]),
      );
}

class TspToast extends StatelessWidget {
  const TspToast({super.key, required this.message, this.variant = 'info', this.theme = StarPlanetTheme.sky});
  final String message;
  final String variant;
  final StarPlanetTheme theme;

  @override
  Widget build(BuildContext context) => Material(
        color: Colors.transparent,
        child: Center(
          child: Container(
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
            decoration: ShapeDecoration(color: _variantColor(variant, theme).withValues(alpha: .92), shape: const StadiumBorder()),
            child: Text(message, style: const TextStyle(color: Colors.white, fontWeight: FontWeight.w900)),
          ),
        ),
      );
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
          builder: (_) => AlertDialog(
            title: const Text('确认'),
            content: const Text('组件弹窗完整显示。'),
            actions: [
              TextButton(onPressed: () => Navigator.pop(context), child: const Text('取消')),
              TextButton(
                onPressed: () {
                  Navigator.pop(context);
                  onResult?.call(true);
                },
                child: const Text('确定'),
              ),
            ],
          ),
        ),
      );
}
