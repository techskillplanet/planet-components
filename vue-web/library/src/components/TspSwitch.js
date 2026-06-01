import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspSwitch = defineComponent({
  name: 'TspSwitch',
  props: {
    text: String,
    checked: Boolean,
    checkedText: String,
    uncheckedText: String,
    loading: Boolean,
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    return () => h('button', {
      type: 'button',
      disabled: props.disabled || props.loading,
      class: cx('bc-switch', props.checked && 'bc-checked', props.loading && 'bc-loading', props.disabled && 'bc-disabled'),
      style: themed(props.theme),
      onClick: props.disabled || props.loading ? undefined : () => emit('change', !props.checked)
    }, [
      h('span', { class: 'bc-switch__track' }, h('span', { class: 'bc-switch__thumb' })),
      (props.text || (props.checked ? props.checkedText : props.uncheckedText)) && h('span', { class: 'bc-switch__text' }, props.text || (props.checked ? props.checkedText : props.uncheckedText))
    ]);
  }
});
