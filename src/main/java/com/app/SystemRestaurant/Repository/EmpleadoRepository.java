package com.app.SystemRestaurant.Repository;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
}
