package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "opportunity_summary_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpportunitySummaryData  implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name = "b_weight")
	private Double bWeight;

	@Column(name = "cmp")
	private Double cmp;

	@Column(name = "m_cap")
	private Double marketCap;

	@Column(name = "pat_first_year")
	private Double patFirstYear;

	@Column(name = "pat_second_year")
	private Double patSecondYear;

	@Column(name = "pat_third_year")
	private Double patThirdYear;

	@Column(name = "pat_fourth_year")
	private Double patFourthYear;

	@Column(name = "pat_fifth_year")
	private Double patFifthYear;

	@Column(name = "pe_first_year")
	private Double peFirstYear;

	@Column(name = "pe_second_year")
	private Double peSecondYear;

	@Column(name = "pe_third_year")
	private Double peThirdYear;

	@Column(name = "pe_fourth_year")
	private Double peFourthYear;

	@Column(name = "pe_fifth_year")
	private Double peFifthYear;

	@Column(name = "roe_first_year")
	private Double roeFirstYear;

	@Column(name = "roe_second_year")
	private Double roeSecondYear;

	@Column(name = "roe_third_year")
	private Double roeThirdYear;

	@Column(name = "roe_fourth_year")
	private Double roeFourthYear;

	@Column(name = "roe_fifth_year")
	private Double roeFifthYear;

	@Column(name = "de_first_year")
	private Double deFirstYear;

	@Column(name = "de_second_year")
	private Double deSecondYear;

	@Column(name = "de_third_year")
	private Double deThirdColour;

	@Column(name = "de_fourth_year")
	private Double deFourthYear;

	@Column(name = "de_fifth_year")
	private Double deFifthYear;

	@Column(name = "pat_growth_first")
	private Double patGrowthFirst;

	@Column(name = "pat_growth_second")
	private Double patGrowthSecond;

	@Column(name = "pat_growth_third")
	private Double patGrowthThird;

	@Column(name = "pat_growth_fourth")
	private Double patGrowthFourth;

	@Column(name = "pat_growth_fifth")
	private Double patGrowthFifth;

	@Column(name = "port_pe_first")
	private Double portPeFirst;

	@Column(name = "port_pe_second")
	private Double portPeSecond;

	@Column(name = "port_pe_third")
	private Double portPeThird;

	@Column(name = "port_pe_fourth")
	private Double portPeFourth;

	@Column(name = "port_pe_fifth")
	private Double portPeFifth;

	@Column(name = "earnings_first")
	private Double earningsFirst;

	@Column(name = "earnings_second")
	private Double earningsSecond;

	@Column(name = "earnings_third")
	private Double earningsThird;

	@Column(name = "earnings_fourth")
	private Double earningsFourth;

	@Column(name = "earnings_fifth")
	private Double earningsFifth;

	@Column(name = "wt_avg_cap")
	private Double wtAvgCap;

	@Column(name = "roe")
	private Double roe;

	@Column(name = "peg_oj")
	private Double pegOj;

	@Column(name = "peg_year_peg")
	private Double pegYearPeg;


	@Transient
    @JsonProperty
	List<StrategyMaster> strategyMasterList;



    @OneToOne
	@JoinColumn(name = "opp_master")
	private OpportunityMaster opportunityMasterid;

    @ManyToOne
    @JoinColumn(name="strategy_mas_id")
    private StrategyMaster strategyMasterId;


    public OpportunityMaster getOpportunityMasterid() {
        return opportunityMasterid;
    }

    public void setOpportunityMasterid(OpportunityMaster opportunityMasterid) {
        this.opportunityMasterid = opportunityMasterid;
    }



    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getbWeight() {
		return bWeight;
	}

	public void setbWeight(Double bWeight) {
		this.bWeight = bWeight;
	}

	public Double getCmp() {
		return cmp;
	}

	public void setCmp(Double cmp) {
		this.cmp = cmp;
	}

	public Double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(Double marketCap) {
		this.marketCap = marketCap;
	}

	public Double getPatFirstYear() {
		return patFirstYear;
	}

	public void setPatFirstYear(Double patFirstYear) {
		this.patFirstYear = patFirstYear;
	}

	public Double getPatSecondYear() {
		return patSecondYear;
	}

	public void setPatSecondYear(Double patSecondYear) {
		this.patSecondYear = patSecondYear;
	}

	public Double getPatThirdYear() {
		return patThirdYear;
	}

	public void setPatThirdYear(Double patThirdYear) {
		this.patThirdYear = patThirdYear;
	}

	public Double getPatFourthYear() {
		return patFourthYear;
	}

	public void setPatFourthYear(Double patFourthYear) {
		this.patFourthYear = patFourthYear;
	}

	public Double getPatFifthYear() {
		return patFifthYear;
	}

	public void setPatFifthYear(Double patFifthYear) {
		this.patFifthYear = patFifthYear;
	}

	public Double getPeFirstYear() {
		return peFirstYear;
	}

	public void setPeFirstYear(Double peFirstYear) {
		this.peFirstYear = peFirstYear;
	}

	public Double getPeSecondYear() {
		return peSecondYear;
	}

	public void setPeSecondYear(Double peSecondYear) {
		this.peSecondYear = peSecondYear;
	}

	public Double getPeThirdYear() {
		return peThirdYear;
	}

	public void setPeThirdYear(Double peThirdYear) {
		this.peThirdYear = peThirdYear;
	}

	public Double getPeFourthYear() {
		return peFourthYear;
	}

	public void setPeFourthYear(Double peFourthYear) {
		this.peFourthYear = peFourthYear;
	}

	public Double getPeFifthYear() {
		return peFifthYear;
	}

	public void setPeFifthYear(Double peFifthYear) {
		this.peFifthYear = peFifthYear;
	}

	public Double getRoeFirstYear() {
		return roeFirstYear;
	}

	public void setRoeFirstYear(Double roeFirstYear) {
		this.roeFirstYear = roeFirstYear;
	}

	public Double getRoeSecondYear() {
		return roeSecondYear;
	}

	public void setRoeSecondYear(Double roeSecondYear) {
		this.roeSecondYear = roeSecondYear;
	}

	public Double getRoeThirdYear() {
		return roeThirdYear;
	}

	public void setRoeThirdYear(Double roeThirdYear) {
		this.roeThirdYear = roeThirdYear;
	}

	public Double getRoeFourthYear() {
		return roeFourthYear;
	}

	public void setRoeFourthYear(Double roeFourthYear) {
		this.roeFourthYear = roeFourthYear;
	}

	public Double getRoeFifthYear() {
		return roeFifthYear;
	}

	public void setRoeFifthYear(Double roeFifthYear) {
		this.roeFifthYear = roeFifthYear;
	}

	public Double getDeFirstYear() {
		return deFirstYear;
	}

	public void setDeFirstYear(Double deFirstYear) {
		this.deFirstYear = deFirstYear;
	}

	public Double getDeSecondYear() {
		return deSecondYear;
	}

	public void setDeSecondYear(Double deSecondYear) {
		this.deSecondYear = deSecondYear;
	}

	public Double getDeThirdColour() {
		return deThirdColour;
	}

	public void setDeThirdColour(Double deThirdColour) {
		this.deThirdColour = deThirdColour;
	}

	public Double getDeFourthYear() {
		return deFourthYear;
	}

	public void setDeFourthYear(Double deFourthYear) {
		this.deFourthYear = deFourthYear;
	}

	public Double getDeFifthYear() {
		return deFifthYear;
	}

	public void setDeFifthYear(Double deFifthYear) {
		this.deFifthYear = deFifthYear;
	}

	public Double getPatGrowthFirst() {
		return patGrowthFirst;
	}

	public void setPatGrowthFirst(Double patGrowthFirst) {
		this.patGrowthFirst = patGrowthFirst;
	}

	public Double getPatGrowthSecond() {
		return patGrowthSecond;
	}

	public void setPatGrowthSecond(Double patGrowthSecond) {
		this.patGrowthSecond = patGrowthSecond;
	}

	public Double getPatGrowthThird() {
		return patGrowthThird;
	}

	public void setPatGrowthThird(Double patGrowthThird) {
		this.patGrowthThird = patGrowthThird;
	}

	public Double getPatGrowthFourth() {
		return patGrowthFourth;
	}

	public void setPatGrowthFourth(Double patGrowthFourth) {
		this.patGrowthFourth = patGrowthFourth;
	}

	public Double getPatGrowthFifth() {
		return patGrowthFifth;
	}

	public void setPatGrowthFifth(Double patGrowthFifth) {
		this.patGrowthFifth = patGrowthFifth;
	}

	public Double getPortPeFirst() {
		return portPeFirst;
	}

	public void setPortPeFirst(Double portPeFirst) {
		this.portPeFirst = portPeFirst;
	}

	public Double getPortPeSecond() {
		return portPeSecond;
	}

	public void setPortPeSecond(Double portPeSecond) {
		this.portPeSecond = portPeSecond;
	}

	public Double getPortPeThird() {
		return portPeThird;
	}

	public void setPortPeThird(Double portPeThird) {
		this.portPeThird = portPeThird;
	}

	public Double getPortPeFourth() {
		return portPeFourth;
	}

	public void setPortPeFourth(Double portPeFourth) {
		this.portPeFourth = portPeFourth;
	}

	public Double getPortPeFifth() {
		return portPeFifth;
	}

	public void setPortPeFifth(Double portPeFifth) {
		this.portPeFifth = portPeFifth;
	}

	public Double getEarningsFirst() {
		return earningsFirst;
	}

	public void setEarningsFirst(Double earningsFirst) {
		this.earningsFirst = earningsFirst;
	}

	public Double getEarningsSecond() {
		return earningsSecond;
	}

	public void setEarningsSecond(Double earningsSecond) {
		this.earningsSecond = earningsSecond;
	}

	public Double getEarningsThird() {
		return earningsThird;
	}

	public void setEarningsThird(Double earningsThird) {
		this.earningsThird = earningsThird;
	}

	public Double getEarningsFourth() {
		return earningsFourth;
	}

	public void setEarningsFourth(Double earningsFourth) {
		this.earningsFourth = earningsFourth;
	}

	public Double getEarningsFifth() {
		return earningsFifth;
	}

	public void setEarningsFifth(Double earningsFifth) {
		this.earningsFifth = earningsFifth;
	}

	public Double getWtAvgCap() {
		return wtAvgCap;
	}

	public void setWtAvgCap(Double wtAvgCap) {
		this.wtAvgCap = wtAvgCap;
	}

	public Double getRoe() {
		return roe;
	}

	public void setRoe(Double roe) {
		this.roe = roe;
	}

	public Double getPegOj() {
		return pegOj;
	}

	public void setPegOj(Double pegOj) {
		this.pegOj = pegOj;
	}

	public Double getPegYearPeg() {
		return pegYearPeg;
	}

	public void setPegYearPeg(Double pegYearPeg) {
		this.pegYearPeg = pegYearPeg;
	}

    public StrategyMaster getStrategyMasterId() {
        return strategyMasterId;
    }

    public void setStrategyMasterId(StrategyMaster strategyMasterId) {
        this.strategyMasterId = strategyMasterId;
    }

    public List<StrategyMaster> getStrategyMasterList() {
        return strategyMasterList;
    }

    public void setStrategyMasterList(List<StrategyMaster> strategyMasterList) {
        this.strategyMasterList = strategyMasterList;
    }

    @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        OpportunitySummaryData opportunitySummaryData = (OpportunitySummaryData) o;
	        if (opportunitySummaryData.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), opportunitySummaryData.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

  /*  @Override
    public String toString() {
        return "OpportunitySummaryData{" +
            "id=" + id +
            ", bWeight=" + bWeight +
            ", cmp=" + cmp +
            ", marketCap=" + marketCap +
            ", patFirstYear=" + patFirstYear +
            ", patSecondYear=" + patSecondYear +
            ", patThirdYear=" + patThirdYear +
            ", patFourthYear=" + patFourthYear +
            ", patFifthYear=" + patFifthYear +
            ", peFirstYear=" + peFirstYear +
            ", peSecondYear=" + peSecondYear +
            ", peThirdYear=" + peThirdYear +
            ", peFourthYear=" + peFourthYear +
            ", peFifthYear=" + peFifthYear +
            ", roeFirstYear=" + roeFirstYear +
            ", roeSecondYear=" + roeSecondYear +
            ", roeThirdYear=" + roeThirdYear +
            ", roeFourthYear=" + roeFourthYear +
            ", roeFifthYear=" + roeFifthYear +
            ", deFirstYear=" + deFirstYear +
            ", deSecondYear=" + deSecondYear +
            ", deThirdColour=" + deThirdColour +
            ", deFourthYear=" + deFourthYear +
            ", deFifthYear=" + deFifthYear +
            ", patGrowthFirst=" + patGrowthFirst +
            ", patGrowthSecond=" + patGrowthSecond +
            ", patGrowthThird=" + patGrowthThird +
            ", patGrowthFourth=" + patGrowthFourth +
            ", patGrowthFifth=" + patGrowthFifth +
            ", portPeFirst=" + portPeFirst +
            ", portPeSecond=" + portPeSecond +
            ", portPeThird=" + portPeThird +
            ", portPeFourth=" + portPeFourth +
            ", portPeFifth=" + portPeFifth +
            ", earningsFirst=" + earningsFirst +
            ", earningsSecond=" + earningsSecond +
            ", earningsThird=" + earningsThird +
            ", earningsFourth=" + earningsFourth +
            ", earningsFifth=" + earningsFifth +
            ", wtAvgCap=" + wtAvgCap +
            ", roe=" + roe +
            ", pegOj=" + pegOj +
            ", pegYearPeg=" + pegYearPeg +
            '}';
    }*/

    @Override
    public String toString() {
        return "OpportunitySummaryData{" +
            "id=" + id +
            ", bWeight=" + bWeight +
            ", cmp=" + cmp +
            ", marketCap=" + marketCap +
            ", patFirstYear=" + patFirstYear +
            ", patSecondYear=" + patSecondYear +
            ", patThirdYear=" + patThirdYear +
            ", patFourthYear=" + patFourthYear +
            ", patFifthYear=" + patFifthYear +
            ", peFirstYear=" + peFirstYear +
            ", peSecondYear=" + peSecondYear +
            ", peThirdYear=" + peThirdYear +
            ", peFourthYear=" + peFourthYear +
            ", peFifthYear=" + peFifthYear +
            ", roeFirstYear=" + roeFirstYear +
            ", roeSecondYear=" + roeSecondYear +
            ", roeThirdYear=" + roeThirdYear +
            ", roeFourthYear=" + roeFourthYear +
            ", roeFifthYear=" + roeFifthYear +
            ", deFirstYear=" + deFirstYear +
            ", deSecondYear=" + deSecondYear +
            ", deThirdColour=" + deThirdColour +
            ", deFourthYear=" + deFourthYear +
            ", deFifthYear=" + deFifthYear +
            ", patGrowthFirst=" + patGrowthFirst +
            ", patGrowthSecond=" + patGrowthSecond +
            ", patGrowthThird=" + patGrowthThird +
            ", patGrowthFourth=" + patGrowthFourth +
            ", patGrowthFifth=" + patGrowthFifth +
            ", portPeFirst=" + portPeFirst +
            ", portPeSecond=" + portPeSecond +
            ", portPeThird=" + portPeThird +
            ", portPeFourth=" + portPeFourth +
            ", portPeFifth=" + portPeFifth +
            ", earningsFirst=" + earningsFirst +
            ", earningsSecond=" + earningsSecond +
            ", earningsThird=" + earningsThird +
            ", earningsFourth=" + earningsFourth +
            ", earningsFifth=" + earningsFifth +
            ", wtAvgCap=" + wtAvgCap +
            ", roe=" + roe +
            ", pegOj=" + pegOj +
            ", pegYearPeg=" + pegYearPeg +
            ", strategyMasterList=" + strategyMasterList +
            ", opportunityMasterid=" + opportunityMasterid +
            ", strategyMasterId=" + strategyMasterId +
            '}';
    }
}
