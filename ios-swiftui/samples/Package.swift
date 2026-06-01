// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "TechSkillPlanetBasicControlsSamples",
    platforms: [.iOS(.v15), .macOS(.v13)],
    products: [
        .library(name: "BasicControlsSamples", targets: ["BasicControlsSamples"])
    ],
    dependencies: [
        .package(path: "../library")
    ],
    targets: [
        .target(
            name: "BasicControlsSamples",
            dependencies: [
                .product(name: "TechSkillPlanetBasicControls", package: "library")
            ],
            path: "Sources/BasicControlsSamples"
        )
    ]
)
