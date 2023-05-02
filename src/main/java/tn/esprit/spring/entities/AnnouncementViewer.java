package tn.esprit.spring.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

///****    Developped by Ahmed bsd    ****////
public class AnnouncementViewer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference(value="view-announcement")
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserA user;

    /*public AnnouncementViewer(Announcement announcement, UserA user) {
        this.announcement = announcement;
        this.user = user;
    }*/

    public AnnouncementViewer(Announcement announcement, UserA user) {
        this.announcement = announcement;
        this.user = user;
    }
}
