package tn.esprit.spring.controllers;



import com.sun.javaws.exceptions.ErrorCodeResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.serviceInterface.IAnnouncementService;
import tn.esprit.spring.serviceInterface.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/user/client/announcement")
public class AnnouncementRestController {

    @Autowired
    IAnnouncementService announcementService;

    @PostMapping(value = {"/addNewAnnouncement"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})

    /** @RequestPart : associe une partie d'une requête en plusieurs parties à l'argument de la méthode,
      ce qui est utile pour envoyer des données multi-attributs complexes

      */
    public Announcement addAnnouncementwithImage(@RequestPart Announcement announcement,
                                                 //multipartfile pour ajouter plusieurs file
                                                 @RequestPart MultipartFile[] file,Principal principal) {
        //Announcement a= announcementService.addAnnouncement(announcement);
     try {
         Set<ImageModel> images = uploadImage(file);
         announcement.setImageAnnouncement(images);
         return announcementService.addAnnouncement(announcement,principal);
     }
     catch (Exception e)
     {
         System.out.println(e.getMessage());
         return null;
     }
    }

    //fonction permet de préparer l'image avant d'enregistrer dans la BD
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException{
        Set<ImageModel> imageModels = new HashSet<>();
        for (MultipartFile File: multipartFiles ){
            ImageModel imageModel= new ImageModel(
                    File.getOriginalFilename(),
                    File.getContentType(),
                    File.getBytes() );
            imageModels.add(imageModel);
        }
      return  imageModels;
    }
    //ajout des annonces
    @PostMapping("/add/announcement")
    @ResponseBody
    public Announcement addAnnouncement(@RequestBody Announcement announcement,Principal principal) {

        Announcement a= announcementService.addAnnouncement(announcement,principal);

        return a;
    }

    // Update + attribution des images
    @PostMapping("/update/Announcement")
    public ResponseEntity<Announcement> updateAnnouncement(@RequestParam("id") Long id,
                                           @RequestParam("name") String name,
                                           @RequestParam("description") String description,
                                           @RequestParam("price") double price,
                                           @RequestParam("region") String region,
                                           @RequestParam("etat") Boolean etat,
                                           @RequestParam("categorieAnnouncement") CategorieAnnouncement categorieAnnouncement,
                                           @RequestParam("image") MultipartFile image) throws IOException {
        Announcement announcement = announcementService.updateAnnouncement(id,name, description,
                                                                       price,region,etat, categorieAnnouncement, image);
        return ResponseEntity.ok(announcement);
    }



    //add with assign
    @PostMapping  ("/assignvAnnouncementToContrat/{idContrat}")
    @ResponseBody
    public Announcement assignAnnouncementToContrat(@RequestBody Announcement announcement ,
                                                  @PathVariable("idContrat") Long idContrat ) {
        Announcement a= announcementService.addAndAssignAnnouncementToContrat(announcement, idContrat);
        return a ;
    }

    //retirer liste des annonces
    @GetMapping("/retrieve/allannouncement")
    @ResponseBody
    public List<Announcement> getAnnouncement(){
    return  announcementService.getAllAnnouncement();
    }

    //retirer annonce par id
    @GetMapping("/retrieve/announcement/{id}")
    @ResponseBody
   public Announcement getannouncementById(@PathVariable("id") Long id){
        return announcementService.findAnnouncement(id);
    }

    @GetMapping("/AllAnnouncementByEtat")
    @ResponseBody
    public List<Announcement> getannouncementByEtat( boolean etat){
        return announcementService.findbyEtat(etat);
    }

    //delete announcement
    @DeleteMapping("/delete/announcement/{id}")
    public void deleteVisit(@PathVariable("id") Long id) {

        announcementService.deletetAnnouncement(id);
    }

    //Recherche multicritére
    @PostMapping("/criteria")
    public List<Announcement> findByCriteria(@RequestBody Announcement announcement){
        return announcementService.findbyCriteria(announcement);
    }

    //like announcement
    @PostMapping("like/{id}")
    public ResponseEntity<Void> likePost(@PathVariable Long id) {
        return  announcementService.likePost(id);

    }
    //dislike announcement
    @PostMapping("dislike/{id}")
    public ResponseEntity<Void> dislikePost(@PathVariable Long id) {
        return  announcementService.dislikePost(id);

    }

    /**
    //Rating des annonces
    @PostMapping("/{announcementId}/evaluations")
   public List<Announcement>  rateAnnouncement(@PathVariable Long announcementId, @RequestBody Map<String, Object> payload) {
       double evaluation = Double.parseDouble(payload.get("evaluation").toString());
       return  announcementService.evaluateAnnouncement(announcementId , evaluation);
   }
   //trie des annonces by categorieet score
   @GetMapping("/AnnouncementByCatAndSCore")
    @ResponseBody
    public Map<CategorieAnnouncement, List<Announcement>> getAnnouncementCategorie(){
        return  announcementService.getAnnouncementsByCategorySortedByScore();
    }
   */
    //Rating des annonces + trie des annonce selon le score par categorie
    @PostMapping("/AnnouncementByCatAndSCore/{announcementId}/evaluations")
    @ResponseBody
    public Map<CategorieAnnouncement, List<Announcement>>  rateAnnouncementAndGetByCategorie
            (@PathVariable Long announcementId, @RequestBody Map<String, Object> payload) {
        double evaluation = Double.parseDouble(payload.get("evaluation").toString());
        return  announcementService.EvaluateAnnouncementAndGetByCategorySortedByScore(announcementId , evaluation);
    }
    //Code Promos
    @PostMapping("/{announcementId}/promo/{promoCode}")
    public ResponseEntity<Response> applyPromoCode(@PathVariable Long announcementId, @PathVariable String promoCode) {
           try {
               Announcement announcement = announcementService.applyPromoCode(announcementId, promoCode);
               return ResponseEntity.ok(new Reponse("Promo code applied successfully"));
           }
           catch (IllegalArgumentException e) {
               //to return a JSON error message to the client
               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
           }


    }
    @PostMapping("/EvaluationAndCodePromos/{announcementId}/evaluations/{promoCode}")
    @ResponseBody
    public Map<CategorieAnnouncement, List<Announcement>>  EvaluationAnnouncementAndCodePromo
            (@PathVariable Long announcementId, @RequestBody Map<String, Object> payload,@PathVariable String promoCode) {
        double evaluation = Double.parseDouble(payload.get("evaluation").toString());
        return  announcementService.EvaluationAnnouncementAndCodePromo(announcementId , evaluation,promoCode);
    }



    //ahmed bsd added
    @PostMapping("/buy/{id}")
    public ResponseEntity<Void> buyAnnouncement(@PathVariable Long id,Principal principal) {
        return  announcementService.buyAnnouncement(id,principal);

    }
}



