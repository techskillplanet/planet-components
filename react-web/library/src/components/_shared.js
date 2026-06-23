/**
 * Shared utilities for TechSkillPlanet React Web components.
 * @module _shared
 */
import React from 'react';
import { starPlanetTheme, themeVars } from '../theme.js';

export { React, starPlanetTheme };

/** Shorthand for React.createElement */
export const h = React.createElement;

/**
 * Joins truthy class names into a single string.
 * @param {...(string|false|null|undefined)} names - Class name segments.
 * @returns {string} Combined class name string.
 */
export const cx = (...names) => names.filter(Boolean).join(' ');

/**
 * Clamp a numeric value between min and max.
 * @param {number|string} value - The value to clamp.
 * @param {number} min - Minimum bound.
 * @param {number} max - Maximum bound.
 * @returns {number} Clamped value.
 */
export const clamp = (value, min, max) => Math.max(min, Math.min(max, Number(value) || min));

/**
 * Extract display text from an option (string or object with title/text/label/value).
 * @param {string|Object} option - The option to extract text from.
 * @returns {string|undefined} Display text.
 */
export const optionText = (option) => typeof option === 'object' && option !== null ? option.title ?? option.text ?? option.label ?? option.value : option;

/**
 * Merge theme CSS variables with optional inline style.
 * @param {Object} theme - Theme color/style object.
 * @param {Object} [style] - Additional inline styles to merge.
 * @returns {Object} Combined style object with CSS custom properties.
 */
export function themed(theme, style) {
  return { ...themeVars(theme), ...style };
}
