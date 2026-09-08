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
  tech_skill_planet_components: ^0.2.0
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

## Notes

- Package: https://pub.dev/packages/tech_skill_planet_components
- Keep MaterialApp / theme separate; pass `StarPlanetTheme` into Tsp* widgets.
- Monorepo library: `flutter/library` (samples must use path dep, not hosted).
