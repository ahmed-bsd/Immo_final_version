package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.service.AdminService;


///****    Developped by Ahmed bsd    ****////
@RestController
@Api(tags = "Sellers")
@RequestMapping( "api/user/seller/")
public class SellerController {



	@ApiOperation(value = "Retrieve all announces")
	@GetMapping("announces")
	@ResponseBody
	public String RetrieveAllannounces() {

		return "List of announcements ..";

	}






}
