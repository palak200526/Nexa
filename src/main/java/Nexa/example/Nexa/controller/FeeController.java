package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Fee;
import Nexa.example.Nexa.repository.FeeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fees")
public class FeeController {

    private final FeeRepository feeRepository;

    public FeeController(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    // Add new fee
    @PostMapping("/add")
    public Fee addFee(@RequestBody Fee fee) {
        return feeRepository.save(fee);
    }

    // Get all fees
    @GetMapping("/all")
    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    // Get fees by student ID
    @GetMapping("/student/{studentId}")
    public List<Fee> getFeesByStudent(@PathVariable String studentId) {
        return feeRepository.findByStudentId(studentId);
    }

    // Update fee payment status
    @PutMapping("/update/{id}")
    public Optional<Fee> updateFeeStatus(@PathVariable Long id, @RequestParam boolean paid) {
        Optional<Fee> feeOptional = feeRepository.findById(id);
        if (feeOptional.isPresent()) {
            Fee fee = feeOptional.get();
            fee.setPaid(paid);
            feeRepository.save(fee);
        }
        return feeOptional;
    }

    // Delete fee
    @DeleteMapping("/delete/{id}")
    public String deleteFee(@PathVariable Long id) {
        feeRepository.deleteById(id);
        return "Fee with id " + id + " deleted successfully";
    }
}
