package com.unify.rrls.domain;

import java.io.Serializable;
import java.util.List;

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
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "external_file_upload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalRAFileUpload extends AbstractAuditingEntity implements Serializable {
	
	   	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   
	    @Column(name = "file_name", length = 65)
	    private String fileName;

	    @Column(name = "file_data")
	    private String fileData;

	    @Column(name="file_format_type")
	    private String filetype;
	  
	    @ManyToOne
	    @JoinColumn(name = "opportunity_master_id")
	    private OpportunityMaster opportunityMasterId;
	    
	    @ManyToOne
	    @JoinColumn(name = "external_ra_id")
	    private ExternalResearchAnalyst externalResearchAnalyst;
	    
	    
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileData() {
			return fileData;
		}

		public void setFileData(String fileData) {
			this.fileData = fileData;
		}

		public String getFiletype() {
			return filetype;
		}

		public void setFiletype(String filetype) {
			this.filetype = filetype;
		}

		public OpportunityMaster getOpportunityMasterId() {
			return opportunityMasterId;
		}

		public void setOpportunityMasterId(OpportunityMaster opportunityMasterId) {
			this.opportunityMasterId = opportunityMasterId;
		}

		public ExternalResearchAnalyst getExternalResearchAnalyst() {
			return externalResearchAnalyst;
		}

		public void setExternalResearchAnalyst(ExternalResearchAnalyst externalResearchAnalyst) {
			this.externalResearchAnalyst = externalResearchAnalyst;
		}
	

}
