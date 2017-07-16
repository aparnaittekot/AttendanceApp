package com.bits.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bits.model.Scholar;

@Service
public interface ScholarService {
	
	public void save(Scholar scholar);

	public List<Scholar> findAllScholars();

	public Scholar findScholar(String id);

	public void deleteScholar(String id);

	public void deleteAll();

	public Integer getScholarCount(String subj, String id);

}
