import { defineComponent, onBeforeUnmount, ref, watch } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Simple carousel / swiper. */
export const TspSwiper = defineComponent({
  name: 'TspSwiper',
  props: {
    items: { type: Array, default: () => [] },
    index: { type: [Number, String], default: 0 },
    autoplay: { type: Boolean, default: false },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  emits: ['change'],
  setup(props, { emit }) {
    const i = ref(Number(props.index) || 0);
    let timer = null;

    const clearTimer = () => {
      if (timer != null) {
        clearInterval(timer);
        timer = null;
      }
    };

    const go = (next) => {
      const list = Array.isArray(props.items) ? props.items : [];
      if (list.length === 0) return;
      const n = ((next % list.length) + list.length) % list.length;
      i.value = n;
      emit('change', n);
    };

    const syncAutoplay = () => {
      clearTimer();
      const list = Array.isArray(props.items) ? props.items : [];
      if (!props.autoplay || list.length < 2) return;
      timer = setInterval(() => {
        const listNow = Array.isArray(props.items) ? props.items : [];
        if (listNow.length < 2) return;
        const next = (i.value + 1) % listNow.length;
        i.value = next;
        emit('change', next);
      }, 3200);
    };

    watch(
      () => props.index,
      (value) => {
        i.value = Number(value) || 0;
      }
    );

    watch(
      () => [props.autoplay, Array.isArray(props.items) ? props.items.length : 0],
      () => syncAutoplay(),
      { immediate: true }
    );

    onBeforeUnmount(clearTimer);

    return () => {
      const list = Array.isArray(props.items) ? props.items : [];
      const current = list[i.value];
      return h(
        'div',
        {
          class: 'bc-swiper',
          style: themed(props.theme),
          role: 'region',
          'aria-roledescription': 'carousel'
        },
        [
          h(
            'div',
            { class: 'bc-swiper__viewport' },
            h(
              'div',
              { class: 'bc-swiper__slide', key: i.value },
              typeof current === 'string' || typeof current === 'number'
                ? current
                : current?.content ?? current?.label ?? null
            )
          ),
          list.length > 1
            ? h(
                'div',
                { class: 'bc-swiper__dots' },
                list.map((_, di) =>
                  h('button', {
                    key: di,
                    type: 'button',
                    class: cx('bc-swiper__dot', di === i.value && 'bc-active'),
                    'aria-label': `Slide ${di + 1}`,
                    onClick: () => go(di)
                  })
                )
              )
            : null,
          list.length > 1
            ? h('div', { class: 'bc-swiper__nav' }, [
                h(
                  'button',
                  {
                    type: 'button',
                    class: 'bc-swiper__arrow',
                    'aria-label': 'Previous',
                    onClick: () => go(i.value - 1)
                  },
                  '‹'
                ),
                h(
                  'button',
                  {
                    type: 'button',
                    class: 'bc-swiper__arrow',
                    'aria-label': 'Next',
                    onClick: () => go(i.value + 1)
                  },
                  '›'
                )
              ])
            : null
        ]
      );
    };
  }
});
