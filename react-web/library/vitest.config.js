import { defineConfig } from 'vitest/config';

export default defineConfig({
  test: {
    environment: 'jsdom',
    globals: true,
    include: ['tests/**/*.test.{js,jsx}'],
    setupFiles: ['tests/setup.js'],
  },
});
