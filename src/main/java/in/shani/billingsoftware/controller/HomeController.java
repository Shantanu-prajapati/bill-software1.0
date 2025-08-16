package in.shani.billingsoftware.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login"; // 🔹 Redirect root URL to /login
    }
}
