package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import tn.esprit.spring.repository.AnnouceRepository;



///****    Developped by Ahmed bsd    ****////
@Service
@Slf4j
public class NearbyPlacesService {
	@Autowired
	AnnouceRepository annouceRepository;

		public String nearby(Float alt,Float lg, String type) {

			try {

				String alt_longit = alt+","+lg;
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.set("X-RapidAPI-Key", "0c18b49153msh1db897d2579e8cap12cb86jsn80920a5063b5");
				headers.set("X-RapidAPI-Host", "trueway-places.p.rapidapi.com");
				headers.set("useQueryString", "true");
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://trueway-places.p.rapidapi.com/FindPlacesNearby")
						.queryParam("location", alt_longit)
						.queryParam("type", type)
						.queryParam("radius", "2000")
						.queryParam("language", "fr");

				HttpEntity<?> entity = new HttpEntity<>(headers);

				ResponseEntity<String> response = restTemplate.exchange(
						builder.toUriString(),
						HttpMethod.GET,
						entity,
						String.class);

				JSONObject json = new JSONObject(response.getBody());

				int taille=json.getJSONArray("results").length();
				int max=0;
				if(taille>5)
					max=5;
				else
					max=taille;
				String msg="",name="",distance="";

				for(int i=0;i<max;i++)
				{
					name = json.getJSONArray("results").getJSONObject(i).getString("name")+" , ";
					distance = json.getJSONArray("results").getJSONObject(i).getInt("distance")+" ; ";
					msg =msg+name+distance;
					//System.out.println(msg);
				}

				System.out.println(msg);
				Thread.sleep(1300);
				return msg;

				
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
				return "error";
			}

       }
}