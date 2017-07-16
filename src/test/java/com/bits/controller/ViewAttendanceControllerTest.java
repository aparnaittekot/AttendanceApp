package com.bits.controller;

import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bits.AttendanceSystemApplication;
import com.bits.config.AppTestConfig;
import com.bits.exceptions.ExceptionHandlerAdvice;
import com.bits.model.Attendence;
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
public class ViewAttendanceControllerTest {

	@Autowired
	private AttendanceRepository attendanceRepo;

	@Autowired
	private ScholarRepository scholarRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private ViewAttendanceController viewAttendanceController = spy(new ViewAttendanceController());

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(viewAttendanceController)
				.setControllerAdvice(new ExceptionHandlerAdvice()).build();

		Scholar scholar1 = initScholar("I327101", "Aparna", "1234", "1", 2016, 2);
		scholarRepository.save(scholar1);
		Scholar scholar2 = initScholar("I327953", "Diptesh", "1234", "1", 2016, 4);
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

	public Attendence initAttendance(String scholarId, Integer classId) {
		Attendence attendance = new Attendence();
		attendance.setScholarId(scholarId);
		attendance.setClassId(classId);

		return attendance;
	}

	public Subject initSubject(String subjectCode, String subjectName, Integer batch, Integer duration) {
		Subject subject = new Subject();
		subject.setBatch(batch);
		subject.setDuration(duration);
		subject.setSubjectCode(subjectCode);
		subject.setSubjectName(subjectName);

		return subject;
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

	public ViewAttendance initViewAttendance(String scholarName, String scholarId, String Period1, String Period2,
			String Period3, String Period4) {
		ViewAttendance viewAttendance = new ViewAttendance();
		viewAttendance.setPeriod1(Period1);
		viewAttendance.setPeriod2(Period2);
		viewAttendance.setPeriod3(Period3);
		viewAttendance.setPeriod4(Period4);
		viewAttendance.setScholarId(scholarId);
		viewAttendance.setScholarName(scholarName);
		return viewAttendance;
	}

	@Test
	public void getDayAttendance() throws Exception {

		Date date = new Date(System.currentTimeMillis());
		System.out.println(date);
		List<ViewAttendance> viewAttendanceList = new ArrayList<ViewAttendance>();
		ViewAttendance attendence1 = initViewAttendance("Diptesh", "I327953", "Present", "Present", "Present",
				"Present");
		Attendence actual = initAttendance("I327953", 100);
		attendanceRepo.save(actual);
		viewAttendanceList.add(attendence1);
		ViewAttendance attendence2 = initViewAttendance("Aparna", "I327101", "Present", "Absent", "Present", "Present");
		Attendence actual1 = initAttendance("I327101", 100);
		attendanceRepo.save(actual1);
		viewAttendanceList.add(attendence2);

		mockMvc.perform(
				get("/viewAttendance").param("date", "2016-10-12").param("batch", "2016").param("duration", "2"))
				.andExpect(jsonPath("$[0].scholarId").value("I327101"))
				.andExpect(jsonPath("$[0].scholarName").value("Aparna"));
	}

}
