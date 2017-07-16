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

import com.bits.model.Subject;
import com.bits.services.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectBL;

	@PostMapping
	public void addSubject(@RequestBody Subject subject) {
		subjectBL.addSubject(subject);
	}

	@PostMapping("/multiple")
	public void addMultipleSubjectDetails(@RequestBody List<Subject> subjects) {
		for (Subject subject : subjects) {
			subjectBL.addSubject(subject);
		}
	}

	@GetMapping
	public List<Subject> getAllSubjects() {
		return subjectBL.getAllSubjects();
	}

	@GetMapping("/id")
	public Subject getsubjectById(@RequestParam("id") String id) {
		return subjectBL.getSubject(id);
	}

	@GetMapping("/batch")
	public List<Subject> getSubjectByBatch(@RequestParam("batch") int batch, @RequestParam("duration") int duration) {
		return subjectBL.getSubjectByBatch(batch, duration);
	}

	@DeleteMapping("/id")
	public void deleteSubjectById(@RequestParam("id") String id) {
		subjectBL.deleteSubject(id);
	}

	@DeleteMapping
	public void deleteAllSubjects() {
		subjectBL.deleteAll();
	}
}
