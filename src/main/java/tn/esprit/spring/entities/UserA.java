package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity

@ToString
public class UserA implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**@SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )**/
    Long idUser;

    //@NotBlank(message = "Username required.")
    String userName;

    //@NotBlank(message = "firstName required.")
    String firstName;

    //@NotBlank(message = "lastName required.")
    String lastName;

    //@NotBlank(message = "email required.")
    @Email(message = "enter a valid email")
    String email;

    //@Size(min = 8, max = 50, message = "password must have 8 to 50 caracters.")
    //@NotBlank(message = "password required.")
    @Column(columnDefinition = "TEXT")
    String password;

    Long  phone;

    String imageUrl="https://res.cloudinary.com/bsd-ah/image/upload/v1665675438/products/avatar.png";
    Boolean  locked=false;
    Boolean  enabled=false;

    Boolean archived=false;

    LocalDateTime archivedUntil;

    LocalDateTime bannedUntil;

    // related to the views couter system //
    /*
    @JsonManagedReference
    @OneToMany(mappedBy = "seller")
    private List<Announcement> announcements = new ArrayList<>();
*/

    @Enumerated (EnumType.STRING)
    Role role;

    @OneToMany(mappedBy = "userA")
    List<Article> articles;


    @OneToMany (mappedBy = "userA")
    List<Visit> visits;

    @OneToMany (mappedBy = "userA")
    List<Investisment> investisments;


    @OneToMany (mappedBy = "userA")
    List<Forum> Forums;


    @OneToMany (mappedBy = "userA")
    List<Subscription> subscriptions;

    @JsonIgnore
    @OneToMany (mappedBy = "userA")
    List<Reclamation> reclamations;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return userName;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }




    public UserA(String userName, String firstName, String lastName, String email, String password, Long phone, Boolean locked, Boolean enabled, Role role) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.locked = locked;
        this.enabled = enabled;
        this.role = role;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Publication> publications;

}
