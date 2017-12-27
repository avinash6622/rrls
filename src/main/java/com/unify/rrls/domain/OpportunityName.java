package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OpportunityMaster.
 */
@Entity
@Table(name = "opportunity_name")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpportunityName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "opp_name")
    private String oppName;
    
    @Column(name = "sector_type")
    private String sectorType;

   

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOppName() {
        return oppName;
    }

    public OpportunityName oppName(String oppName) {
        this.oppName = oppName;
        return this;
    }

    public void setOppName(String oppName) {
        this.oppName = oppName;
    }

    public String getSectorType() {
		return sectorType;
	}
    
    public void setSectorType(String sectorType) {
		this.sectorType = sectorType;
	}
  

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OpportunityName opportunityName = (OpportunityName) o;
        if (opportunityName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), opportunityName.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
