package com.bits.repository;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.bits.model.ScholarClass;

public interface ClassRepository extends CrudRepository<ScholarClass, Integer> {

	@Query("select u from ScholarClass u where u.classDate=?1 and ?2 between u.startTime and u.endTime")
	Iterable<ScholarClass> getPeriod(Date date1, Time time1);

	@Query("select u from ScholarClass u where u.classDate=?1")
	Iterable<ScholarClass> getPeriods(Date date1);

	@Query("select count(u) from ScholarClass u where u.subjectCode=?1")
	Integer getCount(String id);

	@Transactional
	@Modifying
	@Query("delete from ScholarClass where subjectCode=?1 and classDate=?2 and startTime=?3 and endTime=?4")
	Integer deleteClass(String subjectCode, Date date1, Time start, Time end);

	@Query("select u from ScholarClass u where u.subjectCode = ?1")
	Iterable<ScholarClass> getClasses(String subj);
}