import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspProgress = defineComponent({
  name: 'TspProgress',
  props: {
    progress: { type: Number, default: 0 },
    variant: { type: String, default: 'primary' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    const percent = computed(() => clamp(props.progress, 0, 100));
    return () => h('div', { class: cx('bc-progress', `bc-progress--${props.variant}`), style: themed(props.theme), role: 'progressbar', 'aria-valuenow': percent.value, 'aria-valuemin': 0, 'aria-valuemax': 100 }, h('span', { style: { width: `${percent.value}%` } }));
  }
});
