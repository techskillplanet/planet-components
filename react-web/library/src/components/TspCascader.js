import { React, h, cx, themed, starPlanetTheme } from './_shared.js';
import { useEffect, useMemo, useRef, useState } from 'react';

function optId(opt, i) {
  return String(opt?.value ?? opt?.id ?? opt?.key ?? i);
}

function optLabel(opt) {
  return opt?.label ?? opt?.title ?? opt?.text ?? String(opt?.value ?? opt?.id ?? '');
}

function findPathLabels(options, valuePath) {
  const labels = [];
  let level = options;
  for (const v of valuePath) {
    const hit = (level || []).find((o, i) => optId(o, i) === String(v));
    if (!hit) break;
    labels.push(optLabel(hit));
    level = hit.children || [];
  }
  return labels;
}

function buildColumns(options, walk) {
  const columns = [];
  let level = options;
  // Always show root. Then for each selected id, push its children if any.
  for (let i = 0; i <= walk.length; i += 1) {
    if (!Array.isArray(level) || level.length === 0) break;
    columns.push(level);
    const cur = walk[i];
    if (cur == null || cur === '') break;
    const hit = level.find((o, idx) => optId(o, idx) === String(cur));
    level = hit?.children || [];
  }
  return columns;
}

const EMPTY_PATH = [];

/** Multi-level cascader picker. */
export function TspCascader({
  options = [],
  value,
  placeholder = '请选择',
  disabled = false,
  theme = starPlanetTheme,
  onChange,
}) {
  const opts = Array.isArray(options) ? options : [];
  // Stabilize path so closing the panel does not infinite-loop via useEffect deps.
  const pathKey = Array.isArray(value) ? value.map(String).join('\0') : '';
  const path = useMemo(
    () => (pathKey ? pathKey.split('\0') : EMPTY_PATH),
    [pathKey]
  );
  const [open, setOpen] = useState(false);
  const [draft, setDraft] = useState(path);
  const rootRef = useRef(null);

  useEffect(() => {
    if (!open) setDraft(path);
  }, [path, open]);

  useEffect(() => {
    if (!open) return undefined;
    const onDoc = (event) => {
      if (!rootRef.current?.contains(event.target)) setOpen(false);
    };
    document.addEventListener('mousedown', onDoc);
    return () => document.removeEventListener('mousedown', onDoc);
  }, [open]);

  const display = useMemo(() => {
    const labels = findPathLabels(opts, path);
    return labels.length ? labels.join(' / ') : '';
  }, [opts, path]);

  const columns = buildColumns(opts, open ? draft : path);

  const pick = (colIndex, opt, idx) => {
    const id = optId(opt, idx);
    const next = [...draft.slice(0, colIndex), id];
    setDraft(next);
    if (!opt.children?.length) {
      onChange?.(next, findPathLabels(opts, next));
      setOpen(false);
    }
  };

  return h(
    'div',
    {
      ref: rootRef,
      className: cx('bc-cascader', open && 'bc-open', disabled && 'bc-disabled'),
      style: themed(theme),
    },
    h(
      'button',
      {
        type: 'button',
        className: 'bc-cascader__trigger',
        disabled,
        'aria-expanded': open,
        'aria-haspopup': 'listbox',
        onClick: () => {
          if (disabled) return;
          setDraft(path);
          setOpen((v) => !v);
        },
      },
      h('span', { className: cx('bc-cascader__value', !display && 'bc-placeholder') }, display || placeholder),
      h('span', { className: 'bc-cascader__caret', 'aria-hidden': true }, open ? '▴' : '▾')
    ),
    open
      ? h(
          'div',
          { className: 'bc-cascader__panel', role: 'listbox' },
          columns.map((col, ci) =>
            h(
              'div',
              { key: `col-${ci}`, className: 'bc-cascader__col' },
              col.map((opt, oi) => {
                const id = optId(opt, oi);
                const active = draft[ci] === id;
                const hasChildren = Array.isArray(opt.children) && opt.children.length > 0;
                return h(
                  'button',
                  {
                    type: 'button',
                    key: id,
                    className: cx(
                      'bc-cascader__option',
                      active && 'bc-cascader__option--active',
                      hasChildren && 'bc-cascader__option--branch'
                    ),
                    'aria-selected': active,
                    onClick: () => pick(ci, opt, oi),
                  },
                  h('span', { className: 'bc-cascader__option-label' }, optLabel(opt)),
                  hasChildren ? h('span', { className: 'bc-cascader__option-arrow', 'aria-hidden': true }, '›') : null
                );
              })
            )
          )
        )
      : null
  );
}
