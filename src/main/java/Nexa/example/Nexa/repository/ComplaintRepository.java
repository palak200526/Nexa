package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStudentId(Long studentId);

    List<Complaint> findByMentorId(Long mentorId);
}
