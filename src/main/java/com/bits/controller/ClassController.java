package com.bits.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.model.ScholarClass;
import com.bits.model.Subject;
import com.bits.repository.ClassRepository;
import com.bits.repository.SubjectRepository;
import com.bits.services.ClassService;

@RestController
@RequestMapping("class")
public class ClassController {

	@Autowired
	private ClassService classBL;
	
	@PostMapping
	public void addClassDetails(@RequestBody ScholarClass classDetils) {
		classBL.addClass(classDetils);
	}

	@PostMapping("/multiple")
	public void addMultipleClassDetails(@RequestBody List<ScholarClass> scholarClasses) {
		for (ScholarClass scholarClass : scholarClasses) {
			classBL.addClass(scholarClass);
		}
	}

	@GetMapping
	public List<ScholarClass> getAllClasses() {
		return classBL.getAllClass();
	}

	@GetMapping("/id")
	public ScholarClass getClass(@RequestParam("id") Integer id) {
		return classBL.getClass(id);
	}

	@GetMapping("/count")
	public Integer getClassCount(@RequestParam("id") String id) {
		return classBL.getCount(id);
	}

	@GetMapping("/period")
	public ScholarClass getPeriod(@RequestParam("date") String date, @RequestParam("time") String time,
			@RequestParam("batch") int batch, @RequestParam("duration") int duration) throws ParseException {

		Date date1 = java.sql.Date.valueOf(date);
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		Time time1 = new Time((format.parse(time)).getTime());
		return classBL.getPeriod(date1, time1, batch, duration);
	}

	@GetMapping("/periods")
	public List<ScholarClass> getPeriods(@RequestParam("date") String date, @RequestParam("batch") int batch,
			@RequestParam("duration") int duration) throws ParseException {
		Date date1 = java.sql.Date.valueOf(date);
		return classBL.getPeriods(date1, batch, duration);
	}

	@DeleteMapping("/id")
	public void deleteClassById(@RequestParam("id") Integer id) {
		classBL.deleteClass(id);
	}


	@DeleteMapping
	public void deleteAllClass() {
		classBL.deleteAllClasses();
	}
	
	/*@DeleteMapping("/delete")
	public String deleteClass(@RequestBody ScholarClass classDetils) throws ParseException {

		classBL.deleteClassByCode(classDetils.getSubjectCode(), classDetils.getClassDate(), classDetils.getStartTime(),
				classDetils.getEndTime());

		return "class deleted successfully!!!";
	}*/
}
