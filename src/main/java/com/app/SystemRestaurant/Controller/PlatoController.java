package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.Model.ClasesGestion.Plato;
import com.app.SystemRestaurant.Repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/platos")
public class PlatoController {

    @Autowired
    private PlatoRepository platoRepository;

    private static final String UPLOAD_DIR = "/imagenes";

    @GetMapping("/listar")
    public String mostrarPlatos(Model model) {
        List<Plato> lista = platoRepository.findAll();
        model.addAttribute("platos", lista);
        return "system";
    }

    @PostMapping("/agregar")
    public String agregarPlato(@RequestParam String nombrePlato,
                               @RequestParam Double precioPlato,
                               @RequestParam String disponibilidad,
                               @RequestParam("imagen") MultipartFile archivo) throws IOException {

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
        Path rutaArchivo = Paths.get(UPLOAD_DIR + nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        Plato plato = new Plato();
        plato.setNombrePlato(nombrePlato);
        plato.setPrecioPlato(precioPlato);
        plato.setDisponibilidad(disponibilidad);
        plato.setImagenURL("/imagenes/" + nombreArchivo);

        platoRepository.save(plato);
        return "redirect:/system/platos";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Plato obtenerPlato(@PathVariable int id) {
        return platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));
    }
}
