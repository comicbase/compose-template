# Enterprise Compose Template

这是一个面向企业 Android 项目的 Jetpack Compose 多模块模板。它不是只展示目录的空壳：应用可直接运行，包含列表加载、搜索、刷新、文章详情和个人中心，并展示从 UI Event 到 Repository 的完整单向数据流。

## 快速开始

环境要求：JDK 17、Android SDK 36、Android Studio 或 Gradle Wrapper。

```bash
./gradlew test
./gradlew :app:assembleDebug
```

用 Android Studio 打开仓库根目录后运行 `app` 即可。示例使用内存数据库和假网络源，不需要服务端；生产接入点分别是 `core/network` 和 `core/database`。

## 模块职责

| 模块 | 职责 | 可依赖方向 |
| --- | --- | --- |
| `app` | 应用组合根、依赖注入、顶层导航 | core、feature |
| `feature:*` | 独立业务闭环：Screen、State、Event、ViewModel、Navigator | domain、model、designsystem |
| `core:designsystem` | 主题、设计令牌、通用组件 | Compose |
| `core:domain` | 可复用业务用例、Repository 契约 | model |
| `core:data` | Repository 实现、数据协调 | domain、network、database、model |
| `core:network` | 远端数据源边界 | model |
| `core:database` | 本地数据源边界 | model |
| `core:model` | 纯业务模型 | 无业务模块依赖 |
| `core:common` | 调度器等横切能力 | 基础库 |

依赖只向下流动，Feature 之间不互相依赖。`app` 是唯一知道具体实现的组合根。

## 扩展新 Feature

1. 在 `feature/<name>` 创建独立模块。
2. 定义唯一不可变 `UiState`、密封 `Event`，有一次性动作时再定义 `Effect`。
3. 用 `Route` 收集状态和 Effect，用纯 `Screen`/`Section` 展示状态。
4. 业务规则与 Repository 契约放入 `core:domain`，具体数据实现放入 `core:data`。
5. Feature 声明 Navigator 意图，由 `app` 实现并加入顶层目的地。
6. 为 State、UseCase、ViewModel 添加单元测试，为关键 Screen 添加 UI 测试。

更详细的设计依据见 [架构原则落实说明](docs/架构原则落实说明.md)，团队开发约定见 [开发指南](docs/开发指南.md)。
