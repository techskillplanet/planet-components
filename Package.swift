// swift-tools-version: 5.9
import PackageDescription

// SPM publish entry for the GitHub repo URL.
// Source of truth remains ios-swiftui/library; samples keep path: ../library.
let package = Package(
    name: "PlanetComponents",
    platforms: [.iOS(.v15), .macOS(.v13)],
    products: [
        .library(name: "PlanetComponents", targets: ["PlanetComponents"])
    ],
    targets: [
        .target(
            name: "PlanetComponents",
            path: "ios-swiftui/library/Sources/PlanetComponents"
        )
    ]
)
