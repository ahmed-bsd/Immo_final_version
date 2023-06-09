package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.PromoCodeAnnouncement;

import java.util.Optional;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCodeAnnouncement, Long> {

    Optional<PromoCodeAnnouncement> findByCode(String code);
}
