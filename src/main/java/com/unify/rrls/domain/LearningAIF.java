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
@Table(name = "learning_aif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LearningAIF extends AbstractAuditingEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "subject")
	private String subject;

	@Transient
	@JsonProperty
	private List<LearningAIFMapping> learningAIFMapping;

	@Transient
	@JsonProperty
	private List<OpportunityMaster> opportunityMaster;
	
	@Transient
	@JsonProperty
	private List<OpportunityMaster> removeOpportunityMaster;

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
	
	public List<LearningAIFMapping> getLearningAIFMapping() {
		return learningAIFMapping;
	}

	public void setLearningAIFMapping(List<LearningAIFMapping> learningAIFMapping) {
		this.learningAIFMapping = learningAIFMapping;
	}

	public List<OpportunityMaster> getOpportunityMaster() {
		return opportunityMaster;
	}

	public void setOpportunityMaster(List<OpportunityMaster> opportunityMaster) {
		this.opportunityMaster = opportunityMaster;
	}
	public List<OpportunityMaster> getRemoveOpportunityMaster() {
		return removeOpportunityMaster;
	}
	public void setRemoveOpportunityMaster(List<OpportunityMaster> removeOpportunityMaster) {
		this.removeOpportunityMaster = removeOpportunityMaster;
	}
}
