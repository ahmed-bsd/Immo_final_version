package tn.esprit.spring.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
public class Forum implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idForum;

    String title;
    String description;

    @ManyToOne
    UserA userA;


    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    List<Publication> publication;


}

