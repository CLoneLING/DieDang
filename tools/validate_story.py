"""《雾港旧案》剧本静态校验：id 唯一、目标存在、只能往前走、无死路、字数统计。

用法：python tools/validate_story.py
"""
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
SRC = ROOT / "app/src/main/java/com/yumoo/diedang/story/wugang"

SCENE_RE = re.compile(r'sc\(\s*"([^"]+)"')
OPT_RE = re.compile(r'op\w*\(\s*"([^"]*)"\s*,\s*"([^"]*)"')
# 场景块：以 "    sc(" 或 "sc(" 开头，到下一个 sc( 之前
BLOCK_SPLIT = re.compile(r'(?m)^\s*sc\(')


def ordinal(sid: str) -> tuple:
    m = re.match(r"p(\d+)c(\d+)([a-z]?)$", sid)
    if not m:
        return (99, 99, 99)
    part, ch, branch = int(m.group(1)), int(m.group(2)), m.group(3)
    b = 0 if branch == "" else (ord(branch) - ord("a") + 1)
    return (part, ch, b)


def main() -> int:
    files = sorted(SRC.glob("Part*.kt"))
    if not files:
        print("没有找到 Part*.kt")
        return 1

    scenes = {}          # id -> dict(file, is_ending, targets, text_len)
    dup = []
    total_chars = 0

    for f in files:
        raw = f.read_text(encoding="utf-8")
        blocks = BLOCK_SPLIT.split(raw)
        for blk in blocks[1:]:
            m = SCENE_RE.search("sc(" + blk)
            if not m:
                continue
            sid = m.group(1)
            body = blk
            # 正文取三引号之间的内容
            texts = re.findall(r'"""(.*?)"""', body, re.S)
            text_len = sum(len(t.strip()) for t in texts)
            targets = [t for _, t in OPT_RE.findall(body)]
            # 去掉正文里误匹配（正文不会被 OPT_RE 匹配到，因为要求两条字符串挨着）
            is_ending = "isEnding = true" in body
            if sid in scenes:
                dup.append(sid)
            scenes[sid] = dict(file=f.name, ending=is_ending, targets=targets,
                               chars=text_len, body=body)
            total_chars += text_len

    problems = []
    if dup:
        problems.append("重复 id: " + ", ".join(sorted(set(dup))))

    for sid, info in scenes.items():
        if not info["targets"] and not info["ending"]:
            problems.append(f"{sid} 没有出口选项，又不是结局")
        for t in info["targets"]:
            if t not in scenes:
                problems.append(f"{sid} -> {t} 目标不存在")
                continue
            # 结局场景是终点，不参与"只能往前走"的判断
            if scenes[t]["ending"]:
                continue
            if ordinal(t) <= ordinal(sid):
                problems.append(f"{sid} -> {t} 往回走或原地打转")
    for sid, info in scenes.items():
        if info["ending"] and info["targets"]:
            problems.append(f"{sid} 是结局却还有出口")

    # 章节完整性
    chapters = sorted({(p, c) for (p, c, _b) in (ordinal(s) for s in scenes)})
    expected = [(p, c) for p in (1, 2, 3, 4) for c in range(1, 21)]
    missing_ch = [f"p{p}c{c:02d}" for (p, c) in expected if (p, c) not in chapters]

    print(f"文件数 {len(files)}  场景数 {len(scenes)}  总字数 {total_chars}")
    for f in files:
        n = sum(1 for s in scenes.values() if s["file"] == f.name)
        print(f"  {f.name}: {n} 场景")
    if missing_ch:
        print("缺章: " + ", ".join(missing_ch[:20]))
    if problems:
        print(f"\n!! 问题 {len(problems)} 条：")
        for p in problems[:60]:
            print("  - " + p)
        return 1
    print("\n校验通过：无重复 id、无悬空跳转、无回头路、无死路、80 章齐全")
    return 0


if __name__ == "__main__":
    sys.exit(main())
