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

/** Sample 内置主题预设，对齐 Android MainActivity / React Web。 */
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

/**
 * 合并 color token 与 style profile，供组件直接消费。
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
    motion: planetMotion,
  };
}
