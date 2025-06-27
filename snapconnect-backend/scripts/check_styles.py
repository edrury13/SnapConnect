#!/usr/bin/env python3
"""Check what styles exist in the style-taxonomy namespace."""

import os
from pinecone import Pinecone
from dotenv import load_dotenv

load_dotenv()

# Initialize Pinecone
PINECONE_API_KEY = os.getenv("PINECONE_API_KEY")
PINECONE_INDEX_NAME = os.getenv("PINECONE_INDEX_NAME", "snapconnect")

if not PINECONE_API_KEY:
    print("Error: PINECONE_API_KEY not set")
    exit(1)

pc = Pinecone(api_key=PINECONE_API_KEY)
index = pc.Index(PINECONE_INDEX_NAME)

# Get index stats
stats = index.describe_index_stats()
print("Index Statistics:")
print(f"Total vectors: {stats.total_vector_count}")
print(f"Dimensions: {stats.dimension}")
print("\nNamespaces:")
for namespace, ns_stats in stats.namespaces.items():
    print(f"  {namespace}: {ns_stats.vector_count} vectors")

# Query the style-taxonomy namespace to see what's in there
print("\n\nChecking style-taxonomy namespace...")

# Create a random vector to query all items (hack to list all)
import random
random_vector = [random.random() for _ in range(1024)]  # Assuming 1024 dimensions

try:
    results = index.query(
        vector=random_vector,
        top_k=50,  # Get up to 50 styles
        namespace="style-taxonomy",
        include_metadata=True
    )
    
    if results.matches:
        print(f"\nFound {len(results.matches)} styles in style-taxonomy:")
        for match in results.matches:
            style_name = match.metadata.get('style_name', 'unknown')
            technique = match.metadata.get('technique', 'N/A')
            auto_detected = match.metadata.get('auto_detected', False)
            print(f"  - {style_name} (technique: {technique}, auto: {auto_detected})")
    else:
        print("No styles found in style-taxonomy namespace!")
        
except Exception as e:
    print(f"Error querying style-taxonomy: {e}")

print("\n\nDone.") 