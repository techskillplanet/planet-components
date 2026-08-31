# PlanetComponents iOS 发布步骤

Status: **SPM 可通过 git tag `0.2.0` 发布；CocoaPods Trunk 尚未推送。**

约定：先完成下方「发布前验证」，再打 git tag / 推 CocoaPods Trunk。验证未通过前不要执行第 4 节之后的命令。

同一套源码同时发给：

- Swift Package Manager（GitHub + git tag）
- CocoaPods Trunk（`.podspec` 指向同一个 tag）

当前目标版本：**0.2.0**。本地目前没有 `0.2.0` tag，Trunk 上也还没有这个 Pod。

---

## 1. 发布模型

| 渠道 | 消费者拿到什么 | 要不要新建账户 |
| --- | --- | --- |
| SPM | GitHub 仓库根目录 `Package.swift` + tag `0.2.0` | 不用。有 `techskillplanet/planet-components` 的 push 权限即可 |
| CocoaPods | Trunk 上的 `PlanetComponents` pod | 要。一次性注册 CocoaPods Trunk（邮箱验证，无密码） |

| 文件 | 作用 |
| --- | --- |
| `ios-swiftui/library/` | 源码与本地 `Package.swift`（samples 用 `path: ../library`） |
| 仓库根目录 `Package.swift` | SPM 解析 GitHub URL 时读这个 |
| 仓库根目录 `PlanetComponents.podspec` | CocoaPods 读这个；`source_files` 相对 **仓库根** |
| `ios-swiftui/library/CHANGELOG.md` | 发版说明 |

SPM **没有** npm / Maven 那种中央仓库。打 tag 并 push 后，SPM 就算发布完成。CocoaPods 必须再执行一次 `pod trunk push`。

---

## 2. 发布前验证（门禁）

在 **macOS + Xcode** 上做。Linux 只能跑结构检查，不能作为发版证据。

### 2.1 结构与本地编译

在仓库根目录：

```bash
node tools/check-structure.cjs

cd ios-swiftui/library && swift build
cd ios-swiftui/samples && swift build

# 根目录 Package.swift（消费者 SPM 实际解析的入口）
swift build

# CocoaPods 用本地文件 lint，不要求 tag 已存在
pod lib lint PlanetComponents.podspec --allow-warnings
```

通过标准（与仓库 AC-06 对齐）：

- `check-structure.cjs` 含 `ios-swiftui` / `ios-publish-spm` / `ios-publish-cocoapods` 均为 PASS
- `ios-swiftui/library` 与 `ios-swiftui/samples` 均可 `swift build`
- samples 仍依赖本地 `path: ../library`，没有改成 GitHub / Trunk 已发布版本
- `pod lib lint` 无 ERROR（WARNING 若已评估可带 `--allow-warnings`）

### 2.2 产品验证（发版前建议做完）

- 在 Xcode 中跑 iOS sample / 演示页，确认契约组件可交互，且没有用系统 `Toggle` / `alert` 顶替
- 至少对照 RN sample 看 Button、Switch、Toast、Modal、Chip
- 确认 `import PlanetComponents` 与公开 `Tsp*` 类型可用

验证记录（日期、机器、命令输出、对照结论）留在发版说明或 PR 里。**未完成本节，不要打 tag。**

---

## 3. 一次性准备：CocoaPods Trunk 注册

只在第一次发 CocoaPods 时做。官网：

- 注册指南：https://guides.cocoapods.org/making/getting-setup-with-trunk.html
- CocoaPods 首页：https://cocoapods.org
- Trunk：https://trunk.cocoapods.org
- `pod trunk register`：https://guides.cocoapods.org/terminal/commands.html#pod_trunk_register
- 发布后的包页（push 成功才会有）：https://cocoapods.org/pods/PlanetComponents

本机：

```bash
# 建议用组织邮箱；该邮箱会成为 Pod owner
pod trunk register you@example.com 'TechSkillPlanet' --description='work mac'
```

去邮箱点验证链接，然后：

```bash
pod trunk me
```

Trunk 没有密码，只绑定「邮箱 + 这台电脑的 session」。换电脑要重新 `register` 一次（同一邮箱即可）。

SPM 不需要这一步。

---

## 4. 验证通过后的发布顺序

先合入 `main`，再 tag，再 Trunk。顺序不要反。

### 4.1 核对版本号一致

下列文件都应是本次要发的版本（例如 `0.2.0`）：

- `PlanetComponents.podspec` 的 `s.version`
- `ios-swiftui/library/README.md`
- `ios-swiftui/library/CHANGELOG.md`

Swift 的 `Package.swift` **没有** version 字段，版本只来自 git tag。

### 4.2 推代码

把含根目录 `Package.swift`、`PlanetComponents.podspec` 的 commit 合进 `main` 并 push。  
tag 必须指向这份 commit，否则 SPM / CocoaPods 都会解析失败。

### 4.3 打 tag（这一步之后 SPM 已对外可用）

```bash
git checkout main
git pull origin main
git log -1 --oneline
git tag -a 0.2.0 -m "PlanetComponents iOS 0.2.0"
git push origin 0.2.0
```

tag 名用 `0.2.0`，不要混用 `v0.2.0`。同一 tag 不要改写内容后重推。

可选：在 GitHub Releases 基于该 tag 建 Release，粘贴 CHANGELOG。对 SPM 不是必须。

### 4.4 推 CocoaPods Trunk（这一步之后 `pod 'PlanetComponents'` 才可用）

```bash
pod spec lint PlanetComponents.podspec --allow-warnings
pod trunk push PlanetComponents.podspec --allow-warnings
```

`pod spec lint` / `pod trunk push` 会按 podspec 里的 git tag 拉 GitHub 代码，所以必须 **先有 4.3 的远程 tag**。

成功后包页：https://cocoapods.org/pods/PlanetComponents  
Specs 同步可能有几分钟到几小时延迟。

---

## 5. 消费者怎么装

业务代码两边相同：

```swift
import PlanetComponents
```

### SPM

```swift
.package(url: "https://github.com/techskillplanet/planet-components", from: "0.2.0")
```

把 product `PlanetComponents` 加到 target。

### CocoaPods

```ruby
pod 'PlanetComponents', '~> 0.2.0'
```

本仓库 samples **继续**使用 `path: ../library`，不要改成已发布版本。

---

## 6. 以后升版

1. 改 `PlanetComponents.podspec` 的 `s.version`、README、CHANGELOG  
2. 再跑第 2 节验证  
3. 推 `main` → 打 **新** tag（如 `0.2.1`）→ `pod trunk push`  
4. 不要复用已经推过的版本号  

破坏性 API 变更按 SemVer 升 minor / major。

---

## 7. 不要做的事

- 验证未通过就打 tag 或 `pod trunk push`
- 把 samples 改成依赖 Trunk / GitHub 已发布包
- 改写已推送的 tag
- 只发 SPM 或只发 CocoaPods 却对外宣称双渠道都可用（可以分步，但文档/版本说明要写清）
- 把 Trunk session / 邮箱验证链接提交进 Git
