package tn.esprit.spring.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Insurance implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idInsurrance;

    String InsurranceName;
    Float annualPrice;
    Date dateInsurance;
    Date dateExpiration;
    String description;
    String email;
    @Enumerated(EnumType.STRING)
    CategorieAnnouncement categorieAnnouncement;



   // @OneToMany (mappedBy = "insurance")
    //List<Contrat> contrats;



}