package com.unify.rrls.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    
    @Transient
    @JsonProperty
	private List<FileUploadComments> fileUploadCommentList;	

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
    @JsonIgnore
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

    public List<FileUploadComments> getFileUploadCommentList() {
		return fileUploadCommentList;
	}
    
    public void setFileUploadCommentList(List<FileUploadComments> fileUploadCommentList) {
		this.fileUploadCommentList = fileUploadCommentList;
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
