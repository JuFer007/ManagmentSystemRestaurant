package com.app.SystemRestaurant.Service;
import com.app.SystemRestaurant.Model.ClasesEmpleados.Usuario;
import com.app.SystemRestaurant.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UsuarioService {
    private final UsuarioRepository usurioRepository;

    public boolean login(String nombreUsuario, String contraseña) {
        return usurioRepository.findByNombreUsuario(nombreUsuario).map(usuario -> usuario.getContraseñaUsuario().equals(contraseña)).orElse(false);
    }

    public Usuario getUserByNombre(String nombreUsuario) {
        return usurioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
    }
}
