# 写作任务通用说明（所有 Part 文件共同遵守）

你在为 Android 互动小说 App「DieDang」写剧本《雾港旧案》的一部分。
输出到 `C:\Users\YuMoo_\OneDrive\Works\AndroidStudioProjects\DieDang\app\src\main\java\com\yumoo\diedang\story\wugang\<文件名>.kt`。

## 必读（按顺序读，别跳过）
1. `C:\Users\YuMoo_\OneDrive\Works\AndroidStudioProjects\DieDang\docs\wugang_bible.md`
   —— 人物、唯一真相、线索标记表、**全 80 章节拍表**（找到你负责的章，严格按节拍写）、四个结局、写作规范。
2. `C:\Users\YuMoo_\OneDrive\Works\AndroidStudioProjects\DieDang\docs\wugang_spec.md`
   —— Kotlin 语法与文风规范。
3. 参考样本：`...\story\wugang\Part2A.kt`（照它的句法、密度、排版写）。

## 文件格式（照抄结构）
```
package com.yumoo.diedang.story.wugang

fun partXXScenes(): List<Scene> = listOf(
    sc(
        "p3c01",
        """
        正文……

        正文第二段。
        """,
        op("选项文字", "p3c01a"),
        op("选项文字", "p3c01b")
    ),
    sc(
        "p3c01a",
        """
        正文……
        """,
        op("选项文字", "p3c02")
    ),
    ...
)
```
- DSL 在同目录 `Dsl.kt`：`sc(id, text, vararg opts, isEnding = false)`、`op(text, target)`、`opFlag(text, target, vararg flags)`、`opIf(text, target, flag)`、`opUnless`、`opCount`。
- 文件里**不要**有 `import`、不要有 `$`、不要有反斜杠。正文一律用中文引号 `“ ”`，不要用英文引号，避免和三引号冲突。文件最后用 `)` 收口。

## 硬性要求（会逐条检查）
1. **每个选择之后都是新文本**：同一章两条分支不得复用句子，不得汇流后重复讲同一件事；分支可以指向同一个目标场景，但各自正文必须推进不同的信息。
2. **不许循环、不许回跳**：任何 target 的章号必须比当前章大（同章分支可以互指下一章）。
3. **主角周砚靠判断破局**：结论必须由文中已给出的物证、时间、笔迹、听觉、账目推出；禁止巧合、天降线索、「突然想起」。
4. **删除解释性总结**：不写「他意识到」「这说明」「显然」「这一刻他明白了」。犹豫和心理用动作写：停顿、改口、反复看某处、把笔按在纸上没写。
5. 每章必须有线索推进，按 bible 挂 `opFlag` / `opIf`（flag 名照 bible 的线索标记表）。
6. 选项 ≤12 字，必须是判断或动作，不能是「问问情况」这类空转。
7. 文风：冷、短句、白描、克制。不排比、不抒情、不写总结段。

## 交付
自检后只回报：函数名、场景数、总汉字数、是否偏离大纲（一句话）。**不要贴正文**。不要运行 gradle / adb，不要改动其他文件。
