package Nexa.example.Nexa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.Subject;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.model.TeacherSubjectGroup;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.SubjectRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import Nexa.example.Nexa.repository.TeacherSubjectGroupRepository;

@Service
public class TeacherSubjectGroupService {
    
    @Autowired
    private TeacherSubjectGroupRepository teacherSubjectGroupRepository;
    
    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private GroupRepository groupRepository;
    
    public TeacherSubjectGroup assignTeacherToSubjectGroup(Long teacherId, Long subjectId, Long groupId) {
        // Check if assignment already exists
        if (teacherSubjectGroupRepository.existsByTeacherIdAndSubjectIdAndGroupId(teacherId, subjectId, groupId)) {
            throw new IllegalArgumentException("Teacher is already assigned to this subject for this group");
        }
        
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        
        TeacherSubjectGroup assignment = new TeacherSubjectGroup(teacher, subject, group);
        return teacherSubjectGroupRepository.save(assignment);
    }
    
    public List<TeacherSubjectGroup> getAssignmentsByGroup(Long groupId) {
        return teacherSubjectGroupRepository.findByGroupId(groupId);
    }
    
    public List<TeacherSubjectGroup> getAssignmentsByTeacher(Long teacherId) {
        return teacherSubjectGroupRepository.findByTeacherId(teacherId);
    }
    
    public List<TeacherSubjectGroup> getAssignmentsByTeacherAndGroup(Long teacherId, Long groupId) {
        return teacherSubjectGroupRepository.findByTeacherIdAndGroupId(teacherId, groupId);
    }
    
    public List<TeacherSubjectGroup> getAssignmentsBySubject(Long subjectId) {
        return teacherSubjectGroupRepository.findBySubjectId(subjectId);
    }
    
    public void removeAssignment(Long assignmentId) {
        teacherSubjectGroupRepository.deleteById(assignmentId);
    }
    
    public void removeAssignment(Long teacherId, Long subjectId, Long groupId) {
        TeacherSubjectGroup assignment = teacherSubjectGroupRepository
                .findByGroupIdAndSubjectId(groupId, subjectId);
        if (assignment != null && assignment.getTeacher().getId().equals(teacherId)) {
            teacherSubjectGroupRepository.delete(assignment);
        }
    }
    
    public List<TeacherSubjectGroup> getAllAssignments() {
        return teacherSubjectGroupRepository.findAll();
    }
    
    public Optional<TeacherSubjectGroup> getAssignmentById(Long id) {
        return teacherSubjectGroupRepository.findById(id);
    }
}
