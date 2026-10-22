package pe.edu.upeu.bibliotech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pe.edu.upeu.bibliotech.repository.EstudianteRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrestamoServiceTest {

    private EstudianteRepository repository;
    private PrestamoService prestamoService;

    @BeforeEach
    void preparar() {
        repository = Mockito.mock(EstudianteRepository.class);
        prestamoService = new PrestamoService(repository);
    }

    // =====================================================
    //  NUEVAS PRUEBAS - Equipo 1: Estudiante suspendido
    // =====================================================

    @Test
    @DisplayName("VÁLIDO: estudiante NO suspendido puede solicitar préstamo")
    void debePrestarSiEstudianteNoEstaSuspendido() {
        // Arrange
        when(repository.existePorId(1)).thenReturn(true);
        when(repository.estaSuspendido(1)).thenReturn(false);
        when(repository.estaActivo(1)).thenReturn(true);

        // Act
        boolean resultado = prestamoService.puedePrestar(1, 1, 3);

        // Assert
        assertTrue(resultado);
        verify(repository).estaSuspendido(1);
    }

    @Test
    @DisplayName("INVÁLIDO: estudiante suspendido NO puede solicitar préstamo")
    void noDebePrestarSiEstudianteEstaSuspendido() {
        // Arrange
        when(repository.existePorId(3)).thenReturn(true);
        when(repository.estaSuspendido(3)).thenReturn(true);

        // Act + Assert
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> prestamoService.puedePrestar(3, 0, 5));

        assertEquals("Estudiante suspendido", ex.getMessage());
        verify(repository, never()).estaActivo(3);   // se detiene antes
    }

    // =====================================================
    //  PRUEBAS EXISTENTES (CP01 - CP05)
    // =====================================================

    @Test
    @DisplayName("CP01: estudiante no existe lanza excepción")
    void debeLanzarExcepcionSiEstudianteNoExiste() {
        when(repository.existePorId(99)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> prestamoService.puedePrestar(99, 0, 5));

        assertEquals("Estudiante no existe", ex.getMessage());
        verify(repository, never()).estaActivo(99);
    }

    @Test
    @DisplayName("CP02: estudiante inactivo no puede prestar")
    void noDebePrestarSiEstudianteEstaInactivo() {
        when(repository.existePorId(2)).thenReturn(true);
        when(repository.estaActivo(2)).thenReturn(false);

        assertFalse(prestamoService.puedePrestar(2, 0, 5));
    }

    @Test
    @DisplayName("CP03: con 3 préstamos activos no puede prestar")
    void noDebePrestarSiTieneTresLibros() {
        when(repository.existePorId(1)).thenReturn(true);
        when(repository.estaActivo(1)).thenReturn(true);

        assertFalse(prestamoService.puedePrestar(1, 3, 5));
    }

    @Test
    @DisplayName("CP04: sin ejemplares disponibles no puede prestar")
    void noDebePrestarSinEjemplares() {
        when(repository.existePorId(1)).thenReturn(true);
        when(repository.estaActivo(1)).thenReturn(true);

        assertFalse(prestamoService.puedePrestar(1, 1, 0));
    }

    @Test
    @DisplayName("CP05: todas las condiciones se cumplen, préstamo autorizado")
    void debeAutorizarPrestamo() {
        when(repository.existePorId(1)).thenReturn(true);
        when(repository.estaActivo(1)).thenReturn(true);

        assertTrue(prestamoService.puedePrestar(1, 1, 3));
        verify(repository).existePorId(1);
        verify(repository).estaActivo(1);
    }
}
