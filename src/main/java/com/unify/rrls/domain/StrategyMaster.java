package com.unify.rrls.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
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


    @Column(name="AUM")
    private String aum;


 /*   @Column(name="Total_AUM")
    private String totalAum;*/

    @Column(name="Total_stocks")
    private String totalStocks;

    @Transient
    @JsonProperty
    private List<OpportunitySummaryData> opportunitySummaryData;

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

    public List<OpportunitySummaryData> getOpportunitySummaryData() {
        return opportunitySummaryData;
    }

    public void setOpportunitySummaryData(List<OpportunitySummaryData> opportunitySummaryData) {
        this.opportunitySummaryData = opportunitySummaryData;
    }

    public String getAum() {
        return aum;
    }

    public void setAum(String aum) {
        this.aum = aum;
    }

   /* public String getTotalAum() {
        return totalAum;
    }

    public void setTotalAum(String totalAum) {
        this.totalAum = totalAum;
    }*/

    public String getTotalStocks() {
        return totalStocks;
    }

    public void setTotalStocks(String totalStocks) {
        this.totalStocks = totalStocks;
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

  /*  @Override
    public String toString() {
        return "StrategyMaster{" +
            "id=" + id +
            ", strategyCode='" + strategyCode + '\'' +
            ", strategyName='" + strategyName + '\'' +
            ", sStatus=" + sStatus +
            ", dateActive=" + dateActive +
            '}';
    }
*/

}
