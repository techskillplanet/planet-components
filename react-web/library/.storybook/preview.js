import '../src/styles.css';

/** @type { import('@storybook/react').Preview } */
const preview = {
  parameters: {
    layout: 'padded',
    controls: { matchers: { color: /(background|color)$/i } },
  },
};

export default preview;
