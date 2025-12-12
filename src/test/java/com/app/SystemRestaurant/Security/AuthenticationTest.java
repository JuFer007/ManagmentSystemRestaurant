package com.app.SystemRestaurant.Security;
import com.app.SystemRestaurant.Service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de autenticación y autorización
 */
@SpringBootTest

public class AuthenticationTest {
    @Autowired
    private UsuarioService usuarioService;

    @Test
    void testLoginConCredencialesValidas() {
        boolean resultado = usuarioService.login("admin", "1234");
        assertTrue(resultado, "Login con credenciales válidas debe ser exitoso");
    }

    @Test
    void testLoginConCredencialesInvalidas() {
        boolean resultado = usuarioService.login("admin", "wrongpassword");
        assertFalse(resultado, "Login con contraseña incorrecta debe fallar");
    }

    @Test
    void testLoginConUsuarioInexistente() {
        boolean resultado = usuarioService.login("usuarioNoExiste", "password");
        assertFalse(resultado, "Login con usuario inexistente debe fallar");
    }

    @Test
    void testLoginConCamposVacios() {
        boolean resultado = usuarioService.login("", "");
        assertFalse(resultado, "Login con campos vacíos debe fallar");
    }
}
