package com.bits.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.model.AttendenceKey;
import com.bits.model.Scholar;
import com.bits.model.ScholarClass;
import com.bits.repository.AttendanceRepository;
import com.bits.repository.ClassRepository;
import com.bits.repository.ScholarRepository;

@Service
public class ScholarServiceImpl implements ScholarService{
	
	@Autowired 
	private ScholarRepository scholarRepo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private AttendanceRepository attendanceRepo;
	
	public void save(Scholar scholar){
		scholarRepo.save(scholar);
	}
	
	public List<Scholar> findAllScholars(){
		Iterable<Scholar> studentDetails = scholarRepo.findAll();
		List<Scholar> scholarDetails = new ArrayList<>();
		for (Scholar scholar : studentDetails) {
			scholarDetails.add(scholar);
		}
		return scholarDetails;
	}
	
	/*public List<Scholar> findScholars(int batch,int duration){
		return scholarRepo.findScholars(batch,duration);
	}
	*/
	public Scholar findScholar(String id){
		return scholarRepo.findOne(id);
	}
	
	public void deleteScholar(String id){
		scholarRepo.delete(id);
	}
	
	public void deleteAll(){
		scholarRepo.deleteAll();
	}

	public Integer getScholarCount(String subj, String id) {
		Iterable<ScholarClass> classes =  classRepo.getClasses(subj);
		int count=0;
		for (ScholarClass sClass : classes){
			int classId = sClass.getClassId();
			AttendenceKey aKey=new AttendenceKey();
			aKey.setClassId(classId);
			aKey.setScholarId(id);
			if(attendanceRepo.exists(aKey)==true)
				count++;
		}		
		return count;
	}
	
	
	

}
