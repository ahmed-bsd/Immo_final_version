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
@Api(tags = "Token")
@RequestMapping( "api/dashboard/")
public class TokenController {


    //http://localhost:8081/SpringMVC/swagger-ui/index.html

	@ApiOperation(value = "Refresh Token")
	@GetMapping("")
	@ResponseBody
	public String RefreshMytoken() {

		return " refresh  ..";

	}






}
