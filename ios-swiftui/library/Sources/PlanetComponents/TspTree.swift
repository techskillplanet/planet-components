import SwiftUI

public struct TspTreeNode: Identifiable, Equatable {
    public var id: String
    public var label: String
    public var children: [TspTreeNode]

    public init(id: String, label: String, children: [TspTreeNode] = []) {
        self.id = id
        self.label = label
        self.children = children
    }
}

/// Nested tree selector.
public struct TspTree: View {
    public var items: [TspTreeNode]
    public var selectedId: String?
    public var expandedIds: [String]?
    public var theme: StarPlanetTheme
    public var onSelect: (String, TspTreeNode) -> Void
    public var onExpand: ([String]) -> Void

    @State private var localExpanded: Set<String>

    public init(
        items: [TspTreeNode] = [],
        selectedId: String? = nil,
        expandedIds: [String]? = nil,
        theme: StarPlanetTheme = .sky,
        onSelect: @escaping (String, TspTreeNode) -> Void = { _, _ in },
        onExpand: @escaping ([String]) -> Void = { _ in }
    ) {
        self.items = items
        self.selectedId = selectedId
        self.expandedIds = expandedIds
        self.theme = theme
        self.onSelect = onSelect
        self.onExpand = onExpand
        _localExpanded = State(initialValue: Set(expandedIds ?? []))
    }

    private var effectiveExpanded: Set<String> {
        if let expandedIds { return Set(expandedIds) }
        return localExpanded
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            ForEach(items) { node in
                TspTreeNodeRow(
                    node: node,
                    depth: 0,
                    selectedId: selectedId,
                    expanded: effectiveExpanded,
                    theme: theme,
                    onSelect: onSelect,
                    onToggle: toggle
                )
            }
        }
        .padding(.vertical, 6)
        .background(theme.surfaceRaised)
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 1))
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }

    private func toggle(_ id: String) {
        var next = effectiveExpanded
        if next.contains(id) {
            next.remove(id)
        } else {
            next.insert(id)
        }
        if expandedIds == nil {
            localExpanded = next
        }
        onExpand(Array(next))
    }
}

private struct TspTreeNodeRow: View {
    let node: TspTreeNode
    let depth: Int
    let selectedId: String?
    let expanded: Set<String>
    let theme: StarPlanetTheme
    let onSelect: (String, TspTreeNode) -> Void
    let onToggle: (String) -> Void

    var body: some View {
        let open = expanded.contains(node.id)
        let selected = selectedId == node.id
        let hasKids = !node.children.isEmpty

        VStack(alignment: .leading, spacing: 0) {
            HStack(spacing: 0) {
                if hasKids {
                    Button(action: { onToggle(node.id) }) {
                        Text(open ? "▾" : "▸")
                            .font(.system(size: 14, weight: .heavy))
                            .foregroundColor(theme.textSecondary)
                            .frame(width: 24, alignment: .center)
                    }
                    .buttonStyle(.plain)
                    .accessibilityLabel(open ? "Collapse" : "Expand")
                } else {
                    Color.clear.frame(width: 24)
                }
                Button(action: { onSelect(node.id, node) }) {
                    Text(node.label)
                        .font(.system(size: 14, weight: selected ? .black : .bold))
                        .foregroundColor(theme.textPrimary)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                .buttonStyle(.plain)
            }
            .padding(.leading, 8 + CGFloat(depth) * 16)
            .padding(.trailing, 8)
            .padding(.vertical, 8)
            .background(selected ? theme.selectedFill : Color.clear)

            if open {
                ForEach(node.children) { child in
                    TspTreeNodeRow(
                        node: child,
                        depth: depth + 1,
                        selectedId: selectedId,
                        expanded: expanded,
                        theme: theme,
                        onSelect: onSelect,
                        onToggle: onToggle
                    )
                }
            }
        }
    }
}
