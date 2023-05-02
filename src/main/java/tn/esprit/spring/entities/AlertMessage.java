package tn.esprit.spring.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter //generation automatique des getters
@Setter  //generation automatique des setters
@ToString //pour la generation de la methode ToString
@NoArgsConstructor //pour la generation de constructeur non param
public class AlertMessage {


    private String message;

    public AlertMessage(String message) {
        this.message = message;
    }
}


