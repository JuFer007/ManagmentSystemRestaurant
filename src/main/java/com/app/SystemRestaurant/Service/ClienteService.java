package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

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
}
