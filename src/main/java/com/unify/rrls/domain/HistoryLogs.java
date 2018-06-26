package com.unify.rrls.domain;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @Column(name="user_id")
    private Long userId;
    @Column(name="opp_id")
    private Long oppId;
    @Column(name="comments_id")
    private Long commentsId;
    @Column(name="questions_id")
    private Long questionsId;

    @Transient
    @JsonProperty
    String dStatus;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public Long getOppId() {
        return oppId;
    }

    public void setOppId(Long oppId) {
        this.oppId = oppId;
    }    

    public Long getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(Long commentsId) {
		this.commentsId = commentsId;
	}

	public Long getQuestionsId() {
		return questionsId;
	}

	public void setQuestionsId(Long questionsId) {
		this.questionsId = questionsId;
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
            Objects.equals(fileNamecontent, that.fileNamecontent) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(oppId, that.oppId) &&
            Objects.equals(dStatus, that.dStatus) &&
            Objects.equals(commentsId, that.commentsId) &&
            Objects.equals(questionsId, that.questionsId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, oppname, createdBy, lastModifiedBy, createdDate, action, page, fileNamecontent, userId, oppId, dStatus,commentsId,questionsId);
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
            ", userId=" + userId +
            ", oppId=" + oppId +
            ", dStatus='" + dStatus + '\'' +
            ", commentsId='" + commentsId + '\'' +
            ", questionsId='" + questionsId + '\'' +
            '}';
    }
}
