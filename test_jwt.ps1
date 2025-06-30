# Script de test JWT
Write-Host "=== Test du système JWT ===" -ForegroundColor Green

# 1. Authentification
Write-Host "1. Authentification..." -ForegroundColor Yellow
$loginBody = @{
    email = "jean.dupont@email.com"
    password = "motdepasse123"
} | ConvertTo-Json

try {
    $loginResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/clients/login" -Method POST -Body $loginBody -ContentType "application/json"
    $loginData = $loginResponse.Content | ConvertFrom-Json
    $token = $loginData.token
    Write-Host "✅ Authentification réussie" -ForegroundColor Green
    Write-Host "Token: $($token.Substring(0, 50))..." -ForegroundColor Cyan
} catch {
    Write-Host "❌ Erreur d'authentification: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# 2. Test d'accès protégé avec token
Write-Host "`n2. Test d'accès protégé avec token..." -ForegroundColor Yellow
try {
    $headers = @{
        "Authorization" = "Bearer $token"
    }
    $produitsResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/produits" -Method GET -Headers $headers
    Write-Host "✅ Accès protégé réussi" -ForegroundColor Green
    Write-Host "Status: $($produitsResponse.StatusCode)" -ForegroundColor Cyan
    Write-Host "Contenu: $($produitsResponse.Content.Substring(0, 100))..." -ForegroundColor Cyan
} catch {
    Write-Host "❌ Erreur d'accès protégé: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $errorContent = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($errorContent)
        $errorMessage = $reader.ReadToEnd()
        Write-Host "Message d'erreur: $errorMessage" -ForegroundColor Red
    }
}

# 3. Test d'accès sans token
Write-Host "`n3. Test d'accès sans token..." -ForegroundColor Yellow
try {
    $produitsResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/produits" -Method GET
    Write-Host "❌ Erreur: L'accès sans token devrait être refusé" -ForegroundColor Red
} catch {
    Write-Host "✅ Accès sans token correctement refusé" -ForegroundColor Green
    Write-Host "Status: $($_.Exception.Response.StatusCode)" -ForegroundColor Cyan
}

# 4. Validation du token
Write-Host "`n4. Validation du token..." -ForegroundColor Yellow
$validateBody = @{
    token = $token
} | ConvertTo-Json

try {
    $validateResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/clients/validate-token" -Method POST -Body $validateBody -ContentType "application/json"
    Write-Host "✅ Token validé avec succès" -ForegroundColor Green
    Write-Host "Contenu: $($validateResponse.Content)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Erreur de validation: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== Test terminé ===" -ForegroundColor Green 