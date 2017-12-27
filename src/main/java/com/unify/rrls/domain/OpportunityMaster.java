package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

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

	/*@Column(name = "opp_name")
	private String oppName;*/

	@Column(name = "opp_description")
	private String oppDescription;

	@Transient
	@JsonProperty
	private String htmlContent;

	@Transient
	@JsonProperty
	private List<FileUploadComments> fileUploadCommentList;	
	
	
	@Transient
	@JsonProperty
	private List<StrategyMaster> selectedStrategyMaster;
	
	@ManyToOne
	@JoinColumn(name = "strategy_master_id")
	private StrategyMaster strategyMasterId;

	@OneToMany(mappedBy = "opportunityMasterId")
	private List<FileUpload> fileUploads;
	
	@ManyToOne
	@JoinColumn(name = "master_name")
	private OpportunityName masterName;
	
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

	/*public String getOppName() {
		return oppName;
	}

	public OpportunityMaster oppName(String oppName) {
		this.oppName = oppName;
		return this;
	}

	public void setOppName(String oppName) {
		this.oppName = oppName;
	}*/

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

	public StrategyMaster getStrategyMasterId() {
		return strategyMasterId;
	}

	public OpportunityMaster strategyMasterId(StrategyMaster strategyMasterId) {
		this.strategyMasterId = strategyMasterId;
		return this;
	}

	public void setStrategyMasterId(StrategyMaster strategyMasterId) {
		this.strategyMasterId = strategyMasterId;
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
	public int hashCode() {
		return Objects.hashCode(getId());
	}

}
