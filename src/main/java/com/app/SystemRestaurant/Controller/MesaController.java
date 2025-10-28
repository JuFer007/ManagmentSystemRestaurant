package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesGestion.Mesa;
import com.app.SystemRestaurant.Service.MesaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/mesa")
@AllArgsConstructor

public class MesaController {
    private final MesaService mesaService;

    @GetMapping("/numMesas")
    public ResponseEntity<List<Mesa>> cantidadMesas() {
        return ResponseEntity.ok(mesaService.cantidadMesas());
    }
}
