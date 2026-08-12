# 验收标准

Status: confirmed  
Prerequisites: `01-background-and-goal.md`

| ID | 可观察结果 | 验证 |
| --- | --- | --- |
| AC-01 | `react-native/samples` 使用 Expo 57 族与 `react-native` 0.86.x | `package.json` / 安装后 `node_modules` 版本 |
| AC-02 | Library 导出 `TspRefreshLayout`、`TspLoadingDialog`，API 对齐 `COMPONENT_CONTRACT.md` | 源码导出 + Sample 可交互演示 |
| AC-03 | `node tools/check-structure.cjs` 与 `react-native/samples` 结构检查通过 | 命令 exit 0 |
| AC-04 | `npm run android`（或等价）在已连接模拟器上启动成功 | adb/log 或进程可见 |

## 护栏

- 不新增未确认的跨端组件
- Sample 继续 `file:../library` 依赖本地库
- 不破坏既有 25 个 `Tsp*` 公共导出名
