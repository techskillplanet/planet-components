/**
 * Smoke tests for DatePicker + Domain-7 ports (React Native).
 */
import React from 'react';
import { describe, it, expect, vi } from 'vitest';
import renderer from 'react-test-renderer';
import {
  TspDatePicker,
  TspChildSwitcher,
  TspScoreRuleGrid,
  TspRedeemCardGrid,
  TspCalendarHeatmap,
  TspPrintSheet,
  TspBalanceHero,
  TspCheckInStreakCard,
  TspButton,
  starPlanetThemes
} from '../src/starPlanet/index.js';

describe('RN Domain-7 + DatePicker smoke', () => {
  it('exports sunrise theme', () => {
    expect(starPlanetThemes.sunrise).toBeTruthy();
    expect(starPlanetThemes.sky.selectedFill).toBe('#E5F6FF');
    expect(starPlanetThemes.sky.success).toBe('#2BB8E6');
  });

  it('TspButton loading does not throw', () => {
    expect(() => renderer.create(React.createElement(TspButton, { text: 'Go', loading: true }))).not.toThrow();
  });

  it('renders DatePicker and Domain-7 without throw', () => {
    expect(() => renderer.create(React.createElement(TspDatePicker, { value: '2026-08-26', onChange: vi.fn() }))).not.toThrow();
    expect(() => renderer.create(React.createElement(TspChildSwitcher, {
      items: [{ id: 1, label: 'A' }, { id: 2, label: 'B' }],
      selectedId: 1,
      onChange: vi.fn()
    }))).not.toThrow();
    expect(() => renderer.create(React.createElement(TspScoreRuleGrid, {
      rules: [{ id: 1, name: '作业', value: 5, count: 0 }]
    }))).not.toThrow();
    expect(() => renderer.create(React.createElement(TspRedeemCardGrid, {
      items: [{ id: 1, name: '零食', cost: 10 }],
      availablePoints: 20
    }))).not.toThrow();
    expect(() => renderer.create(React.createElement(TspCalendarHeatmap, {
      yearMonth: '2026-08',
      cells: []
    }))).not.toThrow();
    expect(() => renderer.create(React.createElement(TspPrintSheet, {
      title: '默写',
      items: [{ prompt: 'a' }]
    }))).not.toThrow();
    expect(() => renderer.create(React.createElement(TspBalanceHero, { total: 41 }))).not.toThrow();
    expect(() => renderer.create(React.createElement(TspCheckInStreakCard, {
      streakDays: 3,
      totalDays: 10,
      weekProgress: 0.5
    }))).not.toThrow();
  });
});
