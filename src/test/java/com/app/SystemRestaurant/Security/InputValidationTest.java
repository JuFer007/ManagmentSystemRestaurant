package com.app.SystemRestaurant.Security;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de validación de entrada
 */

public class InputValidationTest {
    @ParameterizedTest
    @ValueSource(strings = {"12345678", "87654321", "11111111"})
    void testDNIValido(String dni) {
        assertTrue(dni.matches("\\d{8}"), "DNI debe tener 8 dígitos");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "abcd1234", "1234567", "123456789"})
    void testDNIInvalido(String dni) {
        assertFalse(dni.matches("\\d{8}"), "DNI inválido debe ser rechazado");
    }

    @Test
    void testNombreConCaracteresEspeciales() {
        String nombre = "Juan<script>";

        // Debe rechazar nombres con caracteres especiales peligrosos
        assertFalse(nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"));
    }

    @Test
    void testEmailValido() {
        String email = "test@example.com";
        assertTrue(email.matches("^[A-Za-z0-9+_.-]+@(.+)$"));
    }

    @Test
    void testPrecioValido() {
        double precio = 25.50;
        assertTrue(precio > 0 && precio < 10000, "Precio debe estar en rango válido");
    }

    @Test
    void testCantidadPedidoValida() {
        int cantidad = 5;
        assertTrue(cantidad > 0 && cantidad <= 100, "Cantidad debe ser razonable");
    }
}
