package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/system/clientes")
@CrossOrigin(origins = "*")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public List<Cliente> listarClientes() {
        return clienteService.listarCliente();
    }

    @PostMapping("/guardar")
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return clienteService.agregarCliente(cliente);
    }

    @PostMapping("/actualizar")
    public Cliente actualizarCliente(@RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(cliente);
    }

    @GetMapping("/buscar/{id}")
    public Optional<Cliente> buscarPorId(@PathVariable int id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public List<Cliente> buscarClientes(@RequestParam String termino) {
        return clienteService.buscarClientePorNombre(termino);
    }

    @PostMapping("/crear-desde-texto")
    public Cliente crearClienteDesdeTexto(@RequestBody Map<String, String> datos) {
        String texto = datos.get("texto");
        return clienteService.crearClienteDesdeTexto(texto);
    }
}
