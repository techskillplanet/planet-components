import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspOptionSheet = defineComponent({
  name: 'TspOptionSheet',
  props: {
    title: { type: String, default: '请选择' },
    options: { type: Array, default: () => [] },
    selectedIndex: { type: Number, default: 0 },
    visible: Boolean,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['select', 'cancel'],
  setup(props, { emit }) {
    return () => props.visible ? h('div', { class: 'bc-option-sheet', style: themed(props.theme), role: 'dialog', 'aria-modal': 'true' }, [
      h('button', { type: 'button', class: 'bc-option-sheet__mask', 'aria-label': 'Close', onClick: () => emit('cancel') }),
      h('section', { class: 'bc-option-sheet__panel' }, [
        h('div', { class: 'bc-option-sheet__header' }, [
          h('button', { type: 'button', onClick: () => emit('cancel') }, '取消'),
          h('strong', null, props.title),
          h('span', null)
        ]),
        h('div', { class: 'bc-option-sheet__options' }, props.options.map((option, index) => h('button', {
          key: `${optionText(option)}-${index}`,
          type: 'button',
          class: cx('bc-option-sheet__option', index === props.selectedIndex && 'bc-selected'),
          onClick: () => emit('select', index, option)
        }, [h('span', { class: 'bc-option-sheet__check' }, index === props.selectedIndex ? '✓' : ''), h('span', null, optionText(option))])))
      ])
    ]) : null;
  }
});
