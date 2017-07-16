package com.bits.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bits.model.Attendence;

@Service
public interface AttendanceService {
	
	public void addAttendence(Attendence attendence);

	public List<Attendence> getAllAttendence();

	public void deleteAttendence();
}
