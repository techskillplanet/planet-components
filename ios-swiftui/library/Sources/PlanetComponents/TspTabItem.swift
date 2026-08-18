import SwiftUI

public struct TspTabItem: Identifiable {
    public let id: String
    public let icon: String
    public let title: String
    public init(id: String, icon: String, title: String) {
        self.id = id
        self.icon = icon
        self.title = title
    }
}
