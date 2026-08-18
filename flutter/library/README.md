# tech_skill_planet_components

TechSkillPlanet（技趣星球）Flutter 基础控件库，Sky Planet 设计语言：天蓝主色、云感表面、岛屿式抬起控件。

## Installation

```yaml
dependencies:
  tech_skill_planet_components: ^0.2.0
```

```bash
flutter pub get
```

## Quick Start

```dart
import 'package:flutter/material.dart';
import 'package:tech_skill_planet_components/tech_skill_planet_components.dart';

class DemoPage extends StatelessWidget {
  const DemoPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: TspButton(
          text: 'Get Started',
          variant: TspButtonVariant.primary,
          theme: StarPlanetTheme.sky,
          onTap: () {},
        ),
      ),
    );
  }
}
```

## Themes

| Theme | Description |
| --- | --- |
| `StarPlanetTheme.sky` | Default blue-sky planet (light) |
| `StarPlanetTheme.night` | Dark deep-blue |
| `StarPlanetTheme.mint` | Fresh mint/green |
| `StarPlanetTheme.sunrise` | Warm orange/amber |

Pass `theme:` on each component, or wrap demos with the theme you need.

## Components

Public API is exported from the barrel file. Implementations live one-per-file under `lib/src/`.

```dart
import 'package:tech_skill_planet_components/tech_skill_planet_components.dart';
```

Includes `Tsp` controls such as Button, Switch, Checkbox, Radio, Input, Select, Dialog, Toast, Alert, Chip, Tabs, and related layout/feedback widgets. Common props: `variant`, `disabled`, `selected` / `checked`, `text` / `title` / `message`.

## Sample app

Runnable demos live in the monorepo at [`flutter/samples`](https://github.com/techskillplanet/planet-components/tree/main/flutter/samples) (path dependency on this library during development).

## Links

- Repository: https://github.com/techskillplanet/planet-components
- Publishing notes: https://github.com/techskillplanet/planet-components/blob/main/PUBLISHING.md
