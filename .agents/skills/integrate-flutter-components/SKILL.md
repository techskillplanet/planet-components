---
name: integrate-flutter-components
description: >-
  Integrate tech_skill_planet_components from pub.dev into a Flutter app.
  Use when adding TechSkillPlanet Tsp* widgets, StarPlanetTheme, or Sky Planet theming.
---

# Integrate Flutter Components

## Install

```yaml
dependencies:
  tech_skill_planet_components: ^0.2.1
```

```bash
flutter pub get
```

## Usage

```dart
import 'package:tech_skill_planet_components/tech_skill_planet_components.dart';

TspButton(
  text: 'Get Started',
  variant: TspButtonVariant.primary,
  theme: StarPlanetTheme.sky,
  onTap: () {},
);
```

## Coverage

≈ **57** `Tsp*` widgets. Includes **DatePicker** (`TspDatePicker`) and **Domain-7**: `TspChildSwitcher`, `TspScoreRuleGrid`, `TspRedeemCardGrid`, `TspCalendarHeatmap`, `TspPrintSheet`, `TspBalanceHero`, `TspCheckInStreakCard`. W1: Avatar, Skeleton, Tooltip, Slider, TextArea, Drawer, InputNumber, Swiper. W2: Tag, Fab, TimePicker, Upload, Table, Tree, Cascader.

## Notes

- Package: https://pub.dev/packages/tech_skill_planet_components
- Themes: `StarPlanetTheme.sky|night|mint|sunrise` (or `StarPlanetTheme.fromKey('sunrise')`).
- `TspButton` supports `loading`.
- Keep MaterialApp / theme separate; pass `StarPlanetTheme` into Tsp* widgets.
- Library version **0.2.1** (publish after `./tools/headless-check.sh` is green).
- Monorepo library: `flutter/library` (samples must use path dep, not hosted).
