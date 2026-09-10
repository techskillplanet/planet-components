import React, { useState } from 'react';
import {
  TspCascader,
  TspFab,
  TspTable,
  TspTag,
  starPlanetTheme,
} from '../src/index.js';

export default {
  title: 'Planet/W2',
  parameters: { layout: 'padded' },
};

const wrap = (node) =>
  React.createElement(
    'div',
    {
      style: {
        maxWidth: 420,
        padding: 16,
        background: `linear-gradient(180deg, ${starPlanetTheme.pageStart}, ${starPlanetTheme.pageEnd})`,
      },
    },
    node
  );

export const TagVariants = {
  render: () =>
    wrap(
      React.createElement(
        'div',
        { style: { display: 'flex', flexWrap: 'wrap', gap: 8 } },
        ['default', 'primary', 'success', 'warning', 'danger'].map((variant) =>
          React.createElement(TspTag, {
            key: variant,
            text: variant,
            variant,
            theme: starPlanetTheme,
          })
        ),
        React.createElement(TspTag, {
          key: 'closable',
          text: 'closable',
          closable: true,
          theme: starPlanetTheme,
        })
      )
    ),
};

export const FabPrimary = {
  render: () =>
    wrap(
      React.createElement(
        'div',
        { style: { display: 'flex', gap: 12, alignItems: 'center' } },
        React.createElement(TspFab, { icon: '+', theme: starPlanetTheme }),
        React.createElement(TspFab, {
          icon: '+',
          text: '新建',
          theme: starPlanetTheme,
        }),
        React.createElement(TspFab, {
          icon: '✎',
          text: '默认',
          variant: 'default',
          theme: starPlanetTheme,
        })
      )
    ),
};

export const TableStriped = {
  render: () =>
    wrap(
      React.createElement(TspTable, {
        columns: [
          { key: 'name', title: '名称' },
          { key: 'status', title: '状态' },
        ],
        rows: [
          { name: 'Avatar', status: '就绪' },
          { name: 'Tag', status: '新增' },
          { name: 'Fab', status: '新增' },
        ],
        variant: 'striped',
        theme: starPlanetTheme,
      })
    ),
};

export const CascaderBasic = {
  render: () => {
    const Demo = () => {
      const [value, setValue] = useState([]);
      return React.createElement(TspCascader, {
        options: [
          {
            value: 'asia',
            label: '亚洲',
            children: [
              { value: 'cn', label: '中国' },
              { value: 'jp', label: '日本' },
            ],
          },
          {
            value: 'eu',
            label: '欧洲',
            children: [{ value: 'fr', label: '法国' }],
          },
        ],
        value,
        placeholder: '请选择地区',
        onChange: setValue,
        theme: starPlanetTheme,
      });
    };
    return wrap(React.createElement(Demo));
  },
};
