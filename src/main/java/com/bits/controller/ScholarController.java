package com.bits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.model.Scholar;
import com.bits.services.ScholarService;

@RestController
@RequestMapping("/scholar")
public class ScholarController {

	@Autowired
	private ScholarService scholarBL;

	@PostMapping
	public void addScholar(@RequestBody Scholar scholar) {
		scholarBL.save(scholar);
	}

	@PostMapping("/multiple")
	public void addMultipleScholarDetails(@RequestBody List<Scholar> scholars) {
		for (Scholar scholar : scholars) {
			scholarBL.save(scholar);
		}
	}

	@GetMapping
	public List<Scholar> getAllScholars() {
		return scholarBL.findAllScholars();
	}

	@DeleteMapping("/id")
	public void deleteScholarById(@RequestParam("id") String id) {
		scholarBL.deleteScholar(id);
	}

	@DeleteMapping
	public void deleteAllScholars() {
		scholarBL.deleteAll();
	}

	@GetMapping("/id")
	public Scholar getScholarById(@RequestParam("id") String id) {
		return scholarBL.findScholar(id);
	}

	@GetMapping("/count")
	public Integer getScholarCount(@RequestParam("subj") String subj, @RequestParam("id") String id) {
		return scholarBL.getScholarCount(subj, id);
	}
}
