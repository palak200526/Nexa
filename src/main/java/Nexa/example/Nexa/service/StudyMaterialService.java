package Nexa.example.Nexa.service;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.StudyMaterial;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.repository.StudyMaterialRepository;
import Nexa.example.Nexa.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyMaterialService {

    private final StudyMaterialRepository repo;

    public StudyMaterialService(StudyMaterialRepository repo) {
        this.repo = repo;
    }

    public StudyMaterial saveMaterial(StudyMaterial material) {
        return repo.save(material);
    }

    public List<StudyMaterial> getMaterialsByGroup(Long groupId) {
        return repo.findByGroupId(groupId);
    }

    public void deleteMaterial(Long id) {
        repo.deleteById(id);
    }

    @Autowired
    private StudyMaterialRepository materialRepo;
    @Autowired private GroupRepository groupRepo;
    @Autowired private TeacherRepository teacherRepo;

    public StudyMaterial upload(Long teacherId, Long groupId, StudyMaterial material) {
        Teacher teacher = teacherRepo.findById(teacherId).orElseThrow();
        Group group = groupRepo.findById(groupId).orElseThrow();

        material.setUploadedBy(teacher);
        material.setGroup(group);
        return materialRepo.save(material);
    }
}
