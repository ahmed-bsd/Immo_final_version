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
public class Reclamation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@JsonIgnore
	@ManyToOne
	UserA userA;

	@Enumerated(EnumType.STRING)
	Status status;

	String username_reclaimed;

	public enum Status{
		WAITING,
		CHECKING,
		ACCEPTED,
		REJECTED
	}

	String description;





}
