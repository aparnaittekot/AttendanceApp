package com.bits.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@IdClass(AttendenceKey.class)
@NoArgsConstructor
@AllArgsConstructor
public class Attendence implements Serializable {

	private static final long serialVersionUID = 4924331582341846754L;

	@Id
	@Column
	private String scholarId;

	@Id
	@Column
	private Integer classId;

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
		final Attendence other = (Attendence) obj;
		return new EqualsBuilder().append(this.scholarId, other.scholarId).append(this.classId, other.classId)
				.isEquals();
	}
}
