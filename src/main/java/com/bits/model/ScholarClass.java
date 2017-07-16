package com.bits.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "SUBJECTID", "DAY", "YEAR", "MONTH" }) })
@NoArgsConstructor
@AllArgsConstructor
public class ScholarClass implements Serializable {

	private static final long serialVersionUID = 4924331582341846754L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer classId;

	@Column
	private String subjectCode;

	@Column
	private Date classDate;

	@Column
	private Time startTime;

	@Column
	private Time endTime;

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(classId).append(subjectCode).append(classDate).append(startTime)
				.append(endTime).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ScholarClass other = (ScholarClass) obj;
		return new EqualsBuilder().append(this.classId, other.classId).append(this.subjectCode, other.subjectCode)
				.append(this.classDate, other.classDate).append(this.startTime, other.startTime)
				.append(this.endTime, other.endTime).isEquals();
	}
}
