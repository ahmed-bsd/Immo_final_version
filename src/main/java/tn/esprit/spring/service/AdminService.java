package tn.esprit.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.UserA;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.IAdminService;

import java.util.List;

///****    Developped by Ahmed bsd    ****////
@Service
@Slf4j
public class AdminService implements IAdminService {

	@Autowired
	UserRepository userRepository;


	@Override
	public List<UserA> retrieveAllSellers() {
		return userRepository.findAllByRoleIs("ADMIN").get();
	}





}