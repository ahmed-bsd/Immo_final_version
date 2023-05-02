package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.Request;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.spring.service.NearbyPlacesService;

import java.net.URI;

///****    Developped by Ahmed bsd    ****////
@RestController
@Api(tags = "nearby places")
@RequestMapping( "api/public/")
public class NearbyPlacesController {

     @Autowired
	NearbyPlacesService nearbyPlacesService;

    //http://localhost:8081/SpringMVC/swagger-ui/index.html

	@ApiOperation(value = "Nearby places")
	@GetMapping("nearby")
	@ResponseBody
	public void nearby(@RequestParam Float alt,@RequestParam Float lg,@RequestParam String type) {


		 nearbyPlacesService.nearby(alt,lg,type);

	}






}
