package tn.esprit.spring.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//classe image pour stocker les images eli bch n uploadehom
@Entity
@Table(name = "image_model")
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private  String type;
    @Column(length = 5000000)
    private byte[] picByte;


    public ImageModel(String originalFilename, String contentType, byte[] bytes) {
    }
}
