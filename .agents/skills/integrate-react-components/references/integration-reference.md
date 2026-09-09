# React Component Integration Reference

## Package Overview

| Field | Value |
| --- | --- |
| Package name | `@techskillplanet/planet-components-react` |
| Version | `0.2.1` |
| Peer dependency | `react >= 18` |
| Module type | ESM (`type: "module"`) |
| CSS required | Yes — `import 'pkg/styles.css'` |
| Zero-build | Source-only package (no transpilation needed) |

## Architecture

```
react-web/library/
├── src/
│   ├── index.js              # Public API barrel file
│   ├── theme.js              # Theme definitions + utilities
│   ├── styles.css            # All component CSS rules
│   └── components/
│       ├── _shared.js        # Shared helpers (h, cx, clamp, themed)
│       ├── index.js          # Component re-exports
│       ├── TspButton.js      # One file per component
│       ├── TspCard.js
│       └── ... (25 total)
├── tests/
│   ├── setup.js              # Jest-dom matchers setup
│   └── components.test.js    # Full test suite (83 tests)
├── package.json
├── vitest.config.js
├── LICENSE
└── README.md
```

## Design Principles

1. **Token-driven theming** — All visual values flow from theme objects via CSS custom properties
2. **One component, one file** — Easy to tree-shake, easy to audit
3. **Zero framework deps** — Only React as peer dependency
4. **Accessible by default** — ARIA roles, labels, and semantic HTML
5. **Cross-platform parity** — Same component API across React Web, React Native, Flutter, SwiftUI, Android View, MiniProgram, Kuikly

## Theme Contract

Every theme object MUST provide these keys:

```
pageStart, pageEnd, textPrimary, textSecondary, textTertiary,
surfaceRaised, borderDefault, brandPrimary, brandDark,
success, warning, selectedFill, activeFill, danger
```

Optional style profile keys (for island 3D effect):
```
buttonRaisedShadowEnabled, shadowControlIslandLiftY,
shadowControlPressedY, pressedDropY, buttonFaceHeight
```

## Component API Contract

Every component follows these conventions:
- `theme` prop (optional, defaults to `starPlanetTheme`)
- `variant` prop where applicable
- `disabled` prop for interactive components
- `selected`/`checked` for state-based components
- `onTap`/`onChange`/`onSelect` for callbacks
- CSS class naming: `bc-{component}`, `bc-{component}--{variant}`, `bc-selected`, `bc-disabled`
