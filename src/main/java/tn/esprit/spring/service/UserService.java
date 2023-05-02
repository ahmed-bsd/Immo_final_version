package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.TokenConfirmation;
import tn.esprit.spring.entities.TokenResetpwd;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.requests.EditProfileRequest;
import tn.esprit.spring.requests.InfosProfileResponse;
import tn.esprit.spring.serviceInterface.IUserService;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;


///****    Developped by Ahmed bsd    ****////
@Service
@Slf4j
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenConfirmService tokenConfirmService;

	@Autowired
	TokenResetPwdService tokenResetPwdService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	EmailSenderABService emailSender;

	@Override
	public List<UserA> retrieveAllUsers() {
		return userRepository.findAll();
	}




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserA userA=userRepository.findByUserName(username).get();
		if (userA == null){
			log.error("user not found");
			throw new UsernameNotFoundException("user not found");
		}
		else {
			log.info("user found");

		}
		Collection<SimpleGrantedAuthority> authorities =new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userA.getRole().name()));
		return new User(userA.getUsername(), userA.getPassword(),authorities);
	}

	@Override
	public ResponseEntity<String> Register(UserA u)   {


		if(userRepository.findByEmail(u.getEmail()).isPresent()  )
		{
			return new ResponseEntity<>("email already exist", HttpStatus.BAD_REQUEST);
		}
		if(userRepository.findByUserName(u.getUsername()).isPresent() )
		{
			return new ResponseEntity<>("userName already exist", HttpStatus.BAD_REQUEST);
 		}

		u.setPassword(passwordEncoder.encode(u.getPassword()));

		UserA user=userRepository.save(u);


		//creation d'un token de confirmation

		String token = UUID.randomUUID().toString();
		TokenConfirmation tokenConfirmation = new TokenConfirmation(token, user);

		tokenConfirmService.saveConfirmationToken( tokenConfirmation );

		//envoi du mail
		String link = "http://localhost:8081/SpringMVC/api/registration/confirm/" + token;
		emailSender.send(
				user.getEmail(), "Confirmation", link);



		return new ResponseEntity<>("new account created\n verify your email , token :"+token,HttpStatus.OK);

	}

	@Override
	public InfosProfileResponse findByUsername(String username) {

		InfosProfileResponse infos =new InfosProfileResponse();
		UserA userA =userRepository.findByUserName(username).get();

		infos.setUserName(userA.getUsername());
		infos.setFirstName(userA.getFirstName());
		infos.setLastName(userA.getLastName());
		infos.setPhone(userA.getPhone());
		infos.setEmail(userA.getEmail());
		infos.setImageUrl(userA.getImageUrl());
		// https://res.cloudinary.com/bsd-ah/image/upload/v1665675438/products/
		return infos;
	}

	@Override
	public String EditMyInfos(EditProfileRequest editProfileRequest, Principal principal) {

		String username = principal.getName();

		if ( userRepository.findByUserName(username).isPresent())
		{
			UserA user=userRepository.findByUserName(username).get();

			if( !editProfileRequest.getUserName().isEmpty()){
				user.setUserName(editProfileRequest.getUserName());
			}
			if( !editProfileRequest.getFirstName().isEmpty()){
				user.setFirstName(editProfileRequest.getFirstName());
			}
			if( !editProfileRequest.getLastName().isEmpty()){
				user.setLastName(editProfileRequest.getLastName());
			}
			if( 0!=editProfileRequest.getPhone()){
				user.setPhone(editProfileRequest.getPhone());

			}
            userRepository.save(user);
			return "infos updated";
		}

		return "user not found";


	}

	@Override
	public ResponseEntity<String> demandResetPasswd(String email){
		Optional<UserA> user=userRepository.findByEmail(email);
		if (user.isPresent())
		{
			// create a token and save it
			String token = UUID.randomUUID().toString();
			String link = "http://localhost:8081/SpringMVC/api/reset/" + token;
			TokenResetpwd tokenResetpwd = new TokenResetpwd(token, user.get() );
			tokenResetPwdService.saveToken(tokenResetpwd);

			//send email to reset my pwd
			emailSender.send( email, "Reset password", link);

			return  new ResponseEntity<>("email sent, verify your email account", HttpStatus.OK);
		}


		return new ResponseEntity<>("email not found", HttpStatus.BAD_REQUEST );
	}

	@Override
	public ResponseEntity<String> resetPassword(String token,String password) {

		TokenResetpwd tokenResetpwd = tokenResetPwdService.getToken(token).get();

		if (!tokenResetpwd.getExpired())
		{
			UserA user= userRepository.findByUserName(tokenResetpwd.getUserA().getUsername()).get();
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);
			tokenResetPwdService.disableToken(token);
			return new ResponseEntity<>("password changed !",HttpStatus.OK);
		}
		return  new ResponseEntity<>("invalid token",HttpStatus.BAD_REQUEST);

	}

	@Override
	public ResponseEntity<String> deleteAccount(Principal principal){
		String username = principal.getName();

		if ( userRepository.findByUserName(username).isPresent()) {
			UserA user = userRepository.findByUserName(username).get();
			user.setArchived(true);
			user.setArchivedUntil(LocalDateTime.now().plusDays(60));
			userRepository.save(user);
			return new ResponseEntity<>("your profil is archived !", HttpStatus.OK);
		}
		return  new ResponseEntity<>("something wents wrong",HttpStatus.OK);
	}

	@Override
	@Scheduled(fixedRate = 2*60*1000) // 2 minute
	public void AvertingDeleteAccountTotally() {
		List<UserA> archivedUsers = userRepository.findByArchivedUntilAfter(LocalDateTime.now().minusDays(14));
		for (UserA user : archivedUsers) {
			emailSender.send(user.getEmail(), "Urgent", "your account will be deleted in 14 days." );
		}
	}

	@Override
	@Scheduled(fixedRate = 2*60*1000) // 2 minute
	public void deleteAccountTotally() {
		List<UserA> archivedUsers = userRepository.findByArchivedUntilBefore(LocalDateTime.now().minusDays(14));
		for (UserA user : archivedUsers) {
			userRepository.deleteById(user.getIdUser());
		}
	}


	@Override
	public ResponseEntity<String> banUser(String username, Long duration) {
		UserA user = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username));
		LocalDateTime bannedUntil = LocalDateTime.now().plusHours(duration);
		user.setBannedUntil(bannedUntil);
		user.setLocked(true);
		userRepository.save(user);
		return new ResponseEntity<>("user is banned",HttpStatus.OK );
	}

	@Override
	@Scheduled(fixedRate = 60*1000) // 1 minute
	public void unbanUsers() {
		List<UserA> bannedUsers = userRepository.findByBannedUntilBefore(LocalDateTime.now());
		for (UserA user : bannedUsers) {
			user.setLocked(false);
			user.setBannedUntil(null);
			userRepository.save(user);
		}
	}

	public int enableUser(String email) {
		return userRepository.enableUser(email);
	}





}