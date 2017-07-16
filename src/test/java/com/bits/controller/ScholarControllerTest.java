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
import com.bits.model.Scholar;
import com.bits.repository.ScholarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class ScholarControllerTest {

	@Autowired
	private ScholarRepository scholarRepo;

	@Autowired
	private ScholarController scholarController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(scholarController).setControllerAdvice(new ExceptionHandlerAdvice())
				.build();
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

	//Add a scholar
	@Test
	public void testPostScholarDetails() throws Exception {

		Scholar actual = initScholar("I327953", "Diptesh", "12345678", "1", 2016, 2);
		scholarRepo.deleteAll();
		Scholar actualKey = new Scholar();
		actualKey.setBatch(2016);
		actualKey.setDuration(2);
		actualKey.setScholarId("I327953");
		actualKey.setScholarName("Diptesh");
		actualKey.setScholarPassword("12345678");
		actualKey.setScholarType("1");

		mockMvc.perform(post("/scholar").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(actual)));

		System.out.println(scholarRepo.count());
		long exists = scholarRepo.count();

		org.junit.Assert.assertTrue(exists == 1);
	}

	//Add list of scholars
	@Test
	public void testPostMultipleScholarDetails() throws Exception {

		List<Scholar> scholars = new ArrayList<>();
		Scholar actual = initScholar("I327953", "Diptesh", "12345678", "1", 2016, 2);
		scholars.add(actual);
		scholarRepo.deleteAll();

		mockMvc.perform(post("/scholar/multiple").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(scholars)));

		System.out.println(scholarRepo.count());
		long exists = scholarRepo.count();

		org.junit.Assert.assertTrue(exists == 1);
	}

	//Get list of all scholars
	@Test
	public void testGetAllScholarDetails() throws Exception {

		scholarRepo.deleteAll();
		List<Scholar> scholarList = new ArrayList<Scholar>();
		Scholar scholar1 = initScholar("I327953", "Diptesh", "12345678", "1", 2016, 2);
		scholarList.add(scholar1);
		scholarRepo.save(scholar1);
		Scholar scholar2 = initScholar("I327101", "Aparna", "87654321", "1", 2016, 2);
		scholarList.add(scholar2);
		scholarRepo.save(scholar2);

		mockMvc.perform(get("/scholar")).andExpect(jsonPath("$[0].scholarId").value("I327953"))
				.andExpect(jsonPath("$[0].scholarName").value("Diptesh"))
				.andExpect(jsonPath("$[1].scholarId").value("I327101"))
				.andExpect(jsonPath("$[1].scholarName").value("Aparna"));
	}

	//Delete scholars by ID
	@Test
	public void testDeleteScholarById() throws Exception {
		scholarRepo.deleteAll();
		List<Scholar> scholarList = new ArrayList<Scholar>();
		Scholar scholar1 = initScholar("I327953", "Diptesh", "12345678", "1", 2016, 2);
		scholarList.add(scholar1);
		scholarRepo.save(scholar1);
		Scholar scholar2 = initScholar("I327101", "Aparna", "87654321", "1", 2016, 2);
		scholarList.add(scholar2);
		scholarRepo.save(scholar2);

		mockMvc.perform(delete("/scholar/id").param("id", "I327953"));

		long exists = scholarRepo.count();

		org.junit.Assert.assertTrue(exists == 1);
	}

	//Delete all scholars
	@Test
	public void testDeleteAllScholars() throws Exception {
		scholarRepo.deleteAll();
		List<Scholar> scholarList = new ArrayList<Scholar>();
		Scholar scholar1 = initScholar("I327953", "Diptesh", "12345678", "1", 2016, 2);
		scholarList.add(scholar1);
		scholarRepo.save(scholar1);
		Scholar scholar2 = initScholar("I327101", "Aparna", "87654321", "1", 2016, 2);
		scholarList.add(scholar2);
		scholarRepo.save(scholar2);

		mockMvc.perform(delete("/scholar"));

		long exists = scholarRepo.count();

		org.junit.Assert.assertTrue(exists == 0);
	}

	@Test
	public void testGetScholarById() throws Exception {

		scholarRepo.deleteAll();
		Scholar actual = initScholar("I327953", "Diptesh", "12345678", "1", 2016, 2);
		scholarRepo.save(actual);

		mockMvc.perform(get("/scholar/id").param("id", "12345678"));

		System.out.println(scholarRepo.count());
		long exists = scholarRepo.count();

		org.junit.Assert.assertTrue(exists == 1);
	}

	@Test
	public void testGetScholarCount() throws Exception {

		scholarRepo.deleteAll();
		Scholar actual = initScholar("I327953", "Diptesh", "12345678", "1", 2016, 2);
		scholarRepo.save(actual);
		Scholar actual1 = initScholar("I327953", "Aparna", "768436834", "1", 2016, 2);
		scholarRepo.save(actual1);

		mockMvc.perform(get("/scholar/count").param("subj", "sub1").param("id", "12345678")).andReturn();
	}
}
