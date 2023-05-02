package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Announcement;
import tn.esprit.spring.entities.SponsoringPlan;
import tn.esprit.spring.repository.AnnouceRepository;
import tn.esprit.spring.service.NearbyPlacesService;
import tn.esprit.spring.service.SponsoringService;
import tn.esprit.spring.service.StripePaymentService;
import tn.esprit.spring.service.ViewsService;

///****    Developped by Ahmed bsd    ****////
@RestController
@Api(tags = "sponsoring controller  (bsd)")
@RequestMapping( "api/public")
public class SponsorController {
	@Autowired
	AnnouceRepository annouceRepository;

	@Autowired
	NearbyPlacesService nearbyPlacesService;

	@Autowired
	ViewsService viewsService;

	@Autowired
	private StripePaymentService stripePaymentService;

	@Autowired
	private SponsoringService sponsoringService;

    //http://localhost:8081/SpringMVC/swagger-ui/index.html
/*
	@ApiOperation(value = "add announcement")
	@PostMapping("/add/announcement")
	@ResponseBody
	public String add_announcement(@RequestBody Announcement ann,@RequestParam Float lg,@RequestParam Float alt) {


		ann.setPharmacies(nearbyPlacesService.nearby(alt,lg,"pharmacy"));
		ann.setSchools(nearbyPlacesService.nearby(alt,lg,"school"));
		ann.setHospitals(nearbyPlacesService.nearby(alt,lg,"hospital"));
		ann.setRestaurants(nearbyPlacesService.nearby(alt,lg,"restaurant"));
        annoucementRepository.save(ann);

		return "done , added";

	}

	@ApiOperation(value = "retrieve announce ")
	@GetMapping("/announcement")
	@ResponseBody
	public ResponseEntity<Announcement> get_ann(@RequestParam  Long id ) {

		return new ResponseEntity<>(annoucementRepository.findById(id).get(), HttpStatus.OK);


	}

*/



	@ApiOperation(value = "add sponsoring plan and affect it to announcement")
	@PostMapping("/add/sponsoring")
	@ResponseBody
	public ResponseEntity<String> add_sponsoring_and_affect_to_announcement(@RequestParam  Long id , @RequestBody SponsoringPlan sp) {



		return new ResponseEntity<>(sponsoringService.CreateAndAffectSponsoringPlanToAnnouncement(id,sp), HttpStatus.OK);


	}
	@ApiOperation(value = "select sponsored announce to display by weight")
	@GetMapping("/sponsored_annoucement")
	@ResponseBody
	public ResponseEntity<Announcement> retrieve_sponsored_announcement() {

		return new ResponseEntity(sponsoringService.getSponsoredAnnouncement(), HttpStatus.OK);


	}

	@ApiOperation(value = "open announcement throw sponsoring (add views throw sponsor)")
	@GetMapping("/sponsored_announcement_view")
	@ResponseBody
	public ResponseEntity<String> view_sponsored_announcement(@RequestParam  Long id ) {


		return new ResponseEntity<>(sponsoringService.openAnnouncementThrowSponsoring(id), HttpStatus.OK);


	}





/*

	@PostMapping("/purchase")
	public String purchaseOffer(@RequestParam String offer_name,
								@RequestParam double offer_price,
								@RequestParam String card_number,
								@RequestParam String expiry_date,
								@RequestParam String cvc) {
		// Create a Stripe charge using the Stripe API
		Stripe.apiKey = "sk_test_51MjIKuAPfSPmYV8IDHwVr0P3ymi6tD6Rd5MC9ozAnZEbAxLLqOaJhkrmXjZ4Dlz7yfpw5WbR1T16g3EIk0Y9gfm600Yzqewm0r";
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", (int) (offer_price * 100));
		chargeParams.put("currency", "usd");
		chargeParams.put("description", "Purchase of " + offer_name);
		chargeParams.put("source", card_number);
		chargeParams.put("exp_month", Integer.parseInt(expiry_date.substring(0, 2)));
		chargeParams.put("exp_year", Integer.parseInt(expiry_date.substring(3, 5)));
		chargeParams.put("cvc", cvc);
		try {
			Charge charge = Charge.create(chargeParams);
			// If the charge was successful, return a success page
			return "success";
		} catch (StripeException e) {
			// If there was an error processing the charge, return an error page
			return "error";
		}
	}
*/





}
