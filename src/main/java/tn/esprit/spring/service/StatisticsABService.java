package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.AnnouceRepository;
import tn.esprit.spring.repository.ReclamationRepository;
import tn.esprit.spring.repository.SponsoringPlanRepository;
import tn.esprit.spring.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

///****    Developped by Ahmed bsd    ****////
@Service
@Slf4j
public class StatisticsABService {

	@Autowired
	SponsoringPlanRepository sponsoringPlanRepository;

	@Autowired
	AnnouceRepository annouceRepository;

	@Autowired
	ReclamationRepository reclamationRepository;

	@Autowired
	UserRepository userRepository;

	public Map<String,String> pourcentage_type_users(){

		Integer total=userRepository.findAll().size();
		Integer clients=userRepository.findAllByRoleIsLike(Role.CLIENT).size();
		Integer sellers=userRepository.findAllByRoleIsLike(Role.SELLER).size();

		Integer pourcentage_clients= (clients/total)*100;
		Integer pourcentage_sellers= (sellers/total)*100;

		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("nbre total des comptes ",total.toString());
		hashMap.put("pourcentage des comptes clients", pourcentage_clients.toString() );
		hashMap.put("pourcentage des comptes sellers", pourcentage_sellers.toString());

		return hashMap;

	}

	public Map<String,String> pourcentage_reclamation(){

		Integer total_rec=reclamationRepository.findAll().size();
		Integer accepted_rec=reclamationRepository.findReclamationsByStatusIsLike(Reclamation.Status.ACCEPTED).size();
		Integer rejected_rec=reclamationRepository.findReclamationsByStatusIsLike(Reclamation.Status.REJECTED).size();
		Integer checking_rec=reclamationRepository.findReclamationsByStatusIsLike(Reclamation.Status.CHECKING).size();
		Integer waiting_rec=reclamationRepository.findReclamationsByStatusIsLike(Reclamation.Status.WAITING).size();

		System.out.println(accepted_rec);

		Integer accepted_pourcentage= (accepted_rec/total_rec)*100;
		Integer rejected_pourcentage= (rejected_rec/total_rec)*100;
		Integer checking_pourcentage= (checking_rec/total_rec)*100;
		Integer waiting_pourcentage= (waiting_rec/total_rec)*100;
System.out.println(accepted_pourcentage);
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("nbre total des reclamations ",total_rec.toString());
		hashMap.put(" % pourcentage des reclamations acceptés ", accepted_pourcentage.toString() );
		hashMap.put(" % pourcentage des reclamations rejetés", rejected_pourcentage.toString());
		hashMap.put(" % pourcentage des reclamations en attente",waiting_pourcentage.toString());
		hashMap.put(" % pourcentage des reclamations sous traitement", checking_pourcentage.toString());

		return hashMap;

	}

	public Map<String,String> pourcentage_sponsoring_objectifs(){

		Integer total_sponsoring_plans=sponsoringPlanRepository.findAll().size();
		List<SponsoringPlan> sponsoringPlans=sponsoringPlanRepository.findAll();
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("nbre total des sponsoring plans ",total_sponsoring_plans.toString());
		for(SponsoringPlan sp:sponsoringPlans){
			Long reach=sp.getReach();
			Long viewsSponsoring= sp.getViewsBysponsoring();
			Long result=(viewsSponsoring/reach)*100;

			hashMap.put(" % object atteint", result.toString());

		}


		return hashMap;

	}







}