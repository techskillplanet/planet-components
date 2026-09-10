import {
  starPlanetTheme,
  starPlanetThemes,
  starPlanetStyleProfiles,
  planetMotion,
} from './theme.generated.js';

export {
  starPlanetTheme,
  starPlanetThemes,
  starPlanetStyleProfiles,
  planetMotion,
};

export const builtInThemePresets = [
  { label: 'Sky Planet · 岛屿阴影', colorKey: 'sky', styleProfile: 'island_raised' },
  { label: 'Sky Planet · 扁平无影', colorKey: 'sky', styleProfile: 'island_flat' },
  { label: 'Star Planet · 岛屿阴影', colorKey: 'night', styleProfile: 'island_raised' },
  { label: 'Star Planet · 扁平无影', colorKey: 'night', styleProfile: 'island_flat' },
  { label: 'Mint Planet · 岛屿阴影', colorKey: 'mint', styleProfile: 'island_raised' },
  { label: 'Mint Planet · 扁平无影', colorKey: 'mint', styleProfile: 'island_flat' },
  { label: 'Sunrise Planet · 岛屿阴影', colorKey: 'sunrise', styleProfile: 'island_raised' },
  { label: 'Sunrise Planet · 扁平无影', colorKey: 'sunrise', styleProfile: 'island_flat' },
];

export function resolveTheme(colorKey = 'sky', styleProfile = 'island_raised') {
  const colors = starPlanetThemes[colorKey] || starPlanetThemes.sky;
  const style = starPlanetStyleProfiles[styleProfile] || starPlanetStyleProfiles.island_raised;
  return {
    ...colors,
    ...style,
    colorKey,
    styleProfile,
    buttonHeight: style.buttonFaceHeight + style.shadowControlIslandLiftY,
    motion: planetMotion,
  };
}

export function themeVars(theme = starPlanetTheme) {
  const motion = theme.motion || planetMotion;
  return {
    '--bc-page-start': theme.pageStart,
    '--bc-page-end': theme.pageEnd,
    '--bc-text-primary': theme.textPrimary,
    '--bc-text-secondary': theme.textSecondary,
    '--bc-text-tertiary': theme.textTertiary,
    '--bc-surface': theme.surfaceRaised,
    '--bc-surface-raised': theme.surfaceRaised,
    '--bc-surface-subtle': theme.surfaceSubtle ?? theme.pageEnd,
    '--bc-border': theme.borderDefault,
    '--bc-border-default': theme.borderDefault,
    '--bc-brand-primary': theme.brandPrimary,
    '--bc-brand-dark': theme.brandDark,
    '--bc-brand-soft': theme.brandSubtle ?? theme.selectedFill,
    '--bc-brand-subtle': theme.brandSubtle ?? theme.selectedFill,
    '--bc-success': theme.success,
    '--bc-success-subtle': theme.successSubtle ?? theme.selectedFill,
    '--bc-warning': theme.warning,
    '--bc-selected-fill': theme.selectedFill,
    '--bc-selected-border': theme.selectedBorder ?? theme.brandPrimary,
    '--bc-emphasis-fill': theme.emphasisFill ?? theme.surfaceSubtle ?? theme.pageEnd,
    '--bc-active-fill': theme.activeFill,
    '--bc-danger': theme.danger,
    '--bc-switch-off-bg': theme.switchOffBg ?? '#C8EAFF',
    '--bc-switch-off-border': theme.switchOffBorder ?? '#C8EAFF',
    '--bc-switch-off-text': theme.switchOffText ?? '#365D82',
    '--bc-switch-on-bg': theme.switchOnBg ?? '#31A8FF',
    '--bc-switch-on-border': theme.switchOnBorder ?? '#31A8FF',
    '--bc-switch-on-text': theme.switchOnText ?? '#FFFFFF',
    '--bc-switch-handle-bg': theme.switchHandleBg ?? '#FFFFFF',
    '--bc-switch-handle-border': theme.switchHandleBorder ?? '#C8EAFF',
    '--bc-switch-handle-checked-border': theme.switchHandleCheckedBorder ?? '#31A8FF',
    '--bc-switch-spinner': theme.switchSpinner ?? '#FFFFFF',
    '--bc-shadow-control-island-lift-y': `${theme.shadowControlIslandLiftY ?? 5}px`,
    '--bc-button-face-height': `${theme.buttonFaceHeight ?? 46}px`,
    '--bc-button-height': `${(theme.buttonFaceHeight ?? 46) + (theme.shadowControlIslandLiftY ?? 5)}px`,
    '--bc-pressed-drop-y': `${theme.pressedDropY ?? 2}px`,
    '--bc-hover-lift-y': `${theme.hoverLiftY ?? -1}px`,
    '--bc-button-raised-shadow-display': theme.buttonRaisedShadowEnabled === false ? 'none' : 'block',
    '--bc-card-island-shadow': theme.cardIslandShadow ?? (theme.buttonRaisedShadowEnabled === false ? 'none' : '0 14px 34px rgba(49, 168, 255, 0.20), 0 2px 8px rgba(23, 58, 98, 0.06)'),
    '--bc-space-1': '4px',
    '--bc-space-2': '8px',
    '--bc-space-3': '12px',
    '--bc-space-4': '16px',
    '--bc-space-5': '20px',
    '--bc-space-form-label': '6px',
    '--bc-space-form-field': '16px',
    '--bc-space-form-action': '20px',
    '--bc-space-form-hint': '8px',
    '--bc-space-form-hint-min': '40px',
    '--bc-motion-fast': `${motion.durationFast ?? 150}ms`,
    '--bc-motion-base': `${motion.durationBase ?? 220}ms`,
    '--bc-motion-slow': `${motion.durationSlow ?? 320}ms`,
    '--bc-ease-out': motion.easeOut ?? 'cubic-bezier(0, 0, 0.2, 1)',
    '--bc-ease-std': motion.easeStandard ?? 'cubic-bezier(0.4, 0, 0.2, 1)',
  };
}
