package Nexa.example.Nexa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import Nexa.example.Nexa.service.AdminService;

@Controller
@RequestMapping("/admin/assign")
public class AdminAssignController {

    @Autowired private GroupRepository groupRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private AdminService adminService;

    @GetMapping
    public String showAssignForm(Model model) {
        List<Group> groups = groupRepository.findAll();
        List<Teacher> teachers = teacherRepository.findAll();
        model.addAttribute("groups", groups);
        model.addAttribute("teachers", teachers);
        return "assign_group";
    }

    @PostMapping
    public String assign(@RequestParam Long groupId, @RequestParam Long teacherId, Model model) {
        adminService.assignTeacherToGroup(groupId, teacherId);
        model.addAttribute("success", "Assigned successfully");
        return "redirect:/admin/assign";
    }
}


