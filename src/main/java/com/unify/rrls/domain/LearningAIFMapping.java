package com.unify.rrls.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "learning_aif_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LearningAIFMapping {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "learning_aif_id")
	private LearningAIF learningAIF;
	
	@ManyToOne
	@JoinColumn(name = "opp_mas_id")
	private OpportunityMaster opportunityMaster;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LearningAIF getLearningAIF() {
		return learningAIF;
	}

	public void setLearningAIF(LearningAIF learningAIF) {
		this.learningAIF = learningAIF;
	}

	public OpportunityMaster getOpportunityMaster() {
		return opportunityMaster;
	}

	public void setOpportunityMaster(OpportunityMaster opportunityMaster) {
		this.opportunityMaster = opportunityMaster;
	}	

}
