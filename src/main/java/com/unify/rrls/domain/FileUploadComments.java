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
 * A FileUploadComments.
 */
@Entity
@Table(name = "file_upload_comments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileUploadComments extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 400)
    @Column(name = "file_comments", length = 400)
    private String fileComments;  

    @ManyToOne
    @JoinColumn(name="ra_file_upload_id")
    private FileUpload fileUploadId;

    @ManyToOne
    @JoinColumn(name="add_file_upload_id")
    private AdditionalFileUpload addFileUploadID;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileUploadComments id(Long id) {
        this.id = id;
        return this;
    }

    public String getFileComments() {
        return fileComments;
    }

    public FileUploadComments fileComments(String fileComments) {
        this.fileComments = fileComments;
        return this;
    }

    public void setFileComments(String fileComments) {
        this.fileComments = fileComments;
    }

    public FileUpload getFileUploadId() {
        return fileUploadId;
    }

    public FileUploadComments fileUploadId(FileUpload FileUpload) {
        this.fileUploadId = FileUpload;
        return this;
    }

    public void setFileUploadId(FileUpload FileUpload) {
        this.fileUploadId = FileUpload;
    }

    public AdditionalFileUpload getAddFileUploadID() {
        return addFileUploadID;
    }

    public FileUploadComments addFileUploadID(AdditionalFileUpload AdditionalFileUpload) {
        this.addFileUploadID = AdditionalFileUpload;
        return this;
    }

    public void setAddFileUploadID(AdditionalFileUpload AdditionalFileUpload) {
        this.addFileUploadID = AdditionalFileUpload;
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
        FileUploadComments fileUploadComments = (FileUploadComments) o;
        if (fileUploadComments.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileUploadComments.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileUploadComments{" +
            "id=" + getId() +
            ", fileComments='" + getFileComments() + "'" +           
            "}";
    }
}
