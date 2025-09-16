package Nexa.example.Nexa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Nexa.example.Nexa.model.TeacherSubjectGroup;

@Repository
public interface TeacherSubjectGroupRepository extends JpaRepository<TeacherSubjectGroup, Long> {
    
    List<TeacherSubjectGroup> findByGroupId(Long groupId);
    
    List<TeacherSubjectGroup> findByTeacherId(Long teacherId);
    
    List<TeacherSubjectGroup> findBySubjectId(Long subjectId);
    
    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.group.id = :groupId AND tsg.subject.id = :subjectId")
    TeacherSubjectGroup findByGroupIdAndSubjectId(@Param("groupId") Long groupId, @Param("subjectId") Long subjectId);
    
    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.teacher.id = :teacherId AND tsg.group.id = :groupId")
    List<TeacherSubjectGroup> findByTeacherIdAndGroupId(@Param("teacherId") Long teacherId, @Param("groupId") Long groupId);
    
    @Query("SELECT tsg FROM TeacherSubjectGroup tsg WHERE tsg.teacher.id = :teacherId AND tsg.subject.id = :subjectId")
    List<TeacherSubjectGroup> findByTeacherIdAndSubjectId(@Param("teacherId") Long teacherId, @Param("subjectId") Long subjectId);
    
    boolean existsByTeacherIdAndSubjectIdAndGroupId(Long teacherId, Long subjectId, Long groupId);
}
