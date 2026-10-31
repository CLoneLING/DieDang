# 剧本线上分发可行性（无自有服务器）

> 结论先说：**能做，而且不需要服务器**。把剧本从「编译进 APK 的 Kotlin 代码」改成「可下载的 JSON 剧本包 + 一个索引文件」，再用 GitHub Releases / jsDelivr / 对象存储任意一种做托管即可。真正的工作量在引擎侧的 JSON 剧本格式与校验，不在托管。

## 一、现状

- 剧本目前是 `app/src/main/java/com/yumoo/diedang/story/<剧本>/` 下的 Kotlin DSL（`sc()` / `op()` / `opFlag()`…），编译期打进 APK。
- 加一部新剧本 = 写 Kotlin + 重新编译 + 重新发版 + 用户重新安装。
- `StoryRegistry` 已经是「内置剧本列表」，`Story` / `StoryAdapter` 已经能吃 `List<Scene>`，**只要能把外部 JSON 变成 `List<Scene>`，其余引擎（StorySession、存档、UI）一行都不用改**。

## 二、可选的托管方案

| 方案 | 成本 | 国内可达性 | 适用 | 注意 |
| --- | --- | --- | --- | --- |
| **GitHub Releases**（`releases/latest` + 资产） | 免费 | 一般，本机实测资产下载出现过 `Connection reset` | 与「应用内更新」共用一套代码，最省事 | 资产下载走 `objects.githubusercontent.com`，部分网络会被重置 |
| **jsDelivr CDN**（`cdn.jsdelivr.net/gh/用户/仓库@版本/路径`） | 免费 | 较好 | 单文件剧本包（<20 MB） | 有缓存，用 `@tag` 或 `@commit` 固定版本可绕过；仓库需公开 |
| **Gitee 仓库 raw / Pages** | 免费 | 好 | 面向国内用户的正式分发 | 需实名；Pages 需审核；raw 有防盗链限制 |
| **对象存储**（Cloudflare R2 / 阿里云 OSS / 腾讯 COS / 七牛） | 近乎免费（R2 每月 10 GB 出网免费） | 好 | 长期、多剧本、要统计下载量 | 需要账号与公共读配置；建议自定义域名 + HTTPS |
| **GitHub Gist** | 免费 | 一般 | 1–2 个剧本、临时试水 | 单个 Gist 文件数有限，不适合做商店 |

**推荐组合**：仓库里放剧本包（`stories/<id>/<version>.json`）+ 一个 `index.json` 索引，同时用 jsDelivr 提供加速域名，App 端先试 jsDelivr、失败回退 GitHub raw。这样零成本、无需服务器，国内体验也可接受。

## 三、需要改的引擎部分

1. **JSON 剧本格式**（新）
   ```json
   {
     "id": "wugang",
     "version": "1.1",
     "info": { "title": "雾港旧案", "author": "YuMoo", "genre": "悬疑/推理/犯罪", "description": "…" },
     "start": "p1c01",
     "scenes": [
       {
         "id": "p1c01",
         "text": "凌晨三点四十……",
         "options": [
           { "text": "先看锁孔", "next": "p1c01a", "flags": ["C_LOCK"] },
           { "text": "先翻口袋", "next": "p1c01b", "cond": { "has": ["C_LOCK"] } }
         ],
         "isEnding": false,
         "timeLimit": 0,
         "endingTitle": null
       }
     ]
   }
   ```
   - `flags`：选中后写入的线索标记（对应现在的 `opFlag`）
   - `cond`：`{ "has": [...], "notHas": [...], "counterAtLeast": {"key": 2} }`，覆盖现有 `opIf` / `opUnless` / `opCount`
   - 解析器用现成的 `org.json`，落到 `StoryAdapter`。约 200–300 行。
2. **剧本索引 `index.json`**
   ```json
   { "schema": 1, "stories": [ { "id": "wugang", "title": "雾港旧案", "version": "1.1",
     "url": "https://cdn.jsdelivr.net/gh/CLoneLING/DieDang@stories/stories/wugang/1.1.json",
     "sha256": "…", "size": 812345 } ] }
   ```
3. **下载 + 校验 + 本地库**：下到 `filesDir/stories/`，校验 sha256，解析失败即丢弃；`StoryRegistry` 合并「内置 + 本地已下载」。
4. **导入入口**：设置页加「剧本库 / 检查剧本更新」，支持从 URL 或本地文件（SAF 选 `.json`）导入——**这一步不依赖任何托管，先做就能立刻用**。
5. **安全**：剧本只有文本，无代码执行；限制单剧本 ≤ 5 MB、场景数 ≤ 2000，防止恶意超大文件。

## 四、建议的落地顺序（工作量估算）

| 步骤 | 内容 | 估时 |
| --- | --- | --- |
| 1 | JSON 剧本格式 + 解析器 + 把《雾港旧案》导出一份 JSON 做回归 | 1 天 |
| 2 | 本地导入（文件选择器）+ 剧本库列表（删除/更新） | 0.5 天 |
| 3 | 远程 `index.json` + 下载 + sha256 校验 + 缓存 | 1 天 |
| 4 | 「剧本商店」页（列表 / 下载进度 / 更新提示） | 1 天 |
| 5 | 发布流水：把 Kotlin 剧本导出 JSON 的脚本（可直接复用 `tools/validate_story.py` 的解析逻辑） | 0.5 天 |

## 五、风险与对策

- **网络**：本次实测 GitHub Release 资产下载在模拟器网络上被 `Connection reset`；剧本包只有几百 KB–2 MB，比 14 MB 的 APK 稳，但仍建议 jsDelivr / Gitee 双源。
- **版本兼容**：`index.json` 带 `schema` 字段，引擎不认识的 schema 直接跳过，避免老版本 App 读到新格式崩溃。
- **版权与审核**：剧本是原创内容，走公开仓库即可；若将来做用户上传，需要审核队列（可先用 GitHub Issue 模板收集投稿，人工合并）。
