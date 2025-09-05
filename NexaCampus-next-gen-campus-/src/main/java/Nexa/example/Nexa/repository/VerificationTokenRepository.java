package Nexa.example.Nexa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Nexa.example.Nexa.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>  {
    VerificationToken findByToken(String token);
}
