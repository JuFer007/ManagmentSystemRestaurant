package com.app.SystemRestaurant.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/system")
    public String mostrarIndex(Model model) {
        return "system";
    }
}
