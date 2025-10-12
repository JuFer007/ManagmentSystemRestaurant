package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    //Actualizar cliente por DNI
    public Cliente buscarPorDni(String dni) {
        return clienteRepository.findByDniCliente(dni).orElse(null);
    }

    //Verificar si existe un cliente por DNI
    public boolean existePorDni(String dni) {
        return clienteRepository.findByDniCliente(dni).isPresent();
    }

    //Actualizar nombre y apellido de cliente
    public Cliente actualizarCliente(Cliente cliente) {
        Cliente c = buscarPorDni(cliente.getDniCliente());
        if (c != null) {
            c.setNombreCliente(cliente.getNombreCliente());
            c.setApellidosCliente(cliente.getApellidosCliente());
            return clienteRepository.save(c);
        }
        return null;
    }
}
