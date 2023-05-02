package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class File implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String filename;

    String filetype;

    @Lob
    byte[] data;

    @ManyToOne
    @JsonIgnore
    Publication publication;


    public void setAttachmentUrl(String toString) {
    }

    public void setFileSize(long size) {
    }
}
