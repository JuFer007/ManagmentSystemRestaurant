package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Mesero;
import com.app.SystemRestaurant.Repository.EmpleadoRepository;
import com.app.SystemRestaurant.Repository.MeseroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeseroService{

    private final EmpleadoRepository empleadoRepository;
    private final MeseroRepository meseroRepository;

    //GUARDAR MESERO
    public Mesero registarMesero(Mesero mesero){
        Empleado meseroGuardado = empleadoRepository.save(mesero.getEmpleado());
        mesero.setEmpleado(meseroGuardado);
        return meseroRepository.save(mesero);
    }

    //LISTAR MESEROS
    public List<Mesero> listarMeseros(){
        return meseroRepository.findAll();
    }

    //ENCONTRAR MESERO POR ID
    public Optional<Mesero> encontrarMeseroPorId(Integer id) {
        return meseroRepository.findById(id);
    }

}
