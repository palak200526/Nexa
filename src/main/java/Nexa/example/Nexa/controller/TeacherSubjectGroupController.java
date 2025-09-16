package Nexa.example.Nexa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.model.TeacherSubjectGroup;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.SubjectRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import Nexa.example.Nexa.service.TeacherSubjectGroupService;

@Controller
@RequestMapping("/teacher-subject-groups")
public class TeacherSubjectGroupController {
    
    @Autowired
    private TeacherSubjectGroupService teacherSubjectGroupService;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private GroupRepository groupRepository;
    
    @GetMapping
    public String listAssignments(Model model) {
        model.addAttribute("assignments", teacherSubjectGroupService.getAllAssignments());
        return "teacher_subject_assignments";
    }
    
    @GetMapping("/new")
    public String showAssignmentForm(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("groups", groupRepository.findAll());
        model.addAttribute("assignment", new TeacherSubjectGroup());
        return "teacher_subject_assignment_form";
    }
    
    @PostMapping("/save")
    public String saveAssignment(@RequestParam Long teacherId, 
                                @RequestParam Long subjectId, 
                                @RequestParam Long groupId, 
                                Model model) {
        try {
            teacherSubjectGroupService.assignTeacherToSubjectGroup(teacherId, subjectId, groupId);
            return "redirect:/teacher-subject-groups";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("teachers", teacherRepository.findAll());
            model.addAttribute("subjects", subjectRepository.findAll());
            model.addAttribute("groups", groupRepository.findAll());
            model.addAttribute("error", ex.getMessage());
            return "teacher_subject_assignment_form";
        }
    }
    
    @PostMapping("/delete/{id}")
    public String deleteAssignment(@PathVariable Long id) {
        teacherSubjectGroupService.removeAssignment(id);
        return "redirect:/teacher-subject-groups";
    }
    
    @GetMapping("/by-group/{groupId}")
    public String getAssignmentsByGroup(@PathVariable Long groupId, Model model) {
        List<TeacherSubjectGroup> assignments = teacherSubjectGroupService.getAssignmentsByGroup(groupId);
        Group group = groupRepository.findById(groupId).orElse(null);
        model.addAttribute("assignments", assignments);
        model.addAttribute("group", group);
        return "group_subject_assignments";
    }
    
    @GetMapping("/by-teacher/{teacherId}")
    public String getAssignmentsByTeacher(@PathVariable Long teacherId, Model model) {
        List<TeacherSubjectGroup> assignments = teacherSubjectGroupService.getAssignmentsByTeacher(teacherId);
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        model.addAttribute("assignments", assignments);
        model.addAttribute("teacher", teacher);
        return "teacher_subject_assignments";
    }
}
