package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A OpportunityMaster.
 */
@Entity
@Table(name = "opportunity_master")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpportunityMaster extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "opp_description")
	private String oppDescription;

	@Column(name = "opp_status")
	private String oppStatus;

	@Column(name = "description")
    private String statusDes;

	@Column(name = "status")
    private String status;


	@Transient
	@JsonProperty
	private String htmlContent;

	@Transient
	@JsonProperty
	private List<FileUploadComments> fileUploadCommentList;


	@Transient
	@JsonProperty
	private List<StrategyMaster> selectedStrategyMaster;

	@Transient
    @JsonProperty
    private StrategyMapping strategyMapping;

	/*@OneToMany(mappedBy = "opportunityMasterId",cascade={CascadeType.PERSIST},fetch = FetchType.EAGER)*/
	@Transient
    @JsonProperty
    private List<OpportunityMasterContact> selectedoppContanct;

	@Transient
    @JsonProperty
    private OpportunitySummaryData opportunitySummaryData;

	@Transient
    @JsonProperty
    private FinancialSummaryData financialSummaryData;


    @Transient
    @JsonProperty
    private NonFinancialSummaryData nonFinancialSummaryData;

   	//private List<StrategyMaster> selectedStrategyMaster=new ArrayList<StrategyMaster>();

	@OneToMany(mappedBy = "opportunityMasterId")
	private List<FileUpload> fileUploads;
	
	@OneToMany(mappedBy = "opportunityMasterId")
	private List<CommunicationLetters> communicationLetters;
	
	@OneToMany(mappedBy = "opportunityMasterId")
	private List<ExternalRAFileUpload> externalRAFileUpload;

	@ManyToOne
	@JoinColumn(name = "master_name")
	private OpportunityName masterName;

	 @OneToMany(mappedBy="opportunityMaster")
	 private List<OpportunityQuestion> opportunityQuestions;

	 @Transient
	 @JsonProperty
	 private Integer decimalPoint;
	 
	 @Transient
	 @JsonProperty
	 private String oppName;

 /*   @OneToOne(fetch = FetchType.LAZY,
        cascade =  CascadeType.ALL,
        mappedBy = "OpportunityMasterID")
    private OpportunityMasterContact opportunityMasterContact;*/

	public List<OpportunityQuestion> getOpportunityQuestions() {
		return opportunityQuestions;
	}

	public void setOpportunityQuestions(List<OpportunityQuestion> opportunityQuestions) {
		this.opportunityQuestions = opportunityQuestions;
	}

	/*@OneToMany(mappedBy = "strategyMaster",cascade={CascadeType.PERSIST},fetch = FetchType.EAGER)
	private List<StrategyMapping> strategyMapping;*/
	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOppDescription() {
		return oppDescription;
	}

	public OpportunityMaster oppDescription(String oppDescription) {
		this.oppDescription = oppDescription;
		return this;
	}

	public void setOppDescription(String oppDescription) {
		this.oppDescription = oppDescription;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public List<FileUpload> getFileUploads() {
		return fileUploads;
	}

	public void setFileUploads(List<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}

	public List<FileUploadComments> getFileUploadCommentList() {
		return fileUploadCommentList;
	}

	public String getOppName() {
		return oppName;
	}

	public void setOppName(String oppName) {
		this.oppName = oppName;
	}

	public void setFileUploadCommentList(List<FileUploadComments> fileUploadCommentList) {
		this.fileUploadCommentList = fileUploadCommentList;
	}

	public OpportunityName getMasterName() {
		return masterName;
	}
	public void setMasterName(OpportunityName masterName) {
		this.masterName = masterName;
	}

	/*public List<StrategyMapping> getStrategyMapping() {
		return strategyMapping;
	}
	public void setStrategyMapping(List<StrategyMapping> strategyMapping) {
		this.strategyMapping = strategyMapping;
	}*/

	public void setSelectedStrategyMaster(List<StrategyMaster> selectedStrategyMaster) {
		this.selectedStrategyMaster = selectedStrategyMaster;
	}
	public List<StrategyMaster> getSelectedStrategyMaster() {
		return selectedStrategyMaster;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters
	// and setters here, do not remove
	public String getOppStatus() {
		return oppStatus;
	}
	public void setOppStatus(String oppStatus) {
		this.oppStatus = oppStatus;
	}

    public List<OpportunityMasterContact> getSelectedoppContanct() {
        return selectedoppContanct;
    }

    public void setSelectedoppContanct(List<OpportunityMasterContact> selectedoppContanct) {
        this.selectedoppContanct = selectedoppContanct;
    }


  public OpportunitySummaryData getOpportunitySummaryData() {
        return opportunitySummaryData;
    }

    public void setOpportunitySummaryData(OpportunitySummaryData opportunitySummaryData) {
        this.opportunitySummaryData = opportunitySummaryData;
    }

    public FinancialSummaryData getFinancialSummaryData() {
        return financialSummaryData;
    }

    public void setFinancialSummaryData(FinancialSummaryData financialSummaryData) {
        this.financialSummaryData = financialSummaryData;
    }

    public NonFinancialSummaryData getNonFinancialSummaryData() {
        return nonFinancialSummaryData;
    }

    public void setNonFinancialSummaryData(NonFinancialSummaryData nonFinancialSummaryData) {
        this.nonFinancialSummaryData = nonFinancialSummaryData;
    }

    public Integer getDecimalPoint() {
		return decimalPoint;
	}

	public void setDecimalPoint(Integer decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
	
	public List<CommunicationLetters> getCommunicationLetters() {
		return communicationLetters;
	}

	public void setCommunicationLetters(List<CommunicationLetters> communicationLetters) {
		this.communicationLetters = communicationLetters;
	}

	public List<ExternalRAFileUpload> getExternalRAFileUpload() {
		return externalRAFileUpload;
	}

	public void setExternalRAFileUpload(List<ExternalRAFileUpload> externalRAFileUpload) {
		this.externalRAFileUpload = externalRAFileUpload;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		OpportunityMaster opportunityMaster = (OpportunityMaster) o;
		if (opportunityMaster.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), opportunityMaster.getId());
	}


    @Override
    public String toString() {
        return "OpportunityMaster{" +
            "id=" + id +
            ", oppDescription='" + oppDescription + '\'' +
            ", oppStatus='" + oppStatus + '\'' +
            ", statusDes='" + statusDes + '\'' +
            ", status='" + status + '\'' +
            ", masterName=" + masterName +
            '}';
    }

    @Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }
}
