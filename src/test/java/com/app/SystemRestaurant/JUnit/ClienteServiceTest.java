package com.app.SystemRestaurant.JUnit;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Repository.ClienteRepository;
import com.app.SystemRestaurant.Service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAgregarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNombreCliente("Juan");
        cliente.setApellidosCliente("Pérez");

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente resultado = clienteService.agregarCliente(cliente);

        assertEquals("Juan", resultado.getNombreCliente());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testBuscarPorId() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));

        Optional<Cliente> encontrado = clienteService.buscarPorId(1);

        assertTrue(encontrado.isPresent());
        assertEquals(1, encontrado.get().getIdCliente());
    }
}
