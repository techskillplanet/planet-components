# 音标星球 Kuikly 微信小程序宿主

这是 Kuikly 迁移版的小程序宿主工程，和现有 `miniprogram/` 原生小程序并行存在。

## 构建链路

```bash
cd /Volumes/outmount/code/planet-components/kuikly
./gradlew :shared:packLocalJsBundleDebug -Pkuikly.useLocalKsp=false
./gradlew :miniApp:jsMiniAppDevelopmentWebpack
```

当前小程序壳使用 Maven Central 上的 KuiklyUI 发布依赖，不再要求本机额外 checkout KuiklyUI 源码。

构建完成后，用微信开发者工具打开：

```text
/Volumes/outmount/code/planet-components/kuikly/samples/miniApp/dist
```

## 页面映射

- `pages/PhonicsMap/index` -> `pageName: "PhonicsMap"`
- `pages/PhonicsLearn/index` -> `pageName: "PhonicsLearn"`
- `pages/PhonicsCheck/index` -> `pageName: "PhonicsCheck"`

## 当前状态

- 小程序壳、WXML 模板和资源目录已接入。
- 音频资源已复制到 `dist/assets/audio`，后续由 `:miniApp:copyAssets` 同步。
- `:miniApp:compileKotlinJs` 已验证通过。
- `:shared:compileKotlinJs` 和 `:shared:compileDebugKotlinAndroid` 已验证通过。
- 后续需要生成完整小程序包时，继续执行 `:shared:packLocalJsBundleDebug` 和 `:miniApp:jsMiniAppDevelopmentWebpack`。
