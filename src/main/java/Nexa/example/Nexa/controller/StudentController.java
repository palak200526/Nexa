package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Student;
import Nexa.example.Nexa.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", repo.findAll());
        return "students"; // thymeleaf page
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        repo.save(student);
        return "redirect:/students";
    }
}
