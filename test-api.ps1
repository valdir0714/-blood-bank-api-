# PowerShell script para probar la API Blood Bank
# Ejecutar DESPUES de iniciar la aplicacion en NetBeans

$BASE = "http://localhost:8082/api"

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "  TESTING BLOOD BANK API" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan

# 1. Registrar donante valido
Write-Host "`n[1] POST /api/donantes - Registrar donante" -ForegroundColor Yellow
$donante1 = Invoke-RestMethod -Uri "$BASE/donantes" -Method Post -Body (@{
    nombres = "Carlos"
    apellidos = "Martinez"
    documento = "123456789"
    fechaNacimiento = "1990-05-15"
    tipoSangre = "O+"
    peso = 75
    telefono = "3001234567"
    correo = "carlos@email.com"
    direccion = "Calle 123"
    aceptaConsentimiento = $true
    firmaConsentimiento = "base64:firma_digital_carlos"
} | ConvertTo-Json) -ContentType "application/json"
Write-Host "  OK -> ID: $($donante1.id)" -ForegroundColor Green

# 2. Registrar donante sin consentimiento (deberia fallar)
Write-Host "`n[2] POST /api/donantes - Donante SIN consentimiento" -ForegroundColor Yellow
try {
    $donante2 = Invoke-RestMethod -Uri "$BASE/donantes" -Method Post -Body (@{
        nombres = "Pedro"
        apellidos = "Gomez"
        documento = "987654321"
        fechaNacimiento = "2000-01-01"
        tipoSangre = "A+"
        peso = 60
        telefono = "3007654321"
        correo = "pedro@email.com"
        direccion = "Calle 456"
        aceptaConsentimiento = $false
        firmaConsentimiento = ""
    } | ConvertTo-Json) -ContentType "application/json"
    Write-Host "  ERROR: Deberia haber fallado" -ForegroundColor Red
} catch {
    Write-Host "  OK -> Rechazado correctamente: $($_.Exception.Message)" -ForegroundColor Green
}

# 3. Firmar consentimiento para donante existente
Write-Host "`n[3] POST /api/consentimientos - Firmar consentimiento" -ForegroundColor Yellow
$consent = Invoke-RestMethod -Uri "$BASE/consentimientos" -Method Post -Body (@{
    donanteId = $donante1.id
    aceptaConsentimiento = $true
    firmaConsentimiento = "base64:firma_actualizada"
} | ConvertTo-Json) -ContentType "application/json"
Write-Host "  OK -> Firma registrada" -ForegroundColor Green

# 4. Verificar consentimiento
Write-Host "`n[4] GET /api/consentimientos/{id}/valido" -ForegroundColor Yellow
$valido = Invoke-RestMethod -Uri "$BASE/consentimientos/$($donante1.id)/valido"
Write-Host "  OK -> Consentimiento valido: $valido" -ForegroundColor Green

# 5. Registrar donacion
Write-Host "`n[5] POST /api/donaciones - Registrar donacion" -ForegroundColor Yellow
$donacion = Invoke-RestMethod -Uri "$BASE/donaciones" -Method Post -Body (@{
    donanteId = $donante1.id
    cantidadML = 450
    observaciones = "Donacion voluntaria"
} | ConvertTo-Json) -ContentType "application/json"
Write-Host "  OK -> Codigo: $($donacion.codigoUnico)" -ForegroundColor Green

# 6. Historial de donaciones
Write-Host "`n[6] GET /api/donaciones/donante/{id}" -ForegroundColor Yellow
$historial = Invoke-RestMethod -Uri "$BASE/donaciones/donante/$($donante1.id)"
Write-Host "  OK -> $($historial.Count) donacion(es) encontrada(s)" -ForegroundColor Green

# 7. Consultar inventario
Write-Host "`n[7] GET /api/inventario" -ForegroundColor Yellow
$inventario = Invoke-RestMethod -Uri "$BASE/inventario"
Write-Host "  OK -> Inventario:" -ForegroundColor Green
$inventario | ForEach-Object { Write-Host "    $($_.tipoSangre): $($_.cantidadDisponibleML) ML" }

# 8. Consultar inventario por tipo
Write-Host "`n[8] GET /api/inventario/O+" -ForegroundColor Yellow
$invO = Invoke-RestMethod -Uri "$BASE/inventario/O%2B"
Write-Host "  OK -> O+: $($invO.cantidadDisponibleML) ML" -ForegroundColor Green

# 9. Listar donantes
Write-Host "`n[9] GET /api/donantes" -ForegroundColor Yellow
$donantes = Invoke-RestMethod -Uri "$BASE/donantes"
Write-Host "  OK -> $($donantes.content.Count) donante(s)" -ForegroundColor Green

# 10. Donacion con donante menor de edad (deberia fallar)
Write-Host "`n[10] POST /api/donantes - Donante menor de edad" -ForegroundColor Yellow
$menor = Invoke-RestMethod -Uri "$BASE/donantes" -Method Post -Body (@{
    nombres = "Luis"
    apellidos = "Tovar"
    documento = "111222333"
    fechaNacimiento = "2012-03-10"
    tipoSangre = "B+"
    peso = 55
    telefono = "3005556666"
    correo = "luis@email.com"
    direccion = "Calle 789"
    aceptaConsentimiento = $true
    firmaConsentimiento = "base64:firma_luis"
} | ConvertTo-Json) -ContentType "application/json"
Write-Host "  Registrado ID: $($menor.id)" -ForegroundColor Green

Write-Host "`n[11] POST /api/donaciones - Donacion menor edad (debe fallar)" -ForegroundColor Yellow
try {
    $donacionMenor = Invoke-RestMethod -Uri "$BASE/donaciones" -Method Post -Body (@{
        donanteId = $menor.id
        cantidadML = 450
    } | ConvertTo-Json) -ContentType "application/json"
    Write-Host "  ERROR: Deberia haber fallado" -ForegroundColor Red
} catch {
    Write-Host "  OK -> Rechazado correctamente (menor de edad)" -ForegroundColor Green
}

Write-Host "`n======================================" -ForegroundColor Cyan
Write-Host "  PRUEBAS COMPLETADAS" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
