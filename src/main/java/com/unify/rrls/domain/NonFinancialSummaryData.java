package com.unify.rrls.domain;

import java.io.Serializable;

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
@Table(name = "non_financial_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NonFinancialSummaryData implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "revenue_one")
	private Double revenueOne;
	@Column(name = "revenue_two")
	private Double revenueTwo;
	@Column(name = "revenue_three")
	private Double revenueThree;
	@Column(name = "revenue_four")
	private Double revenueFour;
	@Column(name = "revenue_five")
	private Double revenueFive;
	@Column(name = "rev_growth_one")
	private Double revGrowthOne;
	@Column(name = "rev_growth_two")
	private Double revGrowthTwo;
	@Column(name = "rev_growth_three")
	private Double revGrowthThree;
	@Column(name = "rev_growth_four")
	private Double revGrowthFour;
	@Column(name = "rev_growth_five")
	private Double revGrowthFive;
	@Column(name = "ebitda_one")
	private Double ebitdaOne;
	@Column(name = "ebitda_two")
	private Double ebitdaTwo;
	@Column(name = "ebitda_three")
	private Double ebitdaThree;
	@Column(name = "ebitda_four")
	private Double ebitdaFour;
	@Column(name = "ebitda_five")
	private Double ebitdaFive;
	@Column(name = "margin_one")	
	private Double marginOne;
	@Column(name = "margin_two")	
	private Double marginTwo;
	@Column(name = "margin_three")
	private Double marginThree;
	@Column(name = "margin_four")
	private Double marginFour;
	@Column(name = "margin_five")
	private Double marginFive;
	@Column(name = "ebitda_growth_one")
	private Double ebitdaGrowthOne;
	@Column(name = "ebitda_growth_two")
	private Double ebitdaGrowthTwo;
	@Column(name = "ebitda_growth_three")
	private Double ebitdaGrowthThree;
	@Column(name = "ebitda_growth_four")
	private Double ebitdaGrowthFour;
	@Column(name = "ebitda_growth_five")
	private Double ebitdaGrowthFive;
	@Column(name = "market_cap_one")
	private Double marketCapOne;
	@Column(name = "market_cap_two")
	private Double marketCapTwo;
	@Column(name = "market_cap_three")
	private Double marketCapThree;
	@Column(name = "market_cap_four")
	private Double marketCapFour;
	@Column(name = "market_cap_five")
	private Double marketCapFive;
	@Column(name = "networth_one")
	private Double networthOne;
	@Column(name = "networth_two")
	private Double networthTwo;
	@Column(name = "networth_three")
	private Double networthThree;
	@Column(name = "networth_four")
	private Double networthFour;
	@Column(name = "networth_five")
	private Double networthFive;
	@Column(name = "pb_one")
	private Double pbOne;
	@Column(name = "pb_two")
	private Double pbTwo;
	@Column(name = "pb_three")
	private Double pbThree;
	@Column(name = "pb_four")
	private Double pbFour;
	@Column(name = "pb_five")
	private Double pbFive;
	@Column(name = "roe_one")
	private Double roeOne;
	@Column(name = "roe_two")
	private Double roeTwo;
	@Column(name = "roe_three")
	private Double roeThree;
	@Column(name = "roe_four")
	private Double roeFour;
	@Column(name = "roe_five")
	private Double roefive;
	@Column(name = "tot_deb_one")
	private Double totDebOne;
	@Column(name = "tot_deb_two")
	private Double totDebTwo;
	@Column(name = "tot_deb_three")
	private Double totDebThree;
	@Column(name = "tot_deb_four")
	private Double totDebFour;
	@Column(name = "tot_deb_five")
	private Double totDebFive;
	@Column(name = "de_one")
	private Double deOne;
	@Column(name = "de_two")
	private Double deTwo;
	@Column(name = "de_three")
	private Double deThree;
	@Column(name = "de_four")
	private Double deFour;
	@Column(name = "de_five")
	private Double deFive;
	@ManyToOne
	@JoinColumn(name = "opp_mas_id")
	private OpportunityMaster opportunityMaster;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getRevenueOne() {
		return revenueOne;
	}
	public void setRevenueOne(Double revenueOne) {
		this.revenueOne = revenueOne;
	}
	public Double getRevenueTwo() {
		return revenueTwo;
	}
	public void setRevenueTwo(Double revenueTwo) {
		this.revenueTwo = revenueTwo;
	}
	public Double getRevenueThree() {
		return revenueThree;
	}
	public void setRevenueThree(Double revenueThree) {
		this.revenueThree = revenueThree;
	}
	public Double getRevenueFour() {
		return revenueFour;
	}
	public void setRevenueFour(Double revenueFour) {
		this.revenueFour = revenueFour;
	}
	public Double getRevenueFive() {
		return revenueFive;
	}
	public void setRevenueFive(Double revenueFive) {
		this.revenueFive = revenueFive;
	}
	public Double getRevGrowthOne() {
		return revGrowthOne;
	}
	public void setRevGrowthOne(Double revGrowthOne) {
		this.revGrowthOne = revGrowthOne;
	}
	public Double getRevGrowthTwo() {
		return revGrowthTwo;
	}
	public void setRevGrowthTwo(Double revGrowthTwo) {
		this.revGrowthTwo = revGrowthTwo;
	}
	public Double getRevGrowthThree() {
		return revGrowthThree;
	}
	public void setRevGrowthThree(Double revGrowthThree) {
		this.revGrowthThree = revGrowthThree;
	}
	public Double getRevGrowthFour() {
		return revGrowthFour;
	}
	public void setRevGrowthFour(Double revGrowthFour) {
		this.revGrowthFour = revGrowthFour;
	}
	public Double getRevGrowthFive() {
		return revGrowthFive;
	}
	public void setRevGrowthFive(Double revGrowthFive) {
		this.revGrowthFive = revGrowthFive;
	}
	public Double getEbitdaOne() {
		return ebitdaOne;
	}
	public void setEbitdaOne(Double ebitdaOne) {
		this.ebitdaOne = ebitdaOne;
	}
	public Double getEbitdaTwo() {
		return ebitdaTwo;
	}
	public void setEbitdaTwo(Double ebitdaTwo) {
		this.ebitdaTwo = ebitdaTwo;
	}
	public Double getEbitdaThree() {
		return ebitdaThree;
	}
	public void setEbitdaThree(Double ebitdaThree) {
		this.ebitdaThree = ebitdaThree;
	}
	public Double getEbitdaFour() {
		return ebitdaFour;
	}
	public void setEbitdaFour(Double ebitdaFour) {
		this.ebitdaFour = ebitdaFour;
	}
	public Double getEbitdaFive() {
		return ebitdaFive;
	}
	public void setEbitdaFive(Double ebitdaFive) {
		this.ebitdaFive = ebitdaFive;
	}
	public Double getMarginOne() {
		return marginOne;
	}
	public void setMarginOne(Double marginOne) {
		this.marginOne = marginOne;
	}
	public Double getMarginTwo() {
		return marginTwo;
	}
	public void setMarginTwo(Double marginTwo) {
		this.marginTwo = marginTwo;
	}
	public Double getMarginThree() {
		return marginThree;
	}
	public void setMarginThree(Double marginThree) {
		this.marginThree = marginThree;
	}
	public Double getMarginFour() {
		return marginFour;
	}
	public void setMarginFour(Double marginFour) {
		this.marginFour = marginFour;
	}
	public Double getMarginFive() {
		return marginFive;
	}
	public void setMarginFive(Double marginFive) {
		this.marginFive = marginFive;
	}
	public Double getEbitdaGrowthOne() {
		return ebitdaGrowthOne;
	}
	public void setEbitdaGrowthOne(Double ebitdaGrowthOne) {
		this.ebitdaGrowthOne = ebitdaGrowthOne;
	}
	public Double getEbitdaGrowthTwo() {
		return ebitdaGrowthTwo;
	}
	public void setEbitdaGrowthTwo(Double ebitdaGrowthTwo) {
		this.ebitdaGrowthTwo = ebitdaGrowthTwo;
	}
	public Double getEbitdaGrowthThree() {
		return ebitdaGrowthThree;
	}
	public void setEbitdaGrowthThree(Double ebitdaGrowthThree) {
		this.ebitdaGrowthThree = ebitdaGrowthThree;
	}
	public Double getEbitdaGrowthFour() {
		return ebitdaGrowthFour;
	}
	public void setEbitdaGrowthFour(Double ebitdaGrowthFour) {
		this.ebitdaGrowthFour = ebitdaGrowthFour;
	}
	public Double getEbitdaGrowthFive() {
		return ebitdaGrowthFive;
	}
	public void setEbitdaGrowthFive(Double ebitdaGrowthFive) {
		this.ebitdaGrowthFive = ebitdaGrowthFive;
	}
	public Double getMarketCapOne() {
		return marketCapOne;
	}
	public void setMarketCapOne(Double marketCapOne) {
		this.marketCapOne = marketCapOne;
	}
	public Double getMarketCapTwo() {
		return marketCapTwo;
	}
	public void setMarketCapTwo(Double marketCapTwo) {
		this.marketCapTwo = marketCapTwo;
	}
	public Double getMarketCapThree() {
		return marketCapThree;
	}
	public void setMarketCapThree(Double marketCapThree) {
		this.marketCapThree = marketCapThree;
	}
	public Double getMarketCapFour() {
		return marketCapFour;
	}
	public void setMarketCapFour(Double marketCapFour) {
		this.marketCapFour = marketCapFour;
	}
	public Double getMarketCapFive() {
		return marketCapFive;
	}
	public void setMarketCapFive(Double marketCapFive) {
		this.marketCapFive = marketCapFive;
	}
	public Double getNetworthOne() {
		return networthOne;
	}
	public void setNetworthOne(Double networthOne) {
		this.networthOne = networthOne;
	}
	public Double getNetworthTwo() {
		return networthTwo;
	}
	public void setNetworthTwo(Double networthTwo) {
		this.networthTwo = networthTwo;
	}
	public Double getNetworthThree() {
		return networthThree;
	}
	public void setNetworthThree(Double networthThree) {
		this.networthThree = networthThree;
	}
	public Double getNetworthFour() {
		return networthFour;
	}
	public void setNetworthFour(Double networthFour) {
		this.networthFour = networthFour;
	}
	public Double getNetworthFive() {
		return networthFive;
	}
	public void setNetworthFive(Double networthFive) {
		this.networthFive = networthFive;
	}
	public Double getPbOne() {
		return pbOne;
	}
	public void setPbOne(Double pbOne) {
		this.pbOne = pbOne;
	}
	public Double getPbTwo() {
		return pbTwo;
	}
	public void setPbTwo(Double pbTwo) {
		this.pbTwo = pbTwo;
	}
	public Double getPbThree() {
		return pbThree;
	}
	public void setPbThree(Double pbThree) {
		this.pbThree = pbThree;
	}
	public Double getPbFour() {
		return pbFour;
	}
	public void setPbFour(Double pbFour) {
		this.pbFour = pbFour;
	}
	public Double getPbFive() {
		return pbFive;
	}
	public void setPbFive(Double pbFive) {
		this.pbFive = pbFive;
	}
	public Double getRoeOne() {
		return roeOne;
	}
	public void setRoeOne(Double roeOne) {
		this.roeOne = roeOne;
	}
	public Double getRoeTwo() {
		return roeTwo;
	}
	public void setRoeTwo(Double roeTwo) {
		this.roeTwo = roeTwo;
	}
	public Double getRoeThree() {
		return roeThree;
	}
	public void setRoeThree(Double roeThree) {
		this.roeThree = roeThree;
	}
	public Double getRoeFour() {
		return roeFour;
	}
	public void setRoeFour(Double roeFour) {
		this.roeFour = roeFour;
	}
	public Double getRoefive() {
		return roefive;
	}
	public void setRoefive(Double roefive) {
		this.roefive = roefive;
	}
	public Double getTotDebOne() {
		return totDebOne;
	}
	public void setTotDebOne(Double totDebOne) {
		this.totDebOne = totDebOne;
	}
	public Double getTotDebTwo() {
		return totDebTwo;
	}
	public void setTotDebTwo(Double totDebTwo) {
		this.totDebTwo = totDebTwo;
	}
	public Double getTotDebThree() {
		return totDebThree;
	}
	public void setTotDebThree(Double totDebThree) {
		this.totDebThree = totDebThree;
	}
	public Double getTotDebFour() {
		return totDebFour;
	}
	public void setTotDebFour(Double totDebFour) {
		this.totDebFour = totDebFour;
	}
	public Double getTotDebFive() {
		return totDebFive;
	}
	public void setTotDebFive(Double totDebFive) {
		this.totDebFive = totDebFive;
	}
	public Double getDeOne() {
		return deOne;
	}
	public void setDeOne(Double deOne) {
		this.deOne = deOne;
	}
	public Double getDeTwo() {
		return deTwo;
	}
	public void setDeTwo(Double deTwo) {
		this.deTwo = deTwo;
	}
	public Double getDeThree() {
		return deThree;
	}
	public void setDeThree(Double deThree) {
		this.deThree = deThree;
	}
	public Double getDeFour() {
		return deFour;
	}
	public void setDeFour(Double deFour) {
		this.deFour = deFour;
	}
	public Double getDeFive() {
		return deFive;
	}
	public void setDeFive(Double deFive) {
		this.deFive = deFive;
	}
	public OpportunityMaster getOpportunityMaster() {
		return opportunityMaster;
	}
	public void setOpportunityMaster(OpportunityMaster opportunityMaster) {
		this.opportunityMaster = opportunityMaster;
	}
	
	
}
