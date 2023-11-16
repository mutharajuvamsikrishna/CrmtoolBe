package com.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.model.Pro;
import com.web.repo.ProRepo;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProController {

	@Autowired
	private ProRepo proRepo;
	@Autowired
	private ProRepo repo1;

	@GetMapping("/req")

	public List<Pro> getPros() {
		return repo1.findAll();
	}

	@GetMapping("/search")

	public List<Pro> searchPro(@RequestParam("query") String query, Model model) {
		List<Pro> users1 = proRepo.searchPro(query);
		model.addAttribute("users1", users1);
		return users1;
	}

	@DeleteMapping(value = "/delete") // edited /per455
	public String delete(@RequestParam Long id) {
		repo1.deleteById(id);

		return "Deleted Sucess Fully";

	}

	@PutMapping("/usereditupdate")
	public String updateUserEdit(@RequestBody Pro pro) {
		Long id = pro.getId();
		Optional<Pro> pro1 = repo1.findById(id);
		if (pro1 != null) {
			repo1.save(pro);
		}
		return "updated successfullly";
	}

}
