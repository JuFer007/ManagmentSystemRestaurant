package com.app.SystemRestaurant.Controller;
import com.app.SystemRestaurant.DTO.LoginDTO;
import com.app.SystemRestaurant.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Controller

public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin(Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginDTO") LoginDTO loginDTO, HttpSession session, Model model) {
        boolean valido = usuarioService.login(loginDTO.getNombreUsuario(), loginDTO.getContraseña());
        if (valido) {
            session.setAttribute("usuarioLogueado", loginDTO.getNombreUsuario());
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}