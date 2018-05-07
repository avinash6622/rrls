package com.unify.rrls.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "user_delegation_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDelegationAudit implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="login_name")
    private String loginName;

    @Column(name="opp_name")
    private String oppName;

    @Column(name="delegated_user_name")
    private String deleUserName;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();

    @Column(name="created_by")
    private String cretedBy;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOppName() {
        return oppName;
    }

    public void setOppName(String oppName) {
        this.oppName = oppName;
    }

    public String getDeleUserName() {
        return deleUserName;
    }

    public void setDeleUserName(String deleUserName) {
        this.deleUserName = deleUserName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCretedBy() {
        return cretedBy;
    }

    public void setCretedBy(String cretedBy) {
        this.cretedBy = cretedBy;
    }
}
