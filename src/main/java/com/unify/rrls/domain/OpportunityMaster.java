package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
import sun.plugin.util.UserProfile;

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
    private OpportunityMasterContact opportunityMasterContact;

	//private List<StrategyMaster> selectedStrategyMaster=new ArrayList<StrategyMaster>();

	@OneToMany(mappedBy = "opportunityMasterId")
	private List<FileUpload> fileUploads;

	@ManyToOne
	@JoinColumn(name = "master_name")
	private OpportunityName masterName;


 /*   @OneToOne(fetch = FetchType.LAZY,
        cascade =  CascadeType.ALL,
        mappedBy = "OpportunityMasterID")
    private OpportunityMasterContact opportunityMasterContact;*/

	@OneToMany(mappedBy = "strategyMaster",cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
	private List<StrategyMapping> strategyMapping;
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

	public void setFileUploadCommentList(List<FileUploadComments> fileUploadCommentList) {
		this.fileUploadCommentList = fileUploadCommentList;
	}

	public OpportunityName getMasterName() {
		return masterName;
	}
	public void setMasterName(OpportunityName masterName) {
		this.masterName = masterName;
	}

	public List<StrategyMapping> getStrategyMapping() {
		return strategyMapping;
	}
	public void setStrategyMapping(List<StrategyMapping> strategyMapping) {
		this.strategyMapping = strategyMapping;
	}

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


    public OpportunityMasterContact getOpportunityMasterContact() {
        return opportunityMasterContact;
    }

    public void setOpportunityMasterContact(OpportunityMasterContact opportunityMasterContact) {
        this.opportunityMasterContact = opportunityMasterContact;
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
            ", htmlContent='" + htmlContent + '\'' +
            ", fileUploadCommentList=" + fileUploadCommentList +
            ", selectedStrategyMaster=" + selectedStrategyMaster +
            ", opportunityMasterContact=" + opportunityMasterContact +
            ", masterName=" + masterName +
            ", strategyMapping=" + strategyMapping +
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
