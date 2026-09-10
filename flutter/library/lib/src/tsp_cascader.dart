import 'package:flutter/material.dart';
import 'star_planet_theme.dart';

/// Cascader option with optional nested [children].
class TspCascaderOption {
  const TspCascaderOption({required this.value, required this.label, this.children = const []});

  final String value;
  final String label;
  final List<TspCascaderOption> children;

  factory TspCascaderOption.fromDynamic(dynamic raw, [int index = 0]) {
    if (raw is TspCascaderOption) return raw;
    if (raw is Map) {
      final kids = raw['children'];
      return TspCascaderOption(
        value: '${raw['value'] ?? raw['id'] ?? raw['key'] ?? index}',
        label: '${raw['label'] ?? raw['title'] ?? raw['text'] ?? raw['value'] ?? raw['id'] ?? ''}',
        children: kids is List
            ? kids.asMap().entries.map((e) => TspCascaderOption.fromDynamic(e.value, e.key)).toList()
            : const [],
      );
    }
    return TspCascaderOption(value: '$index', label: '$raw');
  }
}

/// Multi-level cascader picker.
class TspCascader extends StatefulWidget {
  const TspCascader({
    super.key,
    this.options = const [],
    this.value = const [],
    this.placeholder = '请选择',
    this.disabled = false,
    this.theme = StarPlanetTheme.sky,
    this.onChanged,
  });

  final List<dynamic> options;
  final List<String> value;
  final String placeholder;
  final bool disabled;
  final StarPlanetTheme theme;
  final void Function(List<String> path, List<String> labels)? onChanged;

  @override
  State<TspCascader> createState() => _TspCascaderState();
}

class _TspCascaderState extends State<TspCascader> {
  bool _open = false;
  late List<String> _draft;

  @override
  void initState() {
    super.initState();
    _draft = [...widget.value];
  }

  @override
  void didUpdateWidget(covariant TspCascader oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.value != oldWidget.value && !_open) {
      _draft = [...widget.value];
    }
  }

  List<TspCascaderOption> get _opts =>
      widget.options.asMap().entries.map((e) => TspCascaderOption.fromDynamic(e.value, e.key)).toList();

  List<String> _labelsFor(List<TspCascaderOption> opts, List<String> path) {
    final labels = <String>[];
    var level = opts;
    for (final v in path) {
      TspCascaderOption? hit;
      for (final o in level) {
        if (o.value == v) {
          hit = o;
          break;
        }
      }
      if (hit == null) break;
      labels.add(hit.label);
      level = hit.children;
    }
    return labels;
  }

  List<List<TspCascaderOption>> _columns(List<TspCascaderOption> opts, List<String> walk) {
    final cols = <List<TspCascaderOption>>[];
    var level = opts;
    for (var i = 0; i <= walk.length; i++) {
      if (level.isEmpty) break;
      cols.add(level);
      if (i >= walk.length) break;
      final cur = walk[i];
      if (cur.isEmpty) break;
      TspCascaderOption? hit;
      for (final o in level) {
        if (o.value == cur) {
          hit = o;
          break;
        }
      }
      level = hit?.children ?? const [];
    }
    return cols;
  }

  void _pick(int colIndex, TspCascaderOption opt) {
    final next = [..._draft.take(colIndex), opt.value];
    setState(() => _draft = next);
    if (opt.children.isEmpty) {
      widget.onChanged?.call(next, _labelsFor(_opts, next));
      setState(() => _open = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    final opts = _opts;
    final path = widget.value.map((e) => '$e').toList();
    final display = _labelsFor(opts, path).join(' / ');
    final walk = _open ? _draft : path;
    final columns = _columns(opts, walk);
    final theme = widget.theme;

    return Opacity(
      opacity: widget.disabled ? .45 : 1,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          GestureDetector(
            onTap: widget.disabled
                ? null
                : () => setState(() {
                      _draft = [...path];
                      _open = !_open;
                    }),
            child: Container(
              constraints: const BoxConstraints(minHeight: 48),
              padding: const EdgeInsets.symmetric(horizontal: 14, vertical: 14),
              decoration: BoxDecoration(
                color: theme.surfaceRaised,
                borderRadius: BorderRadius.circular(16),
                border: Border.all(color: theme.borderDefault, width: 2),
              ),
              child: Row(
                children: [
                  Expanded(
                    child: Text(
                      display.isEmpty ? widget.placeholder : display,
                      style: TextStyle(
                        color: display.isEmpty ? theme.textTertiary : theme.textPrimary,
                        fontWeight: FontWeight.w700,
                        fontSize: 15,
                      ),
                    ),
                  ),
                  Text('▾', style: TextStyle(color: theme.textSecondary, fontWeight: FontWeight.w800)),
                ],
              ),
            ),
          ),
          if (_open) ...[
            const SizedBox(height: 8),
            Container(
              decoration: BoxDecoration(
                color: theme.surfaceRaised,
                borderRadius: BorderRadius.circular(16),
                border: Border.all(color: theme.borderDefault),
              ),
              child: SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: IntrinsicHeight(
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.stretch,
                    children: [
                      for (var ci = 0; ci < columns.length; ci++)
                        Container(
                          width: 140,
                          decoration: BoxDecoration(
                            border: ci == 0
                                ? null
                                : Border(left: BorderSide(color: theme.borderDefault)),
                          ),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.stretch,
                            children: [
                              for (final opt in columns[ci])
                                InkWell(
                                  onTap: () => _pick(ci, opt),
                                  child: Container(
                                    padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 10),
                                    color: (_draft.length > ci && _draft[ci] == opt.value)
                                        ? theme.selectedFill
                                        : null,
                                    child: Text(
                                      opt.label,
                                      style: TextStyle(
                                        color: theme.textPrimary,
                                        fontWeight: FontWeight.w700,
                                        fontSize: 13,
                                      ),
                                    ),
                                  ),
                                ),
                            ],
                          ),
                        ),
                    ],
                  ),
                ),
              ),
            ),
          ],
        ],
      ),
    );
  }
}
