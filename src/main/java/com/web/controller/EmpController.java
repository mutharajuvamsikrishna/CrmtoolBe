package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.model.MyClass;
import com.web.model.Pro;
import com.web.repo.ProRepo;
import com.web.repo.RegisterRepo;

@RestController
@CrossOrigin(origins = MyClass.BASE_URL)
public class EmpController {

	@Autowired
	private ProRepo repo1;

	@Autowired
	private RegisterRepo repo5;

	@PostMapping("/prosave") // save professional details
	public String profesionalsave(@RequestBody Pro emp, Model model) {

		repo1.save(emp);

		return "personaldetails";

	}

}
