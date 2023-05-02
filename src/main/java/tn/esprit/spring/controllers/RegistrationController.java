package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.service.TokenConfirmService;
import tn.esprit.spring.service.UserService;

import javax.validation.Valid;

///****    Developped by Ahmed bsd    ****////
@RestController
@Api(tags = "Registration")
@RequestMapping( "api/public/")
public class RegistrationController {

	@Autowired
	UserService userService;
	@Autowired
	TokenConfirmService tokenConfirmService;
    //http://localhost:8081/SpringMVC/swagger-ui/index.html

	@ApiOperation(value = "Registration")
	@PostMapping("registration")
	@ResponseBody
	public ResponseEntity<String> AddUser(@Valid @RequestBody UserA u) {

		return userService.Register(u);

	}


	@ApiOperation(value = "Registration")
	@PostMapping("registration/confirm")
	@ResponseBody
	public ResponseEntity<String> AddUser(@RequestParam String token) {


			TokenConfirmation tokenConfirmation = tokenConfirmService.getToken(token).get();
			if (!tokenConfirmation.getExpired())
			{
				userService.enableUser(tokenConfirmation.getUserA().getEmail());
				tokenConfirmService.disableToken(token);
				return new ResponseEntity<>("confirmed account !",HttpStatus.OK);
			}
			return  new ResponseEntity<>("invalid token",HttpStatus.BAD_REQUEST);


	}

	@ApiOperation(value = "Demand Reset Password")
	@PostMapping("resetPassword/demand")
	@ResponseBody
	public ResponseEntity<String> demandResetPassword(@RequestParam String email) {

		return userService.demandResetPasswd(email);

	}

	@ApiOperation(value = "Reset Password")
	@PostMapping("resetPassword/")
	@ResponseBody
	public ResponseEntity<String> resetPwd(@RequestParam String token,@RequestBody String password) {

		return userService.resetPassword(token,password);

	}






}
