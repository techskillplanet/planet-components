import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspListItem = defineComponent({
  name: 'TspListItem',
  props: {
    title: String,
    message: String,
    trailing: String,
    selected: Boolean,
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['tap'],
  setup(props, { emit }) {
    return () => h('button', {
      type: 'button',
      disabled: props.disabled,
      class: cx('bc-list-item', props.selected && 'bc-selected', props.disabled && 'bc-disabled'),
      style: themed(props.theme),
      onClick: props.disabled ? undefined : () => emit('tap')
    }, [
      h('span', { class: 'bc-list-item__body' }, [h('strong', null, props.title), props.message && h('span', null, props.message)]),
      props.trailing && h('span', { class: 'bc-list-item__trailing' }, props.trailing)
    ]);
  }
});
