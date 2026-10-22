# BiblioTech – Práctica de Integración Continua

Sistema de validación de préstamos bibliográficos (Spring Boot + JUnit 5 + Mockito + GitHub Actions).

## Funcionalidad implementada – Equipo 1
**Validación de estudiante suspendido:** un estudiante suspendido no puede solicitar préstamos.

| Capa | Archivo | Cambio |
|---|---|---|
| Model | `model/Estudiante.java` | Campo `suspendido` |
| Repository | `repository/EstudianteRepository.java` | Método `estaSuspendido(id)` |
| Service | `service/PrestamoService.java` | Lanza `IllegalStateException("Estudiante suspendido")` |
| Controller | `controller/PrestamoController.java` | Devuelve 400 con el mensaje |

## Pruebas
- `debePrestarSiEstudianteNoEstaSuspendido` → escenario válido
- `noDebePrestarSiEstudianteEstaSuspendido` → escenario inválido
- CP01–CP05 → reglas existentes

```bash
mvn test
```

## Probar la API
```bash
mvn spring-boot:run
# Ana (habilitada):   http://localhost:8080/api/prestamos/validar?estudianteId=1&prestados=0&ejemplares=5
# Rosa (suspendida):  http://localhost:8080/api/prestamos/validar?estudianteId=3&prestados=0&ejemplares=5
```

## Ramas
- `main` – producción
- `develop` – integración
- `feature/nueva-funcionalidad` – esta mejora
- `practica/error-controlado` – evidencia de Failure → Success
