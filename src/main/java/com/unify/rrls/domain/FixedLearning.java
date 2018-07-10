package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "fixed_learning")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FixedLearning extends AbstractAuditingEntity implements Serializable{
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		
		@Column(name = "subject")
		private String subject;
		
		@Column(name = "description")
		private String description;
		
		@Transient
		@JsonProperty
		private List<FixedLearningMapping> fixedLearningMapping;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		public List<FixedLearningMapping> getFixedLearningMapping() {
			return fixedLearningMapping;
		}
		public void setFixedLearningMapping(List<FixedLearningMapping> fixedLearningMapping) {
			this.fixedLearningMapping = fixedLearningMapping;
		}


}
