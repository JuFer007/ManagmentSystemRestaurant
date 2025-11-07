package com.app.SystemRestaurant.UnitTest_ParameterizedTest;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Repository.ClienteRepository;
import com.app.SystemRestaurant.Service.ClienteService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class ClienteServiceParamTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    public ClienteServiceParamTest() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Pedro", "Maria Gomez", "Luis Alberto Torres"})
    void testCrearClienteDesdeTexto(String textoCompleto) {
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        Cliente cliente = clienteService.crearClienteDesdeTexto(textoCompleto);

        assert cliente.getNombreCliente() != null;
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }
}
