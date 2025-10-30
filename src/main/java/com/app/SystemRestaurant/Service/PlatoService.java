package com.app.SystemRestaurant.Service;

import com.app.SystemRestaurant.Model.ClasesGestion.Plato;
import com.app.SystemRestaurant.Repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service

public class PlatoService {
    @Autowired
    private PlatoRepository platoRepository;

    private static final String CARPETA_PLATOS = "imagenesPlatos/";

    public Plato guardarPlato(String nombrePlato, Double precioPlato, String disponibilidad, MultipartFile imagen) throws IOException {
        String imagenUrl = null;

        if (imagen != null && !imagen.isEmpty()) {
            Files.createDirectories(Paths.get(CARPETA_PLATOS));
            String extension = imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
            String nombreArchivo = nombrePlato.trim().toLowerCase().replaceAll("[^a-z0-9]", "_") + extension;
            Path rutaArchivo = Paths.get(CARPETA_PLATOS + nombreArchivo);
            Files.copy(imagen.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            imagenUrl = CARPETA_PLATOS + nombreArchivo;
        }

        Plato nuevoPlato = new Plato(null, nombrePlato, precioPlato, disponibilidad, imagenUrl);
        return platoRepository.save(nuevoPlato);
    }

    public List<Plato> listarPlatos() {
        return platoRepository.findAll();
    }

    public Optional<Plato> obtenerPlato(Integer id) {
        return platoRepository.findById(id);
    }

    public Plato cambiarEstadoPlato(Integer id, String nuevaDisponibilidad) {
        Plato plato = platoRepository.findById(id).orElse(null);
        if (plato != null) {
            plato.setDisponibilidad(nuevaDisponibilidad);
            platoRepository.save(plato);
        }
        return plato;
    }

    public Plato actualizarPlato(Integer id, String nombrePlato, Double precioPlato, String disponibilidad, MultipartFile imagen) throws IOException {
        Plato platoExistente = platoRepository.findById(id).orElse(null);
        if (platoExistente != null) {
            platoExistente.setNombrePlato(nombrePlato);
            platoExistente.setPrecioPlato(precioPlato);
            platoExistente.setDisponibilidad(disponibilidad);

            if (imagen != null && !imagen.isEmpty()) {
                Files.createDirectories(Paths.get(CARPETA_PLATOS));
                String extension = imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
                String nombreArchivo = nombrePlato.trim().toLowerCase().replaceAll("[^a-z0-9]", "_") + extension;
                Path rutaArchivo = Paths.get(CARPETA_PLATOS + nombreArchivo);
                Files.copy(imagen.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);
                platoExistente.setImagenURL(CARPETA_PLATOS + nombreArchivo);
            }

            platoRepository.save(platoExistente);
        }
        return platoExistente;
    }
}
