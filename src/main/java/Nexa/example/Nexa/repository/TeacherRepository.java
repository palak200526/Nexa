package Nexa.example.Nexa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Nexa.example.Nexa.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);
}
