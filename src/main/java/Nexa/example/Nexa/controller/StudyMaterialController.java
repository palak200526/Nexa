package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Group;
import Nexa.example.Nexa.model.StudyMaterial;
import Nexa.example.Nexa.model.Teacher;
import Nexa.example.Nexa.repository.GroupRepository;
import Nexa.example.Nexa.service.StudyMaterialService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/teacher/groups/{groupId}/materials")
public class StudyMaterialController {

    private final StudyMaterialService service;
    private final GroupRepository groupRepo;

    public StudyMaterialController(StudyMaterialService service,
                                   GroupRepository groupRepo) {
        this.service = service;
        this.groupRepo = groupRepo;
    }

    // Show all materials
    @GetMapping
    public String listMaterials(@PathVariable Long groupId, Model model) {
        Group group = groupRepo.findById(groupId).orElseThrow();
        List<StudyMaterial> materials = service.getMaterialsByGroup(groupId);
        model.addAttribute("group", group);
        model.addAttribute("materials", materials);
        return "materials"; // Thymeleaf page
    }

    // Upload material
    @PostMapping("/upload")
    public String uploadMaterial(@PathVariable Long groupId,
                                 @RequestParam String title,
                                 @RequestParam(required = false) String description,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        Group group = groupRepo.findById(groupId).orElseThrow();
        StudyMaterial material = new StudyMaterial();
        material.setTitle(title);
        material.setDescription(description);
        material.setGroup(group);
        material.setUploadedBy(group.getTeacher());

        service.saveMaterial(material, file);
        return "redirect:/teacher/groups/" + groupId + "/materials";
    }

    // Download material
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
        StudyMaterial material = service.getById(id).orElseThrow();
        Path path = Paths.get(material.getFileUrl());

        if (!path.toFile().exists()) {
            throw new RuntimeException("File not found: " + material.getFileUrl());
        }

        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // Delete material
    @PostMapping("/{id}/delete")
    public String deleteMaterial(@PathVariable Long groupId, @PathVariable Long id) {
        service.deleteMaterial(id);
        return "redirect:/teacher/groups/" + groupId + "/materials";
    }
}
