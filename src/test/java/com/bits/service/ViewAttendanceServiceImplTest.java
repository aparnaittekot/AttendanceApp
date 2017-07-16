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
import com.bits.model.Subject;
import com.bits.model.ViewAttendance;
import com.bits.repository.AttendanceRepository;
import com.bits.repository.ClassRepository;
import com.bits.repository.ScholarRepository;
import com.bits.repository.SubjectRepository;
import com.bits.services.ViewAttendanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class ViewAttendanceServiceImplTest {

	@Autowired
	private ScholarRepository scholarRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Autowired
	private ViewAttendanceService viewAttendanceService;

	public Subject initSubject(String subjectCode, String subjectName, Integer batch, Integer duration) {
		Subject subject = new Subject();
		subject.setBatch(batch);
		subject.setDuration(duration);
		subject.setSubjectCode(subjectCode);
		subject.setSubjectName(subjectName);

		return subject;
	}

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

	public void setUp() throws Exception {
		Scholar scholar1 = initScholar("ITest0", "ABCD", "1234", "1", 2016, 2);
		scholarRepository.save(scholar1);
		Scholar scholar2 = initScholar("ITest1", "ABCE", "1234", "1", 2016, 4);
		scholarRepository.save(scholar2);
		Scholar scholar3 = initScholar("ITest2", "ABCF", "1234", "1", 2016, 4);
		scholarRepository.save(scholar3);

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 4);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-14", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class3);
	}

	@Test
	public void testServiceAbsentForFirstClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(100);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

	@Test
	public void testServicePresentForFirstClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		Attendence attendance1 = initAttendance("ITest0", 101);
		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(101);
		attendanceRepository.save(attendance1);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

	@Test
	public void testServiceAbsentForSecondClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "11:00:00", "13:00:00");
		classRepository.save(class1);

		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(100);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

	@Test
	public void testServicePresentForSecondClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "11:00:00", "13:00:00");
		classRepository.save(class1);

		Attendence attendance1 = initAttendance("ITest0", 101);
		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(101);
		attendanceRepository.save(attendance1);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

	@Test
	public void testServiceAbsentForThirdClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "14:00:00", "16:00:00");
		classRepository.save(class1);

		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(100);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

	@Test
	public void testServicePresentForThirdClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "14:00:00", "16:00:00");
		classRepository.save(class1);

		Attendence attendance1 = initAttendance("ITest0", 101);
		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(101);
		attendanceRepository.save(attendance1);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

	@Test
	public void testServiceAbsentForFourthClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "16:00:00", "18:00:00");
		classRepository.save(class1);

		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(100);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

	@Test
	public void testServicePresentForFourthClass() throws Exception {
		scholarRepository.deleteAll();
		subjectRepository.deleteAll();
		attendanceRepository.deleteAll();
		classRepository.deleteAll();
		setUp();
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "16:00:00", "18:00:00");
		classRepository.save(class1);

		Attendence attendance1 = initAttendance("ITest0", 101);
		AttendenceKey attendanceKey1 = new AttendenceKey();
		attendanceKey1.setScholarId("ITest0");
		attendanceKey1.setClassId(101);
		attendanceRepository.save(attendance1);

		Date date = java.sql.Date.valueOf("2016-10-12");
		List<ViewAttendance> getDayAttendance = viewAttendanceService.getDayAttendance(date, 2016, 2);

		org.junit.Assert.assertTrue(getDayAttendance.size() == 1);
		org.junit.Assert.assertTrue(getDayAttendance.get(0).getScholarId().equals("ITest0"));
	}

}
