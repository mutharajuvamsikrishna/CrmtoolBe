package com.web.service;

import java.util.List;

import com.web.model.Pro;
import com.web.model.Register;

public interface RegisterService {
	public Register saveCustomer(Register customer);
	public Register login(String email,String password);

	
}


