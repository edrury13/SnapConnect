Write-Host "Deploying Story Cleanup Edge Function..." -ForegroundColor Cyan

# Check if supabase CLI is installed
try {
    $null = Get-Command supabase -ErrorAction Stop
} catch {
    Write-Host "Supabase CLI not found. Please install it first:" -ForegroundColor Red
    Write-Host "https://supabase.com/docs/guides/cli" -ForegroundColor Yellow
    exit 1
}

# Check if we're in the project root
if (-not (Test-Path "supabase/functions/cleanup-stories/index.ts")) {
    Write-Host "Error: Edge function not found at supabase/functions/cleanup-stories/index.ts" -ForegroundColor Red
    Write-Host "Make sure you're running this script from the project root directory" -ForegroundColor Yellow
    exit 1
}

# Deploy the function
Write-Host "Deploying cleanup-stories function..." -ForegroundColor Yellow
& supabase functions deploy cleanup-stories

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Edge function deployed successfully!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Test it with:" -ForegroundColor Cyan
    Write-Host "curl -i --location --request POST ``"
    Write-Host "  'https://ngrbhordabshfvlbqmzu.supabase.co/functions/v1/cleanup-stories' ``"
    Write-Host "  --header 'Authorization: Bearer YOUR_ANON_KEY' ``"
    Write-Host "  --header 'Content-Type: application/json'"
} else {
    Write-Host "❌ Deployment failed" -ForegroundColor Red
    Write-Host "Make sure you've run 'supabase login' and linked your project" -ForegroundColor Yellow
} 