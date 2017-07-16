package com.bits.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bits.model.Subject;

public interface SubjectRepository extends CrudRepository<Subject, String> {

	@Query("select u from Subject u where u.batch=?1 and u.duration=?2")
	List<Subject> getSubjectByBatch(int batch,int duration);
}
