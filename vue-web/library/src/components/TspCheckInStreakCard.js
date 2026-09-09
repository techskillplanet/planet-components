import { defineComponent, h } from 'vue';
import { themed, starPlanetTheme } from './_shared.js';

export const TspCheckInStreakCard = defineComponent({
  name: 'TspCheckInStreakCard',
  props: {
    streakDays: { type: Number, default: 0 },
    totalDays: { type: Number, default: 0 },
    weekProgress: { type: Number, default: 0 },
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['open'],
  setup(props, { emit }) {
    return () => {
      const pct = Math.max(0, Math.min(100, Math.round(Number(props.weekProgress) * 100)));
      return h(
        'button',
        {
          type: 'button',
          class: 'bc-check-in-streak-card',
          style: themed(props.theme),
          disabled: props.disabled,
          onClick: props.disabled ? undefined : () => emit('open')
        },
        [
          h('div', { class: 'bc-check-in-streak-card__title' }, '连续打卡'),
          h('div', { class: 'bc-check-in-streak-card__stats' }, [
            h('span', null, `连续 ${props.streakDays} 天`),
            h('span', null, `累计 ${props.totalDays} 天`),
            h('span', null, `本周 ${pct}%`)
          ])
        ]
      );
    };
  }
});
