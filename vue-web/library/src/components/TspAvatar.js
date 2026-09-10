import { defineComponent } from 'vue';
import { h, cx, themed, starPlanetTheme } from './_shared.js';

/** Avatar — initials or image. */
export const TspAvatar = defineComponent({
  name: 'TspAvatar',
  props: {
    text: { type: String, default: '' },
    src: { type: String, default: undefined },
    size: { type: String, default: 'md' },
    variant: { type: String, default: 'default' },
    theme: { type: Object, default: () => starPlanetTheme }
  },
  setup(props) {
    return () => {
      const dim = props.size === 'sm' ? 32 : props.size === 'lg' ? 56 : 40;
      const initial = String(props.text || '?').trim().slice(0, 2).toUpperCase();
      return h(
        'span',
        {
          class: cx('bc-avatar', `bc-avatar--${props.size}`, `bc-avatar--${props.variant}`),
          style: themed(props.theme, { width: dim, height: dim, fontSize: dim * 0.38 }),
          role: 'img',
          'aria-label': props.text || 'Avatar'
        },
        props.src
          ? h('img', { class: 'bc-avatar__img', src: props.src, alt: props.text || '' })
          : h('span', { class: 'bc-avatar__text' }, initial)
      );
    };
  }
});
