package Nexa.example.Nexa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Nexa.example.Nexa.model.Subject;
import Nexa.example.Nexa.service.SubjectService;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    
    @Autowired
    private SubjectService subjectService;
    
    // List all subjects
    @GetMapping
    public String listSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "subjects";
    }
    
    // List subjects by year
    @GetMapping("/year/{year}")
    public String listSubjectsByYear(@PathVariable Integer year, Model model) {
        model.addAttribute("subjects", subjectService.getSubjectsByYear(year));
        model.addAttribute("selectedYear", year);
        return "subjects";
    }
    
    // Show form to add new subject
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject_form";
    }
    
    // Save subject
    @PostMapping("/save")
    public String saveSubject(@ModelAttribute Subject subject, Model model) {
        try {
            subjectService.saveSubject(subject);
            return "redirect:/subjects";
        } catch (Exception ex) {
            model.addAttribute("subject", subject);
            model.addAttribute("error", ex.getMessage());
            return "subject_form";
        }
    }
    
    // Edit subject
    @GetMapping("/edit/{id}")
    public String editSubject(@PathVariable Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        model.addAttribute("subject", subject);
        return "subject_form";
    }
    
    // Delete subject
    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }
    
    // Search subjects by year and name
    @GetMapping("/search")
    public String searchSubjects(@RequestParam(required = false) Integer year,
                                @RequestParam(required = false) String name,
                                Model model) {
        List<Subject> subjects;
        if (year != null && name != null && !name.trim().isEmpty()) {
            subjects = subjectService.searchSubjectsByYearAndName(year, name);
        } else if (year != null) {
            subjects = subjectService.getSubjectsByYear(year);
        } else {
            subjects = subjectService.getAllSubjects();
        }
        
        model.addAttribute("subjects", subjects);
        model.addAttribute("selectedYear", year);
        model.addAttribute("searchName", name);
        return "subjects";
    }
    
}
