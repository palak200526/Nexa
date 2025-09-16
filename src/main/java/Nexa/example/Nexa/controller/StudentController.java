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

import Nexa.example.Nexa.model.Marks;
import Nexa.example.Nexa.model.Student;
import Nexa.example.Nexa.model.StudyMaterial;
import Nexa.example.Nexa.model.TeacherSubjectGroup;
import Nexa.example.Nexa.repository.AttendanceRepository;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.MarksRepository;
import Nexa.example.Nexa.repository.MentorRepository;
import Nexa.example.Nexa.repository.StudyMaterialRepository;
import Nexa.example.Nexa.service.StudentService;
import Nexa.example.Nexa.service.TeacherSubjectGroupService;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired private AttendanceRepository attendanceRepository;
    @Autowired private MarksRepository marksRepository;
    @Autowired private StudyMaterialRepository materialRepository;
    @Autowired private MentorRepository mentorRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private TeacherSubjectGroupService teacherSubjectGroupService;
    // List all students
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }
    
    // List students by year
    @GetMapping("/year/{year}")
    public String listStudentsByYear(@PathVariable Integer year, Model model) {
        model.addAttribute("students", studentService.getStudentsByYear(year));
        model.addAttribute("selectedYear", year);
        return "students";
    }
    
    // Search students by year and name
    @GetMapping("/search")
    public String searchStudents(@RequestParam(required = false) Integer year,
                                @RequestParam(required = false) String name,
                                Model model) {
        List<Student> students;
        if (year != null && name != null && !name.trim().isEmpty()) {
            students = studentService.searchStudentsByYearAndName(year, name);
        } else if (year != null) {
            students = studentService.getStudentsByYear(year);
        } else if (name != null && !name.trim().isEmpty()) {
            students = studentService.searchStudentsByName(name);
        } else {
            students = studentService.getAllStudents();
        }
        
        model.addAttribute("students", students);
        model.addAttribute("selectedYear", year);
        model.addAttribute("searchName", name);
        return "students";
    }

    // Show form to create new student
    @GetMapping("/new")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("mentors", mentorRepository.findAll());
        model.addAttribute("groups", groupRepository.findAll());
        return "student_form";
    }


    // Save student
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student, Model model) {
        String generatedPassword = studentService.addStudent(student);

        model.addAttribute("message",
                "✅ Student added successfully! Generated password: " + generatedPassword);
        return "student_success";
    }

    // Edit student
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("mentors", mentorRepository.findAll());
        model.addAttribute("groups", groupRepository.findAll());
        return "student_form";
    }

    // Delete student
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    // ------------------- STUDENT SELF VIEWS -------------------
    @GetMapping("/{studentId}/attendance")
    public String myAttendance(@PathVariable Long studentId, Model model) {
        model.addAttribute("attendanceList", attendanceRepository.findByStudentId(studentId));
        return "student_attendance";
    }

    @GetMapping("/{studentId}/marks")
    public String myMarks(@PathVariable Long studentId, Model model) {
        List<Marks> marks = marksRepository.findByStudentId(studentId);
        model.addAttribute("marks", marks);
        return "student_marks";
    }

    @GetMapping("/{studentId}/materials")
    public String myMaterials(@PathVariable Long studentId, Model model) {
        Student s = studentService.getStudentById(studentId);
        Long groupId = s.getGroup() != null ? s.getGroup().getId() : null;
        List<StudyMaterial> materials = groupId == null ? java.util.List.of() : materialRepository.findByGroupId(groupId);
        model.addAttribute("materials", materials);
        model.addAttribute("student", s);
        return "study_materials";
    }
    
    @GetMapping("/{studentId}/subjects")
    public String mySubjects(@PathVariable Long studentId, Model model) {
        Student student = studentService.getStudentById(studentId);
        Long groupId = student.getGroup() != null ? student.getGroup().getId() : null;
        List<TeacherSubjectGroup> subjectAssignments = groupId == null ? 
            java.util.List.of() : teacherSubjectGroupService.getAssignmentsByGroup(groupId);
        model.addAttribute("subjectAssignments", subjectAssignments);
        model.addAttribute("student", student);
        return "student_subjects";
    }
}
