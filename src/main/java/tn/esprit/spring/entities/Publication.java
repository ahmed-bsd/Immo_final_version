package tn.esprit.spring.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
public class Publication implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPublication;
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(max = 20, message = "Le titre ne peut pas dépasser {max} caractères")
    String title;
    String content;
    @ManyToOne
    @JsonIgnore
    Forum forum;


    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    List<Comment> comments;

    int likes;
    int dislikes;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    List<File> attachments;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserA user;


    public void incrementLikes() {
        this.likes++;
    }

    public void incrementDislikes() {
        this.dislikes++;
    }

    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }

    public void decrementDislikes() {
        if (this.dislikes > 0) {
            this.dislikes--;
        }
    }


    public boolean containsBadWord(List<String> badWords) {

        for (String badWord : badWords) {
            if (this.title.contains(badWord) || this.content.contains(badWord)) {
                return true;
            }
        }

        return false;
    }

}

