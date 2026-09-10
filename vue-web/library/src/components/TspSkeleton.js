import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Skeleton placeholder with optional shimmer. */
export const TspSkeleton = defineComponent({
  name: 'TspSkeleton',
  props: {
    rows: { type: [Number, String], default: 3 },
    animated: { type: Boolean, default: true },
    avatar: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => {
      const count = Math.max(1, Math.min(12, Number(props.rows) || 3));
      const lines = [];
      for (let i = 0; i < count; i += 1) {
        lines.push(
          h('div', {
            key: i,
            class: cx('bc-skeleton__line', i === count - 1 && 'bc-skeleton__line--short')
          })
        );
      }
      return h(
        'div',
        {
          class: cx(
            'bc-skeleton',
            `bc-skeleton--${props.variant}`,
            props.animated && 'bc-skeleton--animated'
          ),
          style: themed(props.theme),
          'aria-hidden': true
        },
        [
          props.avatar ? h('div', { class: 'bc-skeleton__avatar' }) : null,
          h('div', { class: 'bc-skeleton__body' }, lines)
        ]
      );
    };
  }
});
