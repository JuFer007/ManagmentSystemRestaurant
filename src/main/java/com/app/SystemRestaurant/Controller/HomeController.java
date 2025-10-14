package com.app.SystemRestaurant.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String mostrarIndex(Model model) {
        return "login";
    }

    @GetMapping("/system")
    public String mostrarSystem(Model model){
        return "system";
    }
}
