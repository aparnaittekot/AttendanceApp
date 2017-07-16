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
import com.bits.model.Subject;
import com.bits.repository.SubjectRepository;
import com.bits.services.SubjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { AttendanceSystemApplication.class, AppTestConfig.class })
@WebAppConfiguration
public class SubjectServiceImplTest {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private SubjectRepository subjectRepository;

	public Subject initSubject(String subjectCode, String subjectName, Integer batch, Integer duration) {
		Subject subject = new Subject();
		subject.setBatch(batch);
		subject.setDuration(duration);
		subject.setSubjectCode(subjectCode);
		subject.setSubjectName(subjectName);

		return subject;
	}

	@Test
	public void testServiceAddSingleSubject() {
		subjectRepository.deleteAll();

		Subject subject = initSubject("sub1", "subN1", 2016, 2);

		subjectService.addSubject(subject);

		org.junit.Assert.assertTrue(subjectRepository.count() == 1);
	}

	@Test
	public void testServiceGetAllSubjects() {
		subjectRepository.deleteAll();

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 4);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		List<Subject> subjectList = subjectService.getAllSubjects();

		org.junit.Assert.assertTrue(subjectList.size() == 3);
	}

	@Test
	public void testServiceGetAParticularSubjectById() {
		subjectRepository.deleteAll();

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 4);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		Subject subjectList = subjectService.getSubject("sub1");

		org.junit.Assert.assertTrue(subjectList.getSubjectCode().equals("sub1"));
		org.junit.Assert.assertTrue(subjectList.getSubjectName().equals("subN1"));
	}

	@Test
	public void testServiceDeleteAParticularSubjectById() {
		subjectRepository.deleteAll();

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 4);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		subjectService.deleteSubject("sub1");

		org.junit.Assert.assertTrue(subjectRepository.count() == 2);
	}

	@Test
	public void testServiceDeleteAllSubjects() {
		subjectRepository.deleteAll();

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 4);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		subjectService.deleteAll();

		org.junit.Assert.assertTrue(subjectRepository.count() == 0);
	}

	@Test
	public void testServiceGetSubjectsByBatch() {
		subjectRepository.deleteAll();

		Subject subject1 = initSubject("sub1", "subN1", 2016, 2);
		subjectRepository.save(subject1);
		Subject subject2 = initSubject("sub2", "subN2", 2016, 4);
		subjectRepository.save(subject2);
		Subject subject3 = initSubject("sub3", "subN3", 2016, 2);
		subjectRepository.save(subject3);

		List<Subject> subjectList = subjectService.getSubjectByBatch(2016, 2);

		org.junit.Assert.assertTrue(subjectList.size() == 2);
	}
}
