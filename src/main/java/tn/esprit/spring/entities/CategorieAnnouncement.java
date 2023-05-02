package tn.esprit.spring.entities;


import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


public enum CategorieAnnouncement  {

 // T,M



    MaisonAvendre("Maison A vendre"),
    MaisonALouer("Maison A Louer"),
    Villa("Villa"),
    Appartements("Appartements"),
    Terrain("Terrain ");
    private final String name;
    CategorieAnnouncement(String name){
        this.name=name;
    }


    public String getName() {
        return name;
    }


}