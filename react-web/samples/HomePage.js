/**
 * Marketing home + live phone preview for React Web samples.
 */
import React, { useEffect, useState } from 'react';
import { starPlanetThemes, themeVars, TspButton, TspCard } from '@techskillplanet/planet-components-react';
import { BasicControlsSample } from './BasicControlsSample.js';

const h = React.createElement;
const PACKAGE_VERSION = '0.2.1';

const GITHUB_REPO = "techskillplanet/planet-components";
  const GITHUB_URL = `https://github.com/${GITHUB_REPO}`;
  const GITHUB_STAR_URL = GITHUB_URL; // open repo to Star
  const GITHUB_FORK_URL = `${GITHUB_URL}/fork`; // GitHub fork flow
  const GITHUB_STARGAZERS_URL = `${GITHUB_URL}/stargazers`;
  const GITHUB_FORKS_LIST_URL = `${GITHUB_URL}/forks`;
  const GITHUB_API_URL = `https://api.github.com/repos/${GITHUB_REPO}`;
  function formatCount(value) {
    if (value == null || value === "") return "\u2014";
    const count = Number(value);
    if (!Number.isFinite(count) || count < 0) return "\u2014";
    if (count < 1e3) return String(Math.round(count));
    const thousands = count / 1e3;
    const compact = thousands >= 10 ? thousands.toFixed(0) : thousands.toFixed(1);
    return `${compact.replace(/\.0$/, "")}k`;
  }
  async function fetchGithubStats({ fetchImpl = globalThis.fetch } = {}) {
    if (typeof fetchImpl !== "function") {
      throw new Error("fetch is not available");
    }
    const response = await fetchImpl(GITHUB_API_URL, {
      headers: { Accept: "application/vnd.github+json" }
    });
    if (!response.ok) {
      throw new Error(`GitHub API ${response.status}`);
    }
    const data = await response.json();
    return {
      stars: Number(data.stargazers_count) || 0,
      forks: Number(data.forks_count) || 0
    };
  }
  function openGithub(url = GITHUB_URL, openImpl = typeof window !== "undefined" ? window.open : undefined) {
    if (typeof openImpl === "function") {
      openImpl(url, "_blank", "noopener,noreferrer");
    }
  }

  // homeLocales.js
  const LOCALE_STORAGE_KEY = "planet-home-locale";
  const THEME_STORAGE_KEY = "planet-home-theme";
  const LANGUAGE_OPTIONS = [
    { key: "zh-CN", title: "\u7B80\u4F53\u4E2D\u6587" },
    { key: "zh-TW", title: "\u7E41\u9AD4\u4E2D\u6587" },
    { key: "en", title: "English" },
    { key: "ja", title: "\u65E5\u672C\u8A9E" },
    { key: "ko", title: "\uD55C\uAD6D\uC5B4" },
    { key: "es", title: "Espa\xF1ol" },
    { key: "fr", title: "Fran\xE7ais" },
    { key: "de", title: "Deutsch" }
  ];
  const INSTALL_COMMAND = "npm i @techskillplanet/planet-components-react";
  const SKILL_INSTALL_COMMAND = "./tools/install-ai-plugin.sh";
  const SKILL_PROJECT_COMMAND = "./tools/install-ai-plugin.sh --project /path/to/your-app";
  const HOME_PLATFORMS = ["Android", "React Native", "React Web", "Vue", "Flutter", "iOS", "Mini Program", "Kuikly"];
  const HOME_LOCALES = {
    "zh-CN": {
      htmlLang: "zh-CN",
      pageTitle: "Planet Components \xB7 \u6280\u8DA3\u661F\u7403",
      brand: "\u6280\u8DA3\u661F\u7403",
      navAria: "\u9875\u9762\u5BFC\u822A",
      navPreview: "\u6837\u4F8B",
      navPlatforms: "\u6280\u672F\u6808",
      navFeatures: "\u80FD\u529B",
      navSkills: "AI 技能",
      navInstall: "\u5B89\u88C5",
      languageLabel: "\u8BED\u8A00",
      themeLabel: "\u4E3B\u9898",
      dayMode: "\u767D\u5929\u6A21\u5F0F",
      darkMode: "\u6697\u9ED1\u6A21\u5F0F",
      github: "GitHub",
      slogan: "\u6280\u8DA3\u661F\u7403 \xB7 \u7528\u6280\u672F\u521B\u9020\u4E50\u8DA3",
      lead: "面向八端技术栈的基础组件库，契约对齐 57 个控件。语义化 Token 与统一 API 帮助团队在各端复用同一套界面能力。右侧为可交互的 React 样品。",
      statsAria: "GitHub \u6570\u636E",
      star: "Star",
      fork: "Fork",
      forkHint: "在 GitHub 上 Fork 本仓库",
      starHint: "在 GitHub 上为仓库加 Star",
      forkAction: "Fork 仓库",
      starAction: "Star 仓库",
      platformsStat: "\u6280\u672F\u6808",
      goGithub: "\u524D\u5F80 GitHub",
      openRepo: "\u6253\u5F00\u4ED3\u5E93",
      phonePreview: "React samples \u624B\u673A\u9884\u89C8",
      previewCaption: "\u5B9E\u65F6 React \u6837\u54C1\uFF0C\u53EF\u76F4\u63A5\u64CD\u4F5C",
      platformsEyebrow: "覆盖范围",
      platformsTitle: "\u5DF2\u843D\u5730\u7684\u6280\u672F\u6808",
      platformsLead: "八个技术栈均提供独立可发布的 library，以及用于验证接入的 samples。",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "\u4EA7\u54C1\u80FD\u529B",
      featuresTitle: "\u4E3A\u5DE5\u7A0B\u63A5\u5165\u800C\u51C6\u5907",
      featuresLead: "\u4E3B\u9898\u3001\u63A5\u53E3\u4E0E\u6837\u54C1\u6309\u53EF\u53D1\u5E03\u7EC4\u4EF6\u5E93\u7EC4\u7EC7\uFF0C\u4FBF\u4E8E\u5728\u771F\u5B9E\u9879\u76EE\u4E2D\u590D\u7528\u4E0E\u6269\u5C55\u3002",
      features: [
        { title: "\u8BED\u4E49\u5316 Token", body: "\u989C\u8272\u4E0E\u6837\u5F0F\u7531\u7EDF\u4E00 Token \u63CF\u8FF0\uFF0C\u5404\u6280\u672F\u6808\u4F7F\u7528\u540C\u4E00\u5957\u8BED\u4E49\u540D\u79F0\uFF0C\u964D\u4F4E\u6362\u80A4\u4E0E\u5BF9\u9F50\u6210\u672C\u3002" },
        { title: "\u6309\u6808\u72EC\u7ACB\u4EA4\u4ED8", body: "\u6BCF\u4E2A\u6280\u672F\u6808\u62E5\u6709\u72EC\u7ACB library \u4E0E samples\uFF0C\u6837\u54C1\u4F9D\u8D56\u672C\u5730\u5E93\uFF0C\u7528\u4E8E\u9A8C\u8BC1\u771F\u5B9E\u63A5\u5165\u8DEF\u5F84\u3002" },
        { title: "Sky Planet \u89C6\u89C9\u8BED\u8A00", body: "\u5929\u7A7A\u84DD\u3001\u4E91\u9762\u4E0E\u5C9B\u5C7F\u63A7\u4EF6\u6784\u6210\u7EDF\u4E00\u7684\u89C6\u89C9\u4F53\u7CFB\uFF0C\u5E76\u652F\u6301\u767D\u5929\u4E0E\u6697\u9ED1\u4E24\u79CD\u5916\u89C2\u3002" },
        { title: "\u4E00\u81F4\u7684\u7EC4\u4EF6\u5951\u7EA6", body: "variant\u3001disabled\u3001selected \u4EE5\u53CA text / title / message \u7B49\u63A5\u53E3\u5728\u5404\u6808\u4FDD\u6301\u5BF9\u9F50\u3002" }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "AI Skill \u63D2\u4EF6",
      skillsLead: "\u4E3A Cursor\u3001Claude Code \u4E0E Codex \u63D0\u4F9B\u7EC4\u4EF6\u5E93\u6280\u80FD\u3002\u88C5\u4E0A\u540E\uFF0CAgent \u4F1A\u6309\u5951\u7EA6\u5B89\u88C5\u5BF9\u5E94\u6280\u672F\u6808\u7684\u5305\u5E76\u4F20\u5165 theme\uFF0C\u800C\u4E0D\u662F\u53E6\u5199\u4E00\u5957 UI\u3002",
      skills: [
        { name: "use-planet-components", when: "\u5199\u9875\u9762\u3001\u8868\u5355\u3001\u5BFC\u822A\u6216\u4E3B\u9898", body: "\u8BC6\u522B\u5F53\u524D\u6280\u672F\u6808\uFF0C\u5B89\u88C5\u5BF9\u5E94\u5305\uFF0C\u53EA\u4F7F\u7528\u5951\u7EA6 API\uFF0C\u4E0D\u5E73\u884C\u9020\u7EC4\u4EF6\u3002" },
        { name: "build-planet-components", when: "\u5728\u672C\u4ED3\u5E93\u6269\u5C55\u7EC4\u4EF6", body: "\u6309\u72EC\u7ACB library\u3001\u53EF\u8FD0\u884C samples\u3001Token \u4E0E\u4E00\u6587\u4EF6\u4E00\u7EC4\u4EF6\u7684\u89C4\u5219\u843D\u5730\u3002" },
        { name: "build-android-view-ui", when: "\u6539 Android View \u5B9E\u73B0", body: "\u4F7F\u7528 Java \u4E0E XML\uFF0C\u4E0D\u64C5\u81EA\u5F15\u5165 Compose \u6216 Kotlin\u3002" },
        { name: "integrate-react-components", when: "\u53EA\u63A5\u5165 React Web", body: "\u5B8C\u6210\u5B89\u88C5\u3001\u6837\u5F0F\u5F15\u5165\u4E0E starPlanetThemes \u4E3B\u9898\u6CE8\u5165\u3002" }
      ],
      skillInstallTitle: "\u5B89\u88C5\u4E0E\u4F7F\u7528",
      skillInstallLead: "\u5728\u4ED3\u5E93\u6839\u76EE\u5F55\u6267\u884C\u5B89\u88C5\u811A\u672C\uFF0C\u5C06 Skill \u94FE\u5230\u672C\u673A Cursor / Claude / Codex\u3002\u4E1A\u52A1\u9879\u76EE\u53EF\u7528 --project \u53EA\u94FE\u6D88\u8D39\u6280\u80FD\u3002",
      skillCursorHint: "Cursor\uFF1ASettings \u2192 Plugins\uFF0C\u6DFB\u52A0\u672C\u4ED3\u5E93\u6839\u76EE\u5F55\u3002\u65B0\u5F00 Agent \u5BF9\u8BDD\u540E\uFF0C\u63D0\u5230\u6280\u8DA3\u661F\u7403\u6216 Tsp \u7EC4\u4EF6\u5373\u4F1A\u52A0\u8F7D\u3002",
      skillDocs: "\u67E5\u770B\u5B8C\u6574\u8BF4\u660E",
      installEyebrow: "React Web",
      installTitle: "\u4ECE React \u6837\u54C1\u5F00\u59CB",
      installLead: "\u5B89\u88C5\u4F9D\u8D56\u5E76\u5F15\u5165\u6837\u5F0F\u540E\uFF0C\u5C06 theme \u4F20\u5165\u7EC4\u4EF6\u5373\u53EF\u3002\u53F3\u4FA7\u9884\u89C8\u4F7F\u7528\u7684\u662F\u672C\u5730 library\u3002",
      footer: "MIT License \xB7 \u6280\u8DA3\u661F\u7403"
    },
    "zh-TW": {
      htmlLang: "zh-Hant-TW",
      pageTitle: "Planet Components \xB7 \u6280\u8DA3\u661F\u7403",
      brand: "\u6280\u8DA3\u661F\u7403",
      navAria: "\u9801\u9762\u5C0E\u89BD",
      navPreview: "\u7BC4\u4F8B",
      navPlatforms: "\u6280\u8853\u68E7",
      navFeatures: "\u80FD\u529B",
      navSkills: "AI 技能",
      navInstall: "\u5B89\u88DD",
      languageLabel: "\u8A9E\u8A00",
      themeLabel: "\u4E3B\u984C",
      dayMode: "\u65E5\u9593\u6A21\u5F0F",
      darkMode: "\u6697\u9ED1\u6A21\u5F0F",
      github: "GitHub",
      slogan: "\u6280\u8DA3\u661F\u7403 \xB7 \u7528\u6280\u8853\u5275\u9020\u6A02\u8DA3",
      lead: "面向八端技術棧的基礎元件庫，契約對齊 57 個控件。語意化 Token 與統一 API 協助團隊在各端重用同一套介面能力。右側為可互動的 React 樣品。",
      statsAria: "GitHub \u6578\u64DA",
      star: "Star",
      fork: "Fork",
      forkHint: "在 GitHub 上 Fork 本倉庫",
      starHint: "在 GitHub 上為倉庫加 Star",
      forkAction: "Fork 倉庫",
      starAction: "Star 倉庫",
      platformsStat: "\u6280\u8853\u68E7",
      goGithub: "\u524D\u5F80 GitHub",
      openRepo: "\u958B\u555F\u5009\u5EAB",
      phonePreview: "React samples \u624B\u6A5F\u9810\u89BD",
      previewCaption: "\u5373\u6642 React \u6A23\u54C1\uFF0C\u53EF\u76F4\u63A5\u64CD\u4F5C",
      platformsEyebrow: "涵蓋範圍",
      platformsTitle: "\u5DF2\u843D\u5730\u7684\u6280\u8853\u68E7",
      platformsLead: "八個技術棧均提供獨立可發布的 library，以及用於驗證接入的 samples。",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "\u7522\u54C1\u80FD\u529B",
      featuresTitle: "\u70BA\u5DE5\u7A0B\u63A5\u5165\u800C\u6E96\u5099",
      featuresLead: "\u4E3B\u984C\u3001\u4ECB\u9762\u8207\u6A23\u54C1\u4F9D\u53EF\u767C\u5E03\u5143\u4EF6\u5EAB\u7D44\u7E54\uFF0C\u4FBF\u65BC\u5728\u771F\u5BE6\u5C08\u6848\u4E2D\u91CD\u7528\u8207\u64F4\u5145\u3002",
      features: [
        { title: "\u8A9E\u610F\u5316 Token", body: "\u984F\u8272\u8207\u6A23\u5F0F\u7531\u7D71\u4E00 Token \u63CF\u8FF0\uFF0C\u5404\u6280\u8853\u68E7\u4F7F\u7528\u540C\u4E00\u5957\u8A9E\u610F\u540D\u7A31\uFF0C\u964D\u4F4E\u63DB\u819A\u8207\u5C0D\u9F4A\u6210\u672C\u3002" },
        { title: "\u6309\u68E7\u7368\u7ACB\u4EA4\u4ED8", body: "\u6BCF\u500B\u6280\u8853\u68E7\u64C1\u6709\u7368\u7ACB library \u8207 samples\u3002\u6A23\u54C1\u4F9D\u8CF4\u672C\u5730\u5EAB\uFF0C\u7528\u65BC\u9A57\u8B49\u771F\u5BE6\u63A5\u5165\u8DEF\u5F91\u3002" },
        { title: "Sky Planet \u8996\u89BA\u8A9E\u8A00", body: "\u5929\u7A7A\u85CD\u3001\u96F2\u9762\u8207\u5CF6\u5DBC\u63A7\u4EF6\u69CB\u6210\u7D71\u4E00\u7684\u8996\u89BA\u9AD4\u7CFB\uFF0C\u4E26\u652F\u63F4\u65E5\u9593\u8207\u6697\u9ED1\u5169\u7A2E\u5916\u89C0\u3002" },
        { title: "\u4E00\u81F4\u7684\u5143\u4EF6\u5951\u7D04", body: "variant\u3001disabled\u3001selected \u4EE5\u53CA text / title / message \u7B49\u4ECB\u9762\u5728\u5404\u68E7\u4FDD\u6301\u5C0D\u9F4A\u3002" }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "AI Skill \u5916\u639B",
      skillsLead: "\u70BA Cursor\u3001Claude Code \u8207 Codex \u63D0\u4F9B\u5143\u4EF6\u5EAB\u6280\u80FD\u3002\u5B89\u88DD\u5F8C\uFF0CAgent \u6703\u4F9D\u5951\u7D04\u5B89\u88DD\u5C0D\u61C9\u6280\u8853\u68E7\u7684\u5957\u4EF6\u4E26\u50B3\u5165 theme\uFF0C\u800C\u4E0D\u662F\u53E6\u5BEB\u4E00\u5957 UI\u3002",
      skills: [
        { name: "use-planet-components", when: "\u64B0\u5BEB\u9801\u9762\u3001\u8868\u55AE\u3001\u5C0E\u89BD\u6216\u4E3B\u984C", body: "\u8B58\u5225\u76EE\u524D\u6280\u8853\u68E7\uFF0C\u5B89\u88DD\u5C0D\u61C9\u5957\u4EF6\uFF0C\u53EA\u4F7F\u7528\u5951\u7D04 API\uFF0C\u4E0D\u5E73\u884C\u6253\u9020\u5143\u4EF6\u3002" },
        { name: "build-planet-components", when: "\u5728\u672C\u5009\u5EAB\u64F4\u5145\u5143\u4EF6", body: "\u4F9D\u7368\u7ACB library\u3001\u53EF\u57F7\u884C samples\u3001Token \u8207\u4E00\u6A94\u4E00\u5143\u4EF6\u7684\u898F\u5247\u843D\u5730\u3002" },
        { name: "build-android-view-ui", when: "\u4FEE\u6539 Android View \u5BE6\u4F5C", body: "\u4F7F\u7528 Java \u8207 XML\uFF0C\u4E0D\u64C5\u81EA\u5F15\u5165 Compose \u6216 Kotlin\u3002" },
        { name: "integrate-react-components", when: "\u53EA\u63A5\u5165 React Web", body: "\u5B8C\u6210\u5B89\u88DD\u3001\u6A23\u5F0F\u5F15\u5165\u8207 starPlanetThemes \u4E3B\u984C\u6CE8\u5165\u3002" }
      ],
      skillInstallTitle: "\u5B89\u88DD\u8207\u4F7F\u7528",
      skillInstallLead: "\u5728\u5009\u5EAB\u6839\u76EE\u9304\u57F7\u884C\u5B89\u88DD\u6307\u4EE4\u78BC\uFF0C\u5C07 Skill \u9023\u7D50\u5230\u672C\u6A5F Cursor / Claude / Codex\u3002\u696D\u52D9\u5C08\u6848\u53EF\u7528 --project \u53EA\u9023\u7D50\u6D88\u8CBB\u6280\u80FD\u3002",
      skillCursorHint: "Cursor\uFF1ASettings \u2192 Plugins\uFF0C\u65B0\u589E\u672C\u5009\u5EAB\u6839\u76EE\u9304\u3002\u65B0\u958B Agent \u5C0D\u8A71\u5F8C\uFF0C\u63D0\u5230\u6280\u8DA3\u661F\u7403\u6216 Tsp \u5143\u4EF6\u5373\u6703\u8F09\u5165\u3002",
      skillDocs: "\u67E5\u770B\u5B8C\u6574\u8AAA\u660E",
      installEyebrow: "React Web",
      installTitle: "\u5F9E React \u6A23\u54C1\u958B\u59CB",
      installLead: "\u5B89\u88DD\u4F9D\u8CF4\u4E26\u5F15\u5165\u6A23\u5F0F\u5F8C\uFF0C\u5C07 theme \u50B3\u5165\u5143\u4EF6\u5373\u53EF\u3002\u53F3\u5074\u9810\u89BD\u4F7F\u7528\u7684\u662F\u672C\u5730 library\u3002",
      footer: "MIT License \xB7 \u6280\u8DA3\u661F\u7403"
    },
    en: {
      htmlLang: "en",
      pageTitle: "Planet Components \xB7 TechSkillPlanet",
      brand: "TechSkillPlanet",
      navAria: "Page navigation",
      navPreview: "Samples",
      navPlatforms: "Stacks",
      navFeatures: "Capabilities",
      navSkills: "AI Skills",
      navInstall: "Install",
      languageLabel: "Language",
      themeLabel: "Theme",
      dayMode: "Light mode",
      darkMode: "Dark mode",
      github: "GitHub",
      slogan: "TechSkillPlanet \xB7 Create fun with technology",
      lead: "A foundational component library for eight technology stacks, with 57 contract-aligned controls. Semantic tokens and a shared API help teams reuse the same UI primitives. The phone on the right is a live React sample.",
      statsAria: "GitHub stats",
      star: "Star",
      fork: "Fork",
      forkHint: "Fork this repository on GitHub",
      starHint: "Star this repository on GitHub",
      forkAction: "Fork repo",
      starAction: "Star repo",
      platformsStat: "Stacks",
      goGithub: "View on GitHub",
      openRepo: "Open repository",
      phonePreview: "React samples phone preview",
      previewCaption: "Live React sample, fully interactive",
      platformsEyebrow: "Coverage",
      platformsTitle: "Implemented stacks",
      platformsLead: "Each of the eight stacks ships a publishable library plus samples for integration verification.",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "Capabilities",
      featuresTitle: "Designed for adoption",
      featuresLead: "Themes, APIs, and samples are structured as publishable libraries for real product work.",
      features: [
        { title: "Semantic tokens", body: "Color and style are described by a shared token set, so stacks reuse the same semantic names instead of hardcoded values." },
        { title: "Delivered per stack", body: "Each technology stack has its own library and samples. Samples depend on the local library to verify a real integration path." },
        { title: "Sky Planet language", body: "Sky blue, cloud surfaces, and island controls form a consistent visual system, with light and dark appearance." },
        { title: "Aligned component contracts", body: "Interfaces such as variant, disabled, selected, and text / title / message stay consistent across stacks." }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "Agent skills",
      skillsLead: "Skills for Cursor, Claude Code, and Codex. After install, the agent installs the matching stack package, passes theme, and stays on the component contract instead of inventing UI.",
      skills: [
        { name: "use-planet-components", when: "Screens, forms, navigation, or theming", body: "Detect the host stack, install the matching package, and use contract APIs only." },
        { name: "build-planet-components", when: "Extending this repository", body: "Land each stack as a library plus samples, with tokens and one component per file." },
        { name: "build-android-view-ui", when: "Android View implementation", body: "Stay on Java and XML. Do not introduce Compose or Kotlin unless asked." },
        { name: "integrate-react-components", when: "React Web only", body: "Install the package, import styles, and inject starPlanetThemes." }
      ],
      skillInstallTitle: "Install and use",
      skillInstallLead: "Run the installer from the repository root to link skills into local Cursor, Claude, or Codex. Use --project to attach only the consume skill to an application.",
      skillCursorHint: "Cursor: Settings \u2192 Plugins, add this repository root. Start a new agent chat and mention TechSkillPlanet or a Tsp control.",
      skillDocs: "Read the full guide",
      installEyebrow: "React Web",
      installTitle: "Start with the React sample",
      installLead: "Install the package, import styles, and pass a theme. The preview uses the local library.",
      footer: "MIT License \xB7 TechSkillPlanet"
    },
    ja: {
      htmlLang: "ja",
      pageTitle: "Planet Components \xB7 \u6280\u8DA3\u661F\u7403",
      brand: "\u6280\u8DA3\u661F\u7403",
      navAria: "\u30DA\u30FC\u30B8\u30CA\u30D3",
      navPreview: "\u30B5\u30F3\u30D7\u30EB",
      navPlatforms: "\u6280\u8853\u30B9\u30BF\u30C3\u30AF",
      navFeatures: "\u6A5F\u80FD",
      navSkills: "AI Skill",
      navInstall: "\u30A4\u30F3\u30B9\u30C8\u30FC\u30EB",
      languageLabel: "\u8A00\u8A9E",
      themeLabel: "\u30C6\u30FC\u30DE",
      dayMode: "\u30E9\u30A4\u30C8\u30E2\u30FC\u30C9",
      darkMode: "\u30C0\u30FC\u30AF\u30E2\u30FC\u30C9",
      github: "GitHub",
      slogan: "\u6280\u8DA3\u661F\u7403 \xB7 \u6280\u8853\u3067\u697D\u3057\u3055\u3092\u4F5C\u308B",
      lead: "8 つの技術スタック向けの基礎コンポーネントライブラリ。契約に揃えた 57 のコントロールを提供します。セマンティック Token と共通 API で、各端に同じ UI を再利用できます。右側は操作可能な React サンプルです。",
      statsAria: "GitHub \u30C7\u30FC\u30BF",
      star: "Star",
      fork: "Fork",
      forkHint: "GitHub でこのリポジトリを Fork",
      starHint: "GitHub でこのリポジトリに Star",
      forkAction: "Fork する",
      starAction: "Star する",
      platformsStat: "\u6280\u8853\u30B9\u30BF\u30C3\u30AF",
      goGithub: "GitHub \u3092\u958B\u304F",
      openRepo: "\u30EA\u30DD\u30B8\u30C8\u30EA",
      phonePreview: "React \u30B5\u30F3\u30D7\u30EB\u306E\u30B9\u30DE\u30DB\u30D7\u30EC\u30D3\u30E5\u30FC",
      previewCaption: "\u5B9F\u884C\u4E2D\u306E React \u30B5\u30F3\u30D7\u30EB\u3002\u305D\u306E\u307E\u307E\u64CD\u4F5C\u3067\u304D\u307E\u3059",
      platformsEyebrow: "対応範囲",
      platformsTitle: "\u5B9F\u88C5\u6E08\u307F\u306E\u6280\u8853\u30B9\u30BF\u30C3\u30AF",
      platformsLead: "8 つの技術スタックそれぞれに公開可能な library と、接続検証用 samples を用意しています。",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "\u6A5F\u80FD",
      featuresTitle: "\u5C0E\u5165\u3092\u524D\u63D0\u306B\u3057\u305F\u69CB\u6210",
      featuresLead: "\u30C6\u30FC\u30DE\u3001API\u3001\u30B5\u30F3\u30D7\u30EB\u306F\u516C\u958B\u53EF\u80FD\u306A\u30B3\u30F3\u30DD\u30FC\u30CD\u30F3\u30C8\u30E9\u30A4\u30D6\u30E9\u30EA\u3068\u3057\u3066\u6574\u7406\u3055\u308C\u3066\u3044\u307E\u3059\u3002",
      features: [
        { title: "\u30BB\u30DE\u30F3\u30C6\u30A3\u30C3\u30AF Token", body: "\u8272\u3068\u30B9\u30BF\u30A4\u30EB\u306F\u5171\u901A Token \u3067\u8A18\u8FF0\u3055\u308C\u3001\u5404\u30B9\u30BF\u30C3\u30AF\u306F\u540C\u3058\u610F\u5473\u540D\u3092\u518D\u5229\u7528\u3067\u304D\u307E\u3059\u3002" },
        { title: "\u30B9\u30BF\u30C3\u30AF\u5358\u4F4D\u3067\u63D0\u4F9B", body: "\u5404\u6280\u8853\u30B9\u30BF\u30C3\u30AF\u306B\u72EC\u7ACB\u3057\u305F library \u3068 samples \u304C\u3042\u308A\u307E\u3059\u3002\u30B5\u30F3\u30D7\u30EB\u306F\u30ED\u30FC\u30AB\u30EB\u30E9\u30A4\u30D6\u30E9\u30EA\u306B\u4F9D\u5B58\u3057\u3001\u5B9F\u969B\u306E\u5C0E\u5165\u30D1\u30B9\u3092\u691C\u8A3C\u3057\u307E\u3059\u3002" },
        { title: "Sky Planet \u306E\u8996\u899A\u8A00\u8A9E", body: "\u30B9\u30AB\u30A4\u30D6\u30EB\u30FC\u3001\u96F2\u306E\u3088\u3046\u306A\u9762\u3001\u30A2\u30A4\u30E9\u30F3\u30C9\u72B6\u306E\u30B3\u30F3\u30C8\u30ED\u30FC\u30EB\u3067\u4E00\u8CAB\u3057\u305F\u898B\u305F\u76EE\u3092\u4FDD\u3061\u3001\u30E9\u30A4\u30C8\u3068\u30C0\u30FC\u30AF\u306B\u5BFE\u5FDC\u3057\u307E\u3059\u3002" },
        { title: "\u63C3\u3063\u305F\u30B3\u30F3\u30DD\u30FC\u30CD\u30F3\u30C8\u5951\u7D04", body: "variant\u3001disabled\u3001selected\u3001text / title / message \u306A\u3069\u306E\u30A4\u30F3\u30BF\u30FC\u30D5\u30A7\u30FC\u30B9\u306F\u5404\u30B9\u30BF\u30C3\u30AF\u3067\u63C3\u3048\u3066\u3044\u307E\u3059\u3002" }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "AI Skill \u30D7\u30E9\u30B0\u30A4\u30F3",
      skillsLead: "Cursor\u3001Claude Code\u3001Codex \u5411\u3051\u306E\u30B3\u30F3\u30DD\u30FC\u30CD\u30F3\u30C8\u30E9\u30A4\u30D6\u30E9\u30EA Skill \u3067\u3059\u3002\u5C0E\u5165\u5F8C\u3001Agent \u306F\u5951\u7D04\u306B\u5F93\u3063\u3066\u30B9\u30BF\u30C3\u30AF\u306E\u30D1\u30C3\u30B1\u30FC\u30B8\u3092\u5165\u308C\u3001theme \u3092\u6E21\u3057\u307E\u3059\u3002\u72EC\u81EA UI \u306F\u4F5C\u308A\u307E\u305B\u3093\u3002",
      skills: [
        { name: "use-planet-components", when: "\u753B\u9762\u3001\u30D5\u30A9\u30FC\u30E0\u3001\u30CA\u30D3\u3001\u30C6\u30FC\u30DE", body: "\u30DB\u30B9\u30C8\u306E\u6280\u8853\u30B9\u30BF\u30C3\u30AF\u3092\u5224\u5B9A\u3057\u3001\u5BFE\u5FDC\u30D1\u30C3\u30B1\u30FC\u30B8\u3092\u5165\u308C\u3066\u5951\u7D04 API \u3060\u3051\u3092\u4F7F\u3044\u307E\u3059\u3002" },
        { name: "build-planet-components", when: "\u3053\u306E\u30EA\u30DD\u30B8\u30C8\u30EA\u3067\u30B3\u30F3\u30DD\u30FC\u30CD\u30F3\u30C8\u3092\u62E1\u5F35", body: "\u5404\u30B9\u30BF\u30C3\u30AF\u3092\u72EC\u7ACB\u3057\u305F library \u3068 samples\u3001Token\u30011 \u30D5\u30A1\u30A4\u30EB 1 \u30B3\u30F3\u30DD\u30FC\u30CD\u30F3\u30C8\u3067\u5B9F\u88C5\u3057\u307E\u3059\u3002" },
        { name: "build-android-view-ui", when: "Android View \u306E\u5B9F\u88C5", body: "Java \u3068 XML \u3092\u4F7F\u3044\u307E\u3059\u3002\u4F9D\u983C\u304C\u306A\u3044\u9650\u308A Compose \u3084 Kotlin \u306F\u5165\u308C\u307E\u305B\u3093\u3002" },
        { name: "integrate-react-components", when: "React Web \u3060\u3051\u3092\u63A5\u7D9A", body: "\u30D1\u30C3\u30B1\u30FC\u30B8\u5C0E\u5165\u3001\u30B9\u30BF\u30A4\u30EB\u8AAD\u307F\u8FBC\u307F\u3001starPlanetThemes \u306E\u6CE8\u5165\u307E\u3067\u3092\u62C5\u5F53\u3057\u307E\u3059\u3002" }
      ],
      skillInstallTitle: "\u30A4\u30F3\u30B9\u30C8\u30FC\u30EB\u3068\u4F7F\u3044\u65B9",
      skillInstallLead: "\u30EA\u30DD\u30B8\u30C8\u30EA\u76F4\u4E0B\u3067\u30A4\u30F3\u30B9\u30C8\u30FC\u30EB\u30B9\u30AF\u30EA\u30D7\u30C8\u3092\u5B9F\u884C\u3057\u3001\u672C\u6A5F\u306E Cursor / Claude / Codex \u306B Skill \u3092\u30EA\u30F3\u30AF\u3057\u307E\u3059\u3002\u696D\u52D9\u30A2\u30D7\u30EA\u306B\u306F --project \u3067\u6D88\u8CBB\u7528 Skill \u3060\u3051\u3092\u4ED8\u3051\u3089\u308C\u307E\u3059\u3002",
      skillCursorHint: "Cursor\uFF1ASettings \u2192 Plugins \u3067\u3053\u306E\u30EA\u30DD\u30B8\u30C8\u30EA\u306E\u30EB\u30FC\u30C8\u3092\u8FFD\u52A0\u3002\u65B0\u3057\u3044 Agent \u4F1A\u8A71\u3067\u6280\u8DA3\u661F\u7403\u3084 Tsp \u30B3\u30F3\u30DD\u30FC\u30CD\u30F3\u30C8\u306B\u8A00\u53CA\u3059\u308B\u3068\u8AAD\u307F\u8FBC\u307E\u308C\u307E\u3059\u3002",
      skillDocs: "\u8A73\u7D30\u30AC\u30A4\u30C9\u3092\u898B\u308B",
      installEyebrow: "React Web",
      installTitle: "React \u30B5\u30F3\u30D7\u30EB\u304B\u3089\u59CB\u3081\u308B",
      installLead: "\u30D1\u30C3\u30B1\u30FC\u30B8\u3092\u5165\u308C\u3001\u30B9\u30BF\u30A4\u30EB\u3092\u8AAD\u307F\u8FBC\u307F\u3001theme \u3092\u6E21\u3057\u307E\u3059\u3002\u30D7\u30EC\u30D3\u30E5\u30FC\u306F\u30ED\u30FC\u30AB\u30EB\u30E9\u30A4\u30D6\u30E9\u30EA\u3092\u4F7F\u7528\u3057\u307E\u3059\u3002",
      footer: "MIT License \xB7 \u6280\u8DA3\u661F\u7403"
    },
    ko: {
      htmlLang: "ko",
      pageTitle: "Planet Components · 기취성구",
      brand: "\u6280\u8DA3\u661F\u7403",
      navAria: "\uD398\uC774\uC9C0 \uD0D0\uC0C9",
      navPreview: "\uC0D8\uD50C",
      navPlatforms: "\uC2A4\uD0DD",
      navFeatures: "\uAE30\uB2A5",
      navSkills: "AI Skill",
      navInstall: "\uC124\uCE58",
      languageLabel: "\uC5B8\uC5B4",
      themeLabel: "\uD14C\uB9C8",
      dayMode: "\uB77C\uC774\uD2B8 \uBAA8\uB4DC",
      darkMode: "\uB2E4\uD06C \uBAA8\uB4DC",
      github: "GitHub",
      slogan: "\u6280\u8DA3\u661F\u7403 \xB7 \uAE30\uC220\uB85C \uC990\uAC70\uC6C0\uC744 \uB9CC\uB4E4\uB2E4",
      lead: "8개 기술 스택을 위한 기초 컴포넌트 라이브러리로, 계약에 맞춘 57개 컨트롤을 제공합니다. 시맨틱 Token과 공통 API로 각 환경에서 동일한 UI를 재사용할 수 있습니다. 오른쪽은 조작 가능한 React 샘플입니다.",
      statsAria: "GitHub \uD1B5\uACC4",
      star: "Star",
      fork: "Fork",
      forkHint: "GitHub에서 이 저장소 Fork",
      starHint: "GitHub에서 이 저장소에 Star",
      forkAction: "Fork 하기",
      starAction: "Star 하기",
      platformsStat: "\uC2A4\uD0DD",
      goGithub: "GitHub\uB85C \uC774\uB3D9",
      openRepo: "\uC800\uC7A5\uC18C \uC5F4\uAE30",
      phonePreview: "React \uC0D8\uD50C \uD734\uB300\uD3F0 \uBBF8\uB9AC\uBCF4\uAE30",
      previewCaption: "\uC2E4\uC2DC\uAC04 React \uC0D8\uD50C, \uBC14\uB85C \uC870\uC791\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4",
      platformsEyebrow: "커버리지",
      platformsTitle: "\uAD6C\uD604\uB41C \uAE30\uC220 \uC2A4\uD0DD",
      platformsLead: "8개 기술 스택마다 배포 가능한 library와 연동 검증용 samples를 제공합니다.",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "\uC81C\uD488 \uC5ED\uB7C9",
      featuresTitle: "\uC5D4\uC9C0\uB2C8\uC5B4\uB9C1 \uB3C4\uC785\uC744 \uC704\uD55C \uAD6C\uC131",
      featuresLead: "\uD14C\uB9C8, API, \uC0D8\uD50C\uC774 \uAC8C\uC2DC \uAC00\uB2A5\uD55C \uCEF4\uD3EC\uB10C\uD2B8 \uB77C\uC774\uBE0C\uB7EC\uB9AC\uB85C \uC815\uB9AC\uB418\uC5B4 \uC2E4\uC81C \uC81C\uD488\uC5D0 \uC7AC\uC0AC\uC6A9\uD558\uAE30 \uC27D\uC2B5\uB2C8\uB2E4.",
      features: [
        { title: "\uC2DC\uB9E8\uD2F1 Token", body: "\uC0C9\uACFC \uC2A4\uD0C0\uC77C\uC740 \uACF5\uD1B5 Token\uC73C\uB85C \uAE30\uC220\uB418\uBA70, \uAC01 \uC2A4\uD0DD\uC740 \uB3D9\uC77C\uD55C \uC758\uBBF8 \uC774\uB984\uC744 \uC7AC\uC0AC\uC6A9\uD558\uC5EC \uD14C\uB9C8 \uAD50\uCCB4\uC640 \uC815\uB82C \uBE44\uC6A9\uC744 \uC904\uC785\uB2C8\uB2E4." },
        { title: "\uC2A4\uD0DD\uBCC4 \uB3C5\uB9BD \uC81C\uACF5", body: "\uAC01 \uAE30\uC220 \uC2A4\uD0DD\uC740 \uB3C5\uB9BD library\uC640 samples\uB97C \uAC00\uC9D1\uB2C8\uB2E4. \uC0D8\uD50C\uC740 \uB85C\uCEEC \uB77C\uC774\uBE0C\uB7EC\uB9AC\uC5D0 \uC758\uC874\uD574 \uC2E4\uC81C \uC5F0\uB3D9 \uACBD\uB85C\uB97C \uAC80\uC99D\uD569\uB2C8\uB2E4." },
        { title: "Sky Planet \uC2DC\uAC01 \uC5B8\uC5B4", body: "\uC2A4\uCE74\uC774 \uBE14\uB8E8, \uD074\uB77C\uC6B0\uB4DC \uD45C\uBA74, \uC544\uC77C\uB79C\uB4DC \uCEE8\uD2B8\uB864\uC774 \uC77C\uAD00\uB41C \uC2DC\uAC01 \uCCB4\uACC4\uB97C \uC774\uB8E8\uBA70 \uB77C\uC774\uD2B8\uC640 \uB2E4\uD06C\uB97C \uC9C0\uC6D0\uD569\uB2C8\uB2E4." },
        { title: "\uC815\uB82C\uB41C \uCEF4\uD3EC\uB10C\uD2B8 \uACC4\uC57D", body: "variant, disabled, selected\uC640 text / title / message \uC778\uD130\uD398\uC774\uC2A4\uAC00 \uC2A4\uD0DD \uAC04\uC5D0 \uB9DE\uCDB0\uC838 \uC788\uC2B5\uB2C8\uB2E4." }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "AI Skill \uD50C\uB7EC\uADF8\uC778",
      skillsLead: "Cursor, Claude Code, Codex\uC6A9 \uCEF4\uD3EC\uB10C\uD2B8 \uB77C\uC774\uBE0C\uB7EC\uB9AC Skill\uC785\uB2C8\uB2E4. \uC124\uCE58 \uD6C4 Agent\uB294 \uACC4\uC57D\uC5D0 \uB530\uB77C \uD574\uB2F9 \uC2A4\uD0DD \uD328\uD0A4\uC9C0\uB97C \uC124\uCE58\uD558\uACE0 theme\uB97C \uC804\uB2EC\uD558\uBA70, \uBCC4\uB3C4\uC758 UI\uB97C \uB9CC\uB4E4\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4.",
      skills: [
        { name: "use-planet-components", when: "\uD654\uBA74, \uD3FC, \uD0D0\uC0C9 \uB610\uB294 \uD14C\uB9C8", body: "\uD638\uC2A4\uD2B8 \uC2A4\uD0DD\uC744 \uD310\uBCC4\uD558\uACE0 \uD574\uB2F9 \uD328\uD0A4\uC9C0\uB97C \uC124\uCE58\uD558\uBA70 \uACC4\uC57D API\uB9CC \uC0AC\uC6A9\uD569\uB2C8\uB2E4." },
        { name: "build-planet-components", when: "\uC774 \uC800\uC7A5\uC18C\uC5D0\uC11C \uCEF4\uD3EC\uB10C\uD2B8 \uD655\uC7A5", body: "\uAC01 \uC2A4\uD0DD\uC744 \uB3C5\uB9BD library\uC640 samples, Token, \uD30C\uC77C\uB2F9 \uD558\uB098\uC758 \uCEF4\uD3EC\uB10C\uD2B8\uB85C \uAD6C\uD604\uD569\uB2C8\uB2E4." },
        { name: "build-android-view-ui", when: "Android View \uAD6C\uD604", body: "Java\uC640 XML\uC744 \uC0AC\uC6A9\uD569\uB2C8\uB2E4. \uC694\uCCAD\uC774 \uC5C6\uC73C\uBA74 Compose\uB098 Kotlin\uC744 \uB3C4\uC785\uD558\uC9C0 \uC54A\uC2B5\uB2C8\uB2E4." },
        { name: "integrate-react-components", when: "React Web\uB9CC \uC5F0\uB3D9", body: "\uD328\uD0A4\uC9C0 \uC124\uCE58, \uC2A4\uD0C0\uC77C \uAC00\uC838\uC624\uAE30, starPlanetThemes \uC8FC\uC785\uAE4C\uC9C0 \uB2F4\uB2F9\uD569\uB2C8\uB2E4." }
      ],
      skillInstallTitle: "\uC124\uCE58 \uBC0F \uC0AC\uC6A9",
      skillInstallLead: "\uC800\uC7A5\uC18C \uB8E8\uD2B8\uC5D0\uC11C \uC124\uCE58 \uC2A4\uD06C\uB9BD\uD2B8\uB97C \uC2E4\uD589\uD574 \uB85C\uCEEC Cursor / Claude / Codex\uC5D0 Skill\uC744 \uC5F0\uACB0\uD569\uB2C8\uB2E4. \uC5C5\uBB34 \uC571\uC5D0\uB294 --project\uB85C \uC18C\uBE44\uC6A9 Skill\uB9CC \uC5F0\uACB0\uD560 \uC218 \uC788\uC2B5\uB2C8\uB2E4.",
      skillCursorHint: "Cursor: Settings \u2192 Plugins\uC5D0\uC11C \uC774 \uC800\uC7A5\uC18C \uB8E8\uD2B8\uB97C \uCD94\uAC00\uD558\uC138\uC694. \uC0C8 Agent \uB300\uD654\uC5D0\uC11C \u6280\u8DA3\u661F\u7403 \uB610\uB294 Tsp \uCEF4\uD3EC\uB10C\uD2B8\uB97C \uC5B8\uAE09\uD558\uBA74 \uB85C\uB4DC\uB429\uB2C8\uB2E4.",
      skillDocs: "\uC804\uCCB4 \uAC00\uC774\uB4DC \uBCF4\uAE30",
      installEyebrow: "React Web",
      installTitle: "React \uC0D8\uD50C\uBD80\uD130 \uC2DC\uC791",
      installLead: "\uD328\uD0A4\uC9C0\uB97C \uC124\uCE58\uD558\uACE0 \uC2A4\uD0C0\uC77C\uC744 \uAC00\uC838\uC628 \uB4A4 theme\uB97C \uC804\uB2EC\uD558\uC138\uC694. \uBBF8\uB9AC\uBCF4\uAE30\uB294 \uB85C\uCEEC library\uB97C \uC0AC\uC6A9\uD569\uB2C8\uB2E4.",
      footer: "MIT License \xB7 \u6280\u8DA3\u661F\u7403"
    },
    es: {
      htmlLang: "es",
      pageTitle: "Planet Components · TechSkillPlanet",
      brand: "TechSkillPlanet",
      navAria: "Navegaci\xF3n de la p\xE1gina",
      navPreview: "Ejemplos",
      navPlatforms: "Stacks",
      navFeatures: "Capacidades",
      navSkills: "AI Skills",
      navInstall: "Instalar",
      languageLabel: "Idioma",
      themeLabel: "Tema",
      dayMode: "Modo claro",
      darkMode: "Modo oscuro",
      github: "GitHub",
      slogan: "TechSkillPlanet \xB7 Crear diversi\xF3n con tecnolog\xEDa",
      lead: "Biblioteca de componentes base para ocho stacks tecnológicos, con 57 controles alineados por contrato. Tokens semánticos y una API compartida ayudan a reutilizar las mismas primitivas de UI. El teléfono de la derecha es un sample React en vivo.",
      statsAria: "Datos de GitHub",
      star: "Star",
      fork: "Fork",
      forkHint: "Hacer Fork del repositorio en GitHub",
      starHint: "Dar Star al repositorio en GitHub",
      forkAction: "Hacer Fork",
      starAction: "Dar Star",
      platformsStat: "Stacks",
      goGithub: "Ir a GitHub",
      openRepo: "Abrir repositorio",
      phonePreview: "Vista previa del sample React en el tel\xE9fono",
      previewCaption: "Sample de React en vivo, listo para interactuar",
      platformsEyebrow: "Cobertura",
      platformsTitle: "Stacks ya implementados",
      platformsLead: "Cada uno de los ocho stacks incluye una library publicable y samples para verificar la integración.",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "Capacidades",
      featuresTitle: "Preparada para la adopci\xF3n",
      featuresLead: "Temas, APIs y samples est\xE1n organizados como bibliotecas publicables para trabajo de producto real.",
      features: [
        { title: "Tokens sem\xE1nticos", body: "Color y estilo se describen con un conjunto compartido de tokens. Cada stack reutiliza los mismos nombres sem\xE1nticos en lugar de valores fijos." },
        { title: "Entrega por stack", body: "Cada stack tecnol\xF3gico tiene su propia library y samples. Los samples dependen de la biblioteca local para verificar una integraci\xF3n real." },
        { title: "Lenguaje visual Sky Planet", body: "Azul cielo, superficies de nube y controles tipo isla forman un sistema visual coherente, con apariencia clara y oscura." },
        { title: "Contrato de componentes alineado", body: "Interfaces como variant, disabled, selected y text / title / message se mantienen consistentes entre stacks." }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "Complemento de AI Skills",
      skillsLead: "Skills para Cursor, Claude Code y Codex. Tras instalarlas, el agente instala el paquete del stack correspondiente, pasa theme y se mantiene en el contrato de componentes, sin inventar otra UI.",
      skills: [
        { name: "use-planet-components", when: "Pantallas, formularios, navegaci\xF3n o tema", body: "Detecta el stack anfitri\xF3n, instala el paquete correspondiente y usa solo las APIs del contrato." },
        { name: "build-planet-components", when: "Extender este repositorio", body: "Implementa cada stack como library m\xE1s samples, con tokens y un componente por archivo." },
        { name: "build-android-view-ui", when: "Implementaci\xF3n Android View", body: "Usa Java y XML. No introduce Compose ni Kotlin salvo que se pida." },
        { name: "integrate-react-components", when: "Solo React Web", body: "Instala el paquete, importa estilos e inyecta starPlanetThemes." }
      ],
      skillInstallTitle: "Instalaci\xF3n y uso",
      skillInstallLead: "Ejecuta el instalador en la ra\xEDz del repositorio para enlazar las skills en Cursor, Claude o Codex locales. Con --project puedes adjuntar solo la skill de consumo a una aplicaci\xF3n.",
      skillCursorHint: "Cursor: Settings \u2192 Plugins, a\xF1ade la ra\xEDz de este repositorio. Abre un nuevo chat de agente y menciona TechSkillPlanet o un control Tsp.",
      skillDocs: "Ver la gu\xEDa completa",
      installEyebrow: "React Web",
      installTitle: "Empieza con el sample de React",
      installLead: "Instala el paquete, importa los estilos y pasa un theme. La vista previa usa la library local.",
      footer: "Licencia MIT \u00b7 TechSkillPlanet"
    },
    fr: {
      htmlLang: "fr",
      pageTitle: "Planet Components · TechSkillPlanet",
      brand: "TechSkillPlanet",
      navAria: "Navigation de la page",
      navPreview: "Exemples",
      navPlatforms: "Stacks",
      navFeatures: "Capacit\xE9s",
      navSkills: "AI Skills",
      navInstall: "Installer",
      languageLabel: "Langue",
      themeLabel: "Th\xE8me",
      dayMode: "Mode clair",
      darkMode: "Mode sombre",
      github: "GitHub",
      slogan: "TechSkillPlanet \xB7 Cr\xE9er du plaisir avec la technique",
      lead: "Bibliothèque de composants de base pour huit stacks technologiques, avec 57 contrôles alignés sur le contrat. Des tokens sémantiques et une API partagée aident à réutiliser les mêmes primitives UI. Le téléphone à droite est un sample React interactif.",
      statsAria: "Statistiques GitHub",
      star: "Star",
      fork: "Fork",
      forkHint: "Forker le dépôt sur GitHub",
      starHint: "Mettre une Star au dépôt sur GitHub",
      forkAction: "Forker",
      starAction: "Mettre une Star",
      platformsStat: "Stacks",
      goGithub: "Voir sur GitHub",
      openRepo: "Ouvrir le d\xE9p\xF4t",
      phonePreview: "Aper\xE7u t\xE9l\xE9phone du sample React",
      previewCaption: "Sample React en direct, enti\xE8rement interactif",
      platformsEyebrow: "Couverture",
      platformsTitle: "Stacks d\xE9j\xE0 impl\xE9ment\xE9s",
      platformsLead: "Chacun des huit stacks fournit une library publiable et des samples pour vérifier l’intégration.",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "Capacit\xE9s",
      featuresTitle: "Con\xE7ue pour l\u2019adoption",
      featuresLead: "Th\xE8mes, API et samples sont organis\xE9s comme des biblioth\xE8ques publiables, pour un usage produit r\xE9el.",
      features: [
        { title: "Tokens s\xE9mantiques", body: "Couleur et style sont d\xE9crits par un jeu de tokens partag\xE9. Les stacks r\xE9utilisent les m\xEAmes noms s\xE9mantiques plut\xF4t que des valeurs en dur." },
        { title: "Livraison par stack", body: "Chaque stack technologique dispose de sa library et de ses samples. Les samples d\xE9pendent de la biblioth\xE8que locale pour v\xE9rifier un vrai chemin d\u2019int\xE9gration." },
        { title: "Langage visuel Sky Planet", body: "Bleu ciel, surfaces nuageuses et contr\xF4les en \xEEle forment un syst\xE8me visuel coh\xE9rent, avec apparences claire et sombre." },
        { title: "Contrat de composants align\xE9", body: "Des interfaces telles que variant, disabled, selected et text / title / message restent coh\xE9rentes d\u2019un stack \xE0 l\u2019autre." }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "Plugin AI Skills",
      skillsLead: "Skills pour Cursor, Claude Code et Codex. Apr\xE8s installation, l\u2019agent installe le paquet du stack correspondant, transmet theme et reste sur le contrat de composants, sans inventer une autre UI.",
      skills: [
        { name: "use-planet-components", when: "\xC9crans, formulaires, navigation ou th\xE8me", body: "D\xE9tecte le stack h\xF4te, installe le paquet correspondant et n\u2019utilise que les API du contrat." },
        { name: "build-planet-components", when: "\xC9tendre ce d\xE9p\xF4t", body: "Impl\xE9mente chaque stack comme library plus samples, avec tokens et un composant par fichier." },
        { name: "build-android-view-ui", when: "Impl\xE9mentation Android View", body: "Reste sur Java et XML. N\u2019introduit Compose ni Kotlin sauf demande explicite." },
        { name: "integrate-react-components", when: "React Web uniquement", body: "Installe le paquet, importe les styles et injecte starPlanetThemes." }
      ],
      skillInstallTitle: "Installation et usage",
      skillInstallLead: "Ex\xE9cutez l\u2019installateur \xE0 la racine du d\xE9p\xF4t pour lier les skills \xE0 Cursor, Claude ou Codex en local. --project n\u2019attache que la skill de consommation \xE0 une application.",
      skillCursorHint: "Cursor : Settings \u2192 Plugins, ajoutez la racine de ce d\xE9p\xF4t. D\xE9marrez un nouveau chat agent et mentionnez TechSkillPlanet ou un contr\xF4le Tsp.",
      skillDocs: "Lire le guide complet",
      installEyebrow: "React Web",
      installTitle: "Commencer par le sample React",
      installLead: "Installez le paquet, importez les styles et passez un theme. L\u2019aper\xE7u utilise la library locale.",
      footer: "Licence MIT \u00b7 TechSkillPlanet"
    },
    de: {
      htmlLang: "de",
      pageTitle: "Planet Components · TechSkillPlanet",
      brand: "TechSkillPlanet",
      navAria: "Seitennavigation",
      navPreview: "Beispiele",
      navPlatforms: "Stacks",
      navFeatures: "F\xE4higkeiten",
      navSkills: "AI Skills",
      navInstall: "Installieren",
      languageLabel: "Sprache",
      themeLabel: "Thema",
      dayMode: "Heller Modus",
      darkMode: "Dunkler Modus",
      github: "GitHub",
      slogan: "TechSkillPlanet \xB7 Mit Technik Freude schaffen",
      lead: "Eine Basis-Komponentenbibliothek für acht Technologie-Stacks mit 57 vertraglich abgestimmten Controls. Semantische Tokens und eine gemeinsame API helfen, dieselben UI-Primitive wiederzuverwenden. Rechts sehen Sie ein interaktives React-Sample.",
      statsAria: "GitHub-Daten",
      star: "Star",
      fork: "Fork",
      forkHint: "Repository auf GitHub forken",
      starHint: "Repository auf GitHub mit Star markieren",
      forkAction: "Forken",
      starAction: "Star geben",
      platformsStat: "Stacks",
      goGithub: "Auf GitHub ansehen",
      openRepo: "Repository \xF6ffnen",
      phonePreview: "React-Sample in der Telefonvorschau",
      previewCaption: "Live-React-Sample, vollst\xE4ndig interaktiv",
      platformsEyebrow: "Abdeckung",
      platformsTitle: "Bereits umgesetzte Stacks",
      platformsLead: "Jeder der acht Stacks liefert eine veröffentlichbare Library sowie Samples zur Integrationsprüfung.",
      platforms: HOME_PLATFORMS,
      featuresEyebrow: "F\xE4higkeiten",
      featuresTitle: "F\xFCr die \xDCbernahme ausgelegt",
      featuresLead: "Themen, APIs und Samples sind als ver\xF6ffentlichbare Bibliotheken organisiert \u2013 f\xFCr echte Produktarbeit.",
      features: [
        { title: "Semantische Tokens", body: "Farbe und Stil werden \xFCber einen gemeinsamen Token-Satz beschrieben. Stacks nutzen dieselben semantischen Namen statt fest hinterlegter Werte." },
        { title: "Bereitstellung je Stack", body: "Jeder Technologie-Stack hat eine eigene library und samples. Samples h\xE4ngen von der lokalen Bibliothek ab, um einen echten Integrationspfad zu pr\xFCfen." },
        { title: "Sky-Planet-Bildsprache", body: "Himmelblau, Wolkenfl\xE4chen und Insel-Controls bilden ein konsistentes visuelles System mit heller und dunkler Erscheinung." },
        { title: "Abgestimmter Komponentenvertrag", body: "Schnittstellen wie variant, disabled, selected sowie text / title / message bleiben \xFCber Stacks hinweg konsistent." }
      ],
      skillsEyebrow: "Agents",
      skillsTitle: "AI-Skill-Plugin",
      skillsLead: "Skills f\xFCr Cursor, Claude Code und Codex. Nach der Installation installiert der Agent das passende Stack-Paket, \xFCbergibt theme und bleibt beim Komponentenvertrag, statt eine eigene UI zu erfinden.",
      skills: [
        { name: "use-planet-components", when: "Screens, Formulare, Navigation oder Theming", body: "Ermittelt den Host-Stack, installiert das passende Paket und verwendet nur Vertrags-APIs." },
        { name: "build-planet-components", when: "Dieses Repository erweitern", body: "Setzt jeden Stack als library plus samples um, mit Tokens und einer Komponente pro Datei." },
        { name: "build-android-view-ui", when: "Android-View-Implementierung", body: "Bleibt bei Java und XML. F\xFChrt Compose oder Kotlin nur auf ausdr\xFCckliche Anfrage ein." },
        { name: "integrate-react-components", when: "Nur React Web", body: "Installiert das Paket, importiert Styles und injiziert starPlanetThemes." }
      ],
      skillInstallTitle: "Installation und Nutzung",
      skillInstallLead: "F\xFChren Sie das Installationsskript im Repository-Root aus, um Skills mit lokalem Cursor, Claude oder Codex zu verkn\xFCpfen. Mit --project binden Sie nur den Verbrauch-Skill an eine Anwendung.",
      skillCursorHint: "Cursor: Settings \u2192 Plugins, f\xFCgen Sie diesen Repository-Root hinzu. Starten Sie einen neuen Agent-Chat und erw\xE4hnen Sie TechSkillPlanet oder ein Tsp-Control.",
      skillDocs: "Vollst\xE4ndige Anleitung lesen",
      installEyebrow: "React Web",
      installTitle: "Mit dem React-Sample starten",
      installLead: "Paket installieren, Styles importieren und ein theme \xFCbergeben. Die Vorschau nutzt die lokale library.",
      footer: "MIT-Lizenz \u00b7 TechSkillPlanet"
    }
  };
  function detectHomeLocale(language = "") {
    const lower = String(language || "").toLowerCase().replace("_", "-");
    if (lower.startsWith("zh-tw") || lower.startsWith("zh-hk") || lower.startsWith("zh-mo") || lower.includes("hant")) {
      return "zh-TW";
    }
    if (lower.startsWith("zh")) return "zh-CN";
    if (lower.startsWith("ja")) return "ja";
    if (lower.startsWith("ko")) return "ko";
    if (lower.startsWith("es")) return "es";
    if (lower.startsWith("fr")) return "fr";
    if (lower.startsWith("de")) return "de";
    if (lower.startsWith("en")) return "en";
    return "zh-CN";
  }
  function readPreference(key, fallback) {
    try {
      return globalThis.localStorage?.getItem(key) || fallback;
    } catch {
      return fallback;
    }
  }
  function writePreference(key, value) {
    try {
      globalThis.localStorage?.setItem(key, value);
    } catch {
    }
  }
  function homeCopy(locale) {
    return HOME_LOCALES[locale] || HOME_LOCALES["zh-CN"];
  }
  function normalizeAppearance(key) {
    return key === "night" ? "night" : "sky";
  }

  // HomePage.js
  function GithubMark({ size = 18 }) {
    return h(
      "svg",
      {
        className: "tsp-home__github-icon",
        viewBox: "0 0 16 16",
        width: size,
        height: size,
        "aria-hidden": "true"
      },
      h("path", {
        fill: "currentColor",
        d: "M8 0C3.58 0 0 3.58 0 8c0 3.54 2.29 6.53 5.47 7.59.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82A7.68 7.68 0 0 1 8 4.77c.68.003 1.36.092 2 .27 1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8.013 8.013 0 0 0 16 8c0-4.42-3.58-8-8-8z"
      })
    );
  }
  function StarMark({ size = 16 }) {
    return h(
      "svg",
      {
        className: "tsp-home__action-icon",
        viewBox: "0 0 16 16",
        width: size,
        height: size,
        "aria-hidden": "true"
      },
      h("path", {
        fill: "currentColor",
        d: "M8 .25a.75.75 0 0 1 .673.418l1.882 3.815 4.21.612a.75.75 0 0 1 .416 1.279l-3.046 2.97.719 4.192a.751.751 0 0 1-1.088.791L8 12.347l-3.766 1.98a.75.75 0 0 1-1.088-.79l.72-4.194L.818 6.374a.75.75 0 0 1 .416-1.28l4.21-.611L7.327.668A.75.75 0 0 1 8 .25Z"
      })
    );
  }
  function ForkMark({ size = 16 }) {
    return h(
      "svg",
      {
        className: "tsp-home__action-icon",
        viewBox: "0 0 16 16",
        width: size,
        height: size,
        "aria-hidden": "true"
      },
      h("path", {
        fill: "currentColor",
        d: "M5 5.372v.878c0 .192.12.0.2.75h4.5a.75.75 0 0 0 .75-.75v-.878a2.25 2.25 0 1 1 1.5 0v.878a2.25 2.25 0 0 1-2.25 2.25h-1.5v2.128a2.251 2.251 0 1 1-1.5 0V8.5h-1.5A2.25 2.25 0 0 1 3.5 6.25v-.878a2.25 2.25 0 1 1 1.5 0ZM5 3.25a.75.75 0 1 0-1.5 0 .75.75 0 0 0 1.5 0Zm6.75.75a.75.75 0 1 0 0-1.5.75.75 0 0 0 0 1.5Zm-3 8.75a.75.75 0 1 0-1.5 0 .75.75 0 0 0 1.5 0Z"
      })
    );
  }
  function StatLink({ href, label, value, title, action }) {
    return h(
      "a",
      {
        className: "tsp-home__stat" + (action ? " tsp-home__stat--action" : ""),
        href,
        target: "_blank",
        rel: "noopener noreferrer",
        title: title || label,
        "aria-label": title || label,
        "data-stat": String(label).toLowerCase()
      },
      h("span", { className: "tsp-home__stat-label" }, label),
      h("strong", { className: "tsp-home__stat-value" }, value),
      action && h("span", { className: "tsp-home__stat-action" }, action)
    );
  }
  function QuickLink({ href, label, title, icon }) {
    return h(
      "a",
      {
        className: "tsp-home__quick",
        href,
        target: "_blank",
        rel: "noopener noreferrer",
        title: title || label,
        "aria-label": title || label
      },
      icon,
      h("span", null, label)
    );
  }
  function PhoneFrame({ label, caption, children }) {
    return h(
      "div",
      { className: "tsp-phone-wrap" },
      h(
        "div",
        { className: "tsp-phone", "aria-label": label },
        h("div", { className: "tsp-phone__island", "aria-hidden": "true" }),
        h("div", { className: "tsp-phone__screen" }, children),
        h("div", { className: "tsp-phone__home", "aria-hidden": "true" })
      ),
      caption && h("p", { className: "tsp-phone-caption" }, caption)
    );
  }
  function CodeFrame({ command }) {
    return h(
      "div",
      { className: "tsp-home__code-shell" },
      h(
        "div",
        { className: "tsp-home__code-bar", "aria-hidden": "true" },
        h("span"),
        h("span"),
        h("span")
      ),
      h("pre", { className: "tsp-home__code" }, h("code", null, command))
    );
  }
  function initialLocale(explicit) {
    if (explicit) return explicit;
    const stored = readPreference(LOCALE_STORAGE_KEY);
    if (stored && LANGUAGE_OPTIONS.some((item) => item.key === stored)) return stored;
    const language = typeof navigator !== "undefined" ? navigator.language : "zh-CN";
    return detectHomeLocale(language);
  }
  function initialThemeKey(explicit) {
    if (explicit) return explicit;
    const stored = readPreference(THEME_STORAGE_KEY);
    return normalizeAppearance(stored);
  }
  function HomePage({
    loadGithubStats = fetchGithubStats,
    openUrl = openGithub,
    initialLocale: initialLocaleProp,
    initialThemeKey: initialThemeKeyProp
  } = {}) {
    const [stats, setStats] = useState({ stars: null, forks: null });
    const [locale, setLocaleState] = useState(() => initialLocale(initialLocaleProp));
    const [themeKey, setThemeKeyState] = useState(() => initialThemeKey(initialThemeKeyProp));
    const theme = starPlanetThemes[themeKey] || starPlanetThemes.sky;
    const copy = homeCopy(locale);
    const appearance = normalizeAppearance(themeKey);
    const setLocale = (next) => {
      setLocaleState(next);
      writePreference(LOCALE_STORAGE_KEY, next);
    };
    const setThemeKey = (next) => {
      setThemeKeyState(next);
      writePreference(THEME_STORAGE_KEY, next);
    };
    useEffect(() => {
      let cancelled = false;
      loadGithubStats().then((next) => {
        if (!cancelled) setStats(next);
      }).catch(() => {
        if (!cancelled) setStats({ stars: null, forks: null });
      });
      return () => {
        cancelled = true;
      };
    }, [loadGithubStats]);
    useEffect(() => {
      if (typeof document === "undefined") return undefined;
      document.documentElement.lang = copy.htmlLang;
      document.title = copy.pageTitle;
      return undefined;
    }, [copy.htmlLang, copy.pageTitle]);
    const openRepo = () => openUrl(GITHUB_URL);
    return h(
      "div",
      {
        className: "tsp-home",
        id: "top",
        "data-theme": themeKey,
        "data-locale": locale,
        style: themeVars(theme)
      },
      h(
        "header",
        { className: "tsp-nav" },
        h(
          "div",
          { className: "tsp-nav__inner" },
          h(
            "a",
            { className: "tsp-nav__brand", href: "#top" },
            h("span", { className: "tsp-nav__mark", "aria-hidden": "true" }),
            h("span", { className: "tsp-nav__brand-text" }, copy.brand)
          ),
          h(
            "nav",
            { className: "tsp-nav__links", "aria-label": copy.navAria },
            h("a", { href: "#preview" }, copy.navPreview),
            h("a", { href: "#platforms" }, copy.navPlatforms),
            h("a", { href: "#features" }, copy.navFeatures),
            h("a", { href: "#skills" }, copy.navSkills),
            h("a", { href: "#install" }, copy.navInstall)
          ),
          h(
            "div",
            { className: "tsp-nav__controls" },
            h(
              "label",
              { className: "tsp-nav__field" },
              h("span", { className: "tsp-nav__sr-only" }, copy.languageLabel),
              h("select", {
                className: "tsp-nav__select",
                value: locale,
                "aria-label": copy.languageLabel,
                onChange: (event) => setLocale(event.target.value)
              }, LANGUAGE_OPTIONS.map((item) => h("option", { key: item.key, value: item.key }, item.title)))
            ),
            h(
              "label",
              { className: "tsp-nav__field tsp-nav__field--theme", title: copy.themeLabel },
              h("span", { className: "tsp-nav__sr-only" }, copy.themeLabel),
              h("input", {
                type: "checkbox",
                className: "tsp-nav__switch",
                checked: appearance === "night",
                "aria-label": copy.themeLabel,
                onChange: (event) => setThemeKey(event.target.checked ? "night" : "sky")
              })
            ),
            h(
              "div",
              { className: "tsp-nav__github-cluster", role: "group", "aria-label": copy.statsAria },
              h("a", {
                className: "tsp-nav__quick",
                href: GITHUB_STAR_URL,
                target: "_blank",
                rel: "noopener noreferrer",
                title: copy.starHint,
                "aria-label": copy.starHint
              }, h(StarMark, { size: 14 }), h("span", null, copy.star)),
              h("a", {
                className: "tsp-nav__quick",
                href: GITHUB_FORK_URL,
                target: "_blank",
                rel: "noopener noreferrer",
                title: copy.forkHint,
                "aria-label": copy.forkHint
              }, h(ForkMark, { size: 14 }), h("span", null, copy.fork)),
              h("a", {
                className: "tsp-nav__github",
                href: GITHUB_URL,
                target: "_blank",
                rel: "noopener noreferrer"
              }, h(GithubMark, { size: 15 }), ` ${copy.github}`)
            )
          )
        )
      ),
      h(
        "main",
        { className: "tsp-home__main" },
        h(
          "section",
          { className: "tsp-home__hero" },
          h(
            "div",
            { className: "tsp-home__copy" },
            h(
              "p",
              { className: "tsp-home__meta" },
              h("span", null, "MIT"),
              h("span", { className: "tsp-home__meta-sep", "aria-hidden": "true" }, "\xB7"),
              h("span", null, `v${PACKAGE_VERSION}`)
            ),
            h("h1", { className: "tsp-home__title" }, "Planet Components"),
            h("p", { className: "tsp-home__slogan" }, copy.slogan),
            h("p", { className: "tsp-home__lead" }, copy.lead),
            h(
              "div",
              { className: "tsp-home__stats", "aria-label": copy.statsAria },
              h(StatLink, { href: GITHUB_STAR_URL, label: copy.star, value: formatCount(stats.stars), title: copy.starHint, action: copy.starAction }),
              h(StatLink, { href: GITHUB_FORK_URL, label: copy.fork, value: formatCount(stats.forks), title: copy.forkHint, action: copy.forkAction }),
              h(
                "div",
                { className: "tsp-home__stat tsp-home__stat--plain" },
                h("span", { className: "tsp-home__stat-label" }, copy.platformsStat),
                h("strong", { className: "tsp-home__stat-value" }, String(copy.platforms.length))
              )
            ),
            h(
              "div",
              { className: "tsp-home__actions" },
              h(TspButton, {
                text: copy.goGithub,
                variant: "primary",
                theme: { ...theme, buttonRaisedShadowEnabled: false },
                fullWidth: false,
                onTap: openRepo
              }),
              h(QuickLink, {
                href: GITHUB_STAR_URL,
                label: copy.starAction,
                title: copy.starHint,
                icon: h(StarMark, { size: 15 })
              }),
              h(QuickLink, {
                href: GITHUB_FORK_URL,
                label: copy.forkAction,
                title: copy.forkHint,
                icon: h(ForkMark, { size: 15 })
              }),
              h(
                "a",
                {
                  className: "tsp-home__ghost",
                  href: GITHUB_URL,
                  target: "_blank",
                  rel: "noopener noreferrer"
                },
                h(GithubMark),
                ` ${copy.openRepo}`
              )
            )
          ),
          h(
            "div",
            { className: "tsp-home__preview", id: "preview" },
            h(PhoneFrame, {
              label: copy.phonePreview,
              caption: copy.previewCaption
            }, h(BasicControlsSample, {
              forcePlatform: "mobile",
              themeKey,
              locale,
              onThemeKeyChange: setThemeKey,
              onLocaleChange: setLocale
            }))
          )
        ),
        h(
          "section",
          { className: "tsp-home__section", id: "platforms" },
          h(
            "div",
            { className: "tsp-home__panel" },
            h(
              "div",
              { className: "tsp-home__section-head" },
              h("p", { className: "tsp-home__eyebrow" }, copy.platformsEyebrow),
              h("h2", null, copy.platformsTitle),
              h("p", { className: "tsp-home__section-lead" }, copy.platformsLead)
            ),
            h(
              "ul",
              { className: "tsp-home__platforms" },
              copy.platforms.map((name) => h("li", { key: name, className: "tsp-home__stack" }, name))
            )
          )
        ),
        h(
          "section",
          { className: "tsp-home__section", id: "features" },
          h(
            "div",
            { className: "tsp-home__section-head" },
            h("p", { className: "tsp-home__eyebrow" }, copy.featuresEyebrow),
            h("h2", null, copy.featuresTitle),
            h("p", { className: "tsp-home__section-lead" }, copy.featuresLead)
          ),
          h(
            "div",
            { className: "tsp-home__feature-grid" },
            copy.features.map((item, index) => h(
              "article",
              { key: item.title, className: "tsp-home__feature" },
              h(
                TspCard,
                { theme },
                h("span", { className: "tsp-home__feature-index" }, String(index + 1).padStart(2, "0")),
                h("h3", { className: "tsp-home__feature-title" }, item.title),
                h("p", { className: "tsp-home__feature-body" }, item.body)
              )
            ))
          )
        ),
        h(
          "section",
          { className: "tsp-home__section", id: "skills" },
          h(
            "div",
            { className: "tsp-home__section-head" },
            h("p", { className: "tsp-home__eyebrow" }, copy.skillsEyebrow),
            h("h2", null, copy.skillsTitle),
            h("p", { className: "tsp-home__section-lead" }, copy.skillsLead)
          ),
          h(
            "div",
            { className: "tsp-home__feature-grid" },
            copy.skills.map((item) => h(
              "article",
              { key: item.name, className: "tsp-home__feature" },
              h(
                TspCard,
                { theme },
                h("p", { className: "tsp-home__skill-when" }, item.when),
                h("h3", { className: "tsp-home__skill-name" }, item.name),
                h("p", { className: "tsp-home__feature-body" }, item.body)
              )
            ))
          ),
          h(
            "div",
            { className: "tsp-home__skill-install" },
            h(
              "div",
              null,
              h("h3", { className: "tsp-home__skill-install-title" }, copy.skillInstallTitle),
              h("p", { className: "tsp-home__section-lead" }, copy.skillInstallLead),
              h("p", { className: "tsp-home__skill-hint" }, copy.skillCursorHint),
              h("a", {
                className: "tsp-home__skill-docs",
                href: `${GITHUB_URL}/blob/main/docs/AI_PLUGIN.md`,
                target: "_blank",
                rel: "noopener noreferrer"
              }, copy.skillDocs)
            ),
            h(
              "div",
              { className: "tsp-home__skill-commands" },
              h(CodeFrame, { command: SKILL_INSTALL_COMMAND }),
              h(CodeFrame, { command: SKILL_PROJECT_COMMAND })
            )
          )
        ),
        h(
          "section",
          { className: "tsp-home__section tsp-home__section--install", id: "install" },
          h(
            "div",
            { className: "tsp-home__install" },
            h(
              "div",
              { className: "tsp-home__install-copy" },
              h("p", { className: "tsp-home__eyebrow" }, copy.installEyebrow),
              h("h2", null, copy.installTitle),
              h("p", { className: "tsp-home__section-lead" }, copy.installLead)
            ),
            h(CodeFrame, { command: INSTALL_COMMAND })
          )
        )
      ),
      h(
        "footer",
        { className: "tsp-home__footer" },
        h(
          "div",
          { className: "tsp-home__footer-inner" },
          h("span", null, copy.footer),
          h(
            "a",
            { href: GITHUB_URL, target: "_blank", rel: "noopener noreferrer" },
            h(GithubMark, { size: 16 }),
            ` ${copy.github}`
          )
        )
      )
    );
  }

export { HomePage, LANGUAGE_OPTIONS, HOME_LOCALES, HOME_PLATFORMS, PACKAGE_VERSION };
