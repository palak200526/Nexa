package Nexa.example.Nexa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Nexa.example.Nexa.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Find students by year
    List<Student> findByYear(Integer year);

    // Find students by year and name (for searching)
    List<Student> findByYearAndNameContainingIgnoreCase(Integer year, String name);

    // Find students by name (for searching)
    List<Student> findByNameContainingIgnoreCase(String name);

    // Get all unique years
    @Query("SELECT DISTINCT s.year FROM Student s ORDER BY s.year")
    List<Integer> findDistinctYears();

    Optional<Student> findByEmail(String email);

}
