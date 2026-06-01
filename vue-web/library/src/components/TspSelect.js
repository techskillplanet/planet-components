import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';
import { TspOptionSheet } from './TspOptionSheet.js';

export const TspSelect = defineComponent({
  name: 'TspSelect',
  props: {
    options: { type: Array, default: () => [] },
    selectedIndex: { type: Number, default: 0 },
    disabled: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['select'],
  setup(props, { emit }) {
    const open = ref(false);
    return () => h('div', null, [
      h('button', {
        type: 'button',
        class: cx('bc-select', props.disabled && 'bc-disabled'),
        style: themed(props.theme),
        disabled: props.disabled,
        onClick: props.disabled ? undefined : () => { open.value = true; }
      }, [h('span', null, optionText(props.options[props.selectedIndex]) ?? ''), h('span', { class: 'bc-select__chevron' }, '⌄')]),
      h(TspOptionSheet, {
        title: '请选择',
        options: props.options,
        selectedIndex: props.selectedIndex,
        visible: open.value,
        theme: props.theme,
        onCancel: () => { open.value = false; },
        onSelect: (index, option) => {
          open.value = false;
          emit('select', index, option);
        }
      })
    ]);
  }
});
