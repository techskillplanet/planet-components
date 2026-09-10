# Planet icons

Canonical sources:

- SVG: `design/icons/*.svg` (`24×24`, `currentColor` stroke)
- Catalog JSON: `design/icons/planet-icons.json` (path + glyph)

| Name | Glyph |
| --- | --- |
| back | ‹ |
| check | ✓ |
| close | × |
| chevron | › |
| warning | ! |
| search | ⌕ |
| home | ⌂ |
| settings | ⚙ |
| plus | + |
| minus | − |
| info | ℹ |
| star | ★ |
| menu | ☰ |

## Sync

```bash
node tools/sync-icons.cjs
node tools/check-icon-catalog.cjs
```

## Runtime APIs (helper — not in the 50-component contract)

| Stack | API |
| --- | --- |
| React Web | `TspIcon` + `PLANET_ICONS` |
| Vue Web | `TspIcon` + `PLANET_ICONS` |
| React Native | `TspIcon` + `PLANET_ICON_GLYPHS` |
| Flutter | `TspIcon` + `planetIcons` |
| iOS SwiftUI | `TspIcon` + `PlanetIcons` |
| Mini Program | `bc-icon` + `theme/planet-icons.json` |
| Android View | `BasicIconView` + `PlanetIcons` (extension / private) |

Keep SVG + `planet-icons.json` as the design source of truth when adding icons.
