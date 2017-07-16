package com.bits.services;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import com.bits.model.ViewAttendance;

public interface ViewAttendanceService {
	
	public List<ViewAttendance> getDayAttendance(Date date,int batch, int duration) throws ParseException;
}
