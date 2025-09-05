package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
