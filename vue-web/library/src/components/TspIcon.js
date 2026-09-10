import { computed, defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';
import { PLANET_ICONS } from '../icons/planetIcons.js';

export const TspIcon = defineComponent({
  name: 'TspIcon',
  props: {
    name: { type: String, default: 'check' },
    size: { type: [Number, String], default: 20 },
    label: String,
    color: String,
    theme: { type: Object, default: () => starPlanetTheme },
    className: String,
  },
  setup(props) {
    const pathData = computed(() => PLANET_ICONS[props.name] || PLANET_ICONS.check);
    const segments = computed(() =>
      String(pathData.value)
        .split(/(?=M)/)
        .filter(Boolean)
        .map((s) => s.trim())
    );
    return () => {
      const decorative = !props.label;
      return h(
        'span',
        {
          class: cx('bc-icon', props.className),
          style: {
            ...themed(props.theme),
            display: 'inline-flex',
            width: props.size,
            height: props.size,
            color: props.color || props.theme.textPrimary,
          },
          role: decorative ? undefined : 'img',
          'aria-label': props.label || undefined,
          'aria-hidden': decorative ? 'true' : undefined,
        },
        h(
          'svg',
          {
            xmlns: 'http://www.w3.org/2000/svg',
            viewBox: '0 0 24 24',
            width: props.size,
            height: props.size,
            fill: 'none',
            stroke: 'currentColor',
            'stroke-width': 2,
            'stroke-linecap': 'round',
            'stroke-linejoin': 'round',
            focusable: 'false',
          },
          segments.value.map((d, i) => h('path', { key: i, d }))
        )
      );
    };
  },
});
