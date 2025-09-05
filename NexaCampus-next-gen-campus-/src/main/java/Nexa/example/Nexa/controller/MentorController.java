package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Mentor;
import Nexa.example.Nexa.repository.MentorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mentors")
public class MentorController {

    private final MentorRepository mentorRepo;

    public MentorController(MentorRepository mentorRepo) {
        this.mentorRepo = mentorRepo;
    }

    @GetMapping
    public String listMentors(Model model) {
        model.addAttribute("mentors", mentorRepo.findAll());
        return "mentors"; // thymeleaf template
    }

    @PostMapping("/add")
    public String addMentor(@ModelAttribute Mentor mentor) {
        mentorRepo.save(mentor);
        return "redirect:/mentors";
    }
}
