#!/usr/bin/env python3
"""Verify the backend structure is correct before deployment"""

import os
import sys

def check_file_exists(path, description):
    """Check if a file exists and report"""
    exists = os.path.exists(path)
    status = "[OK]" if exists else "[MISSING]"
    print(f"{status} {description}: {path}")
    return exists

def main():
    print("Verifying SnapConnect Backend Structure")
    print("=" * 50)
    
    # Check we're in the right directory
    if not os.path.exists("app"):
        print("ERROR: 'app' directory not found. Run this from the snapconnect-backend root.")
        sys.exit(1)
    
    all_good = True
    
    # Check main files
    print("\nMain Files:")
    all_good &= check_file_exists("requirements.txt", "Requirements file")
    all_good &= check_file_exists("render.yaml", "Render config")
    all_good &= check_file_exists(".gitignore", "Git ignore file")
    
    # Check app structure
    print("\nApp Structure:")
    all_good &= check_file_exists("app/__init__.py", "App package init")
    all_good &= check_file_exists("app/main.py", "Main FastAPI app")
    all_good &= check_file_exists("app/dependencies.py", "Dependencies")
    
    # Check subdirectories
    print("\nSubdirectories:")
    all_good &= check_file_exists("app/api/__init__.py", "API package")
    all_good &= check_file_exists("app/api/routes/__init__.py", "Routes package")
    all_good &= check_file_exists("app/api/routes/embed.py", "Embed routes")
    all_good &= check_file_exists("app/api/routes/search.py", "Search routes")
    all_good &= check_file_exists("app/api/routes/recommend.py", "Recommend routes")
    
    all_good &= check_file_exists("app/core/__init__.py", "Core package")
    all_good &= check_file_exists("app/core/config.py", "Config")
    all_good &= check_file_exists("app/core/security.py", "Security")
    
    all_good &= check_file_exists("app/models/__init__.py", "Models package")
    all_good &= check_file_exists("app/models/schemas.py", "Schemas")
    
    all_good &= check_file_exists("app/services/__init__.py", "Services package")
    all_good &= check_file_exists("app/services/embedding_service.py", "Embedding service")
    all_good &= check_file_exists("app/services/pinecone_service.py", "Pinecone service")
    
    # Check for problematic nested structure
    print("\nChecking for problematic nested structure:")
    if os.path.exists("app/app"):
        print("[WARNING] Found nested app/app directory - this should be removed!")
        all_good = False
    else:
        print("[OK] No nested app/app directory found")
    
    print("\n" + "=" * 50)
    if all_good:
        print("[SUCCESS] All checks passed! Structure looks good for deployment.")
    else:
        print("[FAILED] Some issues found. Please fix them before deploying.")
        sys.exit(1)

if __name__ == "__main__":
    main() 