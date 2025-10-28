package com.app.SystemRestaurant.Controller;

import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
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

    @PostMapping("/actualizar")
    @ResponseBody
    public Empleado actualizarEmpleado(@RequestBody Empleado empleado) {
        return empleadoService.actualizarEmpleado(empleado);
    }

    @GetMapping("/buscar/{id}")
    @ResponseBody
    public Optional<Empleado> buscarEmpleadoById(@PathVariable int id){
        return empleadoService.buscarPorId(id);
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<String> cambiarEstadoEmpleado(@PathVariable Integer id,
                                                        @RequestParam("estadoEmpleado") String nuevoEstado){
        boolean actualizado = empleadoService.cambiarEstado(id, nuevoEstado);
        if (actualizado) {
            return ResponseEntity.ok("Estado del empleado actualizado correctamente.");
        }
        return ResponseEntity.notFound().build();
    }
}