package com.bits.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.model.Attendence;
import com.bits.repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceRepository attendenceRepo;

	public void addAttendence(Attendence attendence) {
		attendenceRepo.save(attendence);
	}

	public List<Attendence> getAllAttendence() {
		Iterable<Attendence> scholarAttendence = attendenceRepo.findAll();
		List<Attendence> attendenceDetails = new ArrayList<>();
		for (Attendence attendence : scholarAttendence) {
			attendenceDetails.add(attendence);
		}
		return attendenceDetails;
	}

	public void deleteAttendence() {
		attendenceRepo.deleteAll();
	}
}
