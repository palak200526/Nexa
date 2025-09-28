package Nexa.example.Nexa.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Nexa.example.Nexa.model.User;
import Nexa.example.Nexa.repository.UserRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    // Home page - show homepage
    @GetMapping("/")
    public String home() {
        return "homepage"; // returns homepage.html
    }

    // Show login form
    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login"; // returns login.html
    }

    // Handle login
    @PostMapping("/login")
    public String doLogin(@RequestParam String email,
                          @RequestParam String password,
                          Model model,
                          HttpSession session) {
        Optional<User> userOpt = userRepo.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (user.getPassword().equals(password)) {
                // Save session
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userRole", user.getRole());

                // Redirect based on role
                switch (user.getRole()) {
                    case "ADMIN":
                        return "redirect:/admin";
                    case "TEACHER":
                        return "redirect:/teacher";
                    case "STUDENT":
                        return "redirect:/student";
                    default:
                        model.addAttribute("error", "Invalid user role");
                        return "login";
                }
            }
        }

        // If login fails
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
