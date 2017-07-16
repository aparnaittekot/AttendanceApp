package com.bits.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bits.AttendanceSystemApplication;
import com.bits.config.AppTestConfig;
import com.bits.exceptions.ExceptionHandlerAdvice;
import com.bits.model.ScholarClass;
import com.bits.model.Subject;
import com.bits.repository.ClassRepository;
import com.bits.repository.SubjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class ClassControllerTest {

	@Autowired
	private ClassRepository classRepo;

	@Autowired
	private ClassController classController;

	@Autowired
	private SubjectRepository subjectRepo;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(classController).setControllerAdvice(new ExceptionHandlerAdvice())
				.build();
	}

	public ScholarClass initScholarClass(String subjectCode, Integer classId, Date classDate, Time startTime,
			Time endTime) {
		ScholarClass scholarClass = new ScholarClass();
		scholarClass.setClassDate(classDate);
		scholarClass.setClassId(classId);
		scholarClass.setEndTime(endTime);
		scholarClass.setStartTime(startTime);
		scholarClass.setSubjectCode(subjectCode);

		return scholarClass;
	}

	@Test
	public void testDeleteClass() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		classRepo.deleteAll();
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(scholarClass1);
		classRepo.save(scholarClass1);
		ScholarClass attendence2 = initScholarClass("SPM", 101, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence2);
		ScholarClass attendence3 = initScholarClass("STM", 102, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence3);

		mockMvc.perform(delete("/class/delete").content(new ObjectMapper().writeValueAsBytes(scholarClass1)))
				.andReturn();

		long exists = classRepo.count();

		org.junit.Assert.assertTrue(exists == 3);
	}

	@Test
	public void testDeleteAllClasses() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(scholarClass1);
		classRepo.save(scholarClass1);
		ScholarClass attendence2 = initScholarClass("SPM", 101, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence2);

		mockMvc.perform(delete("/class"));

		long exists = classRepo.count();

		org.junit.Assert.assertTrue(exists == 0);
	}

	@Test
	public void testPostClassDetails() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		classRepo.deleteAll();
		ScholarClass actual = initScholarClass("OOAD", 100, date, startTime, endTime);
		ScholarClass actualKey = new ScholarClass();
		actual.setClassId(999);
		actualKey.setClassDate(date);
		actualKey.setClassId(100);
		actualKey.setEndTime(endTime);
		actualKey.setStartTime(startTime);
		actualKey.setSubjectCode("OOAD");

		mockMvc.perform(post("/class").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(actual)));

		long exists = classRepo.count();

		org.junit.Assert.assertTrue(exists == 1);
	}

	@Test
	public void testPostMultipleClassDetails() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		classRepo.deleteAll();
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		ScholarClass actual = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(actual);
		ScholarClass actual1 = initScholarClass("ST", 102, date, startTime, endTime);
		scholarClassList.add(actual1);

		mockMvc.perform(post("/class/multiple").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(scholarClassList)));

		long exists = classRepo.count();

		org.junit.Assert.assertTrue(exists == 2);
	}

	@Test
	public void testGetAllClassDetails() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(scholarClass1);
		classRepo.save(scholarClass1);
		ScholarClass attendence2 = initScholarClass("SPM", 101, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence2);

		mockMvc.perform(get("/class")).andExpect(jsonPath("$[0].subjectCode").value("OOAD"))
				.andExpect(jsonPath("$[0].classId").value(100)).andExpect(jsonPath("$[1].subjectCode").value("SPM"))
				.andExpect(jsonPath("$[1].classId").value(101));
	}

	@Test
	public void testGetClassById() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		classRepo.save(scholarClass1);

		mockMvc.perform(get("/class/id").param("id", "100")).andExpect(jsonPath("subjectCode").value("OOAD"))
				.andExpect(jsonPath("classId").value(100));
	}

	@Test
	public void testGetClassCountById() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		classRepo.deleteAll();
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(scholarClass1);
		classRepo.save(scholarClass1);
		ScholarClass attendence2 = initScholarClass("SPM", 101, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence2);

		mockMvc.perform(get("/class/count").param("id", "100")).andReturn();
	}

	@Test
	public void testGetPeriod() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(scholarClass1);
		classRepo.save(scholarClass1);
		ScholarClass attendence2 = initScholarClass("SPM", 101, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence2);

		mockMvc.perform(get("/class/period").param("date", "2016-10-12").param("time", "09:00:00")
				.param("batch", "2016").param("duration", "2")).andExpect(jsonPath("subjectCode").value("ST"));
	}

	@Test
	public void testGetPeriods() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(scholarClass1);
		classRepo.save(scholarClass1);
		ScholarClass attendence2 = initScholarClass("SPM", 101, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence2);

		subjectRepo.deleteAll();
		Subject subject1 = new Subject();
		subject1.setBatch(2016);
		subject1.setDuration(2);
		subject1.setSubjectCode("ST");
		subject1.setSubjectName("Software testing");
		subjectRepo.save(subject1);

		mockMvc.perform(get("/class/periods").param("date", "2016-10-12").param("time", "09:00:00")
				.param("batch", "2016").param("duration", "2"));
	}

	@Test
	public void testDeleteClassById() throws Exception {
		Date date = java.sql.Date.valueOf("2016-10-12");
		Time startTime = java.sql.Time.valueOf("09:00:00");
		Time endTime = java.sql.Time.valueOf("11:00:00");
		List<ScholarClass> scholarClassList = new ArrayList<ScholarClass>();
		classRepo.deleteAll();
		ScholarClass scholarClass1 = initScholarClass("OOAD", 100, date, startTime, endTime);
		scholarClassList.add(scholarClass1);
		classRepo.save(scholarClass1);
		ScholarClass attendence2 = initScholarClass("SPM", 101, date, startTime, endTime);
		scholarClassList.add(attendence2);
		classRepo.save(attendence2);

		mockMvc.perform(delete("/class/id").param("id", "100"));

		long exists = classRepo.count();

		org.junit.Assert.assertTrue(exists == 1);

	}

}