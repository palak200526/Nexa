package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyMaterialRepository extends JpaRepository<StudyMaterial, Long> {
    List<StudyMaterial> findByGroupId(Long groupId);
    List<StudyMaterial> findByStudentId(String studentId);
}
