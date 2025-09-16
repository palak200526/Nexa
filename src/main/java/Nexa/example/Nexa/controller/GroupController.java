package Nexa.example.Nexa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.model.TeacherSubjectGroup;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import Nexa.example.Nexa.service.GroupService;
import Nexa.example.Nexa.service.TeacherSubjectGroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherSubjectGroupService teacherSubjectGroupService;

    // List all groups with students
    @GetMapping
    public String listGroups(Model model) {
        List<Group> groups = groupRepository.findAllWithStudents();
        model.addAttribute("groups", groups);
        return "groups";
    }

    @GetMapping("/new")
    public String showGroupForm(Model model) {
        model.addAttribute("group", new Group());         // Empty Group object
        model.addAttribute("teachers", teacherRepository.findAll()); // List of teachers
        return "group_form";                              // Thymeleaf template
    }

    @PostMapping("/save")
    public String saveGroup(@RequestParam String name,
                            @RequestParam Long teacherId) {
        Group group = new Group();
        group.setName(name);
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        group.setTeacher(teacher);
        groupService.saveGroup(group);                   // Save to DB
        return "redirect:/groups";                        // Go back to group list
    }


    @GetMapping("/edit/{id}")
    public String editGroup(@PathVariable Long id, Model model) {
        Group group = groupService.getGroup(id);
        model.addAttribute("group", group);
        model.addAttribute("teachers", teacherRepository.findAll());
        return "group_form";
    }

    @PostMapping("/update/{id}")
    public String updateGroup(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam Long teacherId) {
        Group group = groupService.getGroup(id);
        group.setName(name);
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        group.setTeacher(teacher);

        groupService.saveGroup(group);
        return "redirect:/groups";
    }

    @PostMapping("/delete/{id}")
    public String deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/subjects")
    public String viewGroupSubjects(@PathVariable Long id, Model model) {
        Group group = groupService.getGroup(id);
        List<TeacherSubjectGroup> assignments = teacherSubjectGroupService.getAssignmentsByGroup(id);
        model.addAttribute("group", group);
        model.addAttribute("assignments", assignments);
        return "group_subjects";
    }
}
