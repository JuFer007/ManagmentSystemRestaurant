package com.app.SystemRestaurant.Service;

import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Repository.EmpleadoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Empleado> buscarPorId(Integer id){
        return empleadoRepository.findById(id);
    }

    //BUSCAR EMPLEADO POR DNI
    public Empleado buscarPorDni(String dni) {
        return empleadoRepository.findByDniEmpleado(dni).orElse(null);
    }

    //Actualizar
    public Empleado actualizarEmpleado(Empleado empleado){
        return  empleadoRepository.findById(empleado.getIdEmpleado())
                .map(Eexistentes ->{
                    Eexistentes.setDniEmpleado(empleado.getDniEmpleado());
                    Eexistentes.setNombreEmpleado(empleado.getNombreEmpleado());
                    Eexistentes.setApellidoPaternoEmpleado(empleado.getApellidoPaternoEmpleado());
                    Eexistentes.setApellidoMaternoEmpleado(empleado.getApellidoMaternoEmpleado());
                    Eexistentes.setCargoEmpleado(empleado.getCargoEmpleado());
                    Eexistentes.setSalarioEmpleado(empleado.getSalarioEmpleado());
                    Eexistentes.setEstadoEmpleado(empleado.getEstadoEmpleado());
                    return empleadoRepository.save(Eexistentes);
                }).orElseThrow(()-> new EntityNotFoundException("Empleado no encontrado con id: " + empleado.getIdEmpleado()));
    }

}
