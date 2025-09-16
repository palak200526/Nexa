package Nexa.example.Nexa.service;

import Nexa.example.Nexa.model.Marks;
import Nexa.example.Nexa.repository.MarksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksService {

    private final MarksRepository repo;

    public MarksService(MarksRepository repo) {
        this.repo = repo;
    }

    public Marks saveMarks(Marks marks) {
        return repo.save(marks);
    }

    public List<Marks> getMarksByGroupAndSubject(Long groupId, Long subjectId) {
        return repo.findByGroupIdAndSubjectId(groupId, subjectId);
    }

    public List<Marks> getMarksByStudent(Long studentId) {
        return repo.findByStudentId(studentId);
    }

    public void deleteMarks(Long id) {
        repo.deleteById(id);
    }
}
