package com.unify.rrls.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "delete_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeleteNotification {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "history_log_id")
    private Integer notiId;
    @Column(name = "status")
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNotiId() {
        return notiId;
    }

    public void setNotiId(Integer notiId) {
        this.notiId = notiId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteNotification that = (DeleteNotification) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(notiId, that.notiId) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, notiId, status);
    }

    @Override
    public String toString() {
        return "DeleteNotification{" +
            "id=" + id +
            ", userId=" + userId +
            ", notiId=" + notiId +
            ", status='" + status + '\'' +
            '}';
    }
}
