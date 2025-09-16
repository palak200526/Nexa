package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByStudentId(String studentId);
}
