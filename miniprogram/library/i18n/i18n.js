const LOCAL = require('../../data/local.js');

let language = 'zh-CN';

function setLanguage(code) {
  language = LOCAL[code] ? code : 'zh-CN';
}

function getLanguage() {
  return language;
}

function languages() {
  return Object.keys(LOCAL);
}

function text(key, fallback) {
  const pack = LOCAL[language] || LOCAL['zh-CN'];
  return pack[key] != null ? pack[key] : (fallback != null ? fallback : key);
}

function format(key, ...args) {
  let value = text(key);
  args.forEach((arg, index) => {
    value = value.replace('%' + (index + 1) + '$s', String(arg));
    value = value.replace('%' + (index + 1) + '$d', String(arg));
  });
  return value;
}

module.exports = { setLanguage, getLanguage, languages, text, format };
