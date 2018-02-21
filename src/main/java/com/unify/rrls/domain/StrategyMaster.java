package com.unify.rrls.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A StrategyMaster.
 */
@Entity
@Table(name = "strategy_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StrategyMaster extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Size(max = 20)
    @Column(name = "strategy_code", length = 20, nullable = false)
    private String strategyCode;

    @NotNull
    @Size(max = 40)
    @Column(name = "strategy_name", length = 40, nullable = false)
    private String strategyName;

    @Column(name = "s_status")
    private Integer sStatus;

    @Column(name = "date_active")
    private LocalDate dateActive;
/*
   @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Size(max = 50)
    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    
    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;*/

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStrategyCode() {
        return strategyCode;
    }

    public StrategyMaster strategyCode(String strategyCode) {
        this.strategyCode = strategyCode;
        return this;
    }

    public void setStrategyCode(String strategyCode) {
        this.strategyCode = strategyCode;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public StrategyMaster strategyName(String strategyName) {
        this.strategyName = strategyName;
        return this;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getsStatus() {
        return sStatus;
    }

    public StrategyMaster sStatus(Integer sStatus) {
        this.sStatus = sStatus;
        return this;
    }

    public void setsStatus(Integer sStatus) {
        this.sStatus = sStatus;
    }

    public LocalDate getDateActive() {
        return dateActive;
    }

    public StrategyMaster dateActive(LocalDate dateActive) {
        this.dateActive = dateActive;
        return this;
    }

    public void setDateActive(LocalDate dateActive) {
        this.dateActive = dateActive;
    }
   

 /*public String getCreatedBy() {
        return createdBy;
    }

    public StrategyMaster createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public StrategyMaster updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public StrategyMaster createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public StrategyMaster updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }*/
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StrategyMaster strategyMaster = (StrategyMaster) o;
        if (strategyMaster.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), strategyMaster.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

   /* @Override
    public String toString() {
        return "StrategyMaster{" +
            "id=" + getId() +
            ", strategyCode='" + getStrategyCode() + "'" +
            ", strategyName='" + getStrategyName() + "'" +
            ", sStatus='" + getsStatus() + "'" +
            ", dateActive='" + getDateActive() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }*/
}
