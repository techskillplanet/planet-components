/**
 * W1/W2 common controls — Cascader regression + Tag/Fab/Slider/InputNumber.
 */
import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import {
  TspCascader,
  TspTag,
  TspFab,
  TspSlider,
  TspInputNumber,
  starPlanetTheme,
} from '../src/index.js';

const cascaderOptions = [
  { value: 'asia', label: '亚洲', children: [{ value: 'cn', label: '中国' }, { value: 'jp', label: '日本' }] },
  { value: 'eu', label: '欧洲', children: [{ value: 'fr', label: '法国' }] },
];

describe('TspCascader', () => {
  it('opens in-flow panel', async () => {
    const wrapper = mount(TspCascader, {
      props: { options: cascaderOptions, theme: starPlanetTheme },
    });
    await wrapper.find('.bc-cascader__trigger').trigger('click');
    expect(wrapper.find('.bc-cascader__panel').exists()).toBe(true);
    expect(wrapper.text()).toContain('亚洲');
    expect(wrapper.text()).toContain('欧洲');
  });

  it('expands children when parent branch is selected', async () => {
    const wrapper = mount(TspCascader, {
      props: { options: cascaderOptions, theme: starPlanetTheme },
    });
    await wrapper.find('.bc-cascader__trigger').trigger('click');
    const eu = wrapper.findAll('.bc-cascader__option').find((n) => n.text().includes('欧洲'));
    expect(eu).toBeTruthy();
    await eu.trigger('click');
    expect(wrapper.text()).toContain('法国');
  });

  it('emits leaf path and closes', async () => {
    const wrapper = mount(TspCascader, {
      props: { options: cascaderOptions, theme: starPlanetTheme },
    });
    await wrapper.find('.bc-cascader__trigger').trigger('click');
    await wrapper.findAll('.bc-cascader__option').find((n) => n.text().includes('欧洲')).trigger('click');
    await wrapper.findAll('.bc-cascader__option').find((n) => n.text().includes('法国')).trigger('click');
    expect(wrapper.emitted('change')[0]).toEqual([['eu', 'fr'], ['欧洲', '法国']]);
    expect(wrapper.find('.bc-cascader__panel').exists()).toBe(false);
  });

  it('uses dedicated active class instead of global bc-selected', async () => {
    const wrapper = mount(TspCascader, {
      props: { options: cascaderOptions, theme: starPlanetTheme },
    });
    await wrapper.find('.bc-cascader__trigger').trigger('click');
    await wrapper.findAll('.bc-cascader__option').find((n) => n.text().includes('欧洲')).trigger('click');
    expect(wrapper.find('.bc-cascader__option--active').exists()).toBe(true);
    expect(wrapper.find('.bc-cascader__option.bc-selected').exists()).toBe(false);
  });

  it('does not open when disabled', async () => {
    const wrapper = mount(TspCascader, {
      props: { options: cascaderOptions, disabled: true, theme: starPlanetTheme },
    });
    await wrapper.find('.bc-cascader__trigger').trigger('click');
    expect(wrapper.find('.bc-cascader__panel').exists()).toBe(false);
  });
});

describe('TspTag', () => {
  it('fires close for closable tag without bubbling tap', async () => {
    const wrapper = mount(TspTag, {
      props: { text: 'closable', closable: true, theme: starPlanetTheme },
    });
    await wrapper.find('.bc-tag__close').trigger('click');
    expect(wrapper.emitted('close')).toHaveLength(1);
    expect(wrapper.emitted('tap')).toBeUndefined();
  });
});

describe('TspFab', () => {
  it('fires tap', async () => {
    const wrapper = mount(TspFab, {
      props: { icon: '+', text: '新建', theme: starPlanetTheme },
    });
    await wrapper.find('button').trigger('click');
    expect(wrapper.emitted('tap')).toHaveLength(1);
  });
});

describe('TspSlider', () => {
  it('emits numeric change', async () => {
    const wrapper = mount(TspSlider, {
      props: { value: 10, min: 0, max: 100, theme: starPlanetTheme },
    });
    const input = wrapper.find('input[type="range"]');
    await input.setValue(42);
    // Vue range may emit via input; setValue triggers update
    const emitted = wrapper.emitted('change');
    expect(emitted).toBeTruthy();
    expect(Number(emitted[emitted.length - 1][0])).toBe(42);
  });
});

describe('TspInputNumber', () => {
  it('increments and decrements with clamp', async () => {
    const wrapper = mount(TspInputNumber, {
      props: { value: 1, min: 0, max: 2, step: 1, theme: starPlanetTheme },
    });
    await wrapper.find('[aria-label="Increase"]').trigger('click');
    expect(wrapper.emitted('change')[0]).toEqual([2]);

    await wrapper.setProps({ value: 2 });
    await wrapper.find('[aria-label="Decrease"]').trigger('click');
    expect(wrapper.emitted('change')[1]).toEqual([1]);

    await wrapper.setProps({ value: 1 });
    await wrapper.find('[aria-label="Decrease"]').trigger('click');
    expect(wrapper.emitted('change')[2]).toEqual([0]);
  });
});
