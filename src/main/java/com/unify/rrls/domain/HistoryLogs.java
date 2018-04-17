package com.unify.rrls.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "history_logs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HistoryLogs {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "opp_id")
    private OpportunityMaster oppId;
    @Column(name = "opp_created_by")
    private String createdBy;
    @Column(name = "opp_last_modified_by")
    private String lastModifiedBy;
    @Column(name = "opp_created_date")
    private Date createdDate ;
    @Column(name = "Action")
    private String action;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OpportunityMaster getOppId() {
        return oppId;
    }

    public void setOppId(OpportunityMaster oppId) {
        this.oppId = oppId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryLogs that = (HistoryLogs) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(oppId, that.oppId) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, oppId, createdBy, lastModifiedBy, createdDate, action);
    }

    @Override
    public String toString() {
        return "HistoryLogs{" +
            "id=" + id +
            ", oppId=" + oppId +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", createdDate=" + createdDate +
            ", action='" + action + '\'' +
            '}';
    }
}
