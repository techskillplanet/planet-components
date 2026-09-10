import SwiftUI

public struct TspCascaderOption: Identifiable, Equatable {
    public var id: String { value }
    public var value: String
    public var label: String
    public var children: [TspCascaderOption]

    public init(value: String, label: String, children: [TspCascaderOption] = []) {
        self.value = value
        self.label = label
        self.children = children
    }
}

/// Multi-level cascader picker.
public struct TspCascader: View {
    public var options: [TspCascaderOption]
    public var value: [String]
    public var placeholder: String
    public var disabled: Bool
    public var theme: StarPlanetTheme
    public var onChange: ([String], [String]) -> Void

    @State private var open = false
    @State private var draft: [String]

    public init(
        options: [TspCascaderOption] = [],
        value: [String] = [],
        placeholder: String = "请选择",
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky,
        onChange: @escaping ([String], [String]) -> Void = { _, _ in }
    ) {
        self.options = options
        self.value = value
        self.placeholder = placeholder
        self.disabled = disabled
        self.theme = theme
        self.onChange = onChange
        _draft = State(initialValue: value)
    }

    private var display: String {
        labels(for: value).joined(separator: " / ")
    }

    private var columns: [[TspCascaderOption]] {
        let walk = open ? draft : value
        var cols: [[TspCascaderOption]] = []
        var level = options
        var i = 0
        while true {
            if level.isEmpty { break }
            cols.append(level)
            if i >= walk.count { break }
            let cur = walk[i]
            guard let hit = level.first(where: { $0.value == cur }) else { break }
            level = hit.children
            i += 1
        }
        return cols
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Button(action: {
                guard !disabled else { return }
                draft = value
                open.toggle()
            }) {
                HStack {
                    Text(display.isEmpty ? placeholder : display)
                        .font(.system(size: 15, weight: .bold))
                        .foregroundColor(display.isEmpty ? theme.textTertiary : theme.textPrimary)
                    Spacer()
                    Text("▾")
                        .font(.system(size: 14, weight: .heavy))
                        .foregroundColor(theme.textSecondary)
                }
                .padding(.horizontal, 14)
                .padding(.vertical, 14)
                .frame(maxWidth: .infinity, minHeight: 48, alignment: .leading)
                .background(theme.surfaceRaised)
                .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 2))
                .clipShape(RoundedRectangle(cornerRadius: 16))
            }
            .buttonStyle(.plain)
            .disabled(disabled)

            if open {
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(alignment: .top, spacing: 0) {
                        ForEach(Array(columns.enumerated()), id: \.offset) { ci, col in
                            VStack(alignment: .leading, spacing: 0) {
                                ForEach(col) { opt in
                                    Button(action: { pick(colIndex: ci, option: opt) }) {
                                        Text(opt.label)
                                            .font(.system(size: 13, weight: .bold))
                                            .foregroundColor(theme.textPrimary)
                                            .frame(width: 140, alignment: .leading)
                                            .padding(.horizontal, 12)
                                            .padding(.vertical, 10)
                                            .background(
                                                (draft.count > ci && draft[ci] == opt.value)
                                                    ? theme.selectedFill
                                                    : Color.clear
                                            )
                                    }
                                    .buttonStyle(.plain)
                                }
                            }
                            .overlay(alignment: .leading) {
                                if ci > 0 {
                                    Rectangle().fill(theme.borderDefault).frame(width: 1)
                                }
                            }
                        }
                    }
                    .background(theme.surfaceRaised)
                    .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 1))
                    .clipShape(RoundedRectangle(cornerRadius: 16))
                }
            }
        }
        .opacity(disabled ? 0.45 : 1)
        .onChange(of: value) { newValue in
            if !open { draft = newValue }
        }
    }

    private func pick(colIndex: Int, option: TspCascaderOption) {
        var next = Array(draft.prefix(colIndex))
        next.append(option.value)
        draft = next
        if option.children.isEmpty {
            onChange(next, labels(for: next))
            open = false
        }
    }

    private func labels(for path: [String]) -> [String] {
        var result: [String] = []
        var level = options
        for v in path {
            guard let hit = level.first(where: { $0.value == v }) else { break }
            result.append(hit.label)
            level = hit.children
        }
        return result
    }
}
