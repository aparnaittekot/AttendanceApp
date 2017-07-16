package com.bits.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.model.Subject;
import com.bits.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;

	public void addSubject(Subject subject) {
		subjectRepo.save(subject);
	}

	public List<Subject> getAllSubjects() {
		Iterable<Subject> studentDetails = subjectRepo.findAll();
		List<Subject> subjectDetails = new ArrayList<>();
		for (Subject subject : studentDetails) {
			subjectDetails.add(subject);
		}
		return subjectDetails;
	}

	public Subject getSubject(String id) {
		return subjectRepo.findOne(id);
	}

	public void deleteSubject(String id) {
		subjectRepo.delete(id);
	}

	public void deleteAll() {
		subjectRepo.deleteAll();
	}

	public List<Subject> getSubjectByBatch(int batch, int duration) {
		return subjectRepo.getSubjectByBatch(batch, duration);
	}
}
