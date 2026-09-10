import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Nested tree selector.
class TspTreeNode {
  const TspTreeNode({required this.id, required this.label, this.children = const []});

  final String id;
  final String label;
  final List<TspTreeNode> children;

  factory TspTreeNode.fromDynamic(dynamic raw, [int index = 0]) {
    if (raw is TspTreeNode) return raw;
    if (raw is Map) {
      final kids = raw['children'];
      return TspTreeNode(
        id: '${raw['id'] ?? raw['key'] ?? index}',
        label: '${raw['label'] ?? raw['title'] ?? raw['text'] ?? raw['id'] ?? ''}',
        children: kids is List
            ? kids.asMap().entries.map((e) => TspTreeNode.fromDynamic(e.value, e.key)).toList()
            : const [],
      );
    }
    return TspTreeNode(id: '$index', label: '$raw');
  }
}

class TspTree extends StatefulWidget {
  const TspTree({
    super.key,
    this.items = const [],
    this.selectedId,
    this.expandedIds,
    this.theme = StarPlanetTheme.sky,
    this.onSelect,
    this.onExpand,
  });

  final List<dynamic> items;
  final String? selectedId;
  final List<String>? expandedIds;
  final StarPlanetTheme theme;
  final void Function(String id, TspTreeNode node)? onSelect;
  final ValueChanged<List<String>>? onExpand;

  @override
  State<TspTree> createState() => _TspTreeState();
}

class _TspTreeState extends State<TspTree> {
  late Set<String> _expanded;

  @override
  void initState() {
    super.initState();
    _expanded = {...(widget.expandedIds ?? const [])};
  }

  @override
  void didUpdateWidget(covariant TspTree oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.expandedIds != null && widget.expandedIds != oldWidget.expandedIds) {
      _expanded = {...widget.expandedIds!};
    }
  }

  Set<String> get _effectiveExpanded =>
      widget.expandedIds != null ? {...widget.expandedIds!} : _expanded;

  void _toggle(String id) {
    final next = {..._effectiveExpanded};
    if (next.contains(id)) {
      next.remove(id);
    } else {
      next.add(id);
    }
    if (widget.expandedIds == null) setState(() => _expanded = next);
    widget.onExpand?.call(next.toList());
  }

  @override
  Widget build(BuildContext context) {
    final nodes = widget.items.asMap().entries.map((e) => TspTreeNode.fromDynamic(e.value, e.key)).toList();
    return Container(
      decoration: BoxDecoration(
        color: widget.theme.surfaceRaised,
        borderRadius: BorderRadius.circular(16),
        border: Border.all(color: widget.theme.borderDefault),
      ),
      padding: const EdgeInsets.symmetric(vertical: 6),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          for (final node in nodes) _buildNode(node, 0),
        ],
      ),
    );
  }

  Widget _buildNode(TspTreeNode node, int depth) {
    final theme = widget.theme;
    final open = _effectiveExpanded.contains(node.id);
    final selected = widget.selectedId == node.id;
    final hasKids = node.children.isNotEmpty;

    return Column(
      crossAxisAlignment: CrossAxisAlignment.stretch,
      children: [
        Material(
          color: selected ? theme.selectedFill : Colors.transparent,
          child: InkWell(
            onTap: () => widget.onSelect?.call(node.id, node),
            child: Padding(
              padding: EdgeInsets.fromLTRB(8 + depth * 16.0, 8, 8, 8),
              child: Row(
                children: [
                  if (hasKids)
                    GestureDetector(
                      onTap: () => _toggle(node.id),
                      behavior: HitTestBehavior.opaque,
                      child: SizedBox(
                        width: 24,
                        child: Text(
                          open ? '▾' : '▸',
                          style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w800),
                        ),
                      ),
                    )
                  else
                    const SizedBox(width: 24),
                  Expanded(
                    child: Text(
                      node.label,
                      style: TextStyle(
                        color: theme.textPrimary,
                        fontWeight: selected ? FontWeight.w900 : FontWeight.w700,
                        fontSize: 14,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
        if (open)
          for (final child in node.children) _buildNode(child, depth + 1),
      ],
    );
  }
}
