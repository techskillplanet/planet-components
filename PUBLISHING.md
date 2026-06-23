# Publishing Plan

Each `library` directory is intended to become an independently published open-source package.

| Platform | Package manager | Current package identity |
| --- | --- | --- |
| Android View | Maven Central / GitHub Packages | `io.github.techskillplanet:planet-components-android` |
| React Native | npm | `@techskillplanet/planet-components-react-native` |
| React Web | npm | `@techskillplanet/planet-components-react` |
| Vue Web | npm | `@techskillplanet/planet-components-vue` |
| Flutter | pub.dev | `tech_skill_planet_components` |
| iOS SwiftUI | Swift Package Manager | `TechSkillPlanetBasicControls` |
| WeChat Mini Program | npm / miniprogram package | `@techskillplanet/planet-components-miniprogram` |
| Kuikly | Maven / internal Kuikly package | `com.techskillplanet:planet-components-kuikly` |

Before publishing a library:

1. Run the platform-specific sample.
2. Run the package build/dry-run command.
3. Confirm sample uses the local library dependency.
4. Confirm build caches are not included.
5. Confirm package metadata points to `techskillplanet/planet-components`.
