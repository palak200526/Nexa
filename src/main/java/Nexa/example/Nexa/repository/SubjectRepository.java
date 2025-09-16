package Nexa.example.Nexa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Nexa.example.Nexa.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    // Find subjects by year
    List<Subject> findByYear(Integer year);
    
    // Find subjects by year and name (for searching)
    List<Subject> findByYearAndNameContainingIgnoreCase(Integer year, String name);
    
    // Get all unique years
    @Query("SELECT DISTINCT s.year FROM Subject s ORDER BY s.year")
    List<Integer> findDistinctYears();
}
