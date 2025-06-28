#!/usr/bin/env python3
"""
generate_captions.py
--------------------
Walks the directory `seed/community_assets` and for every `*.jpg` (or png)
file that has **no** sibling `.txt`, it creates one by generating a short
caption via the OpenAI Chat Completion API.

Requirements:
  • OPENAI_API_KEY in environment (same env var used by backend).
  • python -m pip install openai python-dotenv (already listed in requirements).

Usage:
  python scripts/generate_captions.py

You can run this before `seed_rag.py` so that the seeding process uploads both
image and text embeddings.
"""

import os
import sys
from pathlib import Path
from dotenv import load_dotenv

load_dotenv()

try:
    from openai import OpenAI
except ImportError:
    print("[ERROR] openai package missing. Install with `pip install openai`.")
    sys.exit(1)

API_KEY = "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"
if not API_KEY:
    print("[ERROR] OPENAI_API_KEY not set in environment.")
    sys.exit(1)

client = OpenAI(api_key=API_KEY)

ROOT = Path(__file__).resolve().parent.parent / "scripts" / "seed" / "community_assets"
if not ROOT.exists():
    print(f"[ERROR] Directory {ROOT} does not exist. Generate images first.")
    sys.exit(1)

# Prompt template
PROMPT_TPL = (
    "You are a creative assistant. Given the filename parts describing style "
    "tags, generate a concise, vivid art caption in <= 20 words. "
    "Avoid hashtags or metadata. Style tags: {tags}."
)

def create_caption(tags: str) -> str:
    """Call OpenAI chat API to produce a short caption."""
    prompt = PROMPT_TPL.format(tags=tags.replace("_", " "))
    try:
        response = client.chat.completions.create(
            model="gpt-3.5-turbo-0125",
            messages=[{"role": "user", "content": prompt}],
            max_tokens=30,
            temperature=0.8,
        )
        return response.choices[0].message.content.strip()
    except Exception as exc:
        print(f"[WARN] OpenAI call failed: {exc}. Using fallback caption.")
        return tags.replace("_", " ").title()

count = 0
for img in ROOT.glob("*.jp*g"):
    txt_path = img.with_suffix(".txt")
    if txt_path.exists():
        continue
    # Parse tags between first underscore and last underscore
    parts = img.stem.split("_")
    if len(parts) < 3:
        # Fallback: use entire stem as tags
        tags_segment = " ".join(parts[1:]) if len(parts) > 1 else img.stem
    else:
        tags_segment = " ".join(parts[1:-1])
    caption = create_caption(tags_segment)
    txt_path.write_text(caption, encoding="utf-8")
    count += 1
    print(f"✍️  Wrote caption for {img.name}: {caption}")

print(f"\nGenerated {count} caption files.") 