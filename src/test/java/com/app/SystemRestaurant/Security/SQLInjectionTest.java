package com.app.SystemRestaurant.Security;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Pruebas de seguridad contra inyección SQL
 */
@SpringBootTest
@AutoConfigureMockMvc

public class SQLInjectionTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSQLInjectionEnLogin() throws Exception {
        String maliciousPayload = """
            {
                "nombreUsuario": "admin' OR '1'='1",
                "contraseña": "' OR '1'='1"
            }
            """;

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(maliciousPayload))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testSQLInjectionEnBusquedaCliente() throws Exception {
        mockMvc.perform(post("/system/clientes/buscar")
                        .param("termino", "'; DROP TABLE cliente; --"))
                .andExpect(status().isOk());
    }

    @Test
    void testSQLInjectionEnCrearCliente() throws Exception {
        String maliciousPayload = """
            {
                "dniCliente": "12345678'; DROP TABLE pedido; --",
                "nombreCliente": "Test",
                "apellidosCliente": "User"
            }
            """;

        mockMvc.perform(post("/system/clientes/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(maliciousPayload))
                .andExpect(status().is4xxClientError());
    }
}
