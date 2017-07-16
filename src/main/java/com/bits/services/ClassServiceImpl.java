package com.bits.services;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.model.ScholarClass;
import com.bits.model.Subject;
import com.bits.repository.ClassRepository;
import com.bits.repository.SubjectRepository;

@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	private ClassRepository classRepo;

	@Autowired
	private SubjectRepository subjectRepo;

	public void addClass(ScholarClass scholarClass) {
		classRepo.save(scholarClass);
	}

	public List<ScholarClass> getAllClass() {
		Iterable<ScholarClass> scholarClass = classRepo.findAll();
		List<ScholarClass> scholarDetails = new ArrayList<>();
		for (ScholarClass scholar : scholarClass) {
			scholarDetails.add(scholar);
		}
		return scholarDetails;
	}

	public ScholarClass getClass(int id) {
		return classRepo.findOne(id);
	}

	public ScholarClass getPeriod(Date date1, Time time1, int batch, int duration) {
		ScholarClass sClass = null;
		Iterable<ScholarClass> scholarClass = classRepo.getPeriod(date1, time1);
		for (ScholarClass scholar : scholarClass) {
			Subject sub = subjectRepo.findOne(scholar.getSubjectCode());
			if (sub != null)
				if (sub.getBatch() == batch && sub.getDuration() == duration)
					sClass = scholar;
		}
		return sClass;
	}

	public List<ScholarClass> getPeriods(Date date1, int batch, int duration) {

		Iterable<ScholarClass> scholarClass = classRepo.getPeriods(date1);
		List<ScholarClass> scholarClasses = new ArrayList<ScholarClass>();
		for (ScholarClass sClass : scholarClass) {
			Subject sub = subjectRepo.findOne(sClass.getSubjectCode());
			if (sub != null)
				if (sub.getBatch() == batch && sub.getDuration() == duration)
					scholarClasses.add(sClass);
		}
		return scholarClasses;
	}

	public void deleteClass(int id) {
		classRepo.delete(id);
	}

	public void deleteAllClasses() {
		classRepo.deleteAll();
	}

	public Integer getCount(String id) {
		return classRepo.getCount(id);
	}

	@Override
	public void deleteClassByCode(String subjectCode, Date date1, Time start, Time end) {
		classRepo.deleteClass(subjectCode, date1, start, end);
	}

}
