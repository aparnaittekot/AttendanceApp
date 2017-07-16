package com.bits.controller;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bits.model.ViewAttendance;
import com.bits.services.ViewAttendanceService;

@RestController
@RequestMapping("/viewAttendance")
public class ViewAttendanceController {
	
	@Autowired
	private ViewAttendanceService viewAttendance;
	
	@GetMapping
	public List<ViewAttendance> getDayAttendance(@RequestParam("date") String date, @RequestParam("batch") int batch,
			@RequestParam("duration") int duration) throws ParseException{
		Date date1 = java.sql.Date.valueOf(date);
		return viewAttendance.getDayAttendance(date1,batch,duration);
	}

}
