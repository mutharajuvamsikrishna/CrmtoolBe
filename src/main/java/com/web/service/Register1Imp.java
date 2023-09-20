package com.web.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.web.model.Register1;
import com.web.repo.Register1Repo;
@Service
public class Register1Imp implements Register1Service {
	@Autowired
	private Register1Repo repo8;

	@Override
	public Register1 saveCustomer(Register1 customer) {
		Register1 save=repo8.save(customer);
		return save;
	}

	@Override
	public Register1 login(String id, String password) {
		Register1 login=repo8.findByIdAndPassword(id, password);
		return login;
		
		
		
		
		
		
		
		
		
	}

	public Register1 login1(String id, String password, String email, String mob, String ename, String cnpassword) {
        Register1 login1 = repo8.findByEmail(email);
       
            return login1;
        }
    
}
