package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.CategorieAnnouncement;
import tn.esprit.spring.entities.Visit;

import java.util.List;


@Repository
public interface AnnoucementRepository extends JpaRepository<Announcement, Long> {


    //JPQL:
    @Query("SELECT a FROM Announcement a WHERE a.etat = true ")
    List<Announcement> retrieveAnnouncementbyEtat(boolean etat);

    //trie des annonces par leurs scores
    @Query("SELECT a FROM Announcement a ORDER BY a.score DESC")
    List<Announcement> findAllByOrderByScoreDesc();

    //This query uses JPQL to select the distinct categories from the Announcement table
    @Query("SELECT DISTINCT a.categorieAnnouncement FROM Announcement a")
    List<CategorieAnnouncement> findDistinctCategories();

    //keywords
    List<Announcement> findByCategorieAnnouncementOrderByScoreDesc(CategorieAnnouncement categorieAnnouncement);


    // added by ahmed ben hadid
    public Announcement findByIdAnnouncement (long id);


}
