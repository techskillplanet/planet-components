import { defineConfig } from 'vitest/config';
import { transformSync } from 'esbuild';

function jsxInJs() {
  return {
    name: 'jsx-in-js',
    enforce: 'pre',
    transform(code, id) {
      if (id.includes('node_modules')) return null;
      if (!id.endsWith('.js')) return null;
      if (!code.includes('<') || !code.includes('</') && !code.includes('/>')) return null;
      const result = transformSync(code, {
        loader: 'jsx',
        jsx: 'automatic',
        format: 'esm',
        sourcefile: id,
      });
      return { code: result.code, map: result.map };
    },
  };
}

export default defineConfig({
  plugins: [jsxInJs()],
  test: {
    environment: 'node',
    setupFiles: ['./tests/setup.js'],
    include: ['tests/**/*.test.js'],
  },
});
