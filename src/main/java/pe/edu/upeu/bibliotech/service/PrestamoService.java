package pe.edu.upeu.bibliotech.service;

import org.springframework.stereotype.Service;
import pe.edu.upeu.bibliotech.repository.EstudianteRepository;

@Service
public class PrestamoService {

    private final EstudianteRepository repository;

    public PrestamoService(EstudianteRepository repository) {
        this.repository = repository;
    }

    public boolean puedePrestar(int estudianteId,
                                int librosPrestados,
                                int ejemplaresDisponibles) {

        if (!repository.existePorId(estudianteId)) {
            throw new RuntimeException("Estudiante no existe");
        }

        // NUEVA REGLA (Equipo 1): un estudiante suspendido no puede solicitar préstamos
            if (repository.estaSuspendido(estudianteId)) {
            throw new IllegalStateException("Estudiante suspendido");
        }

        if (!repository.estaActivo(estudianteId)) {
            return false;
        }
        if (librosPrestados >= 3) {
            return false;
        }
        if (ejemplaresDisponibles <= 0) {
            return false;
        }
        return true;
    }
}
