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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * A FileUpload.
 */
@Entity
@Table(name = "ra_file_upload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileUpload extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 65)
    @Column(name = "file_name", length = 65)
    private String fileName;


    @Column(name = "file_data")
    private String fileData;

    @Column(name = "file_data_content_type")
    private String fileDataContentType;

    @Column(name = "add_file_flag", length = 10)
    private Integer addFileFlag;

    @Column(name = "file_status")
    private String fileStatus;

    @Column(name="file_format_type")
    private String filetype;

    @Transient
	@JsonProperty
	private String htmlContent;

   /* @Size(max = 100)
    @Column(name = "created_by", length = 100)
    private String createdBy;

    @Size(max = 100)
    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @NotNull
    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;
*/
    @ManyToOne
    @JoinColumn(name = "opportunity_master_id")
    private OpportunityMaster opportunityMasterId;


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileUpload id(Long id) {
        this.id = id;
        return this;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getFileName() {
        return fileName;
    }

    public FileUpload fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public FileUpload fileData(String fileData) {
        this.fileData = fileData;
        return this;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileDataContentType() {
        return fileDataContentType;
    }

    public FileUpload fileDataContentType(String fileDataContentType) {
        this.fileDataContentType = fileDataContentType;
        return this;
    }

    public void setFileDataContentType(String fileDataContentType) {
        this.fileDataContentType = fileDataContentType;
    }

    public Integer getAddFileFlag() {
        return addFileFlag;
    }

    public FileUpload addFileFlag(Integer addFileFlag) {
        this.addFileFlag = addFileFlag;
        return this;
    }

    public void setAddFileFlag(Integer addFileFlag) {
        this.addFileFlag = addFileFlag;
    }

    public String getHtmlContent() {
		return htmlContent;
	}
    public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

  /*  public String getCreatedBy() {
        return createdBy;
    }

    public FileUpload createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public FileUpload updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public FileUpload createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public FileUpload updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }
*/
    public OpportunityMaster getOpportunityMasterId() {
        return opportunityMasterId;
    }

    public FileUpload opportunityMasterId(OpportunityMaster OpportunityMaster) {
        this.opportunityMasterId = OpportunityMaster;
        return this;
    }

    public void setOpportunityMasterId(OpportunityMaster OpportunityMaster) {
        this.opportunityMasterId = OpportunityMaster;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileUpload fileUpload = (FileUpload) o;
        if (fileUpload.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileUpload.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
/*
    @Override
    public String toString() {
        return "FileUpload{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileData='" + getFileData() + "'" +
            ", fileDataContentType='" + fileDataContentType + "'" +
            ", addFileFlag='" + getAddFileFlag() + "'" +
            "}";
    }*/
}
