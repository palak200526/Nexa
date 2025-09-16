package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.LostItem;
import Nexa.example.Nexa.repository.LostItemRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lostfound")
public class LostAndFoundController {

    @Autowired
    private LostItemRepository lostItemRepository;

    // 🟢 Students submit lost items (only form, no list)
    @PostMapping
    public String reportLostItem(@ModelAttribute LostItem item, HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) {
            return "redirect:/login"; // not logged in
        }

        item.setStudentId(studentId);
        item.setStatus("LOST");
        lostItemRepository.save(item);

        return "redirect:/student/dashboard"; // after reporting, go back to student dashboard
    }

    // 🟠 Mentors view Lost & Found list
    @GetMapping
    public String showLostFoundPage(Model model, HttpSession session) {
        Long mentorId = (Long) session.getAttribute("mentorId");
        if (mentorId == null) {
            return "redirect:/login"; // only mentors can see the list
        }

        model.addAttribute("lostItems", lostItemRepository.findAll());
        return "lostfound"; // mentors see all items
    }


    // Delete an item
    @GetMapping("/delete/{id}")
    public String deleteLostItem(@PathVariable Long id) {
        lostItemRepository.deleteById(id);
        return "redirect:/lostfound";
    }

    // ✅ Optional: JSON API for external/frontend use
    @ResponseBody
    @GetMapping("/api/all")
    public List<LostItem> getAllLostItems() {
        return lostItemRepository.findAll();
    }
}
