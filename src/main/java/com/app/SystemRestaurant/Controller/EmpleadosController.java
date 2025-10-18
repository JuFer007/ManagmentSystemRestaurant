package com.app.SystemRestaurant.Controller;

import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/system/empleados")
public class EmpleadosController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/listar")
    @ResponseBody
    public List<Empleado> listarEmpleados(){
        return empleadoService.listarEmpleado();
    }

    @PostMapping("/guardar")
    @ResponseBody
    public Empleado guardarEmpleado(@RequestBody Empleado empleado){
        return empleadoService.agregarEmpleado(empleado);
    }

    @GetMapping("/buscar/{dni}")
    @ResponseBody
    public Empleado buscarEmpleadoByDni(@PathVariable String dni){
        return empleadoService.buscarPorDni(dni);
    }
}