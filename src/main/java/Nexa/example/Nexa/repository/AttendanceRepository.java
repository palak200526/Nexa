package Nexa.example.Nexa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Nexa.example.Nexa.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
}
