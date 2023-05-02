package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.service.AdminService;
import tn.esprit.spring.service.UserService;

import javax.validation.Valid;
import java.util.List;

///****    Developped by Ahmed bsd    ****////
@RestController
@Api(tags = "Admin Dashboard")
@RequestMapping( "api/dashboard/")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	UserService userService;
    //http://localhost:8081/SpringMVC/swagger-ui/index.html

	@ApiOperation(value = "Retrieve all sellers")
	@GetMapping("sellers")
	@ResponseBody
	public String RetrieveAllSellers() {

		return "List of sellers ..";

	}

	@ApiOperation(value = "ban a user")
	@PostMapping("users/ban/{username}/{duration}")
	@ResponseBody
	public ResponseEntity<String> RetrieveAllSellers( @PathVariable String username,@PathVariable Long duration) {

		return userService.banUser(username,duration);

	}






}
