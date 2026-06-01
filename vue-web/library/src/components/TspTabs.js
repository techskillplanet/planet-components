import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspTabs = defineComponent({
  name: 'TspTabs',
  props: {
    tabs: { type: Array, default: () => [] },
    selectedIndex: { type: Number, default: 0 },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['select'],
  setup(props, { emit }) {
    return () => h('div', { class: 'bc-tabs', style: themed(props.theme), role: 'tablist' }, props.tabs.map((tab, index) => h('button', {
      key: `${tab}-${index}`,
      type: 'button',
      class: cx('bc-tabs__item', index === props.selectedIndex && 'bc-selected'),
      role: 'tab',
      'aria-selected': index === props.selectedIndex,
      onClick: () => emit('select', index, tab)
    }, tab)));
  }
});
