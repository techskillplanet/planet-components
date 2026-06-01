// swift-tools-version: 5.9
import PackageDescription

let package = Package(
    name: "TechSkillPlanetBasicControls",
    platforms: [.iOS(.v15), .macOS(.v13)],
    products: [
        .library(name: "TechSkillPlanetBasicControls", targets: ["TechSkillPlanetBasicControls"])
    ],
    targets: [
        .target(name: "TechSkillPlanetBasicControls", path: "Sources/TechSkillPlanetBasicControls")
    ]
)
