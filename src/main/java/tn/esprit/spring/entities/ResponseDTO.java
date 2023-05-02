package tn.esprit.spring.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class ResponseDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idScraping;
    String region;
    String Population;
    String Superficie;

    String url;

    //added by ahmed bsd to fix the problem of setTitle()
    String title;
}
