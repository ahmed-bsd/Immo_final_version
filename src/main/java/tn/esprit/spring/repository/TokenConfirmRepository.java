package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.entities.TokenConfirmation;

import java.time.LocalDateTime;
import java.util.Optional;

///****    Developped by Ahmed bsd    ****////
@Repository
public interface TokenConfirmRepository extends JpaRepository<TokenConfirmation, Long> {


    Optional<TokenConfirmation> findByToken(String token);

}
