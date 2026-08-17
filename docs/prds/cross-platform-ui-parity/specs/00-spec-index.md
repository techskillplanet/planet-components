# Spec Index — Cross-platform UI parity

Status: draft (implementation in progress under RN polish as SoT)

## Scope
Align **React Native / Flutter / Android View** visual + interaction for shared basic controls.

## Source of truth
RN `starPlanet` (`shared.js` + components) + `design/tokens/*`.

## Confirmed decisions
- SoT = RN polish (inferred from ongoing Flutter verification + user request for full consistency).
- Toast: solid fills; auto-dismiss ~1600ms; info = brandDark.

## Open residual
- Android 公开类名保持 `BasicSwitchView`（View 栈命名空间）；语义与 `TspSwitch` 对齐，见 `05-switch-contract.md`。
- Switch 五端 island 已落地（Vue/React/Android/RN/Flutter）。

## Docs
- Plan: `../plans/01-parity-plan.md`
- Switch contract: `05-switch-contract.md`
