package com.bits.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bits.model.Scholar;

public interface ScholarRepository extends CrudRepository<Scholar, String> {
	
	@Query("select u from Scholar u where u.batch=?1 and u.duration=?2")
	List<Scholar> findScholars(int batch,int duration);
	

}
