package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.StudyMaterial;
import Nexa.example.Nexa.repository.StudyMaterialRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
// import java.util.Optional;

@RestController
@RequestMapping("/study-material")
public class StudyMaterialController {

    private final StudyMaterialRepository studyMaterialRepository;

    public StudyMaterialController(StudyMaterialRepository studyMaterialRepository) {
        this.studyMaterialRepository = studyMaterialRepository;
    }

    // Upload new study material
    @PostMapping("/add")
    public StudyMaterial addMaterial(@RequestBody StudyMaterial material) {
        return studyMaterialRepository.save(material);
    }

    // Get all study materials
    @GetMapping("/all")
    public List<StudyMaterial> getAllMaterials() {
        return studyMaterialRepository.findAll();
    }

    // Get study materials by student ID
    @GetMapping("/student/{studentId}")
    public List<StudyMaterial> getMaterialsByStudent(@PathVariable String studentId) {
        return studyMaterialRepository.findByStudentId(studentId);
    }

    // Delete study material
    @DeleteMapping("/delete/{id}")
    public String deleteMaterial(@PathVariable Long id) {
        studyMaterialRepository.deleteById(id);
        return "Study material with id " + id + " deleted successfully";
    }
}
