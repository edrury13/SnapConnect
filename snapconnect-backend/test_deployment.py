"""
Simple test script to verify deployment is working
"""
import requests
import json

# Replace with your actual deployment URL
BASE_URL = "https://snapconnect-rag-api.onrender.com"
# Replace with your actual API key
API_KEY = "your-api-key-here"

def test_health():
    """Test health endpoint"""
    response = requests.get(f"{BASE_URL}/health")
    print(f"Health check: {response.status_code}")
    print(f"Response: {response.json()}")
    return response.status_code == 200

def test_root():
    """Test root endpoint"""
    response = requests.get(f"{BASE_URL}/")
    print(f"\nRoot endpoint: {response.status_code}")
    print(f"Response: {response.json()}")
    return response.status_code == 200

def test_embed():
    """Test embedding endpoint"""
    headers = {
        "X-API-Key": API_KEY,
        "Content-Type": "application/json"
    }
    
    data = {
        "content": "Test content for embedding",
        "user_id": "test_user",
        "tags": ["test", "deployment"]
    }
    
    response = requests.post(
        f"{BASE_URL}/api/v1/embed/text",
        headers=headers,
        json=data
    )
    
    print(f"\nEmbed endpoint: {response.status_code}")
    print(f"Response: {response.json()}")
    return response.status_code in [200, 201]

if __name__ == "__main__":
    print("Testing SnapConnect RAG API deployment...\n")
    
    tests = [
        ("Health Check", test_health),
        ("Root Endpoint", test_root),
        ("Embed Endpoint", test_embed)
    ]
    
    results = []
    for test_name, test_func in tests:
        try:
            passed = test_func()
            results.append((test_name, passed))
        except Exception as e:
            print(f"\nError in {test_name}: {e}")
            results.append((test_name, False))
    
    print("\n\nTest Results:")
    print("-" * 40)
    for test_name, passed in results:
        status = "✓ PASSED" if passed else "✗ FAILED"
        print(f"{test_name}: {status}") 