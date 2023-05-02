package tn.esprit.spring.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Announcement implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAnnouncement;
    String description;
    String name;
    String localisation;

    double price;
    Boolean etat;

    int garage;
    int pool ;
    int airConditioning;


    @Enumerated(EnumType.STRING)
    CategorieAnnouncement categorieAnnouncement;
    String region;
    int likes;
    int dislikes;
    @Lob
    private  String image;
    double score;
    int numEvaluations;
    LocalDate Date;

    //relation entre la classe announcement et la classe image
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "announcement_image",
            joinColumns = {
                    @JoinColumn(name = "id_announcement")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id")
            }
    )
    private Set<ImageModel> ImageAnnouncement;





    @JsonIgnore
    @OneToMany(mappedBy = "announcement")
    List<Visit> visits;

    @OneToOne
    Contrat contrat;











    //****  added by bsd dev  ****//
    @Column(columnDefinition = "TEXT")
    private String schools;
    @Column(columnDefinition = "TEXT")
    private String restaurants;
    @Column(columnDefinition = "TEXT")
    private String hospitals;
    @Column(columnDefinition = "TEXT")
    private String pharmacies ;

    //bsd added
    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserA seller;

    @JoinColumn(name = "buyer_id")
    @OneToOne
    private UserA buyer;

    //added

    @JsonManagedReference(value="view-announcement")
    @OneToMany(mappedBy = "announcement")
    private List<AnnouncementViewer> viewers = new ArrayList<>();

    @OneToMany(mappedBy = "announcement")
    List<SponsoringPlan> sponsoringPlans;

    private Boolean sponsored;

    private Double sponsoringWeight;

    private Float rating;

    //**** finish add ****//






}
