import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

class TspChildSwitcherItem {
  const TspChildSwitcherItem({
    required this.id,
    this.label,
    this.name,
    this.text,
    this.iconSrc,
    this.emoji,
    this.leading,
  });

  final Object id;
  final String? label;
  final String? name;
  final String? text;
  final String? iconSrc;
  final String? emoji;
  final Widget? leading;

  String get resolvedLabel => label ?? name ?? text ?? '';
}

/// Single-select child chips/tabs.
class TspChildSwitcher extends StatelessWidget {
  const TspChildSwitcher({
    super.key,
    this.items = const [],
    this.selectedId,
    this.variant = 'chip',
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final List<TspChildSwitcherItem> items;
  final Object? selectedId;
  final String variant;
  final bool disabled;
  final StarPlanetTheme theme;
  final ValueChanged<Object>? onChanged;

  @override
  Widget build(BuildContext context) {
    final useTabs = variant == 'tabs';
    final row = Row(
      children: [
        for (var i = 0; i < items.length; i++) ...[
          if (i > 0) const SizedBox(width: 8),
          Flexible(
            fit: useTabs ? FlexFit.tight : FlexFit.loose,
            child: _buildItem(items[i], useTabs),
          ),
        ],
      ],
    );

    if (useTabs) return row;

    return SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        children: [
          for (var i = 0; i < items.length; i++) ...[
            if (i > 0) const SizedBox(width: 8),
            _buildItem(items[i], false),
          ],
        ],
      ),
    );
  }

  Widget _buildItem(TspChildSwitcherItem item, bool useTabs) {
    final selected = '${item.id}' == '$selectedId';
    final leading = item.leading ??
        (item.iconSrc != null
            ? ClipRRect(
                borderRadius: BorderRadius.circular(8),
                child: Image.network(item.iconSrc!, width: 22, height: 22, fit: BoxFit.cover),
              )
            : item.emoji != null
                ? Text(item.emoji!, style: const TextStyle(fontSize: 16))
                : null);

    return Opacity(
      opacity: disabled ? .45 : 1,
      child: GestureDetector(
        onTap: disabled ? null : () => onChanged?.call(item.id),
        child: Container(
          constraints: const BoxConstraints(minHeight: 44),
          padding: const EdgeInsets.symmetric(horizontal: 14),
          decoration: BoxDecoration(
            color: selected ? theme.brandPrimary : theme.surfaceRaised,
            borderRadius: BorderRadius.circular(useTabs ? 14 : 999),
            border: Border.all(color: selected ? theme.brandPrimary : theme.borderDefault),
          ),
          child: Row(
            mainAxisSize: useTabs ? MainAxisSize.max : MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              if (leading != null) ...[leading, const SizedBox(width: 8)],
              Flexible(
                child: Text(
                  item.resolvedLabel,
                  maxLines: 1,
                  overflow: TextOverflow.ellipsis,
                  style: TextStyle(
                    color: selected ? Colors.white : theme.textSecondary,
                    fontWeight: FontWeight.w800,
                    fontSize: 14,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
