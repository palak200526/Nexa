package Nexa.example.Nexa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.TeacherRepository;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private GroupRepository groupRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    // Assign a teacher to a group
    public Group assignTeacherToGroup(Long groupId, Long teacherId) {
        // Fetch group and teacher, throw exception if not found
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        Teacher teacher = teacherRepo.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        // Assign teacher
        group.setTeacher(teacher);

        // Save updated group
        return groupRepo.save(group);
    }

    // Remove teacher from a group
    public Group removeTeacherFromGroup(Long groupId) {
        Group group = groupRepo.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        group.setTeacher(null);
        return groupRepo.save(group);
    }

    // Get all groups
    public List<Group> getAllGroups() {
        return groupRepo.findAll();
    }

    // Get all groups for a specific teacher
    public List<Group> getGroupsByTeacher(Long teacherId) {
        return groupRepo.findByTeacherId(teacherId);
    }
}
