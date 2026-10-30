"""内容质量抽检：禁语、分支重复、选项长度、'你'在叙述中的使用。"""
import re
from pathlib import Path

SRC = Path(r"C:\Users\YuMoo_\OneDrive\Works\AndroidStudioProjects\DieDang\app\src\main\java\com\yumoo\diedang\story\wugang")
BANNED = ["他意识到", "意识到", "这说明", "换句话说", "显然", "这一刻他明白", "他明白了", "心里一沉", "仿佛", "似乎"]

BLOCK = re.compile(r'(?m)^\s*sc\(')
ID_RE = re.compile(r'sc\(\s*"([^"]+)"')
OPT_RE = re.compile(r'op\w*\(\s*"([^"]*)"\s*,\s*"([^"]*)"')
TXT_RE = re.compile(r'"""(.*?)"""', re.S)

scenes = {}
for f in sorted(SRC.glob("Part*.kt")):
    raw = f.read_text(encoding="utf-8")
    for blk in BLOCK.split(raw)[1:]:
        m = ID_RE.search("sc(" + blk)
        if not m:
            continue
        sid = m.group(1)
        texts = TXT_RE.findall(blk)
        body = "\n".join(t.strip() for t in texts)
        opts = OPT_RE.findall(blk)
        scenes[sid] = (f.name, body, opts)

issues = []
for sid, (fn, body, opts) in scenes.items():
    for w in BANNED:
        if w in body:
            issues.append(f"{fn} {sid} 含禁语「{w}」×{body.count(w)}")
    for text, target in opts:
        if len(text) > 12:
            issues.append(f"{fn} {sid} 选项过长 {len(text)} 字：{text}")
    # '你' 是否出现在叙述（这里只统计次数，人工判断用）
print(f"场景 {len(scenes)}")

# 同章两条分支的重复句子检测（按 12 字 shingle 重叠率）
def shingles(s, n=12):
    s = re.sub(r"\s", "", s)
    return {s[i:i+n] for i in range(len(s) - n + 1)}

dups = []
for sid in scenes:
    if sid.endswith("a") and sid[:-1] + "b" in scenes:
        b = sid[:-1] + "b"
        sa = shingles(scenes[sid][1])
        sb = shingles(scenes[b][1])
        if not sa or not sb:
            continue
        j = len(sa & sb) / min(len(sa), len(sb))
        if j > 0.02:
            dups.append(f"{sid}/{b} 重叠 {j:.1%}（{scenes[sid][0]}）")

if dups:
    issues.append("以下同章分支疑似共用文字：")
    issues.extend("  " + d for d in dups)

print(f"问题 {len(issues)} 条")
for i in issues[:80]:
    print(i)
