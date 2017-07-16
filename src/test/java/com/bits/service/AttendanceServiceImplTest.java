package com.bits.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bits.AttendanceSystemApplication;
import com.bits.config.AppTestConfig;
import com.bits.model.Attendence;
import com.bits.model.AttendenceKey;
import com.bits.repository.AttendanceRepository;
import com.bits.services.AttendanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class AttendanceServiceImplTest {
 
	@Autowired
	private AttendanceRepository attendanceRepo;

	@Autowired
	private AttendanceService attendenceService;

	public Attendence initAttendance(String scholarId, Integer classId) {
		Attendence attendance = new Attendence();
		attendance.setScholarId(scholarId);
		attendance.setClassId(classId);

		return attendance;
	}

	@Test
	public void testServiceAddAttendenceOfAScholar() {
		attendanceRepo.deleteAll();
		
		Attendence attendance1 = initAttendance("scholar1", 100);
		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("scholar1");
		attendanceKey1.setClassId(100);

		attendenceService.addAttendence(attendance1);

		Boolean exists = attendanceRepo.exists(attendanceKey1);
		org.junit.Assert.assertTrue(exists);
	}

	@Test
	public void testServiceGetAllAttendenceOfScholars() {
		attendanceRepo.deleteAll();
		
		Attendence attendance1 = initAttendance("scholar1", 100);
		attendanceRepo.save(attendance1);
		Attendence attendance2 = initAttendance("scholar2", 200);
		attendanceRepo.save(attendance2);

		List<Attendence> attendanceList = attendenceService.getAllAttendence();

		org.junit.Assert.assertTrue(attendanceList.size() == 2);
	}
 
	@Test
	public void testServiceGetDeleteAllAttendenceOfScholars() {
		attendanceRepo.deleteAll();
		
		Attendence attendance1 = initAttendance("scholar1", 100);
		attendanceRepo.save(attendance1);
		Attendence attendance2 = initAttendance("scholar2", 200);
		attendanceRepo.save(attendance2);
		Attendence attendance3 = initAttendance("scholar3", 300);
		attendanceRepo.save(attendance3);

		attendenceService.deleteAttendence();

		org.junit.Assert.assertTrue(attendanceRepo.count() == 0);
	}
}
