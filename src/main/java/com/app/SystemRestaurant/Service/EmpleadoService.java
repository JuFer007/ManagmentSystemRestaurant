package com.app.SystemRestaurant.Service;

import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    @Autowired
    private final EmpleadoRepository empleadoRepository;

    //AGREGAR EMPLEADO
    public Empleado agregarEmpleado(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    //LISTAR CLIENTES
    public List<Empleado> listarEmpleado(){
        return empleadoRepository.findAll();
    }

    //BUSCAR EMPLEADO POR DNI
    public Empleado buscarPorDni(String dni) {
        return empleadoRepository.findByDniEmpleado(dni).orElse(null);
    }



}
