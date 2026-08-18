import { defineConfig } from 'vitest/config';
import { fileURLToPath } from 'node:url';
import { dirname, resolve } from 'node:path';

const root = dirname(fileURLToPath(import.meta.url));

export default defineConfig({
  resolve: {
    alias: {
      react: resolve(root, 'node_modules/react'),
      'react-dom/client': resolve(root, 'node_modules/react-dom/client'),
      'react/jsx-runtime': resolve(root, 'node_modules/react/jsx-runtime.js'),
      '@techskillplanet/planet-components-react': root,
    }
  },
  server: {
    fs: {
      allow: [resolve(root, '..')]
    }
  },
  test: {
    environment: 'jsdom',
    globals: true,
    include: ['tests/**/*.test.{js,jsx}'],
    setupFiles: ['tests/setup.js'],
  },
});
