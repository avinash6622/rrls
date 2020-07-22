package com.unify.rrls.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "brochure_file_upload")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BrochureFileUpload {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 300)
    @Column(name = "file_name", length = 300)
    private String fileName;


    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_content_type")
    private String fileContentType;


    @Column(name = "file_status")
    private String fileStatus;

    @Column(name = "file_desc")
    private String fileDesc;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_date")
    private Instant createdDate;

    @Column(name = "last_modified_by")
    private String lastmodifiedBy;

    @Column(name="last_modified_date")
    private Instant lastModifiedDate;




    @OneToMany(mappedBy = "brochureFileUpload",cascade={CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<BrochureSupportingFiles> brochureSupportingFiles;


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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastmodifiedBy() {
        return lastmodifiedBy;
    }

    public void setLastmodifiedBy(String lastmodifiedBy) {
        this.lastmodifiedBy = lastmodifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<BrochureSupportingFiles> getBrochureSupportingFiles() {
        return brochureSupportingFiles;
    }

    public void setBrochureSupportingFiles(List<BrochureSupportingFiles> brochureSupportingFiles) {
        this.brochureSupportingFiles = brochureSupportingFiles;
    }
}