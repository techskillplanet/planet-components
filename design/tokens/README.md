# Design tokens

Canonical sources:

- `design/tokens/color_token.json`
- `design/tokens/style_token.json`

Build runtime artifacts:

```bash
node tools/build-tokens.cjs
```

Generated:

| Path | Purpose |
| --- | --- |
| `design/tokens/generated/runtime-themes.json` | Flat sky/night/mint/sunrise + island profiles + motion |
| `design/tokens/generated/tokens.css` | CSS variables + `prefers-reduced-motion` |
| `*/theme.generated.js` | Consumed by React / Vue / RN `theme.js` |
| `android/.../assets/theme/*.json` | Exact copies of canonical JSON |

After editing tokens, always re-run `build-tokens` and `check-token-drift`.

Figma Variables map:

```bash
node tools/build-figma-manifest.cjs
```

See `docs/FIGMA.md`.
