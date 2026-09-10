import React, { useState } from 'react';
import {
  TspAvatar,
  TspButton,
  TspDrawer,
  TspSkeleton,
  TspSlider,
  TspSwiper,
  starPlanetTheme,
} from '../src/index.js';

export default {
  title: 'Planet/W1',
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

export const Avatar = {
  render: () =>
    wrap(
      React.createElement(
        'div',
        { style: { display: 'flex', gap: 12, alignItems: 'center' } },
        React.createElement(TspAvatar, { text: '技趣', theme: starPlanetTheme }),
        React.createElement(TspAvatar, {
          text: 'SP',
          variant: 'primary',
          size: 'lg',
          theme: starPlanetTheme,
        }),
        React.createElement(TspAvatar, {
          text: 'A',
          variant: 'subtle',
          size: 'sm',
          theme: starPlanetTheme,
        })
      )
    ),
};

export const SkeletonAnimated = {
  render: () =>
    wrap(
      React.createElement(TspSkeleton, {
        rows: 3,
        animated: true,
        avatar: true,
        theme: starPlanetTheme,
      })
    ),
};

export const SliderControlled = {
  render: () => {
    const Demo = () => {
      const [value, setValue] = useState(40);
      return React.createElement(
        'div',
        null,
        React.createElement(TspSlider, {
          value,
          min: 0,
          max: 100,
          onChange: setValue,
          theme: starPlanetTheme,
        }),
        React.createElement('div', { style: { marginTop: 8 } }, String(value))
      );
    };
    return wrap(React.createElement(Demo));
  },
};

export const DrawerBottom = {
  render: () => {
    const Demo = () => {
      const [open, setOpen] = useState(false);
      return React.createElement(
        'div',
        null,
        React.createElement(TspButton, {
          text: 'Open Drawer',
          variant: 'primary',
          theme: starPlanetTheme,
          onTap: () => setOpen(true),
        }),
        React.createElement(
          TspDrawer,
          {
            visible: open,
            title: 'Drawer',
            placement: 'bottom',
            theme: starPlanetTheme,
            onClose: () => setOpen(false),
          },
          'Sky Planet drawer body.'
        )
      );
    };
    return wrap(React.createElement(Demo));
  },
};

export const SwiperSlides = {
  render: () =>
    wrap(
      React.createElement(TspSwiper, {
        items: ['Slide A', 'Slide B', 'Slide C'],
        theme: starPlanetTheme,
      })
    ),
};
