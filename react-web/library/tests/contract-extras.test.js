import React from 'react';
import { describe, it, expect, vi } from 'vitest';
import { render, fireEvent } from '@testing-library/react';
import {
  TspCheckbox,
  TspRadio,
  TspSegmentedControl,
  TspStarRating,
  TspSearchBar,
  starPlanetTheme,
} from '../src/index.js';

describe('contract extras', () => {
  it('TspCheckbox toggles', () => {
    const onChange = vi.fn();
    const { getByRole } = render(
      React.createElement(TspCheckbox, {
        text: 'Agree',
        checked: false,
        onChange,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(getByRole('checkbox'));
    expect(onChange).toHaveBeenCalledWith(true);
  });

  it('TspRadio selects', () => {
    const onChange = vi.fn();
    const { getByRole } = render(
      React.createElement(TspRadio, {
        text: 'A',
        checked: false,
        onChange,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(getByRole('radio'));
    expect(onChange).toHaveBeenCalledWith(true);
  });

  it('TspSegmentedControl selects index', () => {
    const onSelect = vi.fn();
    const { getAllByRole } = render(
      React.createElement(TspSegmentedControl, {
        options: ['Day', 'Week'],
        selectedIndex: 0,
        onSelect,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(getAllByRole('tab')[1]);
    expect(onSelect).toHaveBeenCalledWith(1, 'Week', 'Week');
  });

  it('TspStarRating changes value', () => {
    const onChange = vi.fn();
    const { getByLabelText } = render(
      React.createElement(TspStarRating, {
        value: 2,
        max: 5,
        onChange,
        theme: starPlanetTheme,
      })
    );
    fireEvent.click(getByLabelText('4'));
    expect(onChange).toHaveBeenCalledWith(4);
  });

  it('TspSearchBar emits text', () => {
    const onChange = vi.fn();
    const { container } = render(
      React.createElement(TspSearchBar, {
        value: '',
        onChange,
        theme: starPlanetTheme,
      })
    );
    fireEvent.change(container.querySelector('input'), { target: { value: 'sky' } });
    expect(onChange).toHaveBeenCalledWith('sky');
  });
});
