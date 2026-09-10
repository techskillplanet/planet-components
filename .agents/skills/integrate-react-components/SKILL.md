---
name: integrate-react-components
description: >-
  Integrate @techskillplanet/planet-components-react into a React web app.
  Use when installing, theming, or composing Tsp* controls on React Web
  (Vite, Next.js, CRA) with Sky/Night/Mint/Sunrise themes.
---

# Integrate TechSkillPlanet React Components

## When to Use
- When building a React web app that needs TechSkillPlanet's Star Planet UI components
- When adding themed UI elements (buttons, cards, modals, inputs) to a React project
- When configuring multi-theme support with Sky, Night, Mint, or Sunrise palettes
- When needing accessible, token-driven UI components without heavy dependencies

## Prerequisites
- React >= 18 project (Vite, Next.js, CRA, or custom)
- Node.js >= 18
- npm, yarn, or pnpm package manager

## Workflow

### Step 1: Install the Package

```bash
npm install @techskillplanet/planet-components-react
```

### Step 2: Import Global Styles

In your app entry point (e.g., `main.jsx`, `App.jsx`, `layout.tsx`), import the CSS once:

```jsx
import '@techskillplanet/planet-components-react/styles.css';
```

### Step 3: Import Components and Theme

```jsx
import {
  TspButton,
  TspCard,
  TspInput,
  TspModal,
  starPlanetTheme,
  starPlanetThemes
} from '@techskillplanet/planet-components-react';
```

### Step 4: Use Components with Theme

Every component accepts a `theme` prop. Pass one of the built-in themes:

```jsx
function MyPage() {
  const theme = starPlanetThemes.sky; // or 'night', 'mint', 'sunrise'

  return (
    <TspCard theme={theme}>
      <h2>Welcome</h2>
      <TspButton text="Get Started" variant="primary" theme={theme} onTap={() => {}} />
    </TspCard>
  );
}
```

### Step 5: Switch Themes at Runtime

```jsx
import { useState } from 'react';
import { starPlanetThemes } from '@techskillplanet/planet-components-react';

function App() {
  const [themeKey, setThemeKey] = useState('sky');
  const theme = starPlanetThemes[themeKey];

  return (
    <div>
      <select onChange={(e) => setThemeKey(e.target.value)}>
        <option value="sky">Sky</option>
        <option value="night">Night</option>
        <option value="mint">Mint</option>
        <option value="sunrise">Sunrise</option>
      </select>
      <TspButton text="Themed Button" variant="primary" theme={theme} />
    </div>
  );
}
```

### Step 6: Create Custom Theme

```jsx
import { themeVars } from '@techskillplanet/planet-components-react';

const customTheme = {
  pageStart: '#F5F0FF',
  pageEnd: '#FFFFFF',
  textPrimary: '#2D1B69',
  textSecondary: '#5B4A8A',
  textTertiary: '#9B8DBF',
  surfaceRaised: '#FFFFFF',
  borderDefault: '#E8E0FF',
  brandPrimary: '#7C4DFF',
  brandDark: '#5E35B1',
  success: '#00C853',
  warning: '#FFD600',
  selectedFill: '#F3E5F5',
  activeFill: '#FFF8E1',
  danger: '#FF1744',
};

// Use with any component
<TspButton text="Custom" variant="primary" theme={customTheme} />
```

## Coverage

Inventory baseline ≈ **57** `Tsp*` controls. Themes: `starPlanetThemes.sky|night|mint|sunrise`. `TspButton` supports `loading`.

### DatePicker + Domain-7
- `TspDatePicker`
- `TspChildSwitcher`, `TspScoreRuleGrid`, `TspRedeemCardGrid`, `TspCalendarHeatmap`, `TspPrintSheet`, `TspBalanceHero`, `TspCheckInStreakCard`

### W1 / W2 common controls
- **W1**: `TspAvatar`, `TspSkeleton`, `TspTooltip`, `TspSlider`, `TspTextArea`, `TspDrawer`, `TspInputNumber`, `TspSwiper`
- **W2**: `TspTag`, `TspFab`, `TspTimePicker`, `TspUpload`, `TspTable`, `TspTree`, `TspCascader`

### Core catalog
- Actions: `TspButton` (`loading`), `TspChip`, `TspIconButton`, `TspTextLink`, `TspTag`, `TspFab`
- Surfaces: `TspCard`, `TspListItem`, `TspEmpty`
- Feedback: `TspAlert`, `TspBadge`, `TspProgress`, `TspNotification`, `TspToast`, `TspModal`, `TspLoadingDialog`
- Inputs: `TspInput`, `TspSelect`, `TspOptionSheet`, `TspSwitch`, `TspPinInput`, `TspTimePicker`, `TspUpload`
- Navigation: `TspTopBar`, `TspBottomTab`, `TspTabs`, `TspStickyFooter`
- Data: `TspAmount`, `TspKeyValueLabel`, `TspStepper`, `TspTable`, `TspTree`, `TspCascader`
- Also: `TspRefreshLayout`

## Common Patterns

### Form Page

```jsx
function FormPage({ theme }) {
  const [name, setName] = useState('');
  const [agree, setAgree] = useState(false);

  return (
    <>
      <TspTopBar title="Sign Up" showBack theme={theme} />
      <TspCard theme={theme}>
        <TspInput value={name} placeholder="Your name" onChange={setName} theme={theme} />
        <TspSwitch text="I agree" checked={agree} onChange={setAgree} theme={theme} />
      </TspCard>
      <TspStickyFooter theme={theme}>
        <TspButton text="Submit" variant="primary" disabled={!agree} theme={theme} />
      </TspStickyFooter>
    </>
  );
}
```

### List with Empty State

```jsx
function ItemList({ items, theme }) {
  if (!items.length) {
    return <TspEmpty title="No items" message="Add your first item" actionText="Add" theme={theme} />;
  }
  return items.map((item) => (
    <TspListItem key={item.id} title={item.name} trailing="›" theme={theme} />
  ));
}
```

## Testing Components

```bash
cd react-web/library
npm test          # Run all tests
npm run test:watch  # Watch mode
```

## Troubleshooting

| Issue | Solution |
| --- | --- |
| Components unstyled | Ensure `styles.css` is imported in app entry |
| Theme not applying | Pass `theme` prop to every component |
| TspSelect not opening | Verify React >= 18 (uses hooks) |
| Buttons too wide | Set `fullWidth={false}` on TspButton |

## Reference Files

- Library source: `react-web/library/src/`
- Theme definitions: `react-web/library/src/theme.js`
- CSS styles: `react-web/library/src/styles.css`
- Unit tests: `react-web/library/tests/`
- Sample app: `react-web/samples/index.html`
- Design tokens: `design/tokens/color_token.json`, `design/tokens/style_token.json`
