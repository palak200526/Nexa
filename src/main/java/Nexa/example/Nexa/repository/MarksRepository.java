package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarksRepository extends JpaRepository<Marks, Long> {
    List<Marks> findByGroupIdAndSubjectId(Long groupId, Long subjectId);
    List<Marks> findByStudentId(Long studentId);
}
