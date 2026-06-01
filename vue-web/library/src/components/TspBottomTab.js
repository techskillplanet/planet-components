import { computed, defineComponent, ref } from 'vue';
import { h, cx, clamp, optionText, themed, childrenOr, starPlanetTheme } from './_shared.js';

export const TspBottomTab = defineComponent({
  name: 'TspBottomTab',
  props: {
    tabs: { type: Array, default: () => [] },
    selectedKey: String,
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['select'],
  setup(props, { emit }) {
    return () => h('nav', { class: 'bc-bottom-tab', style: themed(props.theme) }, props.tabs.map((tab) => h('button', {
      key: tab.key,
      type: 'button',
      class: cx('bc-bottom-tab__item', tab.key === props.selectedKey && 'bc-selected'),
      onClick: () => emit('select', tab.key, tab)
    }, [tab.icon && h('span', { class: 'bc-bottom-tab__icon' }, tab.icon), h('span', null, tab.title ?? tab.text ?? tab.key)])));
  }
});
