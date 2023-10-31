package com.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.model.Addmore;
import com.web.repo.AddmoreRepo;

@Service
public class ViewAddmoreDetails {
	@Autowired
	AddmoreRepo addmorerepo;

	public Addmore getAddmores(@RequestParam String email) {
		Addmore add = addmorerepo.findByEmail(email);
		return add;
	}
}
