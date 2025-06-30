# Script pour démarrer PostgreSQL avec Docker
Write-Host "🚀 Démarrage de PostgreSQL avec Docker..." -ForegroundColor Green

# Vérifier si Docker est installé
try {
    docker --version | Out-Null
    Write-Host "✅ Docker est installé" -ForegroundColor Green
} catch {
    Write-Host "❌ Docker n'est pas installé ou n'est pas dans le PATH" -ForegroundColor Red
    Write-Host "Veuillez installer Docker Desktop depuis https://www.docker.com/products/docker-desktop/" -ForegroundColor Yellow
    exit 1
}

# Vérifier si le conteneur existe déjà
$containerExists = docker ps -a --filter "name=postgres-cocktails" --format "table {{.Names}}" | Select-String "postgres-cocktails"

if ($containerExists) {
    Write-Host "📦 Le conteneur postgres-cocktails existe déjà" -ForegroundColor Yellow
    
    # Vérifier si le conteneur est en cours d'exécution
    $containerRunning = docker ps --filter "name=postgres-cocktails" --format "table {{.Names}}" | Select-String "postgres-cocktails"
    
    if ($containerRunning) {
        Write-Host "✅ Le conteneur PostgreSQL est déjà en cours d'exécution" -ForegroundColor Green
    } else {
        Write-Host "🔄 Redémarrage du conteneur PostgreSQL..." -ForegroundColor Yellow
        docker start postgres-cocktails
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Conteneur PostgreSQL redémarré avec succès" -ForegroundColor Green
        } else {
            Write-Host "❌ Erreur lors du redémarrage du conteneur" -ForegroundColor Red
            exit 1
        }
    }
} else {
    Write-Host "🆕 Création d'un nouveau conteneur PostgreSQL..." -ForegroundColor Yellow
    docker run --name postgres-cocktails `
        -e POSTGRES_PASSWORD=postgres `
        -e POSTGRES_DB=cocktail_db `
        -p 5432:5432 `
        -d postgres:15
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Conteneur PostgreSQL créé et démarré avec succès" -ForegroundColor Green
    } else {
        Write-Host "❌ Erreur lors de la création du conteneur" -ForegroundColor Red
        exit 1
    }
}

# Attendre que PostgreSQL soit prêt
Write-Host "⏳ Attente que PostgreSQL soit prêt..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

# Vérifier la connexion
Write-Host "🔍 Vérification de la connexion à PostgreSQL..." -ForegroundColor Yellow
try {
    $result = docker exec postgres-cocktails pg_isready -U postgres
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ PostgreSQL est prêt et accessible" -ForegroundColor Green
    } else {
        Write-Host "❌ PostgreSQL n'est pas encore prêt" -ForegroundColor Red
    }
} catch {
    Write-Host "❌ Erreur lors de la vérification de PostgreSQL" -ForegroundColor Red
}

Write-Host ""
Write-Host "📋 Informations de connexion :" -ForegroundColor Cyan
Write-Host "   Host: localhost" -ForegroundColor White
Write-Host "   Port: 5432" -ForegroundColor White
Write-Host "   Database: cocktail_db" -ForegroundColor White
Write-Host "   Username: postgres" -ForegroundColor White
Write-Host "   Password: postgres" -ForegroundColor White
Write-Host ""
Write-Host "🚀 Vous pouvez maintenant démarrer votre application Spring Boot !" -ForegroundColor Green
Write-Host "   Commande: mvn spring-boot:run" -ForegroundColor Cyan 