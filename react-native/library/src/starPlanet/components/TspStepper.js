import React from 'react';
import { Text, View } from 'react-native';
import { clamp, styles, withTheme } from '../utils/shared';

export function TspStepper({ stepCount, currentStep, theme }) {
  const t = withTheme(theme);
  const count = clamp(stepCount, 3, 5);
  const current = clamp(currentStep, 1, count);
  return <View style={styles.stepper}>{Array.from({ length: count }, (_, index) => { const step = index + 1; const done = step < current; const active = step === current; return <React.Fragment key={step}><View style={[styles.step, { backgroundColor: done ? t.success : active ? t.brandPrimary : t.surfaceRaised, borderColor: active ? t.brandPrimary : t.borderDefault }]}><Text style={{ color: done || active ? '#FFFFFF' : t.textTertiary, fontWeight: '800' }}>{done ? '✓' : step}</Text></View>{step < count && <View style={[styles.stepLine, { backgroundColor: done ? t.success : t.borderDefault }]} />}</React.Fragment>; })}</View>;
}

