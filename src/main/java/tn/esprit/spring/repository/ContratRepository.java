package tn.esprit.spring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Insurance;
import tn.esprit.spring.entities.Visit;


@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {



    public Contrat findByAnnouncementIdAnnouncement(long idAnoncement);
    public Contrat findByInsuranceIdInsurrance(long idInsurance);
    public  Contrat findByIdContrat (long id);
}
