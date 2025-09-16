package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LostItemRepository extends JpaRepository<LostItem, Long> {
    List<LostItem> findByStudentId(Long studentId);

    List<LostItem> findByMentorId(Long mentorId);
}
