package tn.esprit.spring.service;


import io.swagger.models.Path;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.AnnoucementRepository;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.PromoCodeRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.IAnnouncementService;

import javax.persistence.EntityManager;
import java.io.*;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AnnouncementService implements IAnnouncementService {
    @Autowired
    AnnoucementRepository annoucementRepository;
    @Autowired
    PromoCodeRepository promoCodeRepository;
    @Autowired
    ContratRepository contratRepository;

    @Autowired
    UserRepository userRepository;



    @Override
    public Announcement addAnnouncement(Announcement announcement, Principal principal) {
        String username= principal.getName();
        announcement.setSeller(userRepository.findByUserName(username).get());
        //Announcement  ann =annoucementRepository.save(announcement);

        return annoucementRepository.save(announcement);
    }

    @Override
    public Announcement addAndAssignAnnouncementToContrat(Announcement announcement, Long idContrat) {

        Contrat contrat = contratRepository.findById(idContrat).orElse(null);
        //Affectation avec contrat
        announcement.setContrat(contrat);
        annoucementRepository.save(announcement);
        return  announcement;
    }



    @Override
    public List<Announcement> getAllAnnouncementsSortedByScore() {
        return annoucementRepository.findAllByOrderByScoreDesc();
    }

    @Override
    public List<Announcement> getAllAnnouncement() {
        return annoucementRepository.findAll();
    }

    @Override
    public Announcement findAnnouncement(Long id) {
        return annoucementRepository.findById(id).get();
    }


    @Override
    public void deletetAnnouncement(Long id) {
        Announcement announcement = annoucementRepository.findById(id).get();
        annoucementRepository.delete(announcement);

    }

    //recherche multicritére
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Announcement> findbyCriteria(Announcement announcement) {
        String querry = "SELECT a FROM Announcement as a WHERE 1=1 ";
        if (announcement.getName() != null && !announcement.getName().isEmpty())
            querry += "AND a.name='" + announcement.getName() + "'";
        if (announcement.getDescription() != null && !announcement.getDescription().isEmpty())
            querry += "AND a.description='" + announcement.getDescription() + "'";
        if (announcement.getEtat() != null && !announcement.getEtat().booleanValue())
            querry += "AND a.etat='" + announcement.getEtat() + "'";

        return entityManager.createQuery(querry).getResultList();

    }

    @Override
    public List<Announcement> findbyEtat(boolean etat) {
        return annoucementRepository.retrieveAnnouncementbyEtat(etat);
    }

    //like announcement
    @Override
    public ResponseEntity<Void> likePost(Long id) {
        //creation d'une liste d'annoucement geti feha par id les annoucement
        Optional<Announcement> optionalAnnouncement = annoucementRepository.findById(id);
        // is persent() :Return true if there is a value present, otherwise false.
        if (optionalAnnouncement.isPresent()) {
            Announcement announcement = optionalAnnouncement.get();
            int likes = announcement.getLikes();
            //increments its likes count by 1
            announcement.setLikes(likes + 1);
            annoucementRepository.save(announcement);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //dislike announcement
    @Override
    public ResponseEntity<Void> dislikePost(Long id) {
        //creation d'une liste d'annoucement geti feha par id les annoucement
        Optional<Announcement> optionalAnnouncement = annoucementRepository.findById(id);
        // is persent() :Return true if there is a value present, otherwise false.
        if (optionalAnnouncement.isPresent()) {
            Announcement announcement = optionalAnnouncement.get();

            int dislikes = announcement.getDislikes();
            //increments its dislikes count by 1
            announcement.setDislikes(dislikes + 1);

            annoucementRepository.save(announcement);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public Announcement updateAnnouncement(Long id, String name, String description, double price,
                                           String region,Boolean etat,
                                           CategorieAnnouncement categorieAnnouncement,
                                            MultipartFile image) throws IOException {
        Optional<Announcement> optionalAnnouncement = annoucementRepository.findById(id);
        if (optionalAnnouncement.isPresent()) {
            Announcement announcement = optionalAnnouncement.get();
            announcement.setName(name);
            announcement.setDescription(description);
            announcement.setPrice(price);
            announcement.setEtat(etat);
            announcement.setCategorieAnnouncement(categorieAnnouncement);
            announcement.setRegion(region);

            if (image != null) {
                String filename = image.getOriginalFilename();
                File file = new File("images/" + filename);
                if (file.exists()) {
                    // Extract the file name from the original file name
                    String imageName = FilenameUtils.getName(filename);
                    // Save the image name in the  object
                    announcement.setImage(imageName);
                    // Save the image file to disk
                    byte[] imageData = Files.readAllBytes(file.toPath());
                    Files.write(Paths.get("images/" + imageName), imageData);
                } else {
                    throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
                }
            }

            return annoucementRepository.save(announcement);
        } else {
            return null;
        }
    }
/**
    //Rating des annonces + trie des annonce selon le score
    private Map<Integer, Announcement> announcements = new ConcurrentHashMap<>();

    @Override
    public List<Announcement> evaluateAnnouncement(Long announcementId, double evaluation) {
        Announcement announcement = annoucementRepository.findById(announcementId).get();
        System.out.print(announcement);
        if (announcement != null) {
            //recupérrer le score actuelle
            double currentScore = announcement.getScore();
            int numEvaluations = announcement.getNumEvaluations();
            //calculer le nouveau score
            double newScore = ((currentScore * numEvaluations) + evaluation) / (numEvaluations + 1);
            announcement.setScore(newScore);
            announcement.setNumEvaluations(numEvaluations + 1);
            annoucementRepository.save(announcement);
        }
        return annoucementRepository.findAllByOrderByScoreDesc();

    }
    @Override
    public Map<CategorieAnnouncement, List<Announcement>> getAnnouncementsByCategorySortedByScore() {
        Map<CategorieAnnouncement, List<Announcement>> announcementsByCategory = new HashMap<>();

        // Step 1: Retrieve all the categories
        List<CategorieAnnouncement> categories = annoucementRepository.findDistinctCategories();

        // Step 2-4: Sort the announcements by score for each category
        for (CategorieAnnouncement category : categories) {
            List<Announcement> announcements = annoucementRepository.findByCategorieAnnouncementOrderByScoreDesc(category);
            announcementsByCategory.put(category, announcements);
        }

        return announcementsByCategory;
    }
    **/

    //Rating des annonces + trie des annonce selon le score par categorie
    @Override
    public Map<CategorieAnnouncement, List<Announcement>> EvaluateAnnouncementAndGetByCategorySortedByScore(Long announcementId, double evaluation) {
        Announcement announcement = annoucementRepository.findById(announcementId).get();
        // System.out.print(announcement);
        //on utilise Map pour stocker liste quand va retourner car on va trier par categorie Map<clé,valeur>
        Map<CategorieAnnouncement, List<Announcement>> announcementsByCategory = new HashMap<>();

        if (announcement != null) {
            //recupérrer le score actuelle
            double currentScore = announcement.getScore();
            int numEvaluations = announcement.getNumEvaluations();
            //calculer le nouveau score
            double newScore = ((currentScore * numEvaluations) + evaluation) / (numEvaluations + 1);
            announcement.setScore(newScore);
            announcement.setNumEvaluations(numEvaluations + 1);
            annoucementRepository.save(announcement);
        }
        // Step 1: Retrieve all the categories
        List<CategorieAnnouncement> categories = annoucementRepository.findDistinctCategories();
        // Step 2-4: Sort the announcements by score for each category
        for (CategorieAnnouncement category : categories) {
            List<Announcement> announcements = annoucementRepository.findByCategorieAnnouncementOrderByScoreDesc(category);
            announcementsByCategory.put(category, announcements);
        }
        return announcementsByCategory;
    }

    //Code Promos
    @Override
    public Announcement applyPromoCode(Long announcementId, String promoCodeString)  {
        // Get the announcement
        Announcement announcement = annoucementRepository.findById(announcementId).get();
        System.out.print(announcement);
        // Get the promo code
        PromoCodeAnnouncement promoCode = promoCodeRepository.findByCode(promoCodeString).get();
        System.out.print(promoCode);
        //pour tester la validite de la date d'expiration :To fix the issue,
        // you can convert the java.util.Date object to a java.time.LocalDate object,
        Date expirationDate = promoCode.getExpirationDate();
        Instant instant = expirationDate.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        // and then use the isBefore() method to check if it's before the current date.
        if (localDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Promo code has expired");
        } else {
        double oldPrice = announcement.getPrice();
        // Calculate the discounted price
        double newPrice =  oldPrice * (1 - (promoCode.getDiscount() / 100.0));
        // Update the announcement with the discounted price
        announcement.setPrice(newPrice);
        annoucementRepository.save(announcement);
        return announcement; }
    }



    @Override
    public Map<CategorieAnnouncement, List<Announcement>> EvaluationAnnouncementAndCodePromo(Long announcementId, double evaluation,String promoCodeString) {
        Announcement announcement = annoucementRepository.findById(announcementId).get();
        // System.out.print(announcement);
        //on utilise Map pour stocker liste quand va retourner car on va trier par categorie Map<clé,valeur>
        Map<CategorieAnnouncement, List<Announcement>> announcementsByCategory = new HashMap<>();

        if (announcement != null) {
            //recupérrer le score actuelle
            double currentScore = announcement.getScore();
            int numEvaluations = announcement.getNumEvaluations();
            //calculer le nouveau score
            double newScore = ((currentScore * numEvaluations) + evaluation) / (numEvaluations + 1);
            announcement.setScore(newScore);
            announcement.setNumEvaluations(numEvaluations + 1);
            annoucementRepository.save(announcement);
        }
        // Step 1: Retrieve all the categories
        List<CategorieAnnouncement> categories = annoucementRepository.findDistinctCategories();
        // Step 2-4: Sort the announcements by score for each category
        for (CategorieAnnouncement category : categories) {
            List<Announcement> announcements = annoucementRepository.findByCategorieAnnouncementOrderByScoreDesc(category);
            announcementsByCategory.put(category, announcements);
            boolean promoApplied = false;
            List<Announcement> firstAnnouncementList = null;
            //Les éléments d'une Map ne sont pas ordonnés, donc il n'y a pas de "premier" élément dans une Map.
            //get() prend en argument la clé
            // parcourons toutes les valeurs dans la Map avecla méthode values().
            // La première liste de Announcement  stockée dans la variable firstAnnouncementList.
            //  nous pouvons accéder au premier élément en appelant la méthode get(0) sur la liste.
            for (List<Announcement> announcementList : announcementsByCategory.values()) {
                firstAnnouncementList = announcementList;
                break;
            }
            System.out.print("*********************"+firstAnnouncementList.size());


            if (!promoApplied && !firstAnnouncementList.isEmpty()) {
                //pour retourner le premier élement
                Announcement firstAnnouncement = firstAnnouncementList.get(0);
                // Get the promo code
                PromoCodeAnnouncement promoCode = promoCodeRepository.findByCode(promoCodeString).get();

                //pour tester la validite de la date d'expiration :To fix the issue,
                // you can convert the java.util.Date object to a java.time.LocalDate object,
                Date expirationDate = promoCode.getExpirationDate();
                Instant instant = expirationDate.toInstant();
                LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

                // and then use the isBefore() method to check if it's before the current date.
                if (localDate.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("Promo code has expired");
                } else {
                    double oldPrice = firstAnnouncement.getPrice();
                    // Calculate the discounted price
                    double newPrice = oldPrice * (1 - (promoCode.getDiscount() / 100.0));
                    // Update the announcement with the discounted price
                    firstAnnouncement.setPrice(newPrice);
                    annoucementRepository.save(firstAnnouncement);
                }

            }
        }
        return announcementsByCategory;}

    @Override
    public ResponseEntity<Void> buyAnnouncement(Long id, Principal principal) {
        Optional<Announcement> optionalAnnouncement = annoucementRepository.findById(id);
        // is persent() :Return true if there is a value present, otherwise false.
        if (optionalAnnouncement.isPresent()) {
            Announcement announcement = optionalAnnouncement.get();
            Optional<UserA> buyer=userRepository.findByUserName(principal.getName());
            announcement.setBuyer(buyer.get());
            annoucementRepository.save(announcement);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
