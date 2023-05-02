package tn.esprit.spring.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.requests.EditProfileRequest;
import tn.esprit.spring.requests.InfosProfileResponse;
import tn.esprit.spring.service.AdminService;
import tn.esprit.spring.service.CloudinaryService;
import tn.esprit.spring.service.UserService;

import java.io.IOException;
import java.security.Principal;


///****    Developped by Ahmed bsd    ****////
@RestController
@Api(tags = "User APIs")
@RequestMapping( "api/user/")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	CloudinaryService cloudinaryService;
    //http://localhost:8081/SpringMVC/swagger-ui/index.html

	@ApiOperation(value = "Retrieve my infos")
	@GetMapping("/me")
	@ResponseBody
	public InfosProfileResponse editUserInfo(Principal principal) {
		String username = principal.getName() ;
		InfosProfileResponse infosProfileResponse = userService.findByUsername(username);
		return infosProfileResponse;
	}

	@ApiOperation(value = "Retrieve my infos")
	@PutMapping("/me/edit")
	@ResponseBody
	public ResponseEntity<String> saveUserInfo(@RequestBody EditProfileRequest editProfileRequest, Principal principal) {

		String msg =userService.EditMyInfos(editProfileRequest,principal);
		return  new ResponseEntity<>(msg, HttpStatus.OK);

	}

	@ApiOperation(value = "Add photo")
	@PutMapping ("/me/edit/photo")
	@ResponseBody
	public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file,Principal principal) throws IOException {

		return cloudinaryService.uploadImage(file,principal);


	}


	@ApiOperation(value = "Delete my account")
	@DeleteMapping("/me/delete/account")
	@ResponseBody
	public ResponseEntity<String> deleteMyAccount( Principal principal) {


		return  userService.deleteAccount(principal);

	}





}
