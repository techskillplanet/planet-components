Component({
  properties: {
    items: { type: Array, value: [] },
    selectedId: { type: String, value: '' },
    expandedIds: { type: Array, value: null },
    theme: { type: Object, value: {} },
  },
  observers: {
    'items, selectedId, expandedIds': function () {
      this.rebuild();
    },
  },
  data: {
    flatNodes: [],
    localExpanded: [],
  },
  lifetimes: {
    attached() {
      this.rebuild();
    },
  },
  methods: {
    nodeId(node, index) {
      if (!node) return String(index);
      if (node.id != null) return String(node.id);
      if (node.key != null) return String(node.key);
      return String(index);
    },
    nodeLabel(node) {
      if (!node) return '';
      return node.label || node.title || node.text || String(node.id || '');
    },
    expandedSet() {
      if (this.data.expandedIds != null) {
        return new Set((this.data.expandedIds || []).map(String));
      }
      return new Set((this.data.localExpanded || []).map(String));
    },
    rebuild() {
      const items = Array.isArray(this.data.items) ? this.data.items : [];
      const expanded = this.expandedSet();
      const flat = [];
      const walk = (nodes, depth) => {
        (nodes || []).forEach((node, i) => {
          const id = this.nodeId(node, i);
          const kids = Array.isArray(node.children) ? node.children : [];
          const open = expanded.has(id);
          flat.push({
            id,
            label: this.nodeLabel(node),
            depth,
            hasKids: kids.length > 0,
            open,
            selected: String(this.data.selectedId) === id,
            padLeft: 16 + depth * 32,
          });
          if (open && kids.length) walk(kids, depth + 1);
        });
      };
      walk(items, 0);
      this.setData({ flatNodes: flat });
    },
    onToggle(e) {
      const id = String(e.currentTarget.dataset.id);
      const next = this.expandedSet();
      if (next.has(id)) next.delete(id);
      else next.add(id);
      const list = Array.from(next);
      if (this.data.expandedIds == null) {
        this.setData({ localExpanded: list }, () => this.rebuild());
      }
      this.triggerEvent('expand', { expandedIds: list });
    },
    onSelect(e) {
      const id = String(e.currentTarget.dataset.id);
      this.triggerEvent('select', { id });
    },
  },
});
