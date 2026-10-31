# 跌宕（DieDang）

互动小说播放器 · Android

- 包名：`com.yumoo.diedang`
- 版本：2.1.0（versionCode 21000）
- 系统：minSdk 24 / targetSdk 36

## 构建

环境：JDK 21，Android SDK（compileSdk 36.1）。

```powershell
$env:JAVA_HOME    = "D:\Android\jdk-21.0.8"
$env:ANDROID_HOME = "D:\Android\android-sdk\AndroidStudioSDK"
.\gradlew.bat :app:assembleDebug
```

产物：`app/build/outputs/apk/debug/app-debug.apk`。

Release 附件为 debug 签名，可直接安装，不可上架。正式签名请自行生成 keystore 并在 `app/build.gradle.kts` 配置 `signingConfigs`（keystore 与密码不要入库）。

## 目录

```
app/src/main/java/com/yumoo/diedang/
├── model/                     GameState / Scene / Option
├── engine/                    Story、StoryAdapter、StoryRegistry、StorySession
├── save/                      存档与结局收集
├── settings/                  主题 / 字体 / 字号、版本号
├── update/                    GitHub Release 检测、下载、安装
├── story/wugang/              DLC《雾港旧案》
├── story/wutong/              DLC《梧桐树下的回声》
└── ui/                        Menu / StoryList / Game / Settings / About
```

`tools/`：`validate_story.py`（结构校验）、`check_quality.py`（文风校验）。

## DLC

| 剧本 | 作者 | 题材 | 规模 | 结局 |
| --- | --- | --- | --- | --- |
| 《雾港旧案》 | YuMoo | 悬疑 / 推理 / 犯罪 | 四部 80 章，242 场景 | 4 |
| 《梧桐树下的回声》 | YuMoo | 生活 / 推理 / 伦理 | 166 场景 | 9 |

## 版本

- 2.1.0 —— 设置页（主题 / 字体 / 字号）、应用内更新、状态栏修复。
- 2.0.0 —— 引擎与 UI 重写。
