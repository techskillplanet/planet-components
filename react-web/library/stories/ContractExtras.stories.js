import React from 'react';
import {
  TspCheckbox,
  TspCollapse,
  TspDivider,
  TspRadio,
  TspSearchBar,
  TspSegmentedControl,
  TspStarRating,
  starPlanetTheme,
} from '../src/index.js';

export default {
  title: 'Planet/ContractExtras',
  parameters: { layout: 'padded' },
};

const wrap = (node) =>
  React.createElement(
    'div',
    {
      style: {
        maxWidth: 400,
        padding: 16,
        display: 'flex',
        flexDirection: 'column',
        gap: 12,
        background: `linear-gradient(180deg, ${starPlanetTheme.pageStart}, ${starPlanetTheme.pageEnd})`,
      },
    },
    node
  );

export const CheckboxRadio = {
  render: () =>
    wrap([
      React.createElement(TspCheckbox, {
        key: 'c',
        text: 'Agree to terms',
        checked: true,
        theme: starPlanetTheme,
        onChange: () => {},
      }),
      React.createElement(TspRadio, {
        key: 'r',
        text: 'Option A',
        checked: true,
        theme: starPlanetTheme,
        onChange: () => {},
      }),
    ]),
};

export const CollapseDivider = {
  render: () =>
    wrap([
      React.createElement(TspCollapse, {
        key: 'col',
        title: 'Details',
        message: 'Sky Planet token-driven panel.',
        expanded: true,
        theme: starPlanetTheme,
        onChange: () => {},
      }),
      React.createElement(TspDivider, { key: 'd', theme: starPlanetTheme }),
    ]),
};

export const SearchSegmentedStars = {
  render: () =>
    wrap([
      React.createElement(TspSearchBar, {
        key: 's',
        value: 'planet',
        theme: starPlanetTheme,
        onChange: () => {},
      }),
      React.createElement(TspSegmentedControl, {
        key: 'seg',
        options: ['Day', 'Week', 'Month'],
        selectedIndex: 1,
        theme: starPlanetTheme,
        onSelect: () => {},
      }),
      React.createElement(TspStarRating, {
        key: 'star',
        value: 4,
        max: 5,
        theme: starPlanetTheme,
        onChange: () => {},
      }),
    ]),
};
