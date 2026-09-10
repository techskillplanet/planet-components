# @techskillplanet/planet-components-react

> TechSkillPlanet themed React basic controls library with Star Planet design language.

A complete set of 25 production-ready UI components for React (>=18) featuring token-driven theming, island-raised 3D design, multi-theme support, and full accessibility attributes.

## Installation

```bash
npm install @techskillplanet/planet-components-react
# or
yarn add @techskillplanet/planet-components-react
# or
pnpm add @techskillplanet/planet-components-react
```

**Peer Dependency:** React >= 18

## Local Storybook

```bash
cd react-web/library
npm install
npm run storybook
```

Stories live in `stories/`. Accessibility baseline: [`docs/A11Y.md`](../../docs/A11Y.md) · axe gate: `npm test` (`tests/a11y.test.js`).

## Quick Start

```jsx
import React from 'react';
import { TspButton, starPlanetTheme } from '@techskillplanet/planet-components-react';
import '@techskillplanet/planet-components-react/styles.css';

function App() {
  return (
    <TspButton
      text="Get Started"
      variant="primary"
      theme={starPlanetTheme}
      onTap={() => console.log('clicked')}
    />
  );
}
```

## Import Paths

| Path | Contents |
| --- | --- |
| `@techskillplanet/planet-components-react` | All components + theme utilities |
| `@techskillplanet/planet-components-react/theme` | Theme objects and helpers only |
| `@techskillplanet/planet-components-react/styles.css` | Required CSS stylesheet |

> **Important:** Import `styles.css` once in your app shell. All components depend on these CSS rules.

---

## Theming

### Built-in Themes

| Theme Key | Description |
| --- | --- |
| `sky` | Default blue-sky planet theme (light) |
| `night` | Dark mode with deep blue tones |
| `mint` | Green/mint fresh palette |
| `sunrise` | Warm orange/amber palette |

### Usage

```jsx
import { starPlanetThemes, resolveTheme, themeVars } from '@techskillplanet/planet-components-react/theme';

// Use a preset theme
const theme = starPlanetThemes.night;

// Or resolve with a style profile
const themeWithStyle = resolveTheme('sky', 'island_raised');

// Get CSS variables object for custom styling
const vars = themeVars(theme);
```

### Style Profiles

| Profile | Effect |
| --- | --- |
| `island_raised` | 3D island shadow with lift effect |
| `island_flat` | Flat appearance without shadows |

### Custom Theme

Create your own theme by providing an object with these keys:

```js
const myTheme = {
  pageStart: '#F0F8FF',        // Gradient start
  pageEnd: '#FFFFFF',          // Gradient end
  textPrimary: '#1A1A2E',     // Primary text
  textSecondary: '#4A4A6A',   // Secondary text
  textTertiary: '#8A8AA0',    // Tertiary text
  surfaceRaised: '#FFFFFF',   // Card/surface background
  borderDefault: '#E0E0F0',   // Default border color
  brandPrimary: '#6C5CE7',    // Primary brand color
  brandDark: '#4834D4',       // Darker brand shade
  success: '#00B894',         // Success color
  warning: '#FDCB6E',         // Warning color
  selectedFill: '#F0EDFF',    // Selected item background
  activeFill: '#FFF8E1',      // Active item background
  danger: '#E17055',          // Danger/error color
};
```

---

## Components

### Actions

#### TspButton

Primary action button with island-raised 3D shadow.

```jsx
<TspButton text="Submit" variant="primary" theme={theme} onTap={handleClick} />
<TspButton text="Cancel" variant="default" theme={theme} />
<TspButton text="Delete" variant="danger" disabled theme={theme} />
<TspButton variant="text" theme={theme}>Custom JSX</TspButton>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `text` | string | — | Button label |
| `variant` | `'primary'\|'default'\|'danger'\|'text'` | `'default'` | Visual variant |
| `disabled` | boolean | `false` | Disable interaction |
| `fullWidth` | boolean | `true` | Take full container width |
| `theme` | object | `starPlanetTheme` | Theme object |
| `onTap` | function | — | Click handler |
| `children` | ReactNode | — | Custom content (overrides text) |

---

#### TspChip

Selectable tag for filtering and lightweight selection.

```jsx
<TspChip text="React" selected onTap={toggle} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `text` | string | — | Chip label |
| `selected` | boolean | `false` | Selected state |
| `disabled` | boolean | `false` | Disable interaction |
| `theme` | object | `starPlanetTheme` | Theme |
| `onTap` | function | — | Click handler |

---

#### TspIconButton

Icon-only action button for toolbars.

```jsx
<TspIconButton icon="★" variant="primary" selected onTap={fn} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `icon` | string/ReactNode | — | Icon content |
| `variant` | `'default'\|'primary'` | `'default'` | Variant |
| `selected` | boolean | `false` | Selected state |
| `disabled` | boolean | `false` | Disabled |
| `theme` | object | `starPlanetTheme` | Theme |
| `onTap` | function | — | Click handler |

---

#### TspTextLink

Text link for secondary navigation.

```jsx
<TspTextLink text="Learn more" onTap={navigate} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `text` | string | — | Link text |
| `inverse` | boolean | `false` | Light color variant |
| `theme` | object | `starPlanetTheme` | Theme |
| `onTap` | function | — | Click handler |

---

### Surfaces

#### TspCard

Content container with rounded-island surface.

```jsx
<TspCard selected theme={theme}>
  <h3>Title</h3>
  <p>Card body content</p>
</TspCard>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `variant` | string | `'default'` | Card variant |
| `selected` | boolean | `false` | Highlight state |
| `disabled` | boolean | `false` | Dimmed appearance |
| `theme` | object | `starPlanetTheme` | Theme |
| `children` | ReactNode | — | Card content |

---

#### TspListItem

List row with title, message and trailing element.

```jsx
<TspListItem title="Settings" message="App preferences" trailing="›" onTap={goTo} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `title` | string | — | Primary text |
| `message` | string | — | Secondary text |
| `trailing` | string/ReactNode | — | Right-side element |
| `selected` | boolean | `false` | Selected state |
| `disabled` | boolean | `false` | Disabled |
| `theme` | object | `starPlanetTheme` | Theme |
| `onTap` | function | — | Click handler |

---

#### TspEmpty

Empty state placeholder.

```jsx
<TspEmpty title="No Data" message="Try again later" actionText="Refresh" onAction={retry} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `title` | string | — | Title |
| `message` | string | — | Description |
| `actionText` | string | — | Action button text |
| `theme` | object | `starPlanetTheme` | Theme |
| `onAction` | function | — | Action handler |

---

### Feedback

#### TspAlert

Inline alert banner.

```jsx
<TspAlert title="Success" message="Operation completed" variant="success" theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `title` | string | — | Alert title |
| `message` | string | — | Alert body |
| `variant` | `'info'\|'success'\|'warning'\|'error'` | `'info'` | Semantic color |
| `theme` | object | `starPlanetTheme` | Theme |

---

#### TspBadge

Short text status badge.

```jsx
<TspBadge text="NEW" variant="primary" theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `text` | string | — | Badge text |
| `variant` | `'default'\|'primary'\|'success'\|'warning'\|'danger'` | `'default'` | Color variant |
| `disabled` | boolean | `false` | Dimmed |
| `theme` | object | `starPlanetTheme` | Theme |

---

#### TspProgress

Horizontal progress bar.

```jsx
<TspProgress progress={75} variant="success" theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `progress` | number | `0` | Value 0-100 (clamped) |
| `variant` | `'primary'\|'success'\|'warning'\|'danger'` | `'primary'` | Color |
| `theme` | object | `starPlanetTheme` | Theme |

---

#### TspNotification

Notification card for reminders.

```jsx
<TspNotification title="Reminder" message="Task due today" variant="alert" theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `title` | string | — | Title |
| `message` | string | — | Message |
| `variant` | `'info'\|'alert'` | `'info'` | Variant |
| `theme` | object | `starPlanetTheme` | Theme |

---

#### TspToast

Temporary toast notification.

```jsx
<TspToast message="Saved!" variant="success" theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `message` | string | — | Toast text |
| `variant` | `'info'\|'success'\|'warning'\|'error'` | `'info'` | Color |
| `duration` | number | `1800` | Display hint (ms) |
| `theme` | object | `starPlanetTheme` | Theme |

> Note: Auto-dismiss logic should be handled by the parent component.

---

#### TspModal

Confirmation dialog.

```jsx
<TspModal
  title="Delete item?"
  message="This action cannot be undone."
  confirmText="Delete"
  cancelText="Cancel"
  theme={theme}
  onConfirm={handleDelete}
  onCancel={close}
/>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `title` | string | — | Dialog title |
| `message` | string | — | Dialog body |
| `confirmText` | string | `'OK'` | Confirm button text |
| `cancelText` | string | `'Cancel'` | Cancel button text |
| `theme` | object | `starPlanetTheme` | Theme |
| `onConfirm` | function | — | Confirm handler |
| `onCancel` | function | — | Cancel handler |

---

### Inputs

#### TspInput

Single-line text input.

```jsx
<TspInput value={val} placeholder="Enter name" onChange={setVal} theme={theme} />
<TspInput value="" variant="error" placeholder="Required" theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `value` | string | `''` | Controlled value |
| `placeholder` | string | `''` | Placeholder |
| `variant` | `'default'\|'error'` | `'default'` | Appearance |
| `disabled` | boolean | `false` | Disabled |
| `theme` | object | `starPlanetTheme` | Theme |
| `onChange` | function | — | (newValue: string) => void |

---

#### TspSelect

Dropdown selector (opens TspOptionSheet).

```jsx
<TspSelect
  options={['Apple', 'Banana', 'Cherry']}
  selectedIndex={0}
  onSelect={(index, option) => setFruit(option)}
  theme={theme}
/>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `options` | Array | `[]` | String array or objects with title/text/label/value |
| `selectedIndex` | number | `0` | Selected option index |
| `disabled` | boolean | `false` | Disabled |
| `theme` | object | `starPlanetTheme` | Theme |
| `onSelect` | function | — | (index, option) => void |

---

#### TspOptionSheet

Bottom sheet option picker.

```jsx
<TspOptionSheet
  title="Choose"
  options={['A', 'B', 'C']}
  selectedIndex={1}
  visible={isOpen}
  onSelect={(idx, opt) => pick(opt)}
  onCancel={() => close()}
  theme={theme}
/>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `title` | string | `'请选择'` | Header title |
| `options` | Array | `[]` | Option list |
| `selectedIndex` | number | `0` | Selected index |
| `visible` | boolean | `false` | Show/hide |
| `theme` | object | `starPlanetTheme` | Theme |
| `onSelect` | function | — | (index, option) => void |
| `onCancel` | function | — | Dismiss handler |

---

#### TspSwitch

Toggle switch.

```jsx
<TspSwitch text="Notifications" checked={on} onChange={setOn} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `text` | string | — | Label text |
| `checked` | boolean | `false` | On/off state |
| `checkedText` | string | `''` | Text when on |
| `uncheckedText` | string | `''` | Text when off |
| `loading` | boolean | `false` | Loading state |
| `disabled` | boolean | `false` | Disabled |
| `theme` | object | `starPlanetTheme` | Theme |
| `onChange` | function | — | (newChecked: boolean) => void |

---

#### TspPinInput

PIN/verification code input.

```jsx
<TspPinInput
  value={code}
  cellCount={6}
  secure
  onChange={setCode}
  onComplete={(val) => verify(val)}
  theme={theme}
/>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `value` | string | `''` | Current input |
| `cellCount` | number | `4` | Cells (4-6) |
| `secure` | boolean | `false` | Mask with bullets |
| `theme` | object | `starPlanetTheme` | Theme |
| `onChange` | function | — | (value: string) => void |
| `onComplete` | function | — | Called when all cells filled |

---

### Navigation

#### TspTopBar

Top navigation bar.

```jsx
<TspTopBar title="Settings" showBack onBack={() => goBack()} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `title` | string | — | Title text |
| `showBack` | boolean | `false` | Show back button |
| `backgroundColor` | string | — | Override background |
| `theme` | object | `starPlanetTheme` | Theme |
| `onBack` | function | — | Back handler |

---

#### TspBottomTab

Bottom tab bar.

```jsx
<TspBottomTab
  tabs={[
    { key: 'home', title: 'Home', icon: '⌂' },
    { key: 'settings', title: 'Settings', icon: '⚙' }
  ]}
  selectedKey="home"
  onSelect={(key) => setPage(key)}
  theme={theme}
/>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `tabs` | Array<{key, title, icon?}> | `[]` | Tab entries |
| `selectedKey` | string | — | Active tab key |
| `theme` | object | `starPlanetTheme` | Theme |
| `onSelect` | function | — | (key, tab) => void |

---

#### TspTabs

In-page segmented tabs.

```jsx
<TspTabs tabs={['All', 'Active', 'Done']} selectedIndex={0} onSelect={setIdx} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `tabs` | Array<string> | `[]` | Tab labels |
| `selectedIndex` | number | `0` | Active index |
| `theme` | object | `starPlanetTheme` | Theme |
| `onSelect` | function | — | (index, tab) => void |

---

#### TspStickyFooter

Fixed bottom action area.

```jsx
<TspStickyFooter theme={theme}>
  <TspButton text="Confirm" variant="primary" theme={theme} />
</TspStickyFooter>
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `theme` | object | `starPlanetTheme` | Theme |
| `children` | ReactNode | — | Footer content |

---

### Data

#### TspAmount

Monetary/numeric display.

```jsx
<TspAmount symbol="$" value="99.00" cycle="month" theme={theme} />
<TspAmount symbol="¥" value="199" strikeThrough theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `symbol` | string | `'¥'` | Currency symbol |
| `value` | string | — | Numeric value |
| `cycle` | string | `''` | Billing cycle |
| `symbolAfter` | boolean | `false` | Symbol position |
| `strikeThrough` | boolean | `false` | Strikethrough |
| `theme` | object | `starPlanetTheme` | Theme |

---

#### TspKeyValueLabel

Key-value pair display.

```jsx
<TspKeyValueLabel label="Progress" value="12/48" theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `label` | string | — | Label text |
| `value` | string | — | Value text |
| `theme` | object | `starPlanetTheme` | Theme |

---

#### TspStepper

Step progress indicator.

```jsx
<TspStepper stepCount={4} currentStep={2} theme={theme} />
```

| Prop | Type | Default | Description |
| --- | --- | --- | --- |
| `stepCount` | number | `3` | Total steps (3-5) |
| `currentStep` | number | `1` | Current step (1-based) |
| `theme` | object | `starPlanetTheme` | Theme |

---

## Utilities

| Export | Description |
| --- | --- |
| `cx(...names)` | Join truthy class names |
| `clamp(value, min, max)` | Clamp numeric value |
| `optionText(option)` | Extract display text from option |
| `themed(theme, style?)` | Merge theme CSS vars with inline style |
| `themeVars(theme)` | Convert theme to CSS custom properties |
| `resolveTheme(colorKey?, styleProfile?)` | Build full theme from preset |

---

## Browser Compatibility

This library uses standard CSS custom properties and React 18 APIs. Compatible with:

- Chrome 89+
- Firefox 108+
- Safari 16.4+
- Edge 89+

No build step required — works with native ES modules via import maps.

---

## Development

```bash
# Install dev dependencies
npm install

# Run unit tests
npm test

# Watch mode
npm run test:watch

# Syntax check
npm run check

# Dry-run package
npm run pack:dry
```

---

## License

MIT - TechSkillPlanet (技趣星球)
