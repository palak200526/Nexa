package Nexa.example.Nexa.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Mentor;
import Nexa.example.Nexa.model.Student;
import Nexa.example.Nexa.model.User;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.MentorRepository;
import Nexa.example.Nexa.repository.StudentRepository;
import Nexa.example.Nexa.repository.UserRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private UserRepository userRepository;

    // Add or update student + generate password + save to User
    public String addStudent(Student student) {
        // Prevent duplicate by email across users
        userRepository.findByEmail(student.getEmail()).ifPresent(u -> {
            throw new IllegalArgumentException("Email already in use");
        });

        if (student.getMentor() != null && student.getMentor().getId() != null) {
            Mentor m = mentorRepository.findById(student.getMentor().getId()).orElse(null);
            student.setMentor(m);
        } else {
            student.setMentor(null);
        }

        // Generate random password
        String generatedPassword = UUID.randomUUID().toString().substring(0, 8);
        student.setPassword(generatedPassword);

        // Save student
        studentRepository.save(student);

        // Save user
        User user = new User(student.getEmail(), generatedPassword, "STUDENT");
        userRepository.save(user);

        return generatedPassword;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public List<Student> getStudentsByYear(Integer year) {
        return studentRepository.findByYear(year);
    }
    
    public List<Student> searchStudentsByYearAndName(Integer year, String name) {
        return studentRepository.findByYearAndNameContainingIgnoreCase(year, name);
    }
    
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Integer> getDistinctYears() {
        return studentRepository.findDistinctYears();
    }
    

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id: " + id));
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Autowired private StudentRepository studentRepo;
    @Autowired private GroupRepository groupRepo;

    public Student registerStudent(Student student) {
        List<Group> groups = groupRepo.findAll();
        if (!groups.isEmpty()) {
            Group randomGroup = groups.get(new Random().nextInt(groups.size()));
            student.setGroup(randomGroup);
        }
        return studentRepo.save(student);
    }
}
