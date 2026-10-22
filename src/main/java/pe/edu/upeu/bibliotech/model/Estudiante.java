package pe.edu.upeu.bibliotech.model;

public class Estudiante {

    private int id;
    private String nombre;
    private boolean activo;
    private boolean suspendido;   // NUEVO: regla de negocio del Equipo 1

    public Estudiante(int id, String nombre, boolean activo, boolean suspendido) {
        this.id = id;
        this.nombre = nombre;
        this.activo = activo;
        this.suspendido = suspendido;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isActivo() { return activo; }
    public boolean isSuspendido() { return suspendido; }
}
