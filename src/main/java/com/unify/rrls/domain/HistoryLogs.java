package com.unify.rrls.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
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
    @Column(name = "name")
    private String oppname;

    @Column(name = "opp_created_by")
    private String createdBy;
    @Column(name = "opp_last_modified_by")
    private String lastModifiedBy;
    @Column(name = "opp_created_date")
    private Instant createdDate ;
    @Column(name = "Action")
    private String action;
    @Column(name = "page")
    private String page;
    @Column(name="sub_content")
    private String fileNamecontent;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOppname() {
        return oppname;
    }

    public void setOppname(String oppname) {
        this.oppname = oppname;
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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getFileNamecontent() {
        return fileNamecontent;
    }

    public void setFileNamecontent(String fileNamecontent) {
        this.fileNamecontent = fileNamecontent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryLogs that = (HistoryLogs) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(oppname, that.oppname) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(action, that.action) &&
            Objects.equals(page, that.page) &&
            Objects.equals(fileNamecontent, that.fileNamecontent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, oppname, createdBy, lastModifiedBy, createdDate, action, page, fileNamecontent);
    }

    @Override
    public String toString() {
        return "HistoryLogs{" +
            "id=" + id +
            ", oppname='" + oppname + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", createdDate=" + createdDate +
            ", action='" + action + '\'' +
            ", page='" + page + '\'' +
            ", fileNamecontent='" + fileNamecontent + '\'' +
            '}';
    }
}
