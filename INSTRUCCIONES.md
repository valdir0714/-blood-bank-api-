# Blood Bank API - Instrucciones de prueba

## 1. Requisitos previos
- MySQL corriendo en localhost:3306
- Java 21+ (JDK)
- NetBeans con soporte Maven

## 2. Base de datos
```sql
CREATE DATABASE IF NOT EXISTS blood_bank_db;
```

## 3. Configurar conexion
Editar `src/main/resources/application.yml`:
```yaml
username: root       # tu usuario MySQL
password: root       # tu password MySQL
```

## 4. Ejecutar en NetBeans
1. File > Open Project > seleccionar `C:\Users\PC_13\Desktop\blood-bank-api`
2. Boton derecho > Run (o presionar F6)
3. Esperar a que Spring Boot inicie en `http://localhost:8080`

## 5. Verificar que funciona
- Swagger UI: http://localhost:8080/swagger-ui.html
- Endpoints de prueba (PowerShell):
  ```
  .\test-api.ps1
  ```

## 6. Pruebas unitarias
```bash
mvn test
```

## Endpoints disponibles

| Metodo | Endpoint | Descripcion |
|--------|----------|-------------|
| POST | /api/donantes | Registrar donante |
| GET | /api/donantes | Listar donantes (paginado) |
| GET | /api/donantes/{id} | Donante por ID |
| GET | /api/donantes/documento/{doc} | Donante por documento |
| PUT | /api/donantes/{id} | Actualizar donante |
| DELETE | /api/donantes/{id} | Eliminar donante |
| POST | /api/donaciones | Registrar donacion |
| GET | /api/donaciones | Listar donaciones |
| GET | /api/donaciones/{id} | Donacion por ID |
| GET | /api/donaciones/donante/{id} | Historial del donante |
| POST | /api/consentimientos | Firmar consentimiento |
| GET | /api/consentimientos/{id}/valido | Verificar consentimiento |
| GET | /api/inventario | Inventario completo |
| GET | /api/inventario/{tipo} | Inventario por tipo |
