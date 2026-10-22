package pe.edu.upeu.bibliotech.repository;

import org.springframework.stereotype.Repository;
import pe.edu.upeu.bibliotech.model.Estudiante;

import java.util.HashMap;
import java.util.Map;

/**
 * Repositorio en memoria (sin base de datos) con datos de ejemplo.
 */
@Repository
public class EstudianteRepositoryMemoria implements EstudianteRepository {

    private final Map<Integer, Estudiante> estudiantes = new HashMap<>();

    public EstudianteRepositoryMemoria() {
        estudiantes.put(1, new Estudiante(1, "Ana Quispe", true, false));     // habilitada
        estudiantes.put(2, new Estudiante(2, "Luis Mamani", false, false));   // inactivo
        estudiantes.put(3, new Estudiante(3, "Rosa Condori", true, true));    // SUSPENDIDA
    }

    @Override
    public boolean existePorId(int id) {
        return estudiantes.containsKey(id);
    }

    @Override
    public boolean estaActivo(int id) {
        return estudiantes.containsKey(id) && estudiantes.get(id).isActivo();
    }

    @Override
    public boolean estaSuspendido(int id) {
        return estudiantes.containsKey(id) && estudiantes.get(id).isSuspendido();
    }
}
