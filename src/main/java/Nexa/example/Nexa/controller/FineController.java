package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Fine;
import Nexa.example.Nexa.repository.FineRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fines")
public class FineController {

    private final FineRepository fineRepository;

    public FineController(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    // Add new fine
    @PostMapping("/add")
    public Fine addFine(@RequestBody Fine fine) {
        return fineRepository.save(fine);
    }

    // Get all fines
    @GetMapping("/all")
    public List<Fine> getAllFines() {
        return fineRepository.findAll();
    }

    // Get fines by student ID
    @GetMapping("/student/{studentId}")
    public List<Fine> getFinesByStudent(@PathVariable String studentId) {
        return fineRepository.findByStudentId(studentId);
    }

    // Update fine payment status
    @PutMapping("/update/{id}")
    public Optional<Fine> updateFineStatus(@PathVariable Long id, @RequestParam boolean paid) {
        Optional<Fine> fineOptional = fineRepository.findById(id);
        if (fineOptional.isPresent()) {
            Fine fine = fineOptional.get();
            fine.setPaid(paid);
            fineRepository.save(fine);
        }
        return fineOptional;
    }

    // Delete fine
    @DeleteMapping("/delete/{id}")
    public String deleteFine(@PathVariable Long id) {
        fineRepository.deleteById(id);
        return "Fine with id " + id + " deleted successfully";
    }
}
