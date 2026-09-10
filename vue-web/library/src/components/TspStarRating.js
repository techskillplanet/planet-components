import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Interactive star rating. */
export const TspStarRating = defineComponent({
  name: 'TspStarRating',
  props: {
    value: { type: Number, default: 0 },
    max: { type: Number, default: 5 },
    disabled: { type: Boolean, default: false },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () => {
      const count = Math.max(1, Math.min(10, Number(props.max) || 5));
      const readonly = props.variant === 'readonly' || props.disabled;
      const stars = [];
      for (let i = 1; i <= count; i += 1) {
        const filled = i <= props.value;
        stars.push(
          h(
            'button',
            {
              key: i,
              type: 'button',
              class: cx('bc-star-rating__star', filled && 'bc-filled'),
              disabled: readonly,
              'aria-label': `${i}`,
              onClick: readonly ? undefined : () => emit('change', i)
            },
            '★'
          )
        );
      }
      return h(
        'div',
        {
          class: cx(
            'bc-star-rating',
            `bc-star-rating--${props.variant}`,
            props.disabled && 'bc-disabled'
          ),
          style: themed(props.theme),
          role: 'slider',
          'aria-valuemin': 0,
          'aria-valuemax': count,
          'aria-valuenow': props.value
        },
        stars
      );
    };
  }
});
