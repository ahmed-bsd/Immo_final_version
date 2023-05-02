package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.TokenConfirmation;
import tn.esprit.spring.entities.TokenResetpwd;
import tn.esprit.spring.repository.TokenConfirmRepository;
import tn.esprit.spring.repository.TokenResetRepository;

import java.util.Optional;

///****    Developped by Ahmed bsd    ****////
@Service
@Slf4j
public class TokenResetPwdService {

	@Autowired
	TokenResetRepository tokenResetRepository;

	public void saveToken(TokenResetpwd token) {
		tokenResetRepository.save(token);
	}

	public void disableToken(String token) {
		TokenResetpwd tokenResetpwd=tokenResetRepository.findByToken(token).get();
		if (!tokenResetpwd.getExpired()){
			tokenResetpwd.setExpired(true);
			tokenResetRepository.save(tokenResetpwd);
		}
	}

	public Optional<TokenResetpwd> getToken(String token) {
		return tokenResetRepository.findByToken(token);
	}



}