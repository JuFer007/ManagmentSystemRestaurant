package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Model.ClasesGestion.Pedido;
import com.app.SystemRestaurant.Repository.ClienteRepository;
import com.app.SystemRestaurant.Repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    //Agregar cliente
    public Cliente agregarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    //Listar cliente
    public List<Cliente> listarCliente() {
        return clienteRepository.findAll();
    }

    //Buscar cliente por ID
    public Optional<Cliente> buscarPorId(Integer id) {
        return clienteRepository.findById(id);
    }

    //Verificar si existe un cliente por DNI
    public boolean existePorDni(String dni) {
        return clienteRepository.findByDniCliente(dni).isPresent();
    }
    
    //Actualizar cliente
    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepository.findById(cliente.getIdCliente())
        .map(clienteExistente -> {
            clienteExistente.setDniCliente(cliente.getDniCliente());
            clienteExistente.setNombreCliente(cliente.getNombreCliente());
            clienteExistente.setApellidosCliente(cliente.getApellidosCliente());
            return clienteRepository.save(clienteExistente);
        }).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id: " + cliente.getIdCliente()));
    }

    //Buscar cliente por nombre
    public List<Cliente> buscarClientePorNombre(String termino) {
        return clienteRepository.findByNombreClienteContainingIgnoreCaseOrApellidosClienteContainingIgnoreCase(termino, termino);
    }

    //Crear cliente con nombre completo
    public Cliente crearClienteDesdeTexto(String textoCompleto) {
        String[] palabras = textoCompleto.trim().split("\\s+");

        Cliente nuevoCliente = new Cliente();

        if (palabras.length == 1) {
            nuevoCliente.setNombreCliente(palabras[0]);
            nuevoCliente.setApellidosCliente("");
        } else {
            nuevoCliente.setNombreCliente(palabras[0]);
            nuevoCliente.setApellidosCliente(String.join(" ",
            java.util.Arrays.copyOfRange(palabras, 1, palabras.length)));
        }
        return clienteRepository.save(nuevoCliente);
    }

    public Cliente actualizarDniPorPedido(int idPedido, String nuevoDni) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        Cliente cliente = pedido.getIdCliente();

        if (cliente == null) {
            throw new EntityNotFoundException("El pedido no tiene cliente asociado");
        }

        cliente.setDniCliente(nuevoDni);
        return clienteRepository.save(cliente);
    }
}
