# Visual regression (Chromatic)

React Web Storybook is the visual source of truth for Planet Components.

## Local / CI

```bash
cd react-web/library
npm run build-storybook
# Requires Chromatic project token from https://www.chromatic.com
CHROMATIC_PROJECT_TOKEN=... npm run chromatic
```

CI job `chromatic` in `.github/workflows/ci.yml` runs when `CHROMATIC_PROJECT_TOKEN` is set as a repository secret. Without the secret the job skips (exit 0).

## What is captured

Stories under `react-web/library/stories/**` including Basics + ContractExtras (Checkbox, Collapse, Divider, Radio, SearchBar, SegmentedControl, StarRating).

## Relationship to DOM smoke

`tests/visual-smoke.test.js` catches class/structure regressions in unit CI without Chromatic. Chromatic catches pixel diffs across browsers.
