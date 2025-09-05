package Nexa.example.Nexa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Nexa.example.Nexa.model.User;
import org.springframework.stereotype.Repository;
@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
