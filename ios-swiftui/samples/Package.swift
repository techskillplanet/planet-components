// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "PlanetComponentsSamples",
    platforms: [.iOS(.v15), .macOS(.v13)],
    products: [
        .library(name: "PlanetComponentsSamples", targets: ["PlanetComponentsSamples"])
    ],
    dependencies: [
        .package(path: "../library")
    ],
    targets: [
        .target(
            name: "PlanetComponentsSamples",
            dependencies: [
                .product(name: "PlanetComponents", package: "library")
            ],
            path: "Sources/PlanetComponentsSamples"
        )
    ]
)
