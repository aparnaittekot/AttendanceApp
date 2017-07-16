package com.bits.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {

	private static final long serialVersionUID = 4924331582341846754L;

	@Id
	@Column
	private String subjectCode;

	@Column
	private String subjectName;

	@Column
	private Integer batch;

	@Column
	private Integer duration;

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(subjectCode).append(subjectName).append(batch).append(duration)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Subject other = (Subject) obj;
		return new EqualsBuilder().append(this.subjectCode, other.subjectCode)
				.append(this.subjectName, other.subjectName).append(this.batch, other.batch)
				.append(this.duration, other.duration).isEquals();
	}
}
