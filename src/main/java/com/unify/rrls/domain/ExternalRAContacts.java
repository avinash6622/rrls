package com.unify.rrls.domain;

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
@Table(name = "exra_contacts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExternalRAContacts {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "external_ra_id")
	private ExternalResearchAnalyst externalResearchAnalyst;
	
	@Column(name="contact_no")
	private String ContactNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExternalResearchAnalyst getExternalResearchAnalyst() {
		return externalResearchAnalyst;
	}

	public void setExternalResearchAnalyst(ExternalResearchAnalyst externalResearchAnalyst) {
		this.externalResearchAnalyst = externalResearchAnalyst;
	}

	public String getContactNo() {
		return ContactNo;
	}

	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
	}
	
	
}
