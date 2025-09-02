package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
