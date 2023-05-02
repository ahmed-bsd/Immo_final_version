package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.repository.ReclamationRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.IAdminService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


///****    Developped by Ahmed bsd    ****////
@Service
@Slf4j
public class ReclamationService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ReclamationRepository reclamationRepository;

	public List<Reclamation>  retrieveAllReclamations(){ return reclamationRepository.findAll();}
	public List<Reclamation>  retrieveAllReclamationsWaiting(){ return reclamationRepository.findReclamationsByStatusIs(Reclamation.Status.WAITING);}
	public List<Reclamation>  retrieveAllReclamationsChecking(){ return reclamationRepository.findReclamationsByStatusIs(Reclamation.Status.CHECKING);}
	public List<Reclamation>  retrieveAllReclamationsAccepted(){ return reclamationRepository.findReclamationsByStatusIs(Reclamation.Status.ACCEPTED);}
	public List<Reclamation>  retrieveAllReclamationsRejected(){ return reclamationRepository.findReclamationsByStatusIs(Reclamation.Status.REJECTED);}

	public ResponseEntity<String> addReclamation(Reclamation reclamation,Principal principal){
		Optional<UserA> user=userRepository.findByUserName(reclamation.getUsername_reclaimed());
		if(!user.isPresent()){
			return  new ResponseEntity<>("username to reclame on it not found",HttpStatus.BAD_REQUEST);
		}
		String username= principal.getName();
		reclamation.setUserA(userRepository.findByUserName(username).get());
		Reclamation  req =reclamationRepository.save(reclamation);

		return  new ResponseEntity<>("reclamation added !",HttpStatus.OK);

	}

	public ResponseEntity<String> deleteReclamation(Long id, Principal principal){
		String username= principal.getName();
		Reclamation reclamation=reclamationRepository.findById(id).get();
		if(reclamation.getUserA().getUsername().equals(username))
		{
			reclamationRepository.deleteById(id);
			return new ResponseEntity<>("deleted",HttpStatus.OK);
		}

		return new ResponseEntity<>("not found username",HttpStatus.OK);
	}

	public Reclamation  update(Reclamation rec){ return reclamationRepository.saveAndFlush(rec);}





}