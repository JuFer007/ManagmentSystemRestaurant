package com.app.SystemRestaurant.EqualsVerifier;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class ClienteEqualsVerifierTest {

    @Test
    void testEqualsAndHashCode() {
        EqualsVerifier.forClass(Cliente.class)
                .withIgnoredFields("idCliente", "nombreCliente", "apellidosCliente")
                .verify();
    }
}
