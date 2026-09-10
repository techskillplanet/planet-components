# Figma ↔ Planet tokens

Planet keeps **code tokens** as the source of truth:

- `design/tokens/color_token.json`
- `design/tokens/style_token.json`

Figma Variables should mirror those semantic names — not invent parallel `blue/500` scales.

## Manifest

Generate the variable map:

```bash
node tools/build-figma-manifest.cjs
```

Output: `design/tokens/generated/figma-variables.manifest.json`

Each entry has:

| Field | Meaning |
| --- | --- |
| `collection` | Suggested Figma Variable collection |
| `figmaName` | Variable name (`brand/primary`) |
| `tokenPath` | Path inside the JSON token files |
| `type` | `COLOR` / `FLOAT` / `STRING` |
| `value` | Current resolved or raw token value |

## Sync workflow

1. Edit canonical JSON tokens in the repo.
2. `node tools/build-tokens.cjs && node tools/build-figma-manifest.cjs`
3. Update Figma Variables from the manifest (plugin, Tokens Studio, or manual).
4. Prefer **Code Connect** snippets that import published packages:
   - React: `@techskillplanet/planet-components-react` → `Tsp*`
   - Avoid pasting sample-app implementation code.

## Code Connect scope (recommended first pass)

Map these high-traffic components first:

`Button`, `Card`, `Input`, `Switch`, `TopBar`, `Tabs`, `Modal`, `Alert`

Use contract props from `component_contract.json` / `docs/COMPONENT_CONTRACT.md`.

Example React snippet for Figma Code Connect:

```jsx
import { TspButton } from '@techskillplanet/planet-components-react';
import '@techskillplanet/planet-components-react/styles.css';

export default function ButtonPrimary() {
  return <TspButton text="Continue" variant="primary" onTap={() => {}} />;
}
```

## Out of scope (for now)

- Bidirectional auto-push into Figma from CI (needs org Figma token + plugin).
- Pixel-perfect visual regression against Figma frames (React DOM smoke covers class/structure regressions in `react-web/library/tests/visual-smoke.test.js`).
