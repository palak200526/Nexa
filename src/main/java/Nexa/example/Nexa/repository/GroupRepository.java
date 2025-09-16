package Nexa.example.Nexa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Nexa.example.Nexa.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    // Fetch groups along with students to avoid LazyInitializationException
    @Query("SELECT g FROM Group g LEFT JOIN FETCH g.students")
    List<Group> findAllWithStudents();

    List<Group> findByTeacherId(Long teacherId);
}
