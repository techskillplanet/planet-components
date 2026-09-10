import SwiftUI

public struct TspUploadFile: Identifiable, Equatable {
    public var id: String
    public var name: String

    public init(id: String, name: String) {
        self.id = id
        self.name = name
    }
}

/// Lightweight file list + add trigger (mock add; no real picker backend required).
public struct TspUpload: View {
    public var files: [TspUploadFile]
    public var multiple: Bool
    public var disabled: Bool
    public var accept: String?
    public var theme: StarPlanetTheme
    public var onChange: ([TspUploadFile]) -> Void
    public var onRemove: (TspUploadFile) -> Void
    public var onAdd: (() -> [TspUploadFile])?

    public init(
        files: [TspUploadFile] = [],
        multiple: Bool = true,
        disabled: Bool = false,
        accept: String? = nil,
        theme: StarPlanetTheme = .sky,
        onChange: @escaping ([TspUploadFile]) -> Void = { _ in },
        onRemove: @escaping (TspUploadFile) -> Void = { _ in },
        onAdd: (() -> [TspUploadFile])? = nil
    ) {
        self.files = files
        self.multiple = multiple
        self.disabled = disabled
        self.accept = accept
        self.theme = theme
        self.onChange = onChange
        self.onRemove = onRemove
        self.onAdd = onAdd
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            Button(action: handleAdd) {
                Text("选择文件")
                    .font(.system(size: 14, weight: .heavy))
                    .foregroundColor(theme.brandPrimary)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 12)
                    .background(theme.brandSubtle)
                    .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 2))
                    .clipShape(RoundedRectangle(cornerRadius: 16))
            }
            .buttonStyle(.plain)
            .disabled(disabled)

            if files.isEmpty {
                Text("尚未选择文件")
                    .font(.system(size: 13, weight: .semibold))
                    .foregroundColor(theme.textTertiary)
            } else {
                ForEach(files) { file in
                    HStack {
                        Text(file.name)
                            .font(.system(size: 14, weight: .bold))
                            .foregroundColor(theme.textPrimary)
                            .lineLimit(1)
                        Spacer()
                        Button(action: { handleRemove(file) }) {
                            Text("×")
                                .font(.system(size: 18, weight: .bold))
                                .foregroundColor(theme.textSecondary)
                                .padding(8)
                        }
                        .buttonStyle(.plain)
                        .disabled(disabled)
                        .accessibilityLabel("Remove \(file.name)")
                    }
                    .padding(.leading, 12)
                    .padding(.vertical, 2)
                    .background(theme.surfaceRaised)
                    .overlay(RoundedRectangle(cornerRadius: 14).stroke(theme.borderDefault, lineWidth: 1))
                    .clipShape(RoundedRectangle(cornerRadius: 14))
                }
            }
        }
        .opacity(disabled ? 0.45 : 1)
    }

    private func handleAdd() {
        guard !disabled else { return }
        let picked: [TspUploadFile]
        if let onAdd {
            picked = onAdd()
        } else {
            let stamp = String(Int(Date().timeIntervalSince1970 * 1000))
            let name: String
            if let accept, !accept.isEmpty {
                name = "file.\(accept)"
            } else {
                name = "mock-file.txt"
            }
            picked = [TspUploadFile(id: stamp, name: name)]
        }
        guard !picked.isEmpty else { return }
        onChange(multiple ? files + picked : Array(picked.prefix(1)))
    }

    private func handleRemove(_ file: TspUploadFile) {
        guard !disabled else { return }
        onRemove(file)
        onChange(files.filter { $0.id != file.id })
    }
}
