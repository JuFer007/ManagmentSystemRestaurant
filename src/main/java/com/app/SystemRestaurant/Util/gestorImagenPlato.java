package com.app.SystemRestaurant.Util;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

public class gestorImagenPlato {
    private static final String carpeta = "imagenesPlatos/";

    static {
        // Asegurarse de que el directorio de imágenes exista
        try {
            Files.createDirectories(Path.of(carpeta));
        } catch (IOException e) {
            System.err.println("No se pudo crear el directorio para las imágenes de los platos: " + e.getMessage());
        }
    }

//    public static void mostrarImagen(Modelo.ClasesGestion.Plato plato, ImageView imageView) {
//        if (plato.getRutaImagen() != null) {
//            File file = new File(carpeta + plato.getRutaImagen());
//            if (file.exists()) {
//                Image img = new Image(file.toURI().toString());
//                imageView.setImage(img);
//                return;
//            }
//        }
//        imageView.setImage(null);
//    }

    //Cambiar imagen de un plato
    public static void cambiarImagen(String nombrePlato, File nuevaImagen) throws Exception {
        if (nuevaImagen != null && nuevaImagen.exists()) {
            String nombreArchivo = nombrePlato.replaceAll("\\s+", "") + ".jpg";
            Path destino = Path.of(carpeta + nombreArchivo);
    
            Files.copy(
                    nuevaImagen.toPath(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } else {
            throw new NoSuchFileException("El archivo de imagen proporcionado no existe o es nulo.");
        }
    }
}
