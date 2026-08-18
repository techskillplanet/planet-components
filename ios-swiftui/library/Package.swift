// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "PlanetComponents",
    platforms: [.iOS(.v15), .macOS(.v13)],
    products: [
        .library(name: "PlanetComponents", targets: ["PlanetComponents"])
    ],
    targets: [
        .target(name: "PlanetComponents", path: "Sources/PlanetComponents")
    ]
)
