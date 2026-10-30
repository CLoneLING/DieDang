# 跌宕（DieDang）

一款本地运行的互动小说 / 剧本播放器。剧本以 Kotlin DSL 编写，随 App 一起打包，不需要网络、不需要账号，打开就能读。

当前内置两部剧本：

| 剧本 | 题材 | 规模 | 结局 |
| --- | --- | --- | --- |
| 《雾港旧案》 | 悬疑 / 推理 / 犯罪 | 四部 80 章，242 场景，约 23 万字 | 4 |
| 《梧桐树下的回声》 | 生活 / 推理 / 伦理 | 166 场景，约 7.5 万字 | 9 |

## 特性

- **逐段打字机 + 点击推进**：长段落按 `\n\n` 切分，逐段显示，点一下跳过打字、再点进入下一段。
- **判断型选项**：每章提供互斥的判断分支，选择写入线索标记（flag），结局由标记组合判定。
- **分支不重复**：同一个场景的两个分支各自是新文本，结构校验脚本会拦住重复与死路。
- **自动存档**：退出、切后台、换剧本都保留进度，主菜单一键「继续」。
- **结局收集**：剧本列表显示已解锁结局数。
- **纯本地**：无网络权限，存档在 SharedPreferences 中。

## 构建

环境：JDK 21，Android SDK（compileSdk 36.1）。

```powershell
$env:JAVA_HOME   = "D:\Android\jdk-21.0.8"
$env:ANDROID_HOME = "D:\Android\android-sdk\AndroidStudioSDK"
.\gradlew.bat :app:assembleDebug
```

产物：`app/build/outputs/apk/debug/app-debug.apk`（minSdk 24 / targetSdk 36）。

> 说明：仓库内暂未提供正式发布签名。当前 Release 附带的 APK 为 debug 签名，可直接安装，但不适合上架应用商店；如需正式签名，请自行生成 keystore 并在 `app/build.gradle.kts` 中配置 `signingConfigs`（keystore 与密码不要入库）。

## 目录结构

```
app/src/main/java/com/yumoo/diedang/
├── model/StoryModel.kt        GameState / Scene / Option
├── engine/Story.kt            Story 接口、StoryAdapter、结构校验
├── engine/StoryRegistry.kt    剧本注册表（新增剧本只改这里）
├── engine/StorySession.kt     运行时状态机：推段落、跳场景、倒计时
├── save/SaveRepository.kt     存档与结局收集（SharedPreferences + JSON）
├── story/wugang/              《雾港旧案》：Dsl.kt + Story.kt + Part1A..Part4D.kt
├── story/wutong/              《梧桐树下的回声》：StoryScript.kt
└── ui/                        Compose 界面：Menu / StoryList / Game / About
```

`docs/` 下是《雾港旧案》的设定集（`wugang_bible.md`）、写作规范（`wugang_spec.md`）与写作说明（`wugang_brief.md`）。

`tools/` 下是两个校验脚本，写剧本时用得上：

```powershell
python tools/validate_story.py   # 结构：重复 id / 悬空跳转 / 回头路 / 死路 / 章节齐全
python tools/check_quality.py    # 文风：禁语、选项过长、分支重复度
```

## 加一部新剧本

1. 在 `story/` 下新建包，用一个 `buildScenes(): List<Scene>` 返回场景列表（可用 `story/wugang/Dsl.kt` 的 `sc` / `op` / `opFlag` 写法）。
2. 提供 `getStoryInfo(): StoryInfo`。
3. 在 `engine/StoryRegistry.kt` 的 `builtins` 里加一条 `BuiltinStory`。
4. 不用动 UI，也不用动存档层。

## 版本

- 2.0.0 —— 引擎与 UI 重写，新增《雾港旧案》（80 章 / 242 场景 / 4 结局）。
