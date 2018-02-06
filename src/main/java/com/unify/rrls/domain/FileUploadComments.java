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
@Table(name = "opportunity_master_comments")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileUploadComments extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 400)
    @Column(name = "opportunity_comments", length = 400)
    private String opportunityComments;  

    @ManyToOne
    @JoinColumn(name="opportunity_master_id")
    private OpportunityMaster opportunityMaster;

    
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

    public String getOpportunityComments() {
        return opportunityComments;
    }

    public FileUploadComments opportunityComments(String opportunityComments) {
        this.opportunityComments = opportunityComments;
        return this;
    }

    public void setFileUploadComments(String opportunityComments) {
        this.opportunityComments = opportunityComments;
    }

    public OpportunityMaster getOpportunityMaster() {
        return opportunityMaster;
    }

    public FileUploadComments opportunityMaster(OpportunityMaster opportunityMaster) {
        this.opportunityMaster = opportunityMaster;
        return this;
    }

    public void setOpportunityMaster(OpportunityMaster opportunityMaster) {
        this.opportunityMaster = opportunityMaster;
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
            ", opportunityComments='" + getOpportunityComments() + "'" +           
            "}";
    }
}
