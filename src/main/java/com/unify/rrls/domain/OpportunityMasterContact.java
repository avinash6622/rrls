package com.unify.rrls.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "opportunity_master_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OpportunityMasterContact  extends AbstractAuditingEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "contact_name")
     private String name;

    @Column(name = "contact_designation")
     private String designation;

    @Column(name = "contact_email")
    private String email;


    @Column(name = "contact_contact_number")
     private String contactnum;


    @ManyToOne
    @JoinColumn(name = "opportunity_master_id")
    private OpportunityMaster opportunityMasterId;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactnum() {
        return contactnum;
    }

    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }

    public OpportunityMaster getOpportunityMasterId() {
        return opportunityMasterId;
    }

    public void setOpportunityMasterId(OpportunityMaster opportunityMasterId) {
        this.opportunityMasterId = opportunityMasterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpportunityMasterContact that = (OpportunityMasterContact) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(email, that.email) &&
            Objects.equals(contactnum, that.contactnum) &&
            Objects.equals(opportunityMasterId, that.opportunityMasterId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, designation, email, contactnum, opportunityMasterId);
    }

    @Override
    public String toString() {
        return "OpportunityMasterContact{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", designation='" + designation + '\'' +
            ", email='" + email + '\'' +
            ", contactnum='" + contactnum + '\'' +
            ", opportunityMaster=" + opportunityMasterId +
            '}';
    }
}

