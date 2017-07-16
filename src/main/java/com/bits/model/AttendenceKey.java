package com.bits.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AttendenceKey implements Serializable {

	private static final long serialVersionUID = 4893011703125700703L;

	private String scholarId;

	private Integer classId;

	public void setScholarId(String scholarId) {
		this.scholarId = scholarId;
	}

	public String getScholarId() {
		return scholarId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getClassId() {
		return classId;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(scholarId).append(classId).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AttendenceKey other = (AttendenceKey) obj;
		return new EqualsBuilder().append(this.scholarId, other.scholarId).append(this.classId, other.classId)
				.isEquals();
	}
}
