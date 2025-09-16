package Nexa.example.Nexa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public String adminDashboard(Model model) {
        return "admin_dashboard";
    }

    @PostMapping("/assign/{groupId}/{teacherId}")
    public ResponseEntity<Group> assignGroupToTeacher(@PathVariable Long groupId,
                                                      @PathVariable Long teacherId) {
        return ResponseEntity.ok(adminService.assignTeacherToGroup(groupId, teacherId));
    }
}
