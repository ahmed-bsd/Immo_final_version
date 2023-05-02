package tn.esprit.spring.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

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
public class HousePricing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double priceTND;
    private Double priceEUR;
    private String location;
    private String city;
    private String governorate;
    private Double area;
    private Integer pieces;
    private Integer room;
    private Integer bathroom;
    private String age;
    private Integer state;
    private Double latitude;
    private Double longitude;
    private Double distanceToCapital;
    private Integer garage;
    private Integer garden;
    private Integer concierge;
    private Integer beachView;
    private Integer mountainView;
    private Integer pool;
    private Integer elevator;
    private Integer furnished;
    private Integer equippedKitchen;
    private Integer centralHeating;
    private Integer airConditioning;

}
