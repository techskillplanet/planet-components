import { describe, expect, it } from 'vitest';
import dictionary from '../src/i18n/sample_strings.json';
import { createTranslator } from '../src/i18n/createTranslator.js';

describe('TC-I18N sample language table', () => {
  it('TC-I18N-01 returns localized app title', () => {
    const zh = createTranslator(dictionary, 'zh-CN');
    const en = createTranslator(dictionary, 'en');
    const ja = createTranslator(dictionary, 'ja');
    expect(zh.t('sample/app/title')).toBe('基础组件');
    expect(en.t('sample/app/title')).toBe('Basic Controls');
    expect(ja.t('sample/app/title')).toBeTruthy();
    expect(ja.t('sample/app/title')).not.toBe(zh.t('sample/app/title'));
  });

  it('TC-I18N-02 formats placeholders', () => {
    const zh = createTranslator(dictionary, 'zh-CN');
    expect(zh.t('sample/settings/language/current', 'English')).toContain('English');
  });

  it('TC-I18N-03 switches language for tabs and app title', () => {
    const i18n = createTranslator(dictionary, 'zh-CN');
    const zhHome = i18n.t('sample/tab/home');
    const zhTitle = i18n.t('sample/app/title');
    i18n.setLanguage('en');
    expect(i18n.language()).toBe('en');
    expect(i18n.t('sample/tab/home')).not.toBe(zhHome);
    expect(i18n.t('sample/app/title')).not.toBe(zhTitle);
    i18n.setLanguage('ja');
    expect(i18n.t('sample/tab/home')).not.toBe(zhHome);
    expect(i18n.t('sample/app/title')).not.toBe(zhTitle);
  });
});
