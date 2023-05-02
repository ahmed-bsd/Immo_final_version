package tn.esprit.spring.serviceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.CategorieAnnouncement;
import tn.esprit.spring.entities.Visit;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface IAnnouncementService {

    public Announcement addAnnouncement(Announcement announcement, Principal principal);
    public Announcement addAndAssignAnnouncementToContrat(Announcement announcement , Long idContrat );
    public List<Announcement> getAllAnnouncement();
    public Announcement findAnnouncement(Long id);

    public void deletetAnnouncement(Long id);

    public List<Announcement> findbyCriteria(Announcement announcement);

    public  List<Announcement> findbyEtat(boolean etat);

    public ResponseEntity<Void> likePost(Long id);
    public ResponseEntity<Void> dislikePost(Long id);

    public Announcement updateAnnouncement(Long id,
                                           String name,
                                           String description,
                                           double price,
                                           String region,Boolean etat,
                                           CategorieAnnouncement categorieAnnouncement,
                                            MultipartFile image) throws IOException;

    public List<Announcement> getAllAnnouncementsSortedByScore();
   /** List<Announcement>  evaluateAnnouncement(Long postId, double evaluation);
    public Map<CategorieAnnouncement, List<Announcement>> getAnnouncementsByCategorySortedByScore();**/
    public Map<CategorieAnnouncement, List<Announcement>> EvaluateAnnouncementAndGetByCategorySortedByScore(Long announcementId, double evaluation);
    public  Announcement applyPromoCode(Long announcementId, String promoCodeString);

    public Map<CategorieAnnouncement, List<Announcement>> EvaluationAnnouncementAndCodePromo(Long announcementId, double evaluation,String promoCodeString);

    ResponseEntity<Void> buyAnnouncement(Long id,Principal principal);
}
