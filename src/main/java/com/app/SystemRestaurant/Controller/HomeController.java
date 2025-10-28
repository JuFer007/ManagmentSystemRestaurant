package com.app.SystemRestaurant.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // @GetMapping("/")
    // public String redirectToLogin() {
    //     return "redirect:/login";
    // }

    // @GetMapping("/login")
    // public String loginPage() {
    //     return "login";
    // }

    @GetMapping("/index")
    public String systemPage() {
        return "index";
    }
}
