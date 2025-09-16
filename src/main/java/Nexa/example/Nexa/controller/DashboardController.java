package Nexa.example.Nexa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import Nexa.example.Nexa.repository.MentorRepository;
import Nexa.example.Nexa.repository.StudentRepository;
import Nexa.example.Nexa.service.GroupService;
import Nexa.example.Nexa.service.TeacherService;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private TeacherService teacherService;

    // ================= ADMIN DASHBOARD =================
    // Admin dashboard is handled by AdminController

    // Small DB check (optional, can remove later)
    @GetMapping("/test-db")
    @ResponseBody
    public String testDatabase() {
        try {
            long studentCount = studentRepository.count();
            long mentorCount = mentorRepository.count();
            return "Database connection successful! Students: " + studentCount + ", Mentors: " + mentorCount;
        } catch (Exception e) {
            return "Database connection failed: " + e.getMessage();
        }
    }

    // ================= TEACHER DASHBOARD =================
    @GetMapping("/teacher")
    public String teacherDashboard(Model model, HttpSession session) {
        Object emailObj = session.getAttribute("userEmail");
        if (emailObj == null) {
            model.addAttribute("groups", java.util.List.of());
            return "teacher_dashboard";
        }
        String email = emailObj.toString();

        try {
            Long teacherId = teacherService.getTeacherIdByEmail(email);
            var groups = groupService.getGroupsByTeacher(teacherId);
            var teacher = teacherService.getTeacherById(teacherId); // add this
            model.addAttribute("groups", groups);
            model.addAttribute("teacher", teacher); // important!
        } catch (Exception ex) {
            model.addAttribute("groups", java.util.List.of());
        }
        return "teacher_dashboard";
    }


    // ================= STUDENT DASHBOARD =================
//

    @GetMapping("/student")
    public String studentDashboard(Model model, HttpSession session) {
        Object emailObj = session.getAttribute("userEmail");
        if (emailObj == null) {
            return "redirect:/login";
        }
        String email = emailObj.toString();

        try {
            var student = studentRepository.findByEmail(email);
            if (student != null) {
                model.addAttribute("student", student);
            } else {
                model.addAttribute("error", "Student not found for email: " + email);
            }
        } catch (Exception ex) {
            model.addAttribute("error", "Error fetching student: " + ex.getMessage());
        }

        return "student_dashboard"; // student_dashboard.html
    }

}
