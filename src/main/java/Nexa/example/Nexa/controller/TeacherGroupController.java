package Nexa.example.Nexa.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Nexa.example.Nexa.model.Attendance;
import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Marks;
import Nexa.example.Nexa.model.Student;
import Nexa.example.Nexa.model.StudyMaterial;
import Nexa.example.Nexa.model.Subject;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.model.TeacherSubjectGroup;
import Nexa.example.Nexa.repository.AttendanceRepository;
import Nexa.example.Nexa.repository.MarksRepository;
import Nexa.example.Nexa.repository.StudentRepository;
import Nexa.example.Nexa.repository.StudyMaterialRepository;
import Nexa.example.Nexa.repository.SubjectRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import Nexa.example.Nexa.service.GroupService;
import Nexa.example.Nexa.service.TeacherSubjectGroupService;

@Controller
@RequestMapping("/teacher/groups")
public class TeacherGroupController {

    @Autowired private GroupService groupService;
    @Autowired private StudentRepository studentRepository;
    @Autowired private TeacherRepository teacherRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private AttendanceRepository attendanceRepository;
    @Autowired private StudyMaterialRepository materialRepository;
    @Autowired private MarksRepository marksRepository;
    @Autowired private TeacherSubjectGroupService teacherSubjectGroupService;

    // ------------------- LIST GROUPS -------------------
    @GetMapping
    public String listGroups(Model model) {
        Long teacherId = 1L; // TODO: replace with logged-in teacher (Spring Security)

        List<Group> groups = teacherSubjectGroupService.getAssignmentsByTeacher(teacherId)
                .stream()
                .map(TeacherSubjectGroup::getGroup)
                .distinct()
                .toList();

        model.addAttribute("groups", groups);
        return "teacher_groups";
    }

    // ------------------- VIEW STUDENTS -------------------
    @GetMapping("/{groupId}/students")
    public String groupStudents(@PathVariable Long groupId, Model model) {
        Group group = groupService.getGroup(groupId);
        model.addAttribute("group", group);
        model.addAttribute("students", group.getStudents());
        return "group_students";
    }

    // ------------------- MARK ATTENDANCE -------------------
    @PostMapping("/{groupId}/students/{studentId}/attendance")
    public String markAttendance(@PathVariable Long groupId,
                                 @PathVariable Long studentId,
                                 @RequestParam Long subjectId,
                                 @RequestParam boolean present) {

        Long teacherId = 1L; // TODO: replace with logged-in teacher
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

        Attendance attendance = new Attendance();
        attendance.setStudent(studentRepository.findById(studentId).get());
        attendance.setTeacher(teacher);
        attendance.setSubject(subjectRepository.findById(subjectId).get());
        attendance.setPresent(present);
        attendance.setDate(java.time.LocalDate.now());
        attendance.setGroup(groupService.getGroup(groupId));

        attendanceRepository.save(attendance);
        return "redirect:/teacher/groups/" + groupId + "/students";
    }

    // ------------------- STUDY MATERIALS -------------------
    @GetMapping("/{groupId}/materials")
    public String viewMaterials(@PathVariable Long groupId, Model model) {
        Group group = groupService.getGroup(groupId);
        List<StudyMaterial> materials = materialRepository.findByGroupId(groupId);
        model.addAttribute("group", group);
        model.addAttribute("materials", materials);
        return "study_materials";
    }

    @PostMapping("/{groupId}/materials/upload")
    public String uploadMaterial(@PathVariable Long groupId,
                                 @RequestParam String title,
                                 @RequestParam String description,
                                 @RequestParam MultipartFile file) throws IOException {

        Long teacherId = 1L; // TODO: replace with logged-in teacher
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

        StudyMaterial material = new StudyMaterial();
        Group group = groupService.getGroup(groupId);
        material.setGroup(group);
        material.setTitle(title);
        material.setDescription(description);

        material.setFileUrl("/uploads/" + file.getOriginalFilename());

        material.setUploadedBy(teacher);

        java.io.File uploadDir = new java.io.File("uploads");
        if (!uploadDir.exists()) uploadDir.mkdir();

        file.transferTo(new java.io.File(uploadDir, file.getOriginalFilename()));
        materialRepository.save(material);

        return "redirect:/teacher/groups/" + groupId + "/materials";
    }

    // ------------------- MARKS -------------------
    @GetMapping("/{groupId}/marks")
    public String viewMarks(@PathVariable Long groupId, Model model) {
        Long teacherId = 1L; // TODO: replace with logged-in teacher

        Group group = groupService.getGroup(groupId);
        List<Student> students = group.getStudents();

        // Get subjects assigned to this teacher for this group
        List<Subject> subjects = teacherSubjectGroupService
                .getAssignmentsByTeacherAndGroup(teacherId, groupId)
                .stream()
                .map(TeacherSubjectGroup::getSubject)
                .toList();

        model.addAttribute("group", group);
        model.addAttribute("students", students);
        model.addAttribute("subjects", subjects);
        return "marks";
    }

    @PostMapping("/{groupId}/marks/save")
    public String saveMarks(@PathVariable Long groupId,
                            @RequestParam Long studentId,
                            @RequestParam Long subjectId,
                            @RequestParam String examName,
                            @RequestParam double marksObtained) {

        Long teacherId = 1L; // TODO: replace with logged-in teacher
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

        Marks marks = new Marks();
        marks.setStudent(studentRepository.findById(studentId).get());
        marks.setSubject(subjectRepository.findById(subjectId).get());
        marks.setGroup(groupService.getGroup(groupId));
        marks.setTeacher(teacher);
        marks.setExamName(examName);
        marks.setMarksObtained(marksObtained);

        marksRepository.save(marks);
        return "redirect:/teacher/groups/" + groupId + "/marks";
    }

    // ------------------- DELETE STUDY MATERIAL -------------------
    @PostMapping("/{groupId}/materials/{materialId}/delete")
    public String deleteMaterial(@PathVariable Long groupId, @PathVariable Long materialId) {
        materialRepository.deleteById(materialId);
        return "redirect:/teacher/groups/" + groupId + "/materials";
    }
}
