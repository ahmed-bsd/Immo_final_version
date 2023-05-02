package tn.esprit.spring.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.Annulation;
import tn.esprit.spring.entities.Examen;
import tn.esprit.spring.entities.Visit;

import java.util.List;


@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    //LISITE DES VISITE ANNULATION=circulation
    @Query("SELECT v FROM Visit v WHERE v.annulation = 0  ")
    List<Visit> retrieveVisitbyCircuation();

    //LISITE DES VISITE ANNULATION=maladie
    @Query("SELECT v FROM Visit v WHERE v.annulation = 1 ")
    List<Visit> retrieveVisitbyMALADIE();

    @Query("SELECT v FROM Visit v WHERE v.annulation = 2  ")
    List<Visit> retrieveVisitbyUrgence();

    //nbr d'occurence pour chaque type d'annulation
  /**  @Query("SELECT v.annulation , COUNT(v.annulation) FROM visit v GROUP By v.annulation" )
    List<Integer> nbroccurence(Annulation a);**/


}
