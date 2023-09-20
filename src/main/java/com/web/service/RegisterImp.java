package com.web.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.model.Pro;
import com.web.model.Register;
import com.web.repo.ProRepo;
import com.web.repo.RegisterRepo;

@Service
public class RegisterImp implements RegisterService{

	@Autowired
	private RegisterRepo repo7;
	
	

	@Autowired
	private ProRepo prorepo;
	
	

	@Override
	public Register saveCustomer(Register customer) {
		Register save=repo7.save(customer);
		return save;
	}

	@Override
	public Register login(String email, String password) {
		Register login=repo7.findByEmailAndPassword(email,password);
		return login;
	}

	
	
	
	
	
}
