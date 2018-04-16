package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
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
@Table(name = "opportunity_automation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpportunityAutomation  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "market_cap")
	private Double marketCap;
	
	@Column(name = "prev_close")
	private Double prevClose;
	
	@ManyToOne
	@JoinColumn(name = "opp_mas_id")
	private OpportunityMaster opportunityMaster;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getMarketCap() {
		return marketCap;
	}
	public void setMarketCap(Double marketCap) {
		this.marketCap = marketCap;
	}
	public Double getPrevClose() {
		return prevClose;
	}
	public void setPrevClose(Double prevClose) {
		this.prevClose = prevClose;
	}
	public OpportunityMaster getOpportunityMaster() {
		return opportunityMaster;
	}
	public void setOpportunityMaster(OpportunityMaster opportunityMaster) {
		this.opportunityMaster = opportunityMaster;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        OpportunityAutomation opportunityAutomation = (OpportunityAutomation) o;
	        if (opportunityAutomation.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), opportunityAutomation.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

}
