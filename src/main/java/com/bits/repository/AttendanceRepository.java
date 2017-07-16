package com.bits.repository;

import org.springframework.data.repository.CrudRepository;

import com.bits.model.Attendence;
import com.bits.model.AttendenceKey;

public interface AttendanceRepository extends CrudRepository<Attendence, AttendenceKey> {
}