package com.bits.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.bits.model.Attendence;
import com.bits.model.AttendenceKey;
import com.bits.repository.AttendanceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class AttendanceControllerTest {

	@Autowired
	private AttendanceRepository attendanceRepo;

	@Autowired
	private AttendanceController attendanceController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(attendanceController)
				.setControllerAdvice(new ExceptionHandlerAdvice()).build();
	}

	public Attendence initAttendance(String scholarId, Integer classId) {
		Attendence attendance = new Attendence();
		attendance.setScholarId(scholarId);
		attendance.setClassId(classId);

		return attendance;
	}

	@Test
	public void testPostAttendenceDetails() throws Exception {
		attendanceRepo.deleteAll();
		Attendence actual = initAttendance("scholar1", 100);

		AttendenceKey actualKey = new AttendenceKey();
		actualKey.setScholarId("scholar1");
		actualKey.setClassId(100);
		
		mockMvc.perform(post("/attendence").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(actual)));

		long exists = attendanceRepo.count();

		org.junit.Assert.assertTrue(exists == 1);
	}

	@Test
	public void testPostMultipleAttendenceDetails() throws Exception {
		attendanceRepo.deleteAll();
		List<Attendence> attendence = new ArrayList<>();
		Attendence actual = initAttendance("scholar1", 100);
		attendence.add(actual);
		
		AttendenceKey actualKey = new AttendenceKey();
		actualKey.setScholarId("scholar1");
		actualKey.setClassId(100);

		mockMvc.perform(post("/attendence/multiple").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(attendence)));

		long exists = attendanceRepo.count();

		org.junit.Assert.assertTrue(exists == 1);
	}

	@Test
	public void testGetAllScholarsAttendance() throws Exception {
		attendanceRepo.deleteAll();
		List<Attendence> attendanceList = new ArrayList<Attendence>();
		Attendence attendence1 = initAttendance("scholar1", 100);
		attendanceRepo.save(attendence1);
		attendanceList.add(attendence1);
		Attendence attendence2 = initAttendance("scholar2", 200);
		attendanceRepo.save(attendence2);
		attendanceList.add(attendence2);

		mockMvc.perform(get("/attendence")).andExpect(jsonPath("$[0].scholarId").value("scholar1"))
				.andExpect(jsonPath("$[0].classId").value(100)).andExpect(jsonPath("$[1].scholarId").value("scholar2"))
				.andExpect(jsonPath("$[1].classId").value(200));

	}

	@Test
	public void testDeleteAllAttendenceDetails() throws Exception {
		attendanceRepo.deleteAll();
		Attendence actual = initAttendance("scholar1", 100);

		AttendenceKey actualKey = new AttendenceKey();
		actualKey.setScholarId("scholar1");
		actualKey.setClassId(100);

		mockMvc.perform(delete("/attendence").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsBytes(actual)));

		long exists = attendanceRepo.count();
		org.junit.Assert.assertTrue(exists == 0);
	}

}