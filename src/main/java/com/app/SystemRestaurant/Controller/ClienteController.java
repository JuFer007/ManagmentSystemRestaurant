package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cliente;
import com.app.SystemRestaurant.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/clientes")

public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarCliente();
        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new Cliente());
        return "system";
    }

    @GetMapping("/editar/{dni}")
    @ResponseBody
    public String editarCliente(@PathVariable String dni, Model model) {
        Cliente cliente = clienteService.buscarPorDni(dni);
        if (cliente == null) {
            cliente = new Cliente();
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clienteService.listarCliente());
        return "system";
    }

    @PostMapping("/guardar")
    public String guardarOActualizarCliente(@ModelAttribute Cliente cliente) {
        if (clienteService.existePorDni(cliente.getDniCliente())) {
            clienteService.actualizarCliente(cliente);
        } else {
            clienteService.agregarCliente(cliente);
        }
        return "redirect:/system";
    }
}
