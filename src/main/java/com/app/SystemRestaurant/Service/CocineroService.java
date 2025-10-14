package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Cocinero;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import com.app.SystemRestaurant.Repository.CocineroRepository;
import com.app.SystemRestaurant.Repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CocineroService {

    private final EmpleadoRepository empleadoRepository;
    private final CocineroRepository cocineroRepository;

    //Registrar cocinero y empleado al mismo tiempo
    public Cocinero registrarCocinero(Cocinero cocinero) {
        Empleado empleadoGuardado = empleadoRepository.save(cocinero.getEmpleado());
        cocinero.setEmpleado(empleadoGuardado);

        return cocineroRepository.save(cocinero);
    }

    //Obtener todos los cocineros
    public List<Cocinero> listarCocineros() {
        return cocineroRepository.findAll();
    }

    //Buscar cocinero por ID
    public Optional<Cocinero> obtenerCocineroPorId(int id) {
        return cocineroRepository.findById(id);
    }
}
