import React from 'react';
import { TspButton, TspSwitch, TspAlert, TspIcon, starPlanetTheme, resolveTheme } from '../src/index.js';

export default {
  title: 'Planet/Basics',
  parameters: { layout: 'padded' },
};

const wrap = (node) =>
  React.createElement(
    'div',
    {
      style: {
        maxWidth: 360,
        padding: 16,
        background: `linear-gradient(180deg, ${starPlanetTheme.pageStart}, ${starPlanetTheme.pageEnd})`,
      },
    },
    node
  );

export const ButtonPrimary = {
  render: () =>
    wrap(
      React.createElement(TspButton, {
        text: 'Get Started',
        variant: 'primary',
        theme: starPlanetTheme,
        onTap: () => {},
      })
    ),
};

export const ButtonLoading = {
  render: () =>
    wrap(
      React.createElement(TspButton, {
        text: 'Saving',
        variant: 'primary',
        loading: true,
        theme: starPlanetTheme,
      })
    ),
};

export const SwitchChecked = {
  render: () =>
    wrap(
      React.createElement(TspSwitch, {
        text: 'Night reminders',
        checked: true,
        theme: resolveTheme('night'),
        onChange: () => {},
      })
    ),
};

export const AlertInfo = {
  render: () =>
    wrap(
      React.createElement(TspAlert, {
        title: 'Sky Planet',
        message: 'Token-driven controls across eight stacks.',
        variant: 'info',
        theme: starPlanetTheme,
      })
    ),
};

export const Icons = {
  render: () =>
    wrap(
      React.createElement(
        'div',
        { style: { display: 'flex', gap: 12, alignItems: 'center' } },
        ['back', 'check', 'close', 'chevron', 'warning'].map((name) =>
          React.createElement(TspIcon, {
            key: name,
            name,
            label: name,
            size: 24,
            theme: starPlanetTheme,
          })
        )
      )
    ),
};
