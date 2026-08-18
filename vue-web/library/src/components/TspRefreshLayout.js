import { defineComponent, ref, watch } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

export const TspRefreshLayout = defineComponent({
  name: 'TspRefreshLayout',
  props: {
    refreshing: { type: Boolean, default: false },
    loadingMore: { type: Boolean, default: false },
    disabled: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme },
    className: String,
  },
  emits: ['refresh', 'loadMore'],
  setup(props, { emit, slots }) {
    const loadMoreLock = ref(false);
    watch(
      () => props.loadingMore,
      (value) => {
        if (!value) loadMoreLock.value = false;
      }
    );

    const onScroll = (event) => {
      if (props.disabled || props.loadingMore || props.refreshing || loadMoreLock.value) return;
      const el = event.currentTarget;
      const distanceFromEnd = el.scrollHeight - (el.clientHeight + el.scrollTop);
      if (distanceFromEnd < 48) {
        loadMoreLock.value = true;
        emit('loadMore');
      }
    };

    return () =>
      h(
        'div',
        {
          class: cx('bc-refresh-layout', props.disabled && 'bc-disabled', props.className),
          style: themed(props.theme),
          onScroll,
        },
        [
          props.refreshing ? h('div', { class: 'bc-refresh-layout__banner' }, '刷新中…') : null,
          !props.disabled
            ? h(
                'button',
                {
                  type: 'button',
                  class: 'bc-refresh-layout__refresh',
                  onClick: () => emit('refresh'),
                },
                '刷新'
              )
            : null,
          h('div', { class: 'bc-refresh-layout__content' }, slots.default ? slots.default() : null),
          props.loadingMore ? h('div', { class: 'bc-refresh-layout__footer' }, '加载更多…') : null,
        ]
      );
  },
});
