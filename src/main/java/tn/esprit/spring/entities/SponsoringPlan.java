package tn.esprit.spring.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString

public class SponsoringPlan implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idSponsoringPlan;

    Long reach;
    Float rating;
    Long delay;
    Long viewsBysponsoring;
    Long selectedBysponsoring;
    Long price;
    Boolean active;
    @ManyToOne
    Announcement announcement;


}
