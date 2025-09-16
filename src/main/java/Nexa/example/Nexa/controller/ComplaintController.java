package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Complaint;
import Nexa.example.Nexa.repository.ComplaintRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintRepository complaintRepository;

    public ComplaintController(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    // Student submits a complaint
    @PostMapping("/submit")
    public Complaint submitComplaint(@RequestBody Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    // Student views their complaints
    @GetMapping("/student/{studentId}")
    public List<Complaint> getComplaintsByStudent(@PathVariable Long studentId) {
        return complaintRepository.findByStudentId(studentId);
    }

    // Mentor views complaints assigned to them
    @GetMapping("/mentor/{mentorId}")
    public List<Complaint> getComplaintsByMentor(@PathVariable Long mentorId) {
        return complaintRepository.findByMentorId(mentorId);
    }

    // Mentor resolves a complaint
    @PutMapping("/{complaintId}/resolve")
    public Complaint resolveComplaint(@PathVariable Long complaintId, @RequestParam String status) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
}
