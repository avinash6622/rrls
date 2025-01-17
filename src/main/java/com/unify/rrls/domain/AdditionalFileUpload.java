package com.unify.rrls.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AdditionalFileUpload.
 */
@Entity
@Table(name = "add_file_upload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdditionalFileUpload extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "file_name", length = 100)
    private String fileName;

    @Lob
    @Column(name = "file_data")
    private String fileData;

    @Column(name = "file_data_content_type")
    private String fileDataContentType;

    @ManyToOne
    @JoinColumn(name="ra_file_upload_id")
    private FileUpload fileUploadID;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdditionalFileUpload id(Long id) {
        this.id = id;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public AdditionalFileUpload fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public AdditionalFileUpload fileData(String fileData) {
        this.fileData = fileData;
        return this;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileDataContentType() {
        return fileDataContentType;
    }

    public AdditionalFileUpload fileDataContentType(String fileDataContentType) {
        this.fileDataContentType = fileDataContentType;
        return this;
    }

    public void setFileDataContentType(String fileDataContentType) {
        this.fileDataContentType = fileDataContentType;
    }

    public FileUpload getFileUploadID() {
        return fileUploadID;
    }

    public AdditionalFileUpload fileUploadID(FileUpload FileUpload) {
        this.fileUploadID = FileUpload;
        return this;
    }

    public void setFileUploadID(FileUpload FileUpload) {
        this.fileUploadID = FileUpload;
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
        AdditionalFileUpload additionalFileUpload = (AdditionalFileUpload) o;
        if (additionalFileUpload.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), additionalFileUpload.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdditionalFileUpload{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileData='" + getFileData() + "'" +
            ", fileDataContentType='" + fileDataContentType + "'}";
    }
}
