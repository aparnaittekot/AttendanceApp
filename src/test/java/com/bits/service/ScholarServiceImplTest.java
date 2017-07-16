package com.bits.service;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.bits.model.Scholar;
import com.bits.model.ScholarClass;
import com.bits.repository.ClassRepository;
import com.bits.repository.ScholarRepository;
import com.bits.services.ScholarService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class ScholarServiceImplTest {

	@Autowired
	private ScholarRepository scholarRepository;

	@Autowired
	private ScholarService scholarService;

	@Autowired
	private ClassRepository classRepository;

	public Scholar initScholar(String scholarId, String scholarName, String scholarPassword, String scholarType,
			Integer batch, Integer duration) {
		Scholar scholar = new Scholar();
		scholar.setBatch(batch);
		scholar.setDuration(duration);
		scholar.setScholarId(scholarId);
		scholar.setScholarName(scholarName);
		scholar.setScholarPassword(scholarPassword);
		scholar.setScholarType(scholarType);

		return scholar;
	}

	public ScholarClass initClass(Integer classId, String subjectCode, String classDate, String startTime,
			String endTime) throws Exception {

		Date date = java.sql.Date.valueOf(classDate);

		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		Time start = new Time((format.parse(startTime)).getTime());
		Time end = new Time((format.parse(endTime)).getTime());

		ScholarClass scholarClass = new ScholarClass();
		scholarClass.setClassId(classId);
		scholarClass.setClassDate(date);
		scholarClass.setEndTime(end);
		scholarClass.setStartTime(start);
		scholarClass.setSubjectCode(subjectCode);

		return scholarClass;
	}

	public Attendence initAttendance(String scholarId, Integer classId) {
		Attendence attendance = new Attendence();
		attendance.setScholarId(scholarId);
		attendance.setClassId(classId);

		return attendance;
	}
	
	@Test
	public void testServiceAddSingleScholarDetail() {
		scholarRepository.deleteAll();

		Scholar scholar = initScholar("ITest", "ABC", "1234", "1", 2016, 2);

		scholarService.save(scholar);

		org.junit.Assert.assertTrue(scholarRepository.count() == 1);
	}

	@Test
	public void testServiceGetAllScholarDetails() {
		scholarRepository.deleteAll();

		Scholar scholar1 = initScholar("ITest0", "ABC", "1234", "1", 2016, 2);
		scholarRepository.save(scholar1);
		Scholar scholar2 = initScholar("ITest1", "ABC", "1234", "1", 2016, 2);
		scholarRepository.save(scholar2);
		Scholar scholar3 = initScholar("ITest2", "ABC", "1234", "1", 2016, 2);
		scholarRepository.save(scholar3);

		List<Scholar> scholars = scholarService.findAllScholars();

		org.junit.Assert.assertTrue(scholars.size() == 3);
	}

	@Test
	public void testServiceGetAParticularScholarById() {
		scholarRepository.deleteAll();

		Scholar scholar1 = initScholar("ITest0", "ABCD", "1234", "1", 2016, 2);
		scholarRepository.save(scholar1);
		Scholar scholar2 = initScholar("ITest1", "ABCE", "1234", "1", 2016, 4);
		scholarRepository.save(scholar2);
		Scholar scholar3 = initScholar("ITest2", "ABCF", "1234", "1", 2016, 4);
		scholarRepository.save(scholar3);

		Scholar scholars = scholarService.findScholar("ITest0");

		org.junit.Assert.assertTrue(scholars.getScholarId().equals("ITest0"));
		org.junit.Assert.assertTrue(scholars.getBatch() == 2016);
		org.junit.Assert.assertTrue(scholars.getDuration() == 2);
		org.junit.Assert.assertTrue(scholars.getScholarName().equals("ABCD"));
	}

	@Test
	public void testServiceDeleteAParticularScholarDetailById() {
		scholarRepository.deleteAll();

		Scholar scholar1 = initScholar("ITest0", "ABCD", "1234", "1", 2016, 2);
		scholarRepository.save(scholar1);
		Scholar scholar2 = initScholar("ITest1", "ABCE", "1234", "1", 2016, 4);
		scholarRepository.save(scholar2);
		Scholar scholar3 = initScholar("ITest2", "ABCF", "1234", "1", 2016, 4);
		scholarRepository.save(scholar3);

		scholarService.deleteScholar("ITest2");

		org.junit.Assert.assertTrue(scholarRepository.count() == 2);
	}

	@Test
	public void testServiceDeleteAllScholarDetails() {
		scholarRepository.deleteAll();

		Scholar scholar1 = initScholar("ITest0", "ABCD", "1234", "1", 2016, 2);
		scholarRepository.save(scholar1);
		Scholar scholar2 = initScholar("ITest1", "ABCE", "1234", "1", 2016, 4);
		scholarRepository.save(scholar2);
		Scholar scholar3 = initScholar("ITest2", "ABCF", "1234", "1", 2016, 4);
		scholarRepository.save(scholar3);

		scholarService.deleteAll();

		org.junit.Assert.assertTrue(scholarRepository.count() == 0);
	}

	@Test
	public void testServiceGetScholarCount() throws Exception {
		scholarRepository.deleteAll();

		Scholar scholar1 = initScholar("ITest0", "ABCD", "1234", "1", 2016, 2);
		scholarRepository.save(scholar1);
		Scholar scholar2 = initScholar("ITest1", "ABCE", "1234", "1", 2016, 4);
		scholarRepository.save(scholar2);
		Scholar scholar3 = initScholar("ITest2", "ABCF", "1234", "1", 2016, 4);
		scholarRepository.save(scholar3);

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-14", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class3);
		
		Attendence attendance1 = initAttendance("scholar1", 101);
		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("scholar1");
		attendanceKey1.setClassId(100);
		
		int count = scholarService.getScholarCount("sub1", "scholar1");

		org.junit.Assert.assertTrue(count == 0);
	}
}
