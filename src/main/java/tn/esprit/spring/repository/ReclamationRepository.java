package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Reclamation;

import java.util.List;

///****    Developped by Ahmed bsd    ****////
@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

    List<Reclamation> findReclamationsByStatusIs(Reclamation.Status s);
    List<Reclamation> findReclamationsByStatusIsLike(Reclamation.Status s);

    //List<Reclamation> findReclamationsByUserAIs(Usea);

}
