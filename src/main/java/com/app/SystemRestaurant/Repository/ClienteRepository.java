package com.app.SystemRestaurant.Repository;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByDniCliente(String dniCliente);
    long count();
    List<Cliente> findByNombreClienteContainingIgnoreCaseOrApellidosClienteContainingIgnoreCase(String nombre, String apellido);
}
