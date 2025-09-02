package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        return "teachers";
    }

    @PostMapping("/add") // <-- match the form action
    public String addTeacher(@ModelAttribute Teacher teacher) {
        teacherRepository.save(teacher);
        return "redirect:/teachers";
    }
}
