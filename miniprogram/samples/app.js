const { getTheme } = require('./basic-controls/theme/theme');
const i18n = require('./basic-controls/i18n/i18n');
const storage = require('./utils/storage');

App({
  globalData: {
    themeKey: 'sky',
    theme: getTheme('sky'),
    language: 'zh-CN',
    completed: [],
  },

  onLaunch() {
    const themeKey = storage.getThemeKey();
    const language = storage.getLanguage();
    i18n.setLanguage(language);
    this.globalData.themeKey = themeKey;
    this.globalData.language = language;
    this.globalData.completed = storage.getCompleted();
    this.applyTheme(themeKey);
  },

  applyTheme(themeKey) {
    this.globalData.themeKey = themeKey;
    this.globalData.theme = getTheme(themeKey);
    storage.setThemeKey(themeKey);
  },

  setLanguage(code) {
    i18n.setLanguage(code);
    this.globalData.language = code;
    storage.setLanguage(code);
  },

  setCompleted(ids) {
    this.globalData.completed = ids;
    storage.setCompleted(ids);
  },

  t(key) {
    return i18n.text(key);
  },

  tf(key, ...args) {
    return i18n.format(key, ...args);
  },
});
