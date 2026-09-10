module.exports = {
  describe: (name, fn) => fn(),
  it: (name, fn) => { try { fn(); } catch (e) { console.error(name, e); throw e; } },
  expect: (v) => ({ toBe: (x) => { if (v !== x) throw new Error(String(v) + ' !== ' + String(x)); } }),
};
