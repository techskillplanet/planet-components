import SwiftUI
import PlanetComponentsSamples
#if os(macOS)
import AppKit
#endif

@main
struct PlanetComponentsSampleApp: App {
    #if os(macOS)
    @NSApplicationDelegateAdaptor(AppDelegate.self) private var appDelegate
    #endif

    var body: some Scene {
        WindowGroup {
            BasicControlsSampleView()
                #if os(macOS)
                .frame(minWidth: 390, minHeight: 720)
                #endif
        }
        #if os(macOS)
        .defaultSize(width: 420, height: 820)
        #endif
    }
}

#if os(macOS)
private final class AppDelegate: NSObject, NSApplicationDelegate {
    func applicationDidFinishLaunching(_ notification: Notification) {
        NSApp.setActivationPolicy(.regular)
        NSApp.activate(ignoringOtherApps: true)
    }

    func applicationShouldTerminateAfterLastWindowClosed(_ sender: NSApplication) -> Bool {
        true
    }
}
#endif
