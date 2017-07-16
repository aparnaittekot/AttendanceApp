package com.bits.services;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bits.model.ScholarClass;

@Service
public interface ClassService {

	public void addClass(ScholarClass scholarClass);

	public List<ScholarClass> getAllClass();

	public ScholarClass getClass(int id);
	
	public Integer getCount(String id);

	public ScholarClass getPeriod(Date date1, Time time1, int batch, int duration);
	
	public List<ScholarClass> getPeriods(Date date1, int batch, int duration);

	public void deleteClass(int id);

	public void deleteAllClasses();

	public void deleteClassByCode(String subjectCode, Date date1, Time start, Time end);
}
