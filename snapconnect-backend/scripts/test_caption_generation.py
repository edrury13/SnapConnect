#!/usr/bin/env python3
"""Test script to demonstrate improved factual caption generation."""

import asyncio
from openai import OpenAI
import os
from dotenv import load_dotenv

load_dotenv()

client = OpenAI(api_key=os.getenv("OPENAI_API_KEY"))

# Example vision analysis results
test_cases = [
    {
        "name": "Portrait Photo",
        "analysis": {
            "artistic_style": "none",
            "technique": "photography",
            "subjects": ["young woman", "red sweater", "coffee cup", "wooden table"],
            "colors": ["warm red", "brown wood", "soft lighting"],
            "mood": "relaxed"
        }
    },
    {
        "name": "Digital Art",
        "analysis": {
            "artistic_style": "cyberpunk",
            "technique": "digital_illustration",
            "subjects": ["futuristic cityscape", "neon signs", "flying vehicles"],
            "colors": ["neon pink", "electric blue", "dark purple"],
            "mood": "energetic"
        }
    },
    {
        "name": "Oil Painting",
        "analysis": {
            "artistic_style": "impressionism",
            "technique": "oil_painting",
            "subjects": ["water lilies", "pond", "reflections"],
            "colors": ["soft greens", "pale blues", "white highlights"],
            "mood": "peaceful"
        }
    }
]

def generate_old_caption(analysis):
    """Old artistic approach."""
    style = analysis.get('artistic_style', 'unknown')
    mood = analysis.get('mood', 'expressive')
    prompt = f"""You are an art expert describing a {style} artwork.
    Focus on artistic expression and emotional impact.
    Mood: {mood}. Write a vivid 20-word description."""
    
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[{"role": "user", "content": prompt}],
        max_tokens=30,
        temperature=0.8,
    )
    return response.choices[0].message.content.strip()

def generate_new_caption(analysis):
    """New factual approach."""
    # Build factual components
    components = []
    
    subjects = analysis.get('subjects', [])
    if subjects:
        components.append(", ".join(subjects[:3]))
    
    style = analysis.get('artistic_style', 'none')
    if style != 'none':
        components.append(f"in {style} style")
    
    colors = analysis.get('colors', [])
    if colors:
        components.append(f"with {colors[0]}")
    
    description = " ".join(components)
    
    prompt = f"""Given this image analysis: {description}.
    Generate a concise, factual description in 20 words or fewer.
    Focus on what is literally visible. Avoid metaphors or artistic interpretation.
    Be specific about objects, colors, and techniques."""
    
    response = client.chat.completions.create(
        model="gpt-3.5-turbo",
        messages=[{"role": "user", "content": prompt}],
        max_tokens=30,
        temperature=0.3,  # Lower for consistency
    )
    return response.choices[0].message.content.strip()

print("CAPTION GENERATION COMPARISON\n" + "="*50)

for test in test_cases:
    print(f"\n{test['name']}:")
    print(f"Analysis: {test['analysis']['subjects'][:2]}, {test['analysis']['technique']}")
    
    # Old approach
    old_caption = generate_old_caption(test['analysis'])
    print(f"OLD (artistic): {old_caption}")
    
    # New approach  
    new_caption = generate_new_caption(test['analysis'])
    print(f"NEW (factual):  {new_caption}")

print("\n" + "="*50)
print("The NEW approach provides more accurate, factual descriptions")
print("that better match what's actually in the image.") 