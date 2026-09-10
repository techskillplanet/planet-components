# Android extension / private widgets

The **public** cross-stack contract lives in `component_contract.json` (57 components).
Android View historically ships additional `Basic*` widgets used by products and samples (11 extensions).

## Policy

| Kind | Meaning |
| --- | --- |
| **Contract** | Must exist on all 8 stacks; checked by `tools/check-contract-inventory.cjs` |
| **android-private** | Android-only helper / product chrome; do not require other stacks |
| **candidate-for-contract** | Useful enough to consider promoting into the 57-set later |

Allowlist source of truth: [`android/library/extensions.json`](../android/library/extensions.json).

Gate:

```bash
node tools/check-android-extensions.cjs
```

Every `Basic*.java` under `android/library/.../widget/` must be either resolved by the contract inventory **or** listed in `extensions.json`.

## Promoting a candidate

1. Add the component to `component_contract.json` + `docs/COMPONENT_CONTRACT.md`.
2. Implement `Tsp*` / `bc-*` on the other stacks (or use `tools/scaffold-component.cjs`).
3. Remove it from `extensions.json`.
4. Re-run contract inventory + android extension checks.
