package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.TokenConfirmation;
import tn.esprit.spring.entities.TokenResetpwd;

import java.time.LocalDateTime;
import java.util.Optional;

///****    Developped by Ahmed bsd    ****////
@Repository
public interface TokenResetRepository extends JpaRepository<TokenResetpwd, Long> {


    Optional<TokenResetpwd> findByToken(String token);
}
