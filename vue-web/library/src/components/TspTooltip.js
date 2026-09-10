import { defineComponent, ref } from 'vue';
import { h, cx, themed, childrenOr, starPlanetTheme } from './_shared.js';

/** Hover/focus tooltip. */
export const TspTooltip = defineComponent({
  name: 'TspTooltip',
  props: {
    text: { type: String, default: '' },
    placement: { type: String, default: 'top' },
    visible: { type: Boolean, default: undefined },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props, { slots }) {
    const open = ref(false);
    return () => {
      const shown = props.visible != null ? props.visible : open.value;
      return h(
        'span',
        {
          class: cx('bc-tooltip', `bc-tooltip--${props.placement}`, shown && 'bc-open'),
          style: themed(props.theme),
          onMouseEnter: () => {
            open.value = true;
          },
          onMouseLeave: () => {
            open.value = false;
          },
          onFocus: () => {
            open.value = true;
          },
          onBlur: () => {
            open.value = false;
          }
        },
        [
          childrenOr(slots, null),
          shown ? h('span', { class: 'bc-tooltip__bubble', role: 'tooltip' }, props.text) : null
        ]
      );
    };
  }
});
