package com.unify.rrls.domain;

import java.io.Serializable;

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
@Table(name = "strategy_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StrategyMapping  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="opp_master_id")
	private OpportunityMaster opportunityMaster;

	@ManyToOne
	@JoinColumn(name="strategy_master_id")
	private StrategyMaster strategyMaster;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OpportunityMaster getOpportunityMaster() {
		return opportunityMaster;
	}

	public void setOpportunityMaster(OpportunityMaster opportunityMaster) {
		this.opportunityMaster = opportunityMaster;
	}

	public StrategyMaster getStrategyMaster() {
		return strategyMaster;
	}

	public void setStrategyMaster(StrategyMaster strategyMaster) {
		this.strategyMaster = strategyMaster;
	}

   /* @Override
    public String toString() {
        return "StrategyMapping{" +
            "id=" + id +
            ", opportunityMaster=" + opportunityMaster +
            ", strategyMaster=" + strategyMaster +
            '}';
    }*/
}
