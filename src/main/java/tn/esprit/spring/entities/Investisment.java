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
public class Investisment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String category;
    private String roomsCount;
    private String bathroomCount;
    private String size;
    private String type;
    private float price;
    private String city;
    private String region;
    private float logPrice;

    /*   String category,String roomsCount,String bathroomCount,
         String size, String type
         float price,
         String city,
         String region,
         float logPrice
   */

    @ManyToOne
    UserA userA;
}
