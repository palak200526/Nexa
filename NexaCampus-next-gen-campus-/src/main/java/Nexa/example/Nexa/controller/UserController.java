package Nexa.example.Nexa.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import Nexa.example.Nexa.model.User;
import Nexa.example.Nexa.repository.UserRepository;

@Controller

public class UserController {
    @Autowired
    private UserRepository userRepository;

    // Show login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Show register page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Handle signup form
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        model.addAttribute("success", true);
        return "login"; // redirect to login after success
    }

    // Handle login form
    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            return "welcome"; // new page after login
        }
        model.addAttribute("error", true);
        return "login";}
}
