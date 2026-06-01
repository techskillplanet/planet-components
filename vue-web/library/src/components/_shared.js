import { h } from 'vue';
import { starPlanetTheme, themeVars } from '../theme.js';

export { h, starPlanetTheme };
export const cx = (...names) => names.filter(Boolean).join(' ');
export const clamp = (value, min, max) => Math.max(min, Math.min(max, Number(value) || min));
export const themed = (theme, style) => ({ ...themeVars(theme), ...style });
export const childrenOr = (slots, fallback) => slots.default ? slots.default() : fallback;
export const optionText = (option) => typeof option === 'object' && option !== null ? option.title ?? option.text ?? option.label ?? option.value : option;
