package com.unify.rrls.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "due_diligence")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DueDiligence extends AbstractAuditingEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="subject")
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "file_name")
    private String fileName;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "file_data")
    private String fileData;

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    @Column(name="file_format_type")
    private String filetype;


    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
    @ManyToOne
    @JoinColumn(name = "opportunity_master_id")
    private OpportunityMaster opportunityMasterId;

    public OpportunityMaster getOpportunityMasterId() {
        return opportunityMasterId;
    }

    public void setOpportunityMasterId(OpportunityMaster opportunityMasterId) {
        this.opportunityMasterId = opportunityMasterId;
    }

}



