package Nexa.example.Nexa.controller;

import Nexa.example.Nexa.model.Notification;
import Nexa.example.Nexa.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository repo;

    // ✅ List all notifications
    @GetMapping
    public String listNotifications(Model model) {
        List<Notification> notifications = repo.findAll();
        model.addAttribute("notifications", notifications);
        return "notifications"; // points to notifications.html
    }

    // ✅ Show form to add new notification
    @GetMapping("/new")
    public String newNotification(Model model) {
        model.addAttribute("notification", new Notification());
        return "notification_form"; // points to notification_form.html
    }

    // ✅ Save notification (new or updated)
    @PostMapping("/save")
    public String saveNotification(@ModelAttribute Notification notification) {
        repo.save(notification);
        return "redirect:/notifications";
    }

    // ✅ Edit existing notification
    @GetMapping("/edit/{id}")
    public String editNotification(@PathVariable Long id, Model model) {
        Notification notification = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notification Id:" + id));
        model.addAttribute("notification", notification);
        return "notification_form";
    }

    // ✅ Delete notification
    @PostMapping("/delete/{id}")
    public String deleteNotification(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/notifications";
    }
}
