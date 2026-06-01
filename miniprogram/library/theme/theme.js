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
    borderDefault: '#C8EAFF',
    brandPrimary: '#31A8FF',
    brandDark: '#1479D6',
    success: '#43CFC7',
    warning: '#FFD166',
    selectedFill: '#E8FDF7',
    activeFill: '#FFF7D7',
    danger: '#FF6B7A',
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
    borderDefault: '#2A3F5C',
    brandPrimary: '#31A8FF',
    brandDark: '#1479D6',
    success: '#43CFC7',
    warning: '#FFD166',
    selectedFill: '#1E3A52',
    activeFill: '#3A3020',
    danger: '#FF6B7A',
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
    borderDefault: '#BDEFE2',
    brandPrimary: '#20BFA9',
    brandDark: '#0C8F7E',
    success: '#35C58B',
    warning: '#FFD166',
    selectedFill: '#E6FFF4',
    activeFill: '#FFF7D7',
    danger: '#FF6B7A',
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
    borderDefault: '#FFD1AD',
    brandPrimary: '#FF8A3D',
    brandDark: '#D85C12',
    success: '#43CFC7',
    warning: '#FFD166',
    selectedFill: '#FFF1E7',
    activeFill: '#FFF7D7',
    danger: '#E24C5C',
  },
};

function getTheme(key) {
  return THEMES[key] || THEMES.sky;
}

function themeClass(key) {
  return getTheme(key).dark ? 'theme-night' : 'theme-sky';
}

module.exports = { THEMES, getTheme, themeClass };
