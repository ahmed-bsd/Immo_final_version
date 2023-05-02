package tn.esprit.spring.serviceInterface;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.requests.EditProfileRequest;
import tn.esprit.spring.requests.InfosProfileResponse;

import java.security.Principal;
import java.util.List;

///****    Developped by Ahmed bsd    ****////
public interface IUserService {

    List<UserA> retrieveAllUsers();
    ResponseEntity<String> Register(UserA u);

    InfosProfileResponse findByUsername(String username);

    String EditMyInfos(EditProfileRequest editProfileRequest, Principal principal);

    ResponseEntity<String> demandResetPasswd(String email);

    ResponseEntity<String> resetPassword(String token,String password);


    @Scheduled(fixedRate = 2*60*1000) // 2 minute
    void AvertingDeleteAccountTotally();

    @Scheduled(fixedRate = 2*60*1000) // 2 minute
    void deleteAccountTotally();

    ResponseEntity<String> banUser(String username, Long duration);


    ResponseEntity<String> deleteAccount(Principal principal);

    @Scheduled(fixedRate = 60*1000) // 1 minute
    void unbanUsers();
}
