package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "financial_summary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FinancialSummaryData implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="int_id")
	private Integer id;
	@Column(name = "net_int_inc_one")
	private Double netIntOne;
	@Column(name = "net_int_inc_two")
	private Double netIntTwo;
	@Column(name = "net_int_inc_three")
	private Double netIntThree;
	@Column(name = "net_int_inc_four")
	private Double netIntFour;
	@Column(name = "net_int_inc_five")
	private Double netIntFive;
	@Column(name = "non_int_inc_one")
	private Double nonIntOne;
	@Column(name = "non_int_inc_two")
	private Double nonIntTwo;
	@Column(name = "non_int_inc_three")
	private Double nonIntThree;
	@Column(name = "non_int_inc_four")
	private Double nonIntFour;
	@Column(name = "non_int_inc_five")
	private Double nonIntFive;
	@Column(name = "tot_inc_one")
	private Double totIncOne;
	@Column(name = "tot_inc_two")
	private Double totIncTwo;
	@Column(name = "tot_inc_three")
	private Double totIncThree;
	@Column(name = "tot_inc_four")
	private Double totIncFour;
	@Column(name = "tot_inc_five")
	private Double totIncFive;
	@Column(name = "op_exp_one")
	private Double opExpOne;
	@Column(name = "op_exp_two")
	private Double opExpTwo;
	@Column(name = "op_exp_three")
	private Double opExpThree;
	@Column(name = "op_exp_four")
	private Double opExpFour;
	@Column(name = "op_exp_five")
	private Double opExpFive;
	@Column(name = "op_pro_one")
	private Double opProOne;
	@Column(name = "op_pro_two")
	private Double opProTwo;
	@Column(name = "op_pro_three")
	private Double opProThree;
	@Column(name = "op_pro_four")
	private Double opProFour;
	@Column(name = "op_pro_five")
	private Double opProFive;
	@Column(name = "provisions_one")
	private Double provisionsOne;
	@Column(name = "provisions_two")
	private Double provisionsTwo;
	@Column(name = "provisions_three")
	private Double provisionsThree;
	@Column(name = "provisions_four")
	private Double provisionsFour;
	@Column(name = "provisions_five")
	private Double provisionsFive;
	@Column(name = "pat_one")
	private Double patOne;
	@Column(name = "pat_two")
	private Double patTwo;
	@Column(name = "pat_three")
	private Double patThree;
	@Column(name = "pat_four")
	private Double patFour;
	@Column(name = "pat_five")
	private Double patFive;
	@Column(name = "eps_one")
	private Double epsOne;
	@Column(name = "eps_two")
	private Double epsTwo;
	@Column(name = "eps_three")
	private Double epsThree;
	@Column(name = "eps_four")
	private Double epsFour;
	@Column(name = "eps_five")
	private Double epsFive;
	@Column(name = "mar_cap_one")
	private Double marCapOne;
	@Column(name = "mar_cap_two")
	private Double marCapTwo;
	@Column(name = "mar_cap_three")
	private Double marCapThree;
	@Column(name = "mar_cap_four")
	private Double marCapFour;
	@Column(name = "mar_cap_five")
	private Double marCapFive;
	@Column(name = "aum_one")
	private Double aumOne;
	@Column(name = "aum_two")
	private Double aumTwo;
	@Column(name = "aum_three")
	private Double aumThree;
	@Column(name = "aum_four")
	private Double aumFour;
	@Column(name = "aum_five")
	private Double aumFive;
	@Column(name = "networth_one")
	private Double networthOne;
	@Column(name = "networth_two")
	private Double networthTwo;
	@Column(name = "networth_three")
	private Double networthThree;
	@Column(name = "networth_four")
	private Double networthFour;
	@Column(name = "networth_five")
	private Double networthfive;
	@Column(name = "roe_one")
	private Double roeOne;
	@Column(name = "roe_two")
	private Double roeTwo;
	@Column(name = "roe_three")
	private Double roeThree;
	@Column(name = "roe_four")
	private Double roeFour;
	@Column(name = "roe_five")
	private Double roeFive;
	@Column(name = "pbv_one")
	private Double pbvOne;
	@Column(name = "pbv_two")
	private Double pbvTwo;
	@Column(name = "pbv_three")
	private Double pbvThree;
	@Column(name = "pbv_four")
	private Double pbvFour;
	@Column(name = "pbv_five")
	private Double pbvFive;
	@Column(name = "pe_one")
	private Double peOne;
	@Column(name = "pe_two")
	private Double peTwo;
	@Column(name = "pe_three")
	private Double peThree;
	@Column(name = "pe_four")
	private Double peFour;
	@Column(name = "pe_five")
	private Double peFive;
	@OneToOne
	@JoinColumn(name = "opp_master_id")
	private OpportunityMaster opportunityMaster;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getNetIntOne() {
		return netIntOne;
	}
	public void setNetIntOne(Double netIntOne) {
		this.netIntOne = netIntOne;
	}
	public Double getNetIntTwo() {
		return netIntTwo;
	}
	public void setNetIntTwo(Double netIntTwo) {
		this.netIntTwo = netIntTwo;
	}
	public Double getNetIntThree() {
		return netIntThree;
	}
	public void setNetIntThree(Double netIntThree) {
		this.netIntThree = netIntThree;
	}
	public Double getNetIntFour() {
		return netIntFour;
	}
	public void setNetIntFour(Double netIntFour) {
		this.netIntFour = netIntFour;
	}
	public Double getNetIntFive() {
		return netIntFive;
	}
	public void setNetIntFive(Double netIntFive) {
		this.netIntFive = netIntFive;
	}
	public Double getNonIntOne() {
		return nonIntOne;
	}
	public void setNonIntOne(Double nonIntOne) {
		this.nonIntOne = nonIntOne;
	}
	public Double getNonIntTwo() {
		return nonIntTwo;
	}
	public void setNonIntTwo(Double nonIntTwo) {
		this.nonIntTwo = nonIntTwo;
	}
	public Double getNonIntThree() {
		return nonIntThree;
	}
	public void setNonIntThree(Double nonIntThree) {
		this.nonIntThree = nonIntThree;
	}
	public Double getNonIntFour() {
		return nonIntFour;
	}
	public void setNonIntFour(Double nonIntFour) {
		this.nonIntFour = nonIntFour;
	}
	public Double getNonIntFive() {
		return nonIntFive;
	}
	public void setNonIntFive(Double nonIntFive) {
		this.nonIntFive = nonIntFive;
	}
	public Double getTotIncOne() {
		return totIncOne;
	}
	public void setTotIncOne(Double totIncOne) {
		this.totIncOne = totIncOne;
	}
	public Double getTotIncTwo() {
		return totIncTwo;
	}
	public void setTotIncTwo(Double totIncTwo) {
		this.totIncTwo = totIncTwo;
	}
	public Double getTotIncThree() {
		return totIncThree;
	}
	public void setTotIncThree(Double totIncThree) {
		this.totIncThree = totIncThree;
	}
	public Double getTotIncFour() {
		return totIncFour;
	}
	public void setTotIncFour(Double totIncFour) {
		this.totIncFour = totIncFour;
	}
	public Double getTotIncFive() {
		return totIncFive;
	}
	public void setTotIncFive(Double totIncFive) {
		this.totIncFive = totIncFive;
	}
	public Double getOpExpOne() {
		return opExpOne;
	}
	public void setOpExpOne(Double opExpOne) {
		this.opExpOne = opExpOne;
	}
	public Double getOpExpTwo() {
		return opExpTwo;
	}
	public void setOpExpTwo(Double opExpTwo) {
		this.opExpTwo = opExpTwo;
	}
	public Double getOpExpThree() {
		return opExpThree;
	}
	public void setOpExpThree(Double opExpThree) {
		this.opExpThree = opExpThree;
	}
	public Double getOpExpFour() {
		return opExpFour;
	}
	public void setOpExpFour(Double opExpFour) {
		this.opExpFour = opExpFour;
	}
	public Double getOpExpFive() {
		return opExpFive;
	}
	public void setOpExpFive(Double opExpFive) {
		this.opExpFive = opExpFive;
	}
	public Double getOpProOne() {
		return opProOne;
	}
	public void setOpProOne(Double opProOne) {
		this.opProOne = opProOne;
	}
	public Double getOpProTwo() {
		return opProTwo;
	}
	public void setOpProTwo(Double opProTwo) {
		this.opProTwo = opProTwo;
	}
	public Double getOpProThree() {
		return opProThree;
	}
	public void setOpProThree(Double opProThree) {
		this.opProThree = opProThree;
	}
	public Double getOpProFour() {
		return opProFour;
	}
	public void setOpProFour(Double opProFour) {
		this.opProFour = opProFour;
	}
	public Double getOpProFive() {
		return opProFive;
	}
	public void setOpProFive(Double opProFive) {
		this.opProFive = opProFive;
	}
	public Double getProvisionsOne() {
		return provisionsOne;
	}
	public void setProvisionsOne(Double provisionsOne) {
		this.provisionsOne = provisionsOne;
	}
	public Double getProvisionsTwo() {
		return provisionsTwo;
	}
	public void setProvisionsTwo(Double provisionsTwo) {
		this.provisionsTwo = provisionsTwo;
	}
	public Double getProvisionsThree() {
		return provisionsThree;
	}
	public void setProvisionsThree(Double provisionsThree) {
		this.provisionsThree = provisionsThree;
	}
	public Double getProvisionsFour() {
		return provisionsFour;
	}
	public void setProvisionsFour(Double provisionsFour) {
		this.provisionsFour = provisionsFour;
	}
	public Double getProvisionsFive() {
		return provisionsFive;
	}
	public void setProvisionsFive(Double provisionsFive) {
		this.provisionsFive = provisionsFive;
	}
	public Double getPatOne() {
		return patOne;
	}
	public void setPatOne(Double patOne) {
		this.patOne = patOne;
	}
	public Double getPatTwo() {
		return patTwo;
	}
	public void setPatTwo(Double patTwo) {
		this.patTwo = patTwo;
	}
	public Double getPatThree() {
		return patThree;
	}
	public void setPatThree(Double patThree) {
		this.patThree = patThree;
	}
	public Double getPatFour() {
		return patFour;
	}
	public void setPatFour(Double patFour) {
		this.patFour = patFour;
	}
	public Double getPatFive() {
		return patFive;
	}
	public void setPatFive(Double patFive) {
		this.patFive = patFive;
	}
	public Double getEpsOne() {
		return epsOne;
	}
	public void setEpsOne(Double epsOne) {
		this.epsOne = epsOne;
	}
	public Double getEpsTwo() {
		return epsTwo;
	}
	public void setEpsTwo(Double epsTwo) {
		this.epsTwo = epsTwo;
	}
	public Double getEpsThree() {
		return epsThree;
	}
	public void setEpsThree(Double epsThree) {
		this.epsThree = epsThree;
	}
	public Double getEpsFour() {
		return epsFour;
	}
	public void setEpsFour(Double epsFour) {
		this.epsFour = epsFour;
	}
	public Double getEpsFive() {
		return epsFive;
	}
	public void setEpsFive(Double epsFive) {
		this.epsFive = epsFive;
	}
	public Double getMarCapOne() {
		return marCapOne;
	}
	public void setMarCapOne(Double marCapOne) {
		this.marCapOne = marCapOne;
	}
	public Double getMarCapTwo() {
		return marCapTwo;
	}
	public void setMarCapTwo(Double marCapTwo) {
		this.marCapTwo = marCapTwo;
	}
	public Double getMarCapThree() {
		return marCapThree;
	}
	public void setMarCapThree(Double marCapThree) {
		this.marCapThree = marCapThree;
	}
	public Double getMarCapFour() {
		return marCapFour;
	}
	public void setMarCapFour(Double marCapFour) {
		this.marCapFour = marCapFour;
	}
	public Double getMarCapFive() {
		return marCapFive;
	}
	public void setMarCapFive(Double marCapFive) {
		this.marCapFive = marCapFive;
	}
	public Double getAumOne() {
		return aumOne;
	}
	public void setAumOne(Double aumOne) {
		this.aumOne = aumOne;
	}
	public Double getAumTwo() {
		return aumTwo;
	}
	public void setAumTwo(Double aumTwo) {
		this.aumTwo = aumTwo;
	}
	public Double getAumThree() {
		return aumThree;
	}
	public void setAumThree(Double aumThree) {
		this.aumThree = aumThree;
	}
	public Double getAumFour() {
		return aumFour;
	}
	public void setAumFour(Double aumFour) {
		this.aumFour = aumFour;
	}
	public Double getAumFive() {
		return aumFive;
	}
	public void setAumFive(Double aumFive) {
		this.aumFive = aumFive;
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
	public Double getNetworthfive() {
		return networthfive;
	}
	public void setNetworthfive(Double networthfive) {
		this.networthfive = networthfive;
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
	public Double getRoeFive() {
		return roeFive;
	}
	public void setRoeFive(Double roeFive) {
		this.roeFive = roeFive;
	}
	public Double getPbvOne() {
		return pbvOne;
	}
	public void setPbvOne(Double pbvOne) {
		this.pbvOne = pbvOne;
	}
	public Double getPbvTwo() {
		return pbvTwo;
	}
	public void setPbvTwo(Double pbvTwo) {
		this.pbvTwo = pbvTwo;
	}
	public Double getPbvThree() {
		return pbvThree;
	}
	public void setPbvThree(Double pbvThree) {
		this.pbvThree = pbvThree;
	}
	public Double getPbvFour() {
		return pbvFour;
	}
	public void setPbvFour(Double pbvFour) {
		this.pbvFour = pbvFour;
	}
	public Double getPbvFive() {
		return pbvFive;
	}
	public void setPbvFive(Double pbvFive) {
		this.pbvFive = pbvFive;
	}
	public Double getPeOne() {
		return peOne;
	}
	public void setPeOne(Double peOne) {
		this.peOne = peOne;
	}
	public Double getPeTwo() {
		return peTwo;
	}
	public void setPeTwo(Double peTwo) {
		this.peTwo = peTwo;
	}
	public Double getPeThree() {
		return peThree;
	}
	public void setPeThree(Double peThree) {
		this.peThree = peThree;
	}
	public Double getPeFour() {
		return peFour;
	}
	public void setPeFour(Double peFour) {
		this.peFour = peFour;
	}
	public Double getPeFive() {
		return peFive;
	}
	public void setPeFive(Double peFive) {
		this.peFive = peFive;
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
	        FinancialSummaryData financialSummaryData = (FinancialSummaryData) o;
	        if (financialSummaryData.getId() == null || getId() == null) {
	            return false;
	        }
	        return Objects.equals(getId(), financialSummaryData.getId());
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hashCode(getId());
	    }

}
