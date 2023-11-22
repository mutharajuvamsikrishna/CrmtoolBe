package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.web.model.Login;
import com.web.model.Register;
import com.web.model.Register1;
import com.web.repo.Register1Repo;
import com.web.repo.RegisterRepo;

@Service
public class UserLoginService {
	@Autowired
	RegisterRepo userLoginRepo;
	@Autowired
	Register1Repo adminLoginRepo;

	public String userLogin(@RequestBody Login login) {
		String email = login.getEmail();
		String password = login.getPassword();
		Register reg = userLoginRepo.findByEmailAndPassword(email, password);
		if (reg != null) {
			return "personaldetails1";
		} else {
			return "loginfail";
		}
	}

	public String adminLogin(@RequestBody Login login) {
		String email = login.getEmail();
		String password = login.getPassword();
		Register1 reg = adminLoginRepo.findByEmailAndPassword(email, password);
		if (reg != null) {
			return "adminlogin";
		} else {
			return "loginfail";
		}
	}
}
