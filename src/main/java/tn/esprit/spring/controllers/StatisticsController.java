package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.service.*;

///****    Developped by Ahmed bsd    ****////
@RestController
@Api(tags = "Statsticts  controller  (bsd)")
@RequestMapping( "api/public")
public class StatisticsController {

	@Autowired
	StatisticsABService statisticsABService;
	@ApiOperation(value = "pourcentage du type des utilisateurs")
	@GetMapping("/staticts/users")
	@ResponseBody
	public ResponseEntity<Announcement> retrieve_pourcentage_accounts() {

		return new ResponseEntity(statisticsABService.pourcentage_type_users(), HttpStatus.OK);


	}

	@ApiOperation(value = "statistique sur le status des reclamations")
	@GetMapping("/staticts/reclamations")
	@ResponseBody
	public ResponseEntity<Announcement> retrieve_pourcentage_reclamations() {

		return new ResponseEntity(statisticsABService.pourcentage_reclamation(), HttpStatus.OK);


	}

	@ApiOperation(value = "statistique sur l'avanvement des plans de sponsoring pour les annonces")
	@GetMapping("/staticts/sponsoring")
	@ResponseBody
	public ResponseEntity<Announcement> retrieve_pourcentage_sponsoring() {

		return new ResponseEntity(statisticsABService.pourcentage_sponsoring_objectifs(), HttpStatus.OK);


	}




}
