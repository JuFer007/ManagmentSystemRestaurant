package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesGestion.Plato;
import com.app.SystemRestaurant.Repository.PlatoRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/system/platos")

public class PlatoController {
    @Autowired
    private PlatoRepository platoRepository;

    private static final String CARPETA_PLATOS = "imagenesPlatos/";

    //Guardar la imagen de un plato
    @PostMapping("/guardar")
    public Plato guardarPlato(@RequestParam("nombrePlato") String nombrePlato,
                              @RequestParam("precioPlato") Double precioPlato,
                              @RequestParam("disponibilidad") String disponibilidad,
                              @RequestParam(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        String imagenUrl = null;

        if (imagen != null && !imagen.isEmpty()) {
            Files.createDirectories(Paths.get(CARPETA_PLATOS));
            String extension = imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
            String nombreArchivo = nombrePlato.trim().toLowerCase().replaceAll("[^a-z0-9]", "_") + extension;
            Path rutaArchivo = Paths.get(CARPETA_PLATOS + nombreArchivo);
            Files.copy(imagen.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            imagenUrl = "imagenesPlatos/" + nombreArchivo;
        }

        Plato nuevoPlato = new Plato(null, nombrePlato, precioPlato, disponibilidad, imagenUrl);
        return platoRepository.save(nuevoPlato);
    }

    //Listar los platos
    @GetMapping("/listar")
    @ResponseBody
    public List<Plato> listarPlatos() {
        return platoRepository.findAll();
    }

    //Buscar plato por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPlato(@PathVariable Integer id) {
        Optional<Plato> plato = platoRepository.findById(id);
        if (plato.isPresent()) {
            return ResponseEntity.ok(plato.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plato no encontrado");
        }
    }

    //Cambiar estado de plato
    @PutMapping("/cambiarEstado/{id}")
    public ResponseEntity<?> cambiarEstadoPlato(@PathVariable Integer id,
                                                @RequestParam("disponibilidad") String nuevaDisponibilidad) {
        Optional<Plato> optPlato = platoRepository.findById(id);
        if (optPlato.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plato no encontrado");
        }

        Plato plato = optPlato.get();
        plato.setDisponibilidad(nuevaDisponibilidad);
        platoRepository.save(plato);

        return ResponseEntity.ok("Estado actualizado correctamente a: " + nuevaDisponibilidad);
    }

    //Actualizar o editar plato
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarPlato(@PathVariable Integer id,
                                             @RequestParam("nombrePlato") String nombrePlato,
                                             @RequestParam("precioPlato") Double precioPlato,
                                             @RequestParam("disponibilidad") String disponibilidad,
                                             @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        try {
            Optional<Plato> optPlato = platoRepository.findById(id);

            if (optPlato.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plato no encontrado");
            }

            Plato platoExistente = optPlato.get();
            platoExistente.setNombrePlato(nombrePlato);
            platoExistente.setPrecioPlato(precioPlato);
            platoExistente.setDisponibilidad(disponibilidad);

            if (imagen != null && !imagen.isEmpty()) {
                Files.createDirectories(Paths.get(CARPETA_PLATOS));
                String extension = imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
                String nombreArchivo = nombrePlato.trim().toLowerCase().replaceAll("[^a-z0-9]", "_") + extension;
                Path rutaArchivo = Paths.get(CARPETA_PLATOS + nombreArchivo);
                Files.copy(imagen.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
                platoExistente.setImagenURL("imagenesPlatos/" + nombreArchivo);
            }
            platoRepository.save(platoExistente);
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
