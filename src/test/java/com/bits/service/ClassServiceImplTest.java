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
import com.bits.model.ScholarClass;
import com.bits.model.Subject;
import com.bits.repository.ClassRepository;
import com.bits.repository.SubjectRepository;
import com.bits.services.ClassService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class ClassServiceImplTest {

	@Autowired
	private ClassService classService;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SubjectRepository subjectRepository;

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

	public Subject initSubject(String subjectCode, String subjectName, Integer batch, Integer duration) {
		Subject subject = new Subject();
		subject.setBatch(batch);
		subject.setDuration(duration);
		subject.setSubjectCode(subjectCode);
		subject.setSubjectName(subjectName);

		return subject;
	}

	@Test
	public void testServiceAddClass() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "09:00:00", "11:00:00");

		classService.addClass(class1);

		org.junit.Assert.assertTrue(classRepository.count() == 1);
	}

	@Test
	public void testServiceGetAllClasses() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-14", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class3);

		List<ScholarClass> classList = classService.getAllClass();

		org.junit.Assert.assertTrue(classList.size() == 3);
	}

	@Test
	public void testServiceGetASingleClass() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-08", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-01", "09:00:00", "11:00:00");
		classRepository.save(class3);

		ScholarClass singleClass = classService.getClass(102);

		org.junit.Assert.assertTrue(singleClass.getSubjectCode().equals("sub2"));
		org.junit.Assert.assertTrue(singleClass.getClassId() == 102);
	}

	@Test
	public void testServicedeleteClassByCode() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-12", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-14", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class3);
		
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time time1 = java.sql.Time.valueOf("09:00:00");
		Time time2 = java.sql.Time.valueOf("11:00:00");
		
		classService.deleteClassByCode("sub1", date, time1, time2);
		org.junit.Assert.assertTrue(classRepository.count() == 2);
	}
 
	@Test
	public void testServiceGetPeriodForAParticularDateAndTime() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-01", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-02", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-04", "12:00:00", "14:00:00");
		classRepository.save(class3);
		ScholarClass class4 = initClass(104, "sub4", "2016-10-01", "11:00:00", "13:00:00");
		classRepository.save(class4);

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 2);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		Date date = java.sql.Date.valueOf("2016-10-02");

		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		Time time = new Time((format.parse("09:45:00")).getTime());

		ScholarClass singleClass = classService.getPeriod(date, time, 2016, 2);

		org.junit.Assert.assertTrue(singleClass.getSubjectCode().equals("sub2"));
		org.junit.Assert.assertTrue(singleClass.getClassId() == 102);
	}

	@Test
	public void testServiceGetPeriodsForAParticularDate() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-01", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-02", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-04", "12:00:00", "14:00:00");
		classRepository.save(class3);
		ScholarClass class4 = initClass(104, "sub2", "2016-10-02", "11:00:00", "13:00:00");
		classRepository.save(class4);

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 2);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		Date date = java.sql.Date.valueOf("2016-10-02");

		List<ScholarClass> singleClass = classService.getPeriods(date, 2016, 2);

		org.junit.Assert.assertTrue(singleClass.size() == 2);
		org.junit.Assert.assertTrue(singleClass.get(0).getSubjectCode().equals("sub2"));
		org.junit.Assert.assertTrue(singleClass.get(0).getClassId() == 102);
		org.junit.Assert.assertTrue(singleClass.get(1).getSubjectCode().equals("sub2"));
		org.junit.Assert.assertTrue(singleClass.get(1).getClassId() == 104);
	}

	@Test
	public void testServiceDeleteAParticularClass() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-08", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-01", "09:00:00", "11:00:00");
		classRepository.save(class3);

		classService.deleteClass(102);

		org.junit.Assert.assertTrue(classRepository.count() == 2);
	}

	@Test
	public void testServiceDeleteAllClasses() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-08", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-01", "09:00:00", "11:00:00");
		classRepository.save(class3);

		classService.deleteAllClasses();

		org.junit.Assert.assertTrue(classRepository.count() == 0);
	}

	@Test
	public void testServiceCountTheNumberOfClassesBySubjectCode() throws Exception {
		classRepository.deleteAll();

		ScholarClass class1 = initClass(101, "sub1", "2016-10-08", "09:00:00", "11:00:00");
		classRepository.save(class1);
		ScholarClass class2 = initClass(102, "sub2", "2016-10-09", "09:00:00", "11:00:00");
		classRepository.save(class2);
		ScholarClass class3 = initClass(103, "sub3", "2016-10-01", "09:00:00", "11:00:00");
		classRepository.save(class3);
		ScholarClass class4 = initClass(104, "sub1", "2016-10-08", "09:00:00", "11:00:00");
		classRepository.save(class4);

		Integer singleClass = classService.getCount("sub1");

		org.junit.Assert.assertTrue(singleClass == 2);
	}
}