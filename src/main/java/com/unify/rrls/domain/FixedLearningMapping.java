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
@Table(name = "fixed_learning_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FixedLearningMapping {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fixed_learning_id")
	private FixedLearning fixedLearning;
	
	@ManyToOne
	@JoinColumn(name = "opp_mas_id")
	private OpportunityMaster opportunityMaster;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FixedLearning getFixedLearning() {
		return fixedLearning;
	}

	public void setFixedLearning(FixedLearning fixedLearning) {
		this.fixedLearning = fixedLearning;
	}

	public OpportunityMaster getOpportunityMaster() {
		return opportunityMaster;
	}

	public void setOpportunityMaster(OpportunityMaster opportunityMaster) {
		this.opportunityMaster = opportunityMaster;
	}	

}
