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
public class Contrat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long idContrat;
    private
    String CompanyName;

    private
    String Function;
    private
    String email ;

    private
    TypeContrat typeContrat;


    @OneToOne
    Announcement announcement;

    @ManyToOne
    Insurance insurance;


}