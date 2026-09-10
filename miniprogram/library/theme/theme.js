const THEMES = {
  sky: {
    key: 'sky',
    dark: false,
    pageStart: '#DDF4FF',
    pageEnd: '#F9FDFF',
    textPrimary: '#173A62',
    textSecondary: '#365D82',
    textTertiary: '#7895AE',
    surfaceRaised: '#FFFFFF',
    surfaceSubtle: '#F6FBFF',
    borderDefault: '#C8EAFF',
    brandPrimary: '#31A8FF',
    brandDark: '#1479D6',
    brandSubtle: '#E5F6FF',
    /** Sky-cyan feedback only — not selected surface */
    success: '#2BB8E6',
    successSubtle: '#E8F7FC',
    warning: '#FFD166',
    /** Selection = sky brandSubtle (never aurora green) */
    selectedFill: '#E5F6FF',
    selectedBorder: '#31A8FF',
    emphasisFill: '#F6FBFF',
    activeFill: '#FFF7D7',
    danger: '#FF6B7A',
    switchOffBackground: '#C8EAFF',
    switchOffBorder: '#C8EAFF',
    switchOffText: '#365D82',
    switchOnBackground: '#31A8FF',
    switchOnBorder: '#31A8FF',
    switchOnText: '#FFFFFF',
    switchHandleBackground: '#FFFFFF',
    switchHandleBorder: '#C8EAFF',
    switchHandleCheckedBorder: '#31A8FF',
    switchSpinner: '#FFFFFF',
  },
  night: {
    key: 'night',
    dark: true,
    pageStart: '#0F1A2E',
    pageEnd: '#141E32',
    textPrimary: '#E8F4FF',
    textSecondary: '#B8D0E8',
    textTertiary: '#7A94B0',
    surfaceRaised: '#1E2D45',
    surfaceSubtle: '#22324A',
    borderDefault: '#2A3F5C',
    brandPrimary: '#31A8FF',
    brandDark: '#1479D6',
    brandSubtle: '#1E3A5F',
    success: '#2BB8E6',
    successSubtle: '#1A3348',
    warning: '#FFD166',
    selectedFill: '#1E3A5F',
    selectedBorder: '#31A8FF',
    emphasisFill: '#22324A',
    activeFill: '#3A3020',
    danger: '#FF6B7A',
    switchOffBackground: '#2A3F5C',
    switchOffBorder: '#2A3F5C',
    switchOffText: '#B8D0E8',
    switchOnBackground: '#31A8FF',
    switchOnBorder: '#31A8FF',
    switchOnText: '#FFFFFF',
    switchHandleBackground: '#FFFFFF',
    switchHandleBorder: '#2A3F5C',
    switchHandleCheckedBorder: '#31A8FF',
    switchSpinner: '#FFFFFF',
  },
  mint: {
    key: 'mint',
    dark: false,
    pageStart: '#DFFAF2',
    pageEnd: '#F8FFFC',
    textPrimary: '#123F3A',
    textSecondary: '#2F6B63',
    textTertiary: '#6C938D',
    surfaceRaised: '#FFFFFF',
    surfaceSubtle: '#F3FFFA',
    borderDefault: '#BDEFE2',
    brandPrimary: '#20BFA9',
    brandDark: '#0C8F7E',
    brandSubtle: '#E6FFF4',
    success: '#35C58B',
    successSubtle: '#E8FBF3',
    warning: '#FFD166',
    selectedFill: '#E6FFF4',
    selectedBorder: '#20BFA9',
    emphasisFill: '#F3FFFA',
    activeFill: '#FFF7D7',
    danger: '#FF6B7A',
    switchOffBackground: '#BDEFE2',
    switchOffBorder: '#BDEFE2',
    switchOffText: '#2F6B63',
    switchOnBackground: '#20BFA9',
    switchOnBorder: '#20BFA9',
    switchOnText: '#FFFFFF',
    switchHandleBackground: '#FFFFFF',
    switchHandleBorder: '#BDEFE2',
    switchHandleCheckedBorder: '#20BFA9',
    switchSpinner: '#FFFFFF',
  },
  sunrise: {
    key: 'sunrise',
    dark: false,
    pageStart: '#FFE8D6',
    pageEnd: '#FFFDF8',
    textPrimary: '#4A2B1A',
    textSecondary: '#80523A',
    textTertiary: '#AA8068',
    surfaceRaised: '#FFFFFF',
    surfaceSubtle: '#FFF8F2',
    borderDefault: '#FFD1AD',
    brandPrimary: '#FF8A3D',
    brandDark: '#D85C12',
    brandSubtle: '#FFF1E7',
    success: '#2BB8E6',
    successSubtle: '#E8F7FC',
    warning: '#FFD166',
    selectedFill: '#FFF1E7',
    selectedBorder: '#FF8A3D',
    emphasisFill: '#FFF8F2',
    activeFill: '#FFF7D7',
    danger: '#E24C5C',
    switchOffBackground: '#FFD1AD',
    switchOffBorder: '#FFD1AD',
    switchOffText: '#80523A',
    switchOnBackground: '#FF8A3D',
    switchOnBorder: '#FF8A3D',
    switchOnText: '#FFFFFF',
    switchHandleBackground: '#FFFFFF',
    switchHandleBorder: '#FFD1AD',
    switchHandleCheckedBorder: '#FF8A3D',
    switchSpinner: '#FFFFFF',
  },
};

/** Island style profiles — aligns with React Web `starPlanetStyleProfiles`. */
const STYLE_PROFILES = {
  island_raised: {
    key: 'island_raised',
    buttonRaisedShadowEnabled: true,
    shadowControlIslandLiftY: 6,
    shadowControlPressedY: 2,
    pressedDropY: 2,
    hoverLiftY: -1,
    buttonFaceHeight: 46,
    cardIslandShadow: '0 14px 34px rgba(49, 168, 255, 0.20), 0 2px 8px rgba(23, 58, 98, 0.06)',
  },
  island_flat: {
    key: 'island_flat',
    buttonRaisedShadowEnabled: false,
    shadowControlIslandLiftY: 0,
    shadowControlPressedY: 0,
    pressedDropY: 0,
    hoverLiftY: 0,
    buttonFaceHeight: 46,
    cardIslandShadow: 'none',
  },
};

function getStyleProfile(styleProfile) {
  return STYLE_PROFILES[styleProfile] || STYLE_PROFILES.island_raised;
}

/**
 * Color-only lookup (backward compatible).
 * Merges `island_raised` style defaults so callers always get lift/height fields.
 */
function getTheme(key) {
  return resolveTheme(key, 'island_raised');
}

/**
 * Merge color token + style profile — aligns with React `resolveTheme(colorKey, styleProfile)`.
 */
function resolveTheme(colorKey, styleProfile) {
  const colors = THEMES[colorKey] || THEMES.sky;
  const style = getStyleProfile(styleProfile);
  return Object.assign({}, colors, style, {
    colorKey: colors.key,
    styleProfile: style.key,
    buttonHeight: style.buttonFaceHeight + style.shadowControlIslandLiftY,
  });
}

/** Distinct class per color key: theme-sky / theme-night / theme-mint / theme-sunrise. */
function themeClass(key) {
  const resolved = THEMES[key] ? key : 'sky';
  return `theme-${resolved}`;
}

/** Optional style class: style-island_raised / style-island_flat. */
function styleClass(styleProfile) {
  const resolved = STYLE_PROFILES[styleProfile] ? styleProfile : 'island_raised';
  return `style-${resolved}`;
}

/**
 * CSS custom properties for a resolved theme (page / host binding).
 */
function themeVars(theme) {
  const t = theme || resolveTheme();
  const lift = t.shadowControlIslandLiftY ?? 6;
  const face = t.buttonFaceHeight ?? 46;
  return {
    '--bc-page-start': t.pageStart,
    '--bc-page-end': t.pageEnd,
    '--bc-text-primary': t.textPrimary,
    '--bc-text-secondary': t.textSecondary,
    '--bc-text-tertiary': t.textTertiary,
    '--bc-surface': t.surfaceRaised,
    '--bc-surface-subtle': t.surfaceSubtle,
    '--bc-border': t.borderDefault,
    '--bc-brand-primary': t.brandPrimary,
    '--bc-brand-dark': t.brandDark,
    '--bc-brand-subtle': t.brandSubtle,
    '--bc-success': t.success,
    '--bc-success-subtle': t.successSubtle,
    '--bc-warning': t.warning,
    '--bc-selected-fill': t.selectedFill,
    '--bc-selected-border': t.selectedBorder,
    '--bc-emphasis-fill': t.emphasisFill,
    '--bc-active-fill': t.activeFill,
    '--bc-danger': t.danger,
    '--bc-shadow-control-island-lift-y': `${lift}px`,
    '--bc-button-face-height': `${face}px`,
    '--bc-button-height': `${face + lift}px`,
    '--bc-pressed-drop-y': `${t.pressedDropY ?? 2}px`,
    '--bc-hover-lift-y': `${t.hoverLiftY ?? -1}px`,
    '--bc-button-raised-shadow-display': t.buttonRaisedShadowEnabled === false ? 'none' : 'block',
    '--bc-card-island-shadow': t.cardIslandShadow
      || (t.buttonRaisedShadowEnabled === false
        ? 'none'
        : '0 14px 34px rgba(49, 168, 255, 0.20), 0 2px 8px rgba(23, 58, 98, 0.06)'),
  };
}

module.exports = {
  THEMES,
  STYLE_PROFILES,
  getTheme,
  getStyleProfile,
  resolveTheme,
  themeClass,
  styleClass,
  themeVars,
};
