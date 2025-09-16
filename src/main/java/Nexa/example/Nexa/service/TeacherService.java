package Nexa.example.Nexa.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Student;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.model.User;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import Nexa.example.Nexa.repository.UserRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private GroupRepository groupRepo;

    // Add a new teacher + generate password + create user
    public String addTeacher(Teacher teacher) {
        // Prevent duplicate by email across users
        userRepo.findByEmail(teacher.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email already in use");
        });

        // Generate random password
        String generatedPassword = UUID.randomUUID().toString().substring(0, 8);
        teacher.setPassword(generatedPassword);

        // Save teacher
        teacherRepo.save(teacher);

        // Create User for login
        User user = new User(teacher.getEmail(), generatedPassword, "TEACHER");
        userRepo.save(user);

        return generatedPassword;
    }

    // Get all teachers
    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    // Get Teacher object by email
    public Teacher findByEmail(String email) {
        return teacherRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found for email: " + email));
    }


    // Get teacher by ID
    public Teacher getTeacherById(Long id) {
        return teacherRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID"));
    }

    // Delete teacher
    public void deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
    }

    // Get all students for a teacher
    public List<Student> getMyStudents(Long teacherId) {
        List<Group> groups = groupRepo.findByTeacherId(teacherId);
        return groups.stream()
                .flatMap(g -> g.getStudents().stream())
                .toList();
    }

    // Get teacher ID by email
    public Long getTeacherIdByEmail(String email) {
        return teacherRepo.findByEmail(email)
                .map(Teacher::getId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found for email"));
    }
}
