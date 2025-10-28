package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesGestion.Mesa;
import com.app.SystemRestaurant.Repository.MesaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor

public class MesaService {
    private final MesaRepository mesaRepository;

    public List<Mesa> cantidadMesas() {
        return mesaRepository.findAll();
    }
}
