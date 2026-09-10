# Changelog

All notable changes to Planet Components are documented here.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/).

## [Unreleased]

### Added
- W2 common controls: Tag, Fab, TimePicker, Upload, Table, Tree, Cascader (inventory ≈ **57**)
- Samples + docs/catalog/Storybook previews for W2 across eight stacks
- W1 common controls: Avatar, Skeleton, Tooltip, Slider, TextArea, Drawer, InputNumber, Swiper (inventory ≈ **50**)
- Samples + docs/catalog previews for W1 across React Web / Vue / RN / Android
- Contract wave: Checkbox, Collapse, Divider, Radio, SearchBar, SegmentedControl, StarRating (42-component inventory)
- Mini Program `bc-*` + Kuikly `Tsp*` implementations for the 7 new contract controls
- Android listeners: Checkbox/Radio `OnCheckedChangeListener`, Collapse `OnExpandChangeListener`; SearchBar `setBasicDisabled`; StarRating disabled
- Token build pipeline (`tools/build-tokens.cjs`) generating runtime themes + CSS
- GitHub Actions CI, token drift + contract inventory gates
- React Storybook skeleton + axe a11y tests
- Domain-7 sample demos on all eight stacks
- React `TspIcon` + `design/icons` SVG catalog (initial set)
- Cross-stack icon sync (`tools/sync-icons.cjs`) → Vue/RN/Flutter/iOS/Mini/Android helpers
- `tools/scaffold-component.cjs` for cross-stack stubs
- Android extension catalog (`android/library/extensions.json` + `check-android-extensions`)
- Skills ↔ contract gate (`tools/check-skills-contract.cjs`)
- Icon catalog gate (`tools/check-icon-catalog.cjs`)
- React Web visual/DOM smoke snapshots (`tests/visual-smoke.test.js`)
- Chromatic visual regression wiring (`docs/VISUAL_REGRESSION.md`, CI job skips without token)
- Contract wave +7: Checkbox, Collapse, Divider, Radio, SearchBar, SegmentedControl, StarRating (8 stacks; inventory **42**)
- Cross-stack a11y deepen (RN/Flutter/iOS/Android/Mini motion + roles); `docs/API_REFERENCE.md` generated
- Icon catalog expanded to 13 names (search/home/settings/plus/minus/info/star/menu)
- Figma Variables manifest + `docs/FIGMA.md` (`tools/build-figma-manifest.cjs`)
- Android Robolectric scenario tests (TopBar immersive, Segmented/Tabs, edge-to-edge, tokens, icons)

### Changed
- Promoted 7 Android candidates into the public contract; removed from `extensions.json`
- Inventory baseline ≈ **57** components (was ≈ **50**; prior ≈ **42**)

### Fixed
- Android `style_token.json` / `color_token.json` sync with `design/tokens`
- Progress accessible name (`aria-label`) on React/Vue
- Android TopBar transparent status band + SegmentedControl brand selection

## [0.2.1] - 2026-09-09

### Added
- DatePicker + Domain-7 components across eight stacks
- Sunrise theme parity
- Button `loading` on non-React stacks

## [0.2.0] - 2026-09

### Added
- Initial public packages across npm / Maven Central / pub.dev / SPM / CocoaPods
