import { React, h, themed, starPlanetTheme } from './_shared.js';

/**
 * TspCheckInStreakCard – Continuous check-in summary.
 */
export function TspCheckInStreakCard({
  streakDays = 0,
  totalDays = 0,
  weekProgress = 0,
  disabled = false,
  theme = starPlanetTheme,
  onOpen
}) {
  const pct = Math.max(0, Math.min(100, Math.round(Number(weekProgress) * 100)));
  return h(
    'button',
    {
      type: 'button',
      className: 'bc-check-in-streak-card',
      style: themed(theme),
      disabled,
      onClick: disabled ? undefined : () => onOpen?.()
    },
    [
      h('div', { key: 'title', className: 'bc-check-in-streak-card__title' }, '连续打卡'),
      h('div', { key: 'stats', className: 'bc-check-in-streak-card__stats' }, [
        h('span', { key: 's' }, `连续 ${streakDays} 天`),
        h('span', { key: 't' }, `累计 ${totalDays} 天`),
        h('span', { key: 'w' }, `本周 ${pct}%`)
      ])
    ]
  );
}
