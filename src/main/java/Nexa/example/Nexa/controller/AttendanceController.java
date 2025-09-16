import Nexa.example.Nexa.model.Attendance;
import Nexa.example.Nexa.repository.AttendanceRepository;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.StudentRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepo;
    private final StudentRepository studentRepo;
    private final TeacherRepository teacherRepo;
    private final GroupRepository groupRepo;

    public AttendanceController(AttendanceRepository attendanceRepo,
                                StudentRepository studentRepo,
                                TeacherRepository teacherRepo,
                                GroupRepository groupRepo) {
        this.attendanceRepo = attendanceRepo;
        this.studentRepo = studentRepo;
        this.teacherRepo = teacherRepo;
        this.groupRepo = groupRepo;
    }

    // 1️⃣ Show all groups for teacher to select
    @GetMapping("/groups")
    public String selectGroup(Model model) {
        model.addAttribute("groups", groupRepo.findAll());
        model.addAttribute("teachers", teacherRepo.findAll()); // add teachers for selection
        return "attendance_select_group";
    }

    // 2️⃣ Show students in a group with previous attendance
    @GetMapping("/group/{groupId}/mark")
    public String markAttendanceForm(@PathVariable Long groupId,
                                     @RequestParam Long teacherId,
                                     Model model) {
        var group = groupRepo.findById(groupId).orElseThrow();
        var students = group.getStudents(); // all students in the group

        var attendanceList = attendanceRepo.findAll()
                .stream()
                .filter(a -> a.getGroup().getId().equals(groupId)
                        && a.getTeacher().getId().equals(teacherId))
                .toList();

        model.addAttribute("group", group);
        model.addAttribute("students", students);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("attendanceList", attendanceList);

        return "attendance_mark";
    }

    // 3️⃣ Save attendance
    @PostMapping("/group/{groupId}/save")
    public String saveAttendance(@PathVariable Long groupId,
                                 @RequestParam Long teacherId,
                                 @RequestParam(required = false) List<Long> presentStudents) {
        var group = groupRepo.findById(groupId).orElseThrow();
        var teacher = teacherRepo.findById(teacherId).orElseThrow();

        for (var student : group.getStudents()) {
            Attendance att = new Attendance();
            att.setGroup(group);
            att.setTeacher(teacher);
            att.setStudent(student);
            att.setDate(LocalDate.now());
            att.setPresent(presentStudents != null && presentStudents.contains(student.getId()));
            attendanceRepo.save(att);
        }

        return "redirect:/attendance/groups";
    }
}
