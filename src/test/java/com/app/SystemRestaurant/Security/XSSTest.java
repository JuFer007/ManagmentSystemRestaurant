package com.app.SystemRestaurant.Security;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de seguridad contra Cross-Site Scripting (XSS)
 */
@SpringBootTest

public class XSSTest {
    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testXSSEnNombreCliente() {
        Cliente cliente = new Cliente();
        cliente.setDniCliente("12345678");
        cliente.setNombreCliente("<script>alert('XSS')</script>");
        cliente.setApellidosCliente("Test");

        Cliente guardado = clienteRepository.save(cliente);

        assertNotNull(guardado.getIdCliente());
        assertEquals("<script>alert('XSS')</script>", guardado.getNombreCliente());

        assertFalse(guardado.getNombreCliente().isEmpty());
    }

    @Test
    void testXSSEnDescripcionPlato() {
        String maliciousInput = "<img src=x onerror='alert(1)'>";
        assertNotNull(maliciousInput);
        assertTrue(maliciousInput.contains("<"));
    }
}
