package pe.edu.upeu.bibliotech.repository;

public interface EstudianteRepository {

    boolean existePorId(int id);

    boolean estaActivo(int id);

    // NUEVO: indica si el estudiante tiene una suspensión vigente
    boolean estaSuspendido(int id);
}
