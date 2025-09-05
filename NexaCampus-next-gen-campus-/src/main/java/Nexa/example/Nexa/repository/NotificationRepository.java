package Nexa.example.Nexa.repository;

import Nexa.example.Nexa.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
