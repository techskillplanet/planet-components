/**
 * @module @techskillplanet/planet-components-react
 *
 * TechSkillPlanet React Web Basic Controls library.
 * Provides a comprehensive set of themed UI components following the
 * Star Planet design language with token-driven theming.
 *
 * @example
 * import { TspButton, starPlanetTheme } from '@techskillplanet/planet-components-react';
 * import '@techskillplanet/planet-components-react/styles.css';
 *
 * function App() {
 *   return <TspButton text="Hello" variant="primary" theme={starPlanetTheme} />;
 * }
 */

// Theme system
export {
  starPlanetTheme,
  starPlanetThemes,
  starPlanetStyleProfiles,
  builtInThemePresets,
  resolveTheme,
  themeVars
} from './theme.js';

// Utility helpers
export { cx, clamp, optionText, themed } from './components/_shared.js';

// UI Components
export { TspButton } from './components/TspButton.js';
export { TspCard } from './components/TspCard.js';
export { TspAlert } from './components/TspAlert.js';
export { TspBadge } from './components/TspBadge.js';
export { TspChip } from './components/TspChip.js';
export { TspInput } from './components/TspInput.js';
export { TspSelect } from './components/TspSelect.js';
export { TspOptionSheet } from './components/TspOptionSheet.js';
export { TspSwitch } from './components/TspSwitch.js';
export { TspProgress } from './components/TspProgress.js';
export { TspTopBar } from './components/TspTopBar.js';
export { TspBottomTab } from './components/TspBottomTab.js';
export { TspTabs } from './components/TspTabs.js';
export { TspAmount } from './components/TspAmount.js';
export { TspIconButton } from './components/TspIconButton.js';
export { TspKeyValueLabel } from './components/TspKeyValueLabel.js';
export { TspNotification } from './components/TspNotification.js';
export { TspTextLink } from './components/TspTextLink.js';
export { TspStepper } from './components/TspStepper.js';
export { TspStickyFooter } from './components/TspStickyFooter.js';
export { TspPinInput } from './components/TspPinInput.js';
export { TspListItem } from './components/TspListItem.js';
export { TspEmpty } from './components/TspEmpty.js';
export { TspToast } from './components/TspToast.js';
export { TspModal } from './components/TspModal.js';
export { TspLoadingDialog } from './components/TspLoadingDialog.js';
export { TspRefreshLayout } from './components/TspRefreshLayout.js';
