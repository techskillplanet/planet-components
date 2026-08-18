# 全仓库一致性走查报告

Status: evidence snapshot 2026-08-18（整改后）  
Scope: 全栈 `library` + 包标识 + 契约覆盖 + 结构规范  
SoT: `docs/COMPONENT_CONTRACT.md` + RN `starPlanet`（冲突裁决）+ `AGENTS.md`

## 0. 本轮已落地（相对 2026-08-17 快照）

| 波次 | 结果 |
| --- | --- |
| A | Web / RN / 小程序 samples 改为本地 library |
| B | Flutter 拆为 `lib/src/*.dart` |
| C | iOS SPM 产品/目录/import 已改为 `PlanetComponents` |
| D | 小程序补齐 27 项；`bc-*` 映射写入契约 |
| E | Kuikly Gradle 身份改为 `PlanetComponentsKuikly*`；控件拆为 `Tsp*.kt`（契约未齐） |
| F | 全栈 library 版本对齐 **0.2.0**；Android namespace `com.techskillplanet.planetcomponents`；Android sample 依赖 `project(':library')` |

## 1. 总判

| 维度 | 结论 |
| --- | --- |
| 契约组件完备（RN/Web/Flutter/iOS/Android/小程序） | **核心 27 项均已覆盖** |
| 包名 / 版本 / 命名空间 | library 统一 **0.2.0**；Android namespace `planetcomponents`；iOS SPM `PlanetComponents`；Flutter pub 名保持下划线惯例 |
| 一组件一文件 | Flutter / iOS / Kuikly 控件已拆；Android 额外控件仍多于契约 |
| Sample 依赖本地 library | **Android / Web / RN / Flutter / 小程序均合规** |
| Kuikly | 包身份已对齐；控件 `Tsp*` 已拆文件；phonics 业务页仍在 library shared 中；契约未齐 |

`node tools/check-structure.cjs` 现校验：骨架、samples 本地依赖、Flutter 拆文件、小程序组件数量、iOS 元数据、Kuikly README。

---

## 2. 包标识对照（现状）

| 栈 | 包管理器 | 公开坐标 / 包名 | 版本 | 与 `planet-components` 对齐度 |
| --- | --- | --- | --- | --- |
| Android | Maven | `io.github.techskillplanet:planet-components-android` | **0.2.0** | 坐标 OK；namespace `com.techskillplanet.planetcomponents` |
| React Native | npm | `@techskillplanet/planet-components-react-native` | **0.2.0** | OK |
| React Web | npm | `@techskillplanet/planet-components-react` | **0.2.0** | OK |
| Vue Web | npm | `@techskillplanet/planet-components-vue` | **0.2.0** | OK |
| Flutter | pub.dev | `tech_skill_planet_components` | **0.2.0** | pub 下划线惯例；与 npm `planet-components-*` 语义对齐 |
| iOS SPM | SPM | `PlanetComponents` | 0.2.0（README / git tag） | OK |
| Mini Program | npm | `@techskillplanet/planet-components-miniprogram` | **0.2.0** | 包名 OK；标签 `bc-*` |
| Kuikly | Maven/internal | `io.github.techskillplanet` / `PlanetComponentsKuiklyShared` | 0.2.0 | 控件 `Tsp*`；业务页仍为 phonics |

### 包名建议目标态（待确认后改）

统一语义：**TechSkillPlanet Planet Components**

| 栈 | 建议目标名 |
| --- | --- |
| npm 系 | 保持 `@techskillplanet/planet-components-{react\|vue\|react-native\|miniprogram}` |
| Maven Android | 保持 `planet-components-android`；namespace 长期迁到 `com.techskillplanet.planetcomponents`（破坏性，需升大版本） |
| Flutter | 可选保留 `tech_skill_planet_components`（pub 惯例下划线）或增加文档别名说明 |
| iOS | `TechSkillPlanetPlanetComponents` 或 `PlanetComponents`（弃用 BasicControls） |
| Kuikly | 新建 `com.techskillplanet:planet-components-kuikly`，与 phonics 解耦 |

---

## 3. 公开 API / 组件完备性

相对 `COMPONENT_CONTRACT` 27 项：

| 栈 | 契约覆盖 | 公开前缀 | 备注 |
| --- | --- | --- | --- |
| RN | 完整 | `Tsp*` | SoT |
| React Web | 完整 | `Tsp*` | |
| Vue Web | 完整 | `Tsp*` | |
| Flutter | 完整 | `Tsp*` | 另有 `TspModalButton`、`TspTabItem` |
| iOS | 完整 | `Tsp*` | 另有 `TspTabItem` |
| Android | 完整（语义） | **`Basic*`** | 契约允许语义等价；另有大量非契约控件 |
| Mini Program | **27/27** | **`bc-*`** | 标签名映射见契约「Platform public names」 |
| Kuikly | **未按契约实现** | Phonics* | 已在 `kuikly/README.md` 标明非本契约栈 |

---

## 4. 规范与实现不一致清单（按优先级）

### P0 — 与仓库规则 / 契约直接冲突

1. **Sample 必须依赖本地 library** — **已整改**（Web/RN/小程序 `file:../library`；小程序 samples 用 symlink）
2. **一组件一文件** — Flutter **已拆**到 `lib/src/`
3. **小程序契约覆盖** — **已补齐 27 项**；Kuikly 按 E 文档降级，不在本轮改造成组件库

### P1 — 命名与包元数据不一致（影响认知与发布）

4. **版本号** — library 已对齐 **0.2.0**（尚未重新发版）
5. **iOS 产品名** `PlanetComponents` — **已改**
6. **Android namespace** `com.techskillplanet.planetcomponents` — **已改**；公开类名仍为契约允许的 `Basic*`
7. **包元数据**：iOS README/LICENSE/CHANGELOG 已补
8. **Button variant**：Flutter `standard` ≡ 契约 `default`（Dart 保留字）

### P2 — 实现深度 / 视觉对齐（非缺组件）

9. Web RefreshLayout 用「刷新按钮」近似 RN 下拉（能力差，需文档标注）
10. iOS 仍需 macOS `swift build` 与真机视觉对照 RN
11. Android 额外控件超出契约——可保留，标明「扩展集」
12. 小程序新增组件为契约语义实现，视觉 1:1 仍需对照 RN `starPlanet`

### P3 — 文档与工具

13. `tools/check-structure.cjs` **已加强**本地依赖 / Flutter 拆文件 / 小程序数量 / iOS 元数据
14. `PUBLISHING.md` Kuikly 行已改为「尚未发布」
15. Mini sample 现为独立 package name + 本地 library 链接

---

## 5. 推荐统一规范（目标态）

1. **公开组件语义名**：契约表中的英文名；JS/Flutter/iOS = `Tsp{Name}`；Android = `Basic*`（已确认可保留）并维护映射表
2. **CSS/小程序类名**：Web 与小程序使用 `bc-*`；小程序标签映射见契约「Platform public names」
3. **事件名**：文档层统一语义 `onChange` / `onTap`；平台别名：`onChanged`（Flutter）、`change`/`tap`（Vue）必须写进契约
4. **主题**：统一 `StarPlanetTheme` / `starPlanetTheme` + 共享 token 键
5. **Samples**：一律 `file:../library` 或等价 path；禁止默认依赖已发布旧版
6. **版本策略**：同一次「契约完备发版」尽量对齐 minor（例如全到 `0.2.0`），Android 已 0.2.0 可作锚

## 6. Evidence 命令

```bash
node tools/check-structure.cjs
cd flutter/library && flutter test
```
