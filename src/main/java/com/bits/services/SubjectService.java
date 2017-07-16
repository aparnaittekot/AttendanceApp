package com.bits.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bits.model.Subject;

@Service
public interface SubjectService {

	public void addSubject(Subject subject);
	
	public List<Subject> getAllSubjects();
	
	public Subject getSubject(String id);
	
	public void deleteSubject(String id);
	
	public void deleteAll();
	
	public List<Subject> getSubjectByBatch(int batch,int duration);
}
