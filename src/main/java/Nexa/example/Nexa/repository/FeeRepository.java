package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findByStudentId(String studentId);
}
