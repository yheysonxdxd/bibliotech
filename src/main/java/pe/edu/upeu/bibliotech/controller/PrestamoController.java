package pe.edu.upeu.bibliotech.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.bibliotech.service.PrestamoService;

import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    // Ejemplo: GET http://localhost:8080/api/prestamos/validar?estudianteId=3&prestados=0&ejemplares=5
    @GetMapping("/validar")
    public ResponseEntity<Map<String, Object>> validar(@RequestParam int estudianteId,
                                                       @RequestParam int prestados,
                                                       @RequestParam int ejemplares) {
        try {
            boolean permitido = service.puedePrestar(estudianteId, prestados, ejemplares);
            return ResponseEntity.ok(Map.of(
                    "permitido", permitido,
                    "mensaje", permitido ? "Préstamo autorizado" : "Préstamo no autorizado"));
        } catch (IllegalStateException e) {          // estudiante suspendido
            return ResponseEntity.badRequest().body(Map.of(
                    "permitido", false,
                    "mensaje", e.getMessage()));
        } catch (RuntimeException e) {               // estudiante no existe
            return ResponseEntity.status(404).body(Map.of(
                    "permitido", false,
                    "mensaje", e.getMessage()));
        }
    }
}
