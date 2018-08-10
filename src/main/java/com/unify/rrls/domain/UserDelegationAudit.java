package com.unify.rrls.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_delegation_audit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserDelegationAudit extends AbstractAuditingEntity implements Serializable {
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
    
    @Transient
    @JsonProperty
    private String fromName;
    
    @Transient
    @JsonProperty
    private List<OpportunityMaster> selectedOpportunity;


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
    
    public String getFromName() {
		return fromName;
	}
    
    public void setFromName(String fromName) {
		this.fromName = fromName;
	}
    public List<OpportunityMaster> getSelectedOpportunity() {
		return selectedOpportunity;
	}
    public void setSelectedOpportunity(List<OpportunityMaster> selectedOpportunity) {
		this.selectedOpportunity = selectedOpportunity;
	}

}
