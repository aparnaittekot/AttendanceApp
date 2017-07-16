package com.bits.services;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bits.model.AttendenceKey;
import com.bits.model.Scholar;
import com.bits.model.ScholarClass;
import com.bits.model.ViewAttendance;
import com.bits.repository.AttendanceRepository;
import com.bits.repository.ScholarRepository;

@Service
public class ViewAttendanceServiceImpl implements ViewAttendanceService {

	@Autowired
	private ClassService classBL;

	@Autowired
	private ScholarRepository scholarRepo;

	@Autowired
	private AttendanceRepository attendanceRepo;

	public List<ViewAttendance> getDayAttendance(Date date, int batch, int duration) throws ParseException {
		List<ViewAttendance> dayAttendance = new ArrayList<ViewAttendance>();
		ViewAttendance att;
		
		List<Scholar> scholars = scholarRepo.findScholars(batch, duration);
		for (Scholar scholar : scholars) {
			
			att=new ViewAttendance();
			att.setScholarId(scholar.getScholarId());
			att.setScholarName(scholar.getScholarName());
			att.setPeriod1("No Class");
			att.setPeriod2("No Class");
			att.setPeriod3("No Class");
			att.setPeriod4("No Class");
			DateFormat format = new SimpleDateFormat("HH:mm:ss");
			Time time1 = new Time((format.parse("09:00:00")).getTime());
			Time time2 = new Time((format.parse("11:00:00")).getTime());
			Time time3 = new Time((format.parse("14:00:00")).getTime());
			Time time4 = new Time((format.parse("16:00:00")).getTime());
			List<ScholarClass> sClasses = classBL.getPeriods(date, batch, duration);
			for (ScholarClass sClass : sClasses) {
				
				AttendenceKey aKey = new AttendenceKey();
				aKey.setScholarId(scholar.getScholarId());
				aKey.setClassId(sClass.getClassId());
				if(sClass.getStartTime().compareTo(time1) == 0){
					if(attendanceRepo.exists(aKey)==true)
						att.setPeriod1("Present");
					else
						att.setPeriod1("Absent");
				}
				else if(sClass.getStartTime().compareTo(time2) == 0){
					if(attendanceRepo.exists(aKey)==true)
						att.setPeriod2("Present");
					else
						att.setPeriod2("Absent");
				}
				else if(sClass.getStartTime().compareTo(time3) == 0){
					if(attendanceRepo.exists(aKey)==true)
						att.setPeriod3("Present");
					else
						att.setPeriod3("Absent");
				}
				else if(sClass.getStartTime().compareTo(time4) == 0){
					if(attendanceRepo.exists(aKey)==true)
						att.setPeriod4("Present");
					else
						att.setPeriod4("Absent");
				}
			}
			dayAttendance.add(att);
		}
		return dayAttendance;
	}
}
