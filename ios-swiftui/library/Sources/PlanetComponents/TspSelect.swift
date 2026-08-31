import SwiftUI
#if canImport(UIKit)
import UIKit
#endif

public struct TspSelect: View {
    let options: [String]
    @Binding var selectedIndex: Int
    let title: String
    let disabled: Bool
    let theme: StarPlanetTheme

    @State private var sheetVisible = false

    public init(
        options: [String],
        selectedIndex: Binding<Int>,
        title: String = "请选择",
        disabled: Bool = false,
        theme: StarPlanetTheme = .sky
    ) {
        self.options = options
        self._selectedIndex = selectedIndex
        self.title = title
        self.disabled = disabled
        self.theme = theme
    }

    public var body: some View {
        Button {
            guard !disabled else { return }
            sheetVisible = true
        } label: {
            HStack {
                Text(options.indices.contains(selectedIndex) ? options[selectedIndex] : "")
                Spacer()
                Text("⌄").foregroundColor(theme.textTertiary)
            }
            .padding(.horizontal, 14)
            .frame(minHeight: 48)
            .foregroundColor(theme.textPrimary)
            .background(theme.surfaceRaised)
            .overlay(RoundedRectangle(cornerRadius: 16).stroke(theme.borderDefault, lineWidth: 1))
            .clipShape(RoundedRectangle(cornerRadius: 16))
        }
        .buttonStyle(.plain)
        .opacity(disabled ? 0.45 : 1)
        #if os(iOS)
        .fullScreenCover(isPresented: $sheetVisible) {
            sheetContent
        }
        #else
        .sheet(isPresented: $sheetVisible) {
            sheetContent
        }
        #endif
    }

    private var sheetContent: some View {
        TspOptionSheet(
            title: title,
            options: options,
            selectedIndex: selectedIndex,
            theme: theme,
            onSelect: { index in
                selectedIndex = index
                sheetVisible = false
            },
            onCancel: { sheetVisible = false }
        )
        #if canImport(UIKit)
        .background(ClearFullScreenBackground())
        #endif
    }
}

#if canImport(UIKit)
private struct ClearFullScreenBackground: UIViewRepresentable {
    func makeUIView(context: Context) -> UIView {
        let view = UIView()
        DispatchQueue.main.async {
            view.superview?.superview?.backgroundColor = .clear
        }
        return view
    }

    func updateUIView(_ uiView: UIView, context: Context) {}
}
#endif
