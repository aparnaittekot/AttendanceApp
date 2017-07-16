package com.bits.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bits.model.Attendence;
import com.bits.services.AttendanceService;

@RestController
@RequestMapping("attendence")
public class AttendanceController {
                   
	@Autowired
	private AttendanceService attendenceBL;
 
	@PostMapping
	public void addAttendenceDetails(@RequestBody Attendence attendence) {
		attendenceBL.addAttendence(attendence);
	}

	@PostMapping("/multiple")
	public void addMultipleAttendenceDetails(@RequestBody List<Attendence> attendence) {
		for (Attendence att : attendence) {
			attendenceBL.addAttendence(att);
		}
	}

	@GetMapping
	public List<Attendence> getAllScholarsAttendance() {
		return attendenceBL.getAllAttendence();
	}

	@DeleteMapping
	public void deleteAllAttendence() {
		attendenceBL.deleteAttendence();
	}

}
