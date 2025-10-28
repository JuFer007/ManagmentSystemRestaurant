package com.app.SystemRestaurant;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Usuario;
import com.app.SystemRestaurant.Repository.EmpleadoRepository;
import com.app.SystemRestaurant.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest

public class UsuarioUniqueTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Test
    @Transactional
    public void testUniqueNombreUsuario() {
        // Primero necesitamos un empleado, porque idEmpleado es obligatorio
        Empleado empleado1 = new Empleado();
        empleado1.setNombreEmpleado("Juanito");
        empleado1.setApellidoPaternoEmpleado("Pelaez");
        empleado1.setApellidoMaternoEmpleado("Lopez");
        empleado1.setDniEmpleado("12340670");
        empleado1.setCargoEmpleado("Cocinero");
        empleado1.setSalarioEmpleado(1500.0);
        empleado1.setEstadoEmpleado("Activo");
        empleadoRepository.save(empleado1);

        // Crear primer usuario
        Usuario u1 = Usuario.builder()
                .idEmpleado(empleado1)
                .nombreUsuario("usuario11")
                .contraseñaUsuario("1233")
                .build();
        usuarioRepository.save(u1);

        // Crear segundo usuario con mismo nombre
        Usuario u2 = Usuario.builder()
                .idEmpleado(empleado1)
                .nombreUsuario("usuario22")
                .contraseñaUsuario("456")
                .build();

        // Verificamos que lanza excepción por UNIQUE
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.saveAndFlush(u2);
        });
    }
}
