package Nexa.example.Nexa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.service.TeacherService;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    // List all teachers
    @GetMapping
    public String listTeachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teachers";
    }

    // Show form to add new teacher
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher_form";
    }

    // Save teacher
    @PostMapping("/save")
    public String saveTeacher(@ModelAttribute Teacher teacher, Model model) {
        try {
            String generatedPassword = teacherService.addTeacher(teacher);
            model.addAttribute("message",
                    "✅ Teacher added successfully! Generated password: " + generatedPassword);
            return "teacher_success";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("teacher", teacher);
            model.addAttribute("error", ex.getMessage());
            return "teacher_form";
        }
    }

    // Edit teacher
    @GetMapping("/edit/{id}")
    public String editTeacher(@PathVariable Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        return "teacher_form";
    }

    // Delete teacher
    @PostMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }
}
