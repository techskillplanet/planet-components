export const starPlanetTheme = {
  pageStart: '#DDF4FF',
  pageEnd: '#F9FDFF',
  textPrimary: '#173A62',
  textSecondary: '#365D82',
  textTertiary: '#7895AE',
  surfaceRaised: '#FFFFFF',
  borderDefault: '#C8EAFF',
  brandPrimary: '#31A8FF',
  brandDark: '#1479D6',
  success: '#43CFC7',
  warning: '#FFD166',
  selectedFill: '#E8FDF7',
  activeFill: '#FFF7D7',
  danger: '#FF6B7A',
  switchOffBg: '#C8EAFF',
  switchOffBorder: '#C8EAFF',
  switchOffText: '#365D82',
  switchOnBg: '#31A8FF',
  switchOnBorder: '#31A8FF',
  switchOnText: '#FFFFFF',
  switchHandleBg: '#FFFFFF',
  switchHandleBorder: '#C8EAFF',
  switchHandleCheckedBorder: '#31A8FF',
  switchSpinner: '#FFFFFF',
};

export const starPlanetThemes = {
  sky: starPlanetTheme,
  night: {
    pageStart: '#0F1A2E',
    pageEnd: '#141E32',
    textPrimary: '#E8F4FF',
    textSecondary: '#B8D0E8',
    textTertiary: '#7A94B0',
    surfaceRaised: '#1E2D45',
    borderDefault: '#2A3F5C',
    brandPrimary: '#31A8FF',
    brandDark: '#1479D6',
    success: '#43CFC7',
    warning: '#FFD166',
    selectedFill: '#1E3A52',
    activeFill: '#3A3020',
    danger: '#FF6B7A',
    switchOffBg: '#2A3F5C',
    switchOffBorder: '#2A3F5C',
    switchOffText: '#B8D0E8',
    switchOnBg: '#31A8FF',
    switchOnBorder: '#31A8FF',
    switchOnText: '#FFFFFF',
    switchHandleBg: '#FFFFFF',
    switchHandleBorder: '#2A3F5C',
    switchHandleCheckedBorder: '#31A8FF',
    switchSpinner: '#FFFFFF',
  },
  mint: {
    pageStart: '#DFFAF2',
    pageEnd: '#F8FFFC',
    textPrimary: '#123F3A',
    textSecondary: '#2F6B63',
    textTertiary: '#6C938D',
    surfaceRaised: '#FFFFFF',
    borderDefault: '#BDEFE2',
    brandPrimary: '#20BFA9',
    brandDark: '#0C8F7E',
    success: '#35C58B',
    warning: '#FFD166',
    selectedFill: '#E6FFF4',
    activeFill: '#FFF7D7',
    danger: '#FF6B7A',
    switchOffBg: '#BDEFE2',
    switchOffBorder: '#BDEFE2',
    switchOffText: '#2F6B63',
    switchOnBg: '#20BFA9',
    switchOnBorder: '#20BFA9',
    switchOnText: '#FFFFFF',
    switchHandleBg: '#FFFFFF',
    switchHandleBorder: '#BDEFE2',
    switchHandleCheckedBorder: '#20BFA9',
    switchSpinner: '#FFFFFF',
  },
};

/** style_token.json themes：岛屿阴影 / 扁平无影。 */
export const starPlanetStyleProfiles = {
  island_raised: {
    buttonRaisedShadowEnabled: true,
    shadowControlIslandLiftY: 5,
    shadowControlPressedY: 2,
    pressedDropY: 2,
    buttonFaceHeight: 46,
  },
  island_flat: {
    buttonRaisedShadowEnabled: false,
    shadowControlIslandLiftY: 0,
    shadowControlPressedY: 0,
    pressedDropY: 0,
    buttonFaceHeight: 46,
  },
};

/** Sample 内置主题预设，对齐 Android MainActivity。 */
export const builtInThemePresets = [
  { label: 'Sky Planet · 岛屿阴影', colorKey: 'sky', styleProfile: 'island_raised' },
  { label: 'Sky Planet · 扁平无影', colorKey: 'sky', styleProfile: 'island_flat' },
  { label: 'Star Planet · 岛屿阴影', colorKey: 'night', styleProfile: 'island_raised' },
  { label: 'Star Planet · 扁平无影', colorKey: 'night', styleProfile: 'island_flat' },
  { label: 'Mint Planet · 岛屿阴影', colorKey: 'mint', styleProfile: 'island_raised' },
  { label: 'Mint Planet · 扁平无影', colorKey: 'mint', styleProfile: 'island_flat' },
];

/**
 * 合并 color token 与 style profile，供组件直接消费。
 *
 * @param {string} colorKey starPlanetThemes 键名。
 * @param {string} styleProfile starPlanetStyleProfiles 键名。
 */
export function resolveTheme(colorKey = 'sky', styleProfile = 'island_raised') {
  const colors = starPlanetThemes[colorKey] || starPlanetThemes.sky;
  const style = starPlanetStyleProfiles[styleProfile] || starPlanetStyleProfiles.island_raised;
  return {
    ...colors,
    ...style,
    colorKey,
    styleProfile,
    buttonHeight: style.buttonFaceHeight + style.shadowControlIslandLiftY,
  };
}
