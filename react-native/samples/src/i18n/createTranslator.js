/**
 * Sample i18n helper aligned with Android BasicI18nManager + sample_strings.json.
 */

export function createTranslator(dictionary, language = 'zh-CN') {
  const tables = dictionary && typeof dictionary === 'object' ? dictionary : {};
  let current = tables[language] ? language : (tables['zh-CN'] ? 'zh-CN' : Object.keys(tables)[0] || 'zh-CN');

  function table() {
    return tables[current] || tables['zh-CN'] || {};
  }

  function t(key, ...args) {
    const raw = table()[key] ?? tables['zh-CN']?.[key] ?? key;
    if (!args.length) return String(raw);
    let index = 0;
    return String(raw).replace(/%s/g, () => {
      const value = args[index++];
      return value == null ? '' : String(value);
    });
  }

  return {
    language: () => current,
    setLanguage(next) {
      if (tables[next]) current = next;
      return current;
    },
    t,
  };
}
