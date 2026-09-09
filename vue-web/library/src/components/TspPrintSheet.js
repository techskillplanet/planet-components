import { defineComponent, h } from 'vue';
import { themed, starPlanetTheme } from './_shared.js';

export const TspPrintSheet = defineComponent({
  name: 'TspPrintSheet',
  props: {
    title: { type: String, default: '' },
    items: { type: Array, default: () => [] },
    columns: { type: Number, default: 5 },
    footerFields: { type: Array, default: () => ['姓名', '日期', '得分'] },
    variant: { type: String, default: 'pinyin' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => {
      const cols = Math.max(1, Number(props.columns) || 5);
      return h(
        'div',
        {
          class: `bc-print-sheet bc-print-sheet--${props.variant}`,
          style: themed(props.theme, { '--bc-print-cols': String(cols) }),
          'data-variant': props.variant
        },
        [
          props.title ? h('h3', { class: 'bc-print-sheet__title' }, props.title) : null,
          h(
            'div',
            { class: 'bc-print-sheet__grid' },
            (props.items || []).map((item, index) => {
              const prompt = typeof item === 'string' ? item : item?.prompt ?? '';
              return h('div', { key: index, class: 'bc-print-sheet__cell' }, [
                h('div', { class: 'bc-print-sheet__prompt' }, prompt),
                h('div', { class: 'bc-print-sheet__blank' })
              ]);
            })
          ),
          props.footerFields?.length
            ? h(
                'div',
                { class: 'bc-print-sheet__footer' },
                props.footerFields.map((field) => h('span', { key: field }, `${field}：________`))
              )
            : null
        ]
      );
    };
  }
});
