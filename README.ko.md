# Planet Components

<p align="center">
  <strong>TechSkillPlanet · 技趣星球</strong><br/>
  <em>기술로 즐거움을 만든다 · Create Joy with Technology</em>
</p>

<p align="center">
  공유 디자인 시스템 <strong>Sky Planet</strong> 기반의 크로스플랫폼 UI 컴포넌트 라이브러리입니다.<br/>
  하늘색 프라이머리, 구름형 서피스, 아일랜드 스타일 컨트롤.
</p>

<p align="center">
  <a href="README.md">English</a> ·
  <a href="README.zh-CN.md">简体中文</a> ·
  <a href="README.ja.md">日本語</a> ·
  <a href="README.ko.md">한국어</a>
</p>

<p align="center">
  <img alt="version" src="https://img.shields.io/badge/version-0.2.1-31A8FF?style=flat-square" />
  <img alt="license" src="https://img.shields.io/badge/license-MIT-2BB8E6?style=flat-square" />
  <img alt="platforms" src="https://img.shields.io/badge/platforms-8_stacks-1479D6?style=flat-square" />
</p>

---

## 특징

- **하나의 비주얼 언어**: `design/tokens/` 시맨틱 토큰을 모든 스택에서 공유
- **8개 플랫폼**: 각 스택에 배포 가능한 `library` + 실행 가능한 `samples`
- **일관된 API**: `variant` / `disabled` / `selected` / `text` 등
- **공개 버전 0.2.0**: npm · Maven Central · pub.dev · SPM · CocoaPods

---

## 지원 플랫폼

| 스택 | 패키지 | 레지스트리 |
| --- | --- | --- |
| Android View | `io.github.techskillplanet:planet-components-android` | Maven Central |
| iOS SwiftUI | `PlanetComponents` | SPM / CocoaPods |
| React Web | `@techskillplanet/planet-components-react` | npm |
| Vue Web | `@techskillplanet/planet-components-vue` | npm |
| React Native | `@techskillplanet/planet-components-react-native` | npm |
| Flutter | `tech_skill_planet_components` | pub.dev |
| 위챗 미니프로그램 | `@techskillplanet/planet-components-miniprogram` | npm |
| Kuikly | `io.github.techskillplanet:planet-components-kuikly` | Maven Central |

설치 세부 단계는 [English](README.md) / [简体中文](README.zh-CN.md)를 참고하세요.

### 빠른 시작 예시

```bash
npm install @techskillplanet/planet-components-react
```

```gradle
implementation "io.github.techskillplanet:planet-components-android:0.2.1"
```

```yaml
# pubspec.yaml
dependencies:
  tech_skill_planet_components: ^0.2.1
```

---

## 문서

| 문서 | 설명 |
| --- | --- |
| [README.md](README.md) | 영어 홈 |
| [README.zh-CN.md](README.zh-CN.md) | 중국어 · 전체 설치 가이드 |
| [PUBLISHING.md](PUBLISHING.md) | 배포 절차 |
| [docs/COMPONENT_CONTRACT.md](docs/COMPONENT_CONTRACT.md) | 컴포넌트 계약 |

---

## 라이선스

[MIT](LICENSE) © TechSkillPlanet
