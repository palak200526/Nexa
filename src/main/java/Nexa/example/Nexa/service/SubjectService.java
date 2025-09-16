package Nexa.example.Nexa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Nexa.example.Nexa.model.Subject;
import Nexa.example.Nexa.repository.SubjectRepository;

@Service
public class SubjectService {
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    
    public List<Subject> getSubjectsByYear(Integer year) {
        return subjectRepository.findByYear(year);
    }
    
    public List<Subject> searchSubjectsByYearAndName(Integer year, String name) {
        return subjectRepository.findByYearAndNameContainingIgnoreCase(year, name);
    }
    
    public List<Integer> getAllYears() {
        return subjectRepository.findDistinctYears();
    }
    
    
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }
    
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
    
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
    
}
