package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesGestion.Plato;
import com.app.SystemRestaurant.Service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/system/platos")

public class PlatoController {
    @Autowired
    private PlatoService platoService;

    @PostMapping("/guardar")
    public Plato guardarPlato(@RequestParam String nombrePlato,
                              @RequestParam Double precioPlato,
                              @RequestParam String disponibilidad,
                              @RequestParam(required = false) MultipartFile imagen) throws IOException {
        return platoService.guardarPlato(nombrePlato, precioPlato, disponibilidad, imagen);
    }

    @GetMapping("/listar")
    public List<Plato> listarPlatos() {
        return platoService.listarPlatos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPlato(@PathVariable Integer id) {
        Optional<Plato> plato = platoService.obtenerPlato(id);
        return plato.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<?> cambiarEstadoPlato(@PathVariable Integer id,
                                                @RequestParam("disponibilidad") String nuevaDisponibilidad) {
        Plato plato = platoService.cambiarEstadoPlato(id, nuevaDisponibilidad);
        if (plato == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plato no encontrado");
        return ResponseEntity.ok("Estado actualizado correctamente a: " + nuevaDisponibilidad);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPlato(@PathVariable Integer id,
                                             @RequestParam("nombrePlato") String nombrePlato,
                                             @RequestParam("precioPlato") Double precioPlato,
                                             @RequestParam("disponibilidad") String disponibilidad,
                                             @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            Plato plato = platoService.actualizarPlato(id, nombrePlato, precioPlato, disponibilidad, imagen);
            if (plato == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plato no encontrado");
            return ResponseEntity.ok("Plato actualizado correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la imagen del plato: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el plato: " + e.getMessage());
        }
    }
}
