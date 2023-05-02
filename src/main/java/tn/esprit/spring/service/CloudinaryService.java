package tn.esprit.spring.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.repository.UserRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

///****    Developped by Ahmed bsd    ****////
@AllArgsConstructor
@Service
@Slf4j
public class CloudinaryService {


	@Autowired
	Cloudinary cloudinary;
	@Autowired
	UserRepository userRepository;

	/*public CloudinaryService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}*/

	public ResponseEntity<String> uploadImage(MultipartFile file, Principal principal) throws IOException {
		String username = principal.getName();
		// double check if the user is present
		if ( userRepository.findByUserName(username).isPresent()) {

			UserA user = userRepository.findByUserName(username).get();
			String oldImageUrl = user.getImageUrl();

			String publicId = getPublicIdFromUrl(oldImageUrl);
			cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
		    user.setImageUrl(uploadResult.get("secure_url").toString());
			userRepository.save(user);
		    return  new ResponseEntity<>("photo updated", HttpStatus.OK);
		}
		return new ResponseEntity<>("something wents wrong" , HttpStatus.BAD_REQUEST);
	}

	private String getPublicIdFromUrl(String url) {
		String[] splitUrl = url.split("/");
		String publicIdWithExtension = splitUrl[splitUrl.length - 1];
		return publicIdWithExtension.substring(0, publicIdWithExtension.lastIndexOf("."));
	}



}