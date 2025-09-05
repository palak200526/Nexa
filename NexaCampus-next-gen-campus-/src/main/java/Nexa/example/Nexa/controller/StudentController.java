package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Mentor;
import Nexa.example.Nexa.model.Student;
import Nexa.example.Nexa.repository.MentorRepository;
import Nexa.example.Nexa.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepo;
    private final MentorRepository mentorRepo;

    public StudentController(StudentRepository studentRepo, MentorRepository mentorRepo) {
        this.studentRepo = studentRepo;
        this.mentorRepo = mentorRepo;
    }

    // List all students
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentRepo.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    // Show form to create new student
    @GetMapping("/new")
    public String newStudent(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("mentors", mentorRepo.findAll());
        return "student_form";
    }

    // Save (create or update) student
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        // If the form bound a mentor id only, fetch the Mentor entity to attach fully
        if (student.getMentor() != null && student.getMentor().getId() != null) {
            Long mentorId = student.getMentor().getId();
            Mentor m = mentorRepo.findById(mentorId).orElse(null);
            student.setMentor(m);
        } else {
            student.setMentor(null);
        }

        studentRepo.save(student);
        return "redirect:/students";
    }

    // Edit: load existing student into the same form
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student s = studentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
        model.addAttribute("student", s);
        model.addAttribute("mentors", mentorRepo.findAll());
        return "student_form";
    }

    // Delete (POST to avoid accidental GET deletions)
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentRepo.deleteById(id);
        return "redirect:/students";
    }
}
