package Nexa.example.Nexa.service;

import Nexa.example.Nexa.model.StudyMaterial;
import Nexa.example.Nexa.repository.StudyMaterialRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;

@Service
public class StudyMaterialService {

    private final StudyMaterialRepository repo;

    @Value("${upload.dir}")
    private String uploadDir;

    public StudyMaterialService(StudyMaterialRepository repo) {
        this.repo = repo;
    }

    public StudyMaterial saveMaterial(StudyMaterial material, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            material.setFileUrl(filePath.toString());
        }
        return repo.save(material);
    }

    public List<StudyMaterial> getMaterialsByGroup(Long groupId) {
        return repo.findByGroupId(groupId);
    }

    public Optional<StudyMaterial> getById(Long id) {
        return repo.findById(id);
    }

    public void deleteMaterial(Long id) {
        repo.deleteById(id);
    }
}
