package com.bits.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
import com.bits.model.Subject;
import com.bits.repository.SubjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class SubjectControllerTest {

	@Autowired
	private SubjectRepository subjectRepo;

	@Autowired
	private SubjectController subjectController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(subjectController).setControllerAdvice(new ExceptionHandlerAdvice())
				.build();
	}

	//Add scholar details
	@Test
	public void testPostSubjectDetails() throws Exception {
		subjectRepo.deleteAll();
		Subject subject = new Subject();
		subject.setBatch(2016);
		subject.setDuration(2);
		subject.setSubjectCode("ST");
		subject.setSubjectName("Software testing");

		mockMvc.perform(post("/subject").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(subject)));

		long count = subjectRepo.count();
		org.junit.Assert.assertTrue(count == 1);
	}

	//Add multiple scholar details
	@Test
	public void testPostMultipleSubjectDetails() throws Exception {
		subjectRepo.deleteAll();
		List<Subject> subjectList = new ArrayList<Subject>();

		Subject subject1 = new Subject();
		subject1.setBatch(2016);
		subject1.setDuration(2);
		subject1.setSubjectCode("ST");
		subject1.setSubjectName("Software testing");
		subjectList.add(subject1);

		Subject subject2 = new Subject();
		subject2.setBatch(2016);
		subject2.setDuration(4);
		subject2.setSubjectCode("DW");
		subject2.setSubjectName("Data warehouse");
		subjectList.add(subject2);

		mockMvc.perform(post("/subject/multiple").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(subjectList)));

		long count = subjectRepo.count();
		org.junit.Assert.assertTrue(count == 2);
	}

	//Get all the subject details
	@Test
	public void testGetAllSubjectDetails() throws Exception {
		subjectRepo.deleteAll();

		Subject subject1 = new Subject();
		subject1.setBatch(2016);
		subject1.setDuration(2);
		subject1.setSubjectCode("ST");
		subject1.setSubjectName("Software testing");
		subjectRepo.save(subject1);

		Subject subject2 = new Subject();
		subject2.setBatch(2016);
		subject2.setDuration(4);
		subject2.setSubjectCode("DW");
		subject2.setSubjectName("Data warehouse");
		subjectRepo.save(subject2);

		mockMvc.perform(get("/subject")).andExpect(jsonPath("$[0].subjectCode").value("ST"))
				.andExpect(jsonPath("$[0].subjectName").value("Software testing"));

	}

	//Get subject details based on subject ID
	@Test
	public void testGetSubjectById() throws Exception {
		subjectRepo.deleteAll();

		Subject subject1 = new Subject();
		subject1.setBatch(2016);
		subject1.setDuration(2);
		subject1.setSubjectCode("ST");
		subject1.setSubjectName("Software testing");
		subjectRepo.save(subject1);

		Subject subject2 = new Subject();
		subject2.setBatch(2016);
		subject2.setDuration(4);
		subject2.setSubjectCode("DW");
		subject2.setSubjectName("Data warehouse");
		subjectRepo.save(subject2);

		mockMvc.perform(get("/subject/id").param("id", "ST")).andExpect(jsonPath("$.subjectCode").value("ST"))
				.andExpect(jsonPath("$.subjectName").value("Software testing"));
	}

	//Get subject details based on batch and duration
	@Test
	public void testGetSubjectByBatchAndDuration() throws Exception {
		subjectRepo.deleteAll();

		Subject subject1 = new Subject();
		subject1.setBatch(2016);
		subject1.setDuration(2);
		subject1.setSubjectCode("ST");
		subject1.setSubjectName("Software testing");
		subjectRepo.save(subject1);

		Subject subject2 = new Subject();
		subject2.setBatch(2016);
		subject2.setDuration(4);
		subject2.setSubjectCode("DW");
		subject2.setSubjectName("Data warehouse");
		subjectRepo.save(subject2);

		mockMvc.perform(get("/subject/batch").param("batch", "2016").param("duration", "2"))
				.andExpect(jsonPath("$[0].subjectCode").value("ST"))
				.andExpect(jsonPath("$[0].subjectName").value("Software testing"));
	}

	//Delete subject by ID
	@Test
	public void testDeleteSubjectById() throws Exception {
		subjectRepo.deleteAll();

		Subject subject1 = new Subject();
		subject1.setBatch(2016);
		subject1.setDuration(2);
		subject1.setSubjectCode("ST");
		subject1.setSubjectName("Software testing");
		subjectRepo.save(subject1);

		Subject subject2 = new Subject();
		subject2.setBatch(2016);
		subject2.setDuration(4);
		subject2.setSubjectCode("DW");
		subject2.setSubjectName("Data warehouse");
		subjectRepo.save(subject2);

		mockMvc.perform(delete("/subject/id").param("id", "ST"));

		long count = subjectRepo.count();
		org.junit.Assert.assertTrue(count == 1);
	}

	//Delete all subject details
	@Test
	public void testDeleteAllSubjects() throws Exception {

		subjectRepo.deleteAll();

		Subject subject1 = new Subject();
		subject1.setBatch(2016);
		subject1.setDuration(2);
		subject1.setSubjectCode("ST");
		subject1.setSubjectName("Software testing");
		subjectRepo.save(subject1);

		Subject subject2 = new Subject();
		subject2.setBatch(2016);
		subject2.setDuration(4);
		subject2.setSubjectCode("DW");
		subject2.setSubjectName("Data warehouse");
		subjectRepo.save(subject2);

		mockMvc.perform(delete("/subject"));

		long count = subjectRepo.count();
		org.junit.Assert.assertTrue(count == 0);
	}
}
